package com.example.madcamp2_fe.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
//    private val loginFlow = MutableStateFlow<LoginResponse?>(null)
//
//    fun event(loginRequest: LoginRequest) {
//        viewModelScope.launch() {
//            kotlin.runCatching {
//                LoginRepository.fetchUser(loginRequest)
//            }.onSuccess {
//                loginFlow.value = it
//                Log.d("loginFlow 성공", "${loginFlow.value}")
//            }.onFailure {
//                Log.d("loginFlow 실패", "${loginFlow.value}")
//            }
//        }
//    }
}