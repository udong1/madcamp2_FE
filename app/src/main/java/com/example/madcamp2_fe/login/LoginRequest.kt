package com.example.madcamp2_fe.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    var token:String
)

