package com.example.madcamp2_fe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel(): ViewModel() {
    private var userEmail = MutableLiveData<String>()
    private var userName = MutableLiveData<String>()
    private var userProfile = MutableLiveData<Any>()

}