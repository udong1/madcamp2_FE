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
import androidx.lifecycle.viewModelScope
import com.example.madcamp2_fe.home.LocationData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class WalkViewModel : ViewModel() {
    private lateinit var userNickname : String
    private lateinit var userEmail : String
    private lateinit var userAccessToken : String
    private var userIsRegistered : Boolean = false
    private lateinit var userProfileImg : String
    private var profileChanged : MutableLiveData<Boolean> = MutableLiveData(false)
    private var lon : MutableLiveData<Double> = MutableLiveData()
    private var lat : MutableLiveData<Double> = MutableLiveData()
    private val locationTracker = mutableListOf<Location>()
    private var distanceTracker : MutableLiveData<Double> = MutableLiveData(0.0)
    private lateinit var walkStartTime : LocalDateTime
    private lateinit var walkTerminateTime : LocalDateTime
    private lateinit var duration : String
    private var walkCount = 0


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
    fun getProfileChanged():MutableLiveData<Boolean>{
        return profileChanged
    }
    fun getWalkStartTime():LocalDateTime{
        return walkStartTime
    }
    fun getWalkTerminateTime():LocalDateTime{
        return walkTerminateTime
    }
    fun getDistanceTracker():MutableLiveData<Double>{
        return distanceTracker
    }
    fun getDuration():String{
        duration = Duration.between(walkStartTime, walkTerminateTime).seconds.toString()
        return duration
    }
    fun getLastLocation():Location{
        return locationTracker.last()
    }
    fun getSecondLastLocation():Location{
        return if(locationTracker.size >= 2){
            locationTracker[locationTracker.size -2]
        } else{
            locationTracker.first()
        }
    }
    fun getLocationTracker(): MutableList<Location>{
        return locationTracker
    }
    fun getWalkCount():Int{
        return walkCount
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
    fun setProfileChanged(bool:Boolean){
        profileChanged.value = bool
    }

    fun addLocation(location:Location){
        if(locationTracker.isNotEmpty()){
            distanceTracker.value = distanceTracker.value!! + locationTracker.last().distanceTo(location).toDouble()
        }
        locationTracker.add(location)
        Log.d("distance", distanceTracker.value.toString())
    }
    fun setWalkStartTime(){
        walkStartTime = LocalDateTime.now()

    }
    fun setWalkTerminateTime(){
        walkTerminateTime = LocalDateTime.now()
    }

    fun setWalkCount(day:Int){
        walkCount = day
    }






}