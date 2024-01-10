package com.example.madcamp2_fe.friends_walks

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FollowListResponse(
    var userId: Long,
    var email: String,
    var nickname: String,

    @SerializedName("profile_img_url")
    var profileImgUrl: String?,
    @SerializedName("walking_start_date_time")
    var recentWalkTime: String?
)
