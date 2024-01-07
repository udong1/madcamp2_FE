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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    private lateinit var getResult : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nameEdit : String = ""
        var imgEdit : String = ""

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
            walkViewModel.changeUserName(nameEdit)
            walkViewModel.setUserProfile(imgEdit)
        }
        binding.imageUpdateButton.setOnClickListener{
            //갤러리로 연결
            //뷰모델로 연결
            addFromGallery()
        }
    }
    private fun addFromGallery(){

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