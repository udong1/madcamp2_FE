package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime


data class Walk(
    @SerializedName("location_list") val locList : List<LocationData>,
    @SerializedName("walking_start_date_time") val walkStartDateTime : String,
    @SerializedName("total_walking_time") val walkingTime : Long,
    @SerializedName("walking_distance") val distance : Double
)
