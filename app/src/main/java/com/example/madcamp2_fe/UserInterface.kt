package com.example.madcamp2_fe

import com.example.madcamp2_fe.home.UpdateRequest
import com.example.madcamp2_fe.home.UpdateResponse
import com.example.madcamp2_fe.login.LoginRequest
import com.example.madcamp2_fe.login.LoginResponse
import com.example.madcamp2_fe.utils.API
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserInterface {
    @POST(API.LOGIN)
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @Multipart
    @POST(API.UPDATE)
    fun updateUserInfo(
        @Header("Token") token:String,
        @Part("nickname") userName: UpdateRequest,
        @Part("profile_img_url") profileImg : MultipartBody.Part
    ) : Call<UpdateResponse>
}