package com.example.madcamp2_fe.utils

enum class RESPONSE_STATE {
    OKAY,
    FAIL
}
enum class TYPE{
    LOGIN
}

object API{
    const val BASE_URL = "http://ec2-3-38-106-102.ap-northeast-2.compute.amazonaws.com:8080/api/"

    const val LOGIN : String = "oauth/authenticate/kakao/token/v1"

    const val UPDATE : String = "user/modify/v1"
}