package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @SerializedName("nickname") var updatedNickname : String,
    @SerializedName("profile_img_url") var updatedProfileImg : String
)
