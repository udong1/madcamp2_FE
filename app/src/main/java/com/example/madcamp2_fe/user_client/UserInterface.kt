package com.example.madcamp2_fe.user_client

import com.example.madcamp2_fe.profile_update_client.UpdateRequest
import com.example.madcamp2_fe.profile_update_client.UpdateResponse
import com.example.madcamp2_fe.utils.API
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface UserInterface {
    @POST(API.LOGIN)
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>


    @Multipart
    @POST(API.UPDATE)
    fun updateUserInfo(
        @Header("Authorization") authorization:String,
        @Part profileImg: MultipartBody.Part,
        @Part("nickname") userName: RequestBody
    ) : Call<UpdateResponse>
}