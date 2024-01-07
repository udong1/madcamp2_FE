package com.example.madcamp2_fe.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.nicknameEdit.hint = walkViewModel.getUserName()
        binding.emailEdit.text = walkViewModel.getUserEmail()
        binding.cancel.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.confirm.setOnClickListener{
            //백엔드로 posting
            walkViewModel.setUserInfo(nameEdit, walkViewModel.getUserEmail(), walkViewModel.getUserAccessToken(), walkViewModel.getUserIsRegistered())
            walkViewModel.setUserProfile(imgEdit)
        }
        binding.imageUpdateButton.setOnClickListener{
            //갤러리로 연결
            //뷰모델로 연결
            startGallery()

        }
    }
    //갤러리 접근
    private fun startGallery(){
        val readPermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val writePermission = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_GALLERY)
        }
        else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
//            imageResult.launch(intent)
        }
    }
//    private val imageResult = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ){  result ->
//        if(result.resultCode == RESULT_OK){
//            val imageUri = result.data?.data
//            imageUri?.let{
//                imageFile = File(getRealPathFromUri(it))
//                Glide.with(this)
//                    .load(imageUri)
//                    .into(binding.profile)
//            }
//        }
//
//    }
//    private fun getRealPathFromUri(uri: Uri):String{
//        val buildName = Build.MANUFACTURER
//        if(buildName.equals("Xiaomi")){
//            return uri.path!!
//        }
//        var columnIndex = 0
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
////        val cursor = contentResolver.query(uri, proj, null, null, null)
//        if (cursor!!.moveToFirst()){
//            columnIndex = cursor.getColumnIndexOrThrow
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() : ProfileEditFragment {
            return ProfileEditFragment()
        }

        const val REQ_GALLERY = 1
    }
}