package com.example.madcamp2_fe

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

class WalkViewModel : ViewModel() {
    private lateinit var userNickname : String
    private lateinit var userEmail : String
    private lateinit var userAccessToken : String
    private var userIsRegistered : Boolean = false
    private lateinit var userProfileImg : String
    private var lon : MutableLiveData<Double> = MutableLiveData()
    private var lat : MutableLiveData<Double> = MutableLiveData()


    fun getUserName():String{
        return userNickname
    }
    fun getUserEmail():String{
        return userEmail
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
    fun getLon():MutableLiveData<Double>{
        return lon
    }
    fun getLat():MutableLiveData<Double>{
        return lat
    }
    fun setLon(longitude : Double){
        lon.value = longitude
    }
    fun setLat(latitude : Double){
        lat.value = latitude
    }
    fun setUserInfo(name : String, email:String, token : String, bool : Boolean){
        userNickname = name
        userEmail = email
        userAccessToken = token
        userIsRegistered = bool
    }
    fun setUserProfile(img:String){
        userProfileImg = img
    }
    fun changeUserName(changedName : String){
        userNickname = changedName
    }


}