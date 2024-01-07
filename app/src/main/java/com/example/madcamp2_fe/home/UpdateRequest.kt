package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName

data class UpdateRequest(
    @SerializedName("nickname") var insertedNickname : String,
    @SerializedName("profile_img_url") var insertedProfileImg : String,
    @SerializedName("token") var token : String,
)
