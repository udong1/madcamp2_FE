package com.example.madcamp2_fe.user_client

import android.util.Log
import com.example.madcamp2_fe.home.Walk
import com.example.madcamp2_fe.login.LoginRequest
import com.example.madcamp2_fe.login.LoginResponse
import com.example.madcamp2_fe.profile_update.UpdateResponse
import com.example.madcamp2_fe.utils.API
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants
import com.kakao.sdk.user.Constants.TAG
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class UserClientManager {
    companion object{
        val instance = UserClientManager()
    }
    private val userInterface : UserInterface? = UserClient.getClient(API.BASE_URL)
        ?.create(UserInterface::class.java)

    fun login(loginRequest : LoginRequest, completion:(RESPONSE_STATE, LoginResponse)-> Unit){
        Log.d("login1", "$userInterface")
        val call = userInterface?.login(loginRequest) ?:return
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

    fun updateProfile(token : String, profileImg : MultipartBody.Part?, userName : RequestBody, completion:(RESPONSE_STATE, UpdateResponse)-> Unit){
        Log.d("updateProfile", "$userInterface")
        val call = userInterface?.updateUserInfo("Bearer $token", profileImg, userName) ?:return
        Log.d("token",token)
        Log.d("userName","$userName")
        call.enqueue(object : retrofit2.Callback<UpdateResponse>{
            override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
                Log.d("response.isSuccessful", "${response.isSuccessful}")
                if (response.isSuccessful){
                    Log.d(Constants.TAG, "프로필 업데이트 응답 성공 ${response.body()}")
                    completion(RESPONSE_STATE.OKAY, response.body()!!)
                }
                else{
                    Log.d(Constants.TAG, "프로필 업데이트 응답 실패")
                    Log.d(Constants.TAG, "${response.errorBody()?.string()}")
                    completion(RESPONSE_STATE.FAIL, UpdateResponse("",""))
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                Log.d(Constants.TAG, "프로필 업데이트 응답 실패 on Failure")
                completion(RESPONSE_STATE.FAIL, UpdateResponse("", ""))
            }
        })
    }


    fun updateWalk(token : String, walk : Walk, completion:(RESPONSE_STATE)-> Unit){
        Log.d("updateWalk", "$userInterface")
        val call = userInterface?.updateWalk("Bearer $token", walk) ?:return
        Log.d("distance","${walk.distance}")
        call.enqueue(object : retrofit2.Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("response.isSuccessful", "${response.isSuccessful}")
                if (response.isSuccessful){
                    Log.d(Constants.TAG, "산책 업데이트 응답 성공")
                    completion(RESPONSE_STATE.OKAY)
                }
                else{
                    Log.d(Constants.TAG, "산책 업데이트 응답 실패")
                    Log.d(Constants.TAG, "${response.errorBody()?.string()}")
                    completion(RESPONSE_STATE.FAIL)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d(Constants.TAG, "프로필 업데이트 응답 실패 on Failure")
                completion(RESPONSE_STATE.FAIL)
            }
        })
    }

}