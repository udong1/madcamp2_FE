package com.example.madcamp2_fe

import androidx.lifecycle.ViewModel

class WalkViewModel : ViewModel() {
    private lateinit var userNickname : String
    private lateinit var userAccessToken : String
    private var userIsRegistered : Boolean = false
    private lateinit var userProfileImg : String

    fun getUserName():String{
        return userNickname
    }
    fun getUserAccessToken():String{
        return userAccessToken
    }
    fun getUserIsRegistered():Boolean{
        return userIsRegistered
    }
    fun getUserProfileImg():String{
        return userProfileImg
    }
    fun setUserInfo(name : String,token : String, bool : Boolean){
        userNickname = name
        userAccessToken = token
        userIsRegistered = bool
    }
    fun setUserProfile(img:String){
        userProfileImg = img
    }


}