package com.example.madcamp2_fe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madcamp2_fe.Login.LoginRepository
import com.example.madcamp2_fe.Login.LoginResponse
import kotlinx.coroutines.launch

class UserViewModel(): ViewModel() {
    private var loginResponse = MutableLiveData<LoginResponse>()

    fun event(token : String) {
        viewModelScope.launch() {
            kotlin.runCatching {
                LoginRepository.fetchUser(token)
            }.onSuccess {
                loginResponse.value = it
            }.onFailure {
                Log.d("viewmodel login 실패", "실패!")
            }
        }
    }
    fun getResponse() : LoginResponse? {
        return loginResponse.value
    }

}