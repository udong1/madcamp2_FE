package com.example.madcamp2_fe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.madcamp2_fe.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.UserApiClient

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding?  = null
    private val binding get() = _binding!!
    private val userViewModel : UserViewModel by activityViewModels()
    companion object {
        fun newInstance() = LoginFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.connectKakao.setOnClickListener {
            kakaoTokenCheck()
            kakaoLoginRequest()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun kakaoTokenCheck(){
        UserApiClient.instance.accessTokenInfo{ tokenInfo, error ->
            if (error != null){
                Log.e(Constants.TAG, "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null){
                Log.i(Constants.TAG, "토큰 정보 보기 성공" +"\n토큰 정보 : $tokenInfo")
            }

        }
    }

    private fun kakaoLoginRequest(){
        val userId = binding.idBox.text
        val userPassword = binding.passwordBox.text


        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(Constants.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(Constants.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                UserApiClient.instance.me{
                        user, error ->
                    if(error != null){
                        Log.e(Constants.TAG, "사용자 정보 요청 실패", error)
                    }
                    else if (user !=null){
                        Log.i(Constants.TAG, "사용자 정보 요청 성공" + "\n이메일:${user.kakaoAccount?.email}")
                    }
                }
            }
        }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(Constants.TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        requireContext(),
                        callback = callback
                    )
                } else if (token != null) {
                    Log.i(Constants.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
//                    kakaoLogin = true
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
//            kakaoLogin = true
        }
    }


}