package com.example.madcamp2_fe.login

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("nickname") var nickname : String,
    @SerializedName("access_token") var accessToken : String,
    @SerializedName("is_registered") var isRegistered : Boolean=false,
    @SerializedName("profile_img") var profileImg : String

)
