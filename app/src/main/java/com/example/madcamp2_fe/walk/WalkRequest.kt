package com.example.madcamp2_fe.walk

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class WalkRequest(
    @SerializedName("walk") var walk : List<LonLat>,
    @SerializedName("time") var time : LocalDateTime,
    @SerializedName("duration") var duration : Int,
    @SerializedName("walk_distance") var distance : Int
)
