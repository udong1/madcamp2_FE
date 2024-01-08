package com.example.madcamp2_fe.walk

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class LonLat(
    @SerializedName("longitude") val lon : Double,
    @SerializedName("latitude") var lat :Double)
