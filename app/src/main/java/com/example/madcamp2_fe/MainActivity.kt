package com.example.madcamp2_fe


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.madcamp2_fe.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.Constants.TAG
import com.kakao.sdk.user.UserApiClient


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KakaoSdk.init(this, "710f05baaf03d564cb1cb51f782e5c03")

        //로그인 안된 경우에만 loginfragment를 불러오도록 변경할 것
        val login = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.display, login)
            .addToBackStack(null)
            .commit()







    }

}