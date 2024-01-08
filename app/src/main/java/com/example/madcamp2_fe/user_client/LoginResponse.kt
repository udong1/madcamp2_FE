package com.example.madcamp2_fe.user_client

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("nickname") var nickname : String,
    @SerializedName("email") var email : String,
    @SerializedName("access_token") var accessToken : String,
    @SerializedName("is_registered") var isRegistered : Boolean=false,
    @SerializedName("profile_img") var profileImg : String

)
