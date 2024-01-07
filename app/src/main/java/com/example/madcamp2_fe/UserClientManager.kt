package com.example.madcamp2_fe

import android.util.Log
import com.example.madcamp2_fe.login.LoginRequest
import com.example.madcamp2_fe.login.LoginResponse
import com.example.madcamp2_fe.utils.API
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants.TAG
import retrofit2.Call
import retrofit2.Response

class UserClientManager {
    companion object{
        val instance = UserClientManager()
    }
    private val userLoginInterface : UserInterface? = UserClient.getClient(API.BASE_URL)
        ?.create(UserInterface::class.java)

    fun login(loginRequest : LoginRequest, completion:(RESPONSE_STATE, LoginResponse)-> Unit){
        Log.d("login1", "$userLoginInterface")
        val call = userLoginInterface?.login(loginRequest) ?:return
        Log.d("login2", "$call")
        call.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("response.isSuccessful", "${response.isSuccessful}")
                if (response.isSuccessful){
                    Log.d(TAG, "응답 성공 ${response.body()}")
                    completion(RESPONSE_STATE.OKAY, response.body()!!)
                }
                else{
                    Log.d(TAG, "응답 실패")
                    Log.d(TAG, "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "응답 실패 on Failure")
                completion(RESPONSE_STATE.FAIL, LoginResponse("", "", "",false, ""))
            }
        })

    }
}