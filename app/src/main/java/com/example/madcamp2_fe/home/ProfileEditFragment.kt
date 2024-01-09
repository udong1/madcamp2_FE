package com.example.madcamp2_fe.home

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentProfileEditBinding
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ProfileEditFragment : Fragment() {

    private var _binding : FragmentProfileEditBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private val binding get() = _binding!!
    private lateinit var userSelectImg : Uri
    private var editImg = false
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
        editImg = false
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ result ->
            val bundle = Bundle().apply{
                putParcelable("GALLERY_RESULT", result)
            }
            if(result!=null)
            {
                setFragmentResult("GALLERY_REQUEST", bundle)
                binding.confirm.isEnabled = true
                editImg = true
                binding.confirm.setTextColor(Color.BLACK)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nameEdit : String = walkViewModel.getUserName()

        if(walkViewModel.getUserProfileImg()!="default"){
            Glide.with(this)
                .load(walkViewModel.getUserProfileImg())
                .into(binding.profile)
        }

        binding.nicknameEdit.text = Editable.Factory.getInstance().newEditable(walkViewModel.getUserName())
        binding.emailEdit.text = walkViewModel.getUserEmail()
        binding.cancel.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.confirm.setOnClickListener{
            //백엔드로 posting
            if (!editImg) {
//                userSelectImg = null
//                Log.d("userSelectImg", "$userSelectImg")
//                Log.d("walkviewmodel", walkViewModel.getUserProfileImg())
                UserClientManager.instance.updateProfile(
                    userName = RequestBody.create("text/plain".toMediaTypeOrNull(), nameEdit),
                    token = walkViewModel.getUserAccessToken(),
                    profileImg = null,
                    completion = {
                            responseState, responseBody ->
                        when(responseState){
                            RESPONSE_STATE.OKAY ->{
                                Log.d(Constants.TAG, "업데이트 성공 : $responseBody")
                                walkViewModel.changeUserName(responseBody.updatedNickname)
                                if(responseBody.updatedProfileImg == null){
                                    walkViewModel.setUserProfile(Uri.parse("file:///android_asset/default_image.png").toString())
                                    Log.d("default IMG", walkViewModel.getUserProfileImg())
                                }
                                else{
                                    walkViewModel.setUserProfile(responseBody.updatedProfileImg!!)
                                }
                                walkViewModel.setProfileChanged(true)
                            }
                            RESPONSE_STATE.FAIL ->{
                                //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                                Log.d(Constants.TAG, "없데이트")
                            }
                        }
                    })
            }
            else{
                UserClientManager.instance.updateProfile(
                    userName = RequestBody.create("text/plain".toMediaTypeOrNull(), nameEdit),
                    token = walkViewModel.getUserAccessToken(),
                    profileImg = uriToMultipartBodyPart(userSelectImg),
                    completion = {
                            responseState, responseBody ->
                        when(responseState){
                            RESPONSE_STATE.OKAY ->{
                                Log.d(Constants.TAG, "업데이트 성공 : $responseBody")
                                walkViewModel.changeUserName(responseBody.updatedNickname)
                                walkViewModel.setProfileChanged(true)
                            }
                            RESPONSE_STATE.FAIL ->{
                                //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                                Log.d(Constants.TAG, "없데이트")
                            }
                        }
                    })
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.imageUpdateButton.setOnClickListener{
            //갤러리로 연결
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.confirm.setTextColor(Color.LTGRAY)
        binding.confirm.isEnabled = false
        binding.nicknameEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                nameEdit = s.toString()
                if(walkViewModel.getUserName() == s.toString() || s.isNullOrBlank()){
                    binding.confirm.setTextColor(Color.LTGRAY)
                    binding.confirm.isEnabled = false
                }
                else{
                    binding.confirm.setTextColor(Color.BLACK)
                    binding.confirm.isEnabled = true
                }
            }

        })

    }
    private fun uriToMultipartBodyPart(uri: Uri): MultipartBody.Part {

        val inputStream: InputStream? = context?.contentResolver?.openInputStream(uri)
        val file: File = createTempFileFromInputStream(inputStream)
        val requestFile: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("profileImg", file.name, requestFile)

    }

    private fun createTempFileFromInputStream(inputStream: InputStream?): File {
        val tempFile = File.createTempFile("temp_image", null)
        tempFile.deleteOnExit()

        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
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