package com.example.madcamp2_fe.user_client

import com.example.madcamp2_fe.friends_walks.FollowListResponse
import com.example.madcamp2_fe.friends_walks.FriendBySearchResponse
import com.example.madcamp2_fe.home.WalkResponse
import com.example.madcamp2_fe.login.LoginRequest
import com.example.madcamp2_fe.login.LoginResponse
import com.example.madcamp2_fe.profile_update.UpdateResponse
import com.example.madcamp2_fe.utils.API
import com.example.madcamp2_fe.home.WalkRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserInterface {
    @POST(API.LOGIN)
    fun login(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>

    @Multipart
    @POST(API.UPDATE)
    fun updateUserInfo(
        @Header("Authorization") authorization:String,
        @Part profileImg: MultipartBody.Part?,
        @Part("nickname") userName: RequestBody
    ) : Call<UpdateResponse>

    @POST(API.DELETE_PROFILE)
    fun deleteProfile(
        @Header("Authorization") authorization:String
    ) : Call<Void>

    @POST(API.WALK)
    fun updateWalk(
        @Header("Authorization") authorization:String,
        @Body walk : WalkRequest
    ) : Call<Void>

    @GET(API.FOLLOW_LIST)
    fun getFollowList(
        @Header("Authorization") authorization:String
    ): Call<List<FollowListResponse>>

    @GET(API.GET_WALK)
    fun getWalk(
        @Header("Authorization") authorization:String
    ) : Call<List<WalkResponse>>

    @GET(API.GET_WALK_OF_FRIEND)
    fun getWalkOfFriend(
        @Header("Authorization") authorization:String,
        @Query("followedUserId") followedUserId: Long
    ) : Call<List<WalkResponse>>

    @DELETE(API.DELETE_WALK_RECORD)
    fun deleteWalkRecord(
        @Header("Authorization") authorization:String,
        @Query("walkingRecordId") walkingRecordId: Long
    ) : Call<Void>

    @GET(API.SEARCH_FRIEND)
    fun searchFriend(
        @Header("Authorization") authorization:String,
        @Query("search") search: String
    ) : Call<List<FriendBySearchResponse>>

    @GET(API.FOLLOW)
    fun followFriend(
        @Header("Authorization") authorization:String,
        @Query("followedUserId") followedUserId: Long
    ) : Call<Void>

    @DELETE(API.UNFOLLOW)
    fun unFollowFriend(
        @Header("Authorization") authorization:String,
        @Query("followedUserId") followedUserId: Long
    ) : Call<Void>
}