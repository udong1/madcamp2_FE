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

    const val WALK : String = "walkingRecord/save/v1"

    const val FOLLOW_LIST : String = "user/follow/list/v1"

    const val GET_WALK : String = "walkingRecord/my-list/v1"

    const val SEARCH_FRIEND : String = "user/search/v1"

    const val FOLLOW : String = "user/follow/v1"

    const val UNFOLLOW : String = "user/unFollow/v1"
}