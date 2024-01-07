package com.example.madcamp2_fe.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentHomeBinding
import com.example.madcamp2_fe.databinding.FragmentProfileEditBinding
import java.io.File

class ProfileEditFragment : Fragment() {

    private var _binding : FragmentProfileEditBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private val binding get() = _binding!!
    private lateinit var userSelectImg : Uri
    private lateinit var pickMedia :ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("GALLERY_REQUEST"){ _, bundle ->
            val result:Uri? = bundle.getParcelable("GALLERY_RESULT")
            userSelectImg = result!!
            Glide.with(this)
                .load(result)
                .into(binding.profile)
            Log.d("gallery","gallery called with $result")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ result ->
            val bundle = Bundle().apply{
                putParcelable("GALLERY_RESULT", result)
            }
            if(result!=null)
            {
                setFragmentResult("GALLERY_REQUEST", bundle)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nameEdit : String = ""

        Glide.with(this)
            .load(R.drawable.background)
            .into(binding.background)
        Glide.with(this)
            .load(walkViewModel.getUserProfileImg())
            .into(binding.profile)
        Glide.with(this)
            .load(R.drawable.white_background)
            .into(binding.lowerPartBack)

        binding.nicknameEdit.text = Editable.Factory.getInstance().newEditable(walkViewModel.getUserName())
        binding.emailEdit.text = walkViewModel.getUserEmail()
        binding.cancel.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.confirm.setOnClickListener{
            //백엔드로 posting
            //뷰모델 업데이트
            walkViewModel.changeUserName(nameEdit)
            walkViewModel.setUserProfile(userSelectImg.toString())
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.imageUpdateButton.setOnClickListener{
            //갤러리로 연결
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() : ProfileEditFragment {
            return ProfileEditFragment()
        }
    }
}