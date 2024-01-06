package com.example.madcamp2_fe.login

import com.example.madcamp2_fe.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInterface {
    @POST(API.LOGIN)
    fun login(@Body loginRequest:LoginRequest) : Call<LoginResponse>
}