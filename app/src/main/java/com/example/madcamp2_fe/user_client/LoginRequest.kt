package com.example.madcamp2_fe.user_client

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    var token:String
)

