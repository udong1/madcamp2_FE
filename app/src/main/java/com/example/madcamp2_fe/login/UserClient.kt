package com.example.madcamp2_fe.login

import android.util.Log
import com.kakao.sdk.user.Constants.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//object LoginRepository {
//    suspend fun fetchUser(loginRequest: LoginRequest): LoginResponse {
//
//        val url = "http://ec2-3-38-106-102.ap-northeast-2.compute.amazonaws.com:8080/api/oauth/authenticate/kakao/token/v1"
//
//        val httpClient = HttpClient {
//            install(JsonFeature) {
//                serializer = KotlinxSerializer()
//            }
//        }
//
//        return httpClient.use {
//            it.post<LoginResponse>(url) {
//                contentType(ContentType.Application.Json)
//                body = loginRequest
//            }
//        }
//    }
//}
object UserClient{
    private lateinit var userLoginClient : Retrofit
    fun getClient(baseUrl : String ):Retrofit? {
        userLoginClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.d(TAG, "RetrofitClient - getClient() called, $userLoginClient")
        return userLoginClient
    }

}