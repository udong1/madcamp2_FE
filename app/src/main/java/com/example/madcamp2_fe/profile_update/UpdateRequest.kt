package com.example.madcamp2_fe.profile_update

import com.google.gson.annotations.SerializedName

data class UpdateRequest(
    @SerializedName("nickname") var insertedNickname : String
)
