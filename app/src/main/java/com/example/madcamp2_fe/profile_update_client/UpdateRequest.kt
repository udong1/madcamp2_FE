package com.example.madcamp2_fe.profile_update_client

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdateRequest(
    @SerializedName("nickname") var insertedNickname : String
)
