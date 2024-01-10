package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName


data class WalkResponse(
    @SerializedName("location_list") val locList : List<LocationData>,
    @SerializedName("walking_start_date_time") val walkStartDateTime : String,
    @SerializedName("total_walking_time") val walkingTime : Long,
    @SerializedName("walking_distance") val distance : Double,
    @SerializedName("walking_record_id") val walkId : Long
)
