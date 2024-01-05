package com.example.madcamp2_fe


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.madcamp2_fe.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.UserApiClient


class MainActivityLogin : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val loginViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KakaoSdk.init(this,"710f05baaf03d564cb1cb51f782e5c03")

//        로그인 안된 경우에는 login 시작
        kakaoLoginRequest()



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
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(Constants.TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(
                        this,
                        callback = callback
                    )
                } else if (token != null) {
                    Log.i(Constants.TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
//
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
//            kakaoLogin = true
        }
    }

}