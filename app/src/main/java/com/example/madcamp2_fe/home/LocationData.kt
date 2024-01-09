package com.example.madcamp2_fe.home

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("latitude") val latitude:Double,
    @SerializedName("longitude") val longitude:Double,
    @SerializedName("recordOrder") val recordOrder:Long
)
