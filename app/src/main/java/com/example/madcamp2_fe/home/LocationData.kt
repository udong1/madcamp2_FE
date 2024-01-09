package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("latitude") val lat:Double,
    @SerializedName("longitude") val lon:Double,
    @SerializedName("recordOrder") val order:Long
)
