package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class Walk(
    @SerializedName("location_list") var locationList : List<LocationData>,
    @SerializedName("walking_start_date_time") var startDateTime : LocalDateTime,
    @SerializedName("total_walking_time") var walkTime : Long,
    @SerializedName("walking_distance") var distance : Double
)
