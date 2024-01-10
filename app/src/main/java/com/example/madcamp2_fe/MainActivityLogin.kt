package com.example.madcamp2_fe


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.madcamp2_fe.login.LoginRequest
import com.example.madcamp2_fe.databinding.ActivityMainBinding
import com.example.madcamp2_fe.login.LoginResponse
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient


class MainActivityLogin : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var loginInfo : MutableLiveData<LoginResponse> = MutableLiveData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KakaoSdk.init(this,"710f05baaf03d564cb1cb51f782e5c03")

//        로그인 안된 경우에는 login 시작
        binding.connectKakao.setOnClickListener{
            kakaoLoginRequest()
        }
        loginInfo.observe(this, Observer {

            val intent = Intent(this, WalkActivity::class.java)
            if (loginInfo.value!!.accessToken != ""){
                intent.putExtra("nickname", loginInfo.value!!.nickname)
                intent.putExtra("email", loginInfo.value!!.email)
                intent.putExtra("accessToken", loginInfo.value!!.accessToken)
                intent.putExtra("isRegistered", loginInfo.value!!.isRegistered)
                intent.putExtra("profileImg", loginInfo.value!!.profileImg)
                if(loginInfo.value!!.walkCount != null){
                    intent.putExtra("walkCount",loginInfo.value!!.walkCount)
                }
                else{
                    intent.putExtra("walkCount",0L)
                }
                Log.d("observe", "move to walkActivity")
                startActivity(intent)
                finish()
            }
        })


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
        var loginRequest = LoginRequest("")

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(Constants.TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(Constants.TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                //transaction
                loginRequest.token = token.accessToken
                Log.d("before Manager", loginRequest.token)
                UserClientManager.instance.login(loginRequest= loginRequest, completion = {
                        responseState, responseBody ->
                    when(responseState){
                        RESPONSE_STATE.OKAY ->{
                            Log.d(TAG, "로그인 성공 : $responseBody")
                            loginInfo.value = responseBody
                        }
                        RESPONSE_STATE.FAIL ->{
                            Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
//                UserApiClient.instance.me{
//                        user, error ->
//                    if(error != null){
//                        Log.e(Constants.TAG, "사용자 정보 요청 실패", error)
//                    }
//                    else if (user !=null){
//                        Log.i(Constants.TAG, "사용자 정보 요청 성공" + "\n이메일:${user.kakaoAccount?.email}")
//                    }
//                }
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
                    loginRequest.token = token.accessToken

                    Log.d("last", loginRequest.token)
                    UserClientManager.instance.login(loginRequest = loginRequest, completion = {
                            responseState, responseBody ->
                        when(responseState){
                            RESPONSE_STATE.OKAY ->{
                                Log.d(TAG, "로그인 성공 : $responseBody")
                                loginInfo.value = responseBody
                            }
                            RESPONSE_STATE.FAIL ->{
                                Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }

}