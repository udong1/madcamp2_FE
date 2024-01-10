package com.example.madcamp2_fe.user_client

import android.util.Log
import com.kakao.sdk.user.Constants.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserClient{
    private lateinit var userLoginClient : Retrofit
    fun getClient(baseUrl : String ):Retrofit? {
        userLoginClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d(TAG, "RetrofitClient - getClient() called, $userLoginClient")
        return userLoginClient
    }
}