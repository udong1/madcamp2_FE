package com.example.madcamp2_fe.login

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("access_token") var acess_token : String,
    @SerializedName("refresh_token") var refresh_token : String,
    @SerializedName("is_registered") var is_registered : Boolean=false
)
