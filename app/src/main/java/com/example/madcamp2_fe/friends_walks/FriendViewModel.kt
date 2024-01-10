package com.example.madcamp2_fe.friends_walks

import androidx.lifecycle.ViewModel

class FriendViewModel: ViewModel() {
    private var friendList = arrayListOf<FollowListResponse>()

    fun addFriend(oneFriend : FollowListResponse){
        friendList.add(oneFriend)
    }
    fun getFriendList() : ArrayList<FollowListResponse>{
        return friendList
    }
    fun reset(){
        friendList = ArrayList<FollowListResponse>()
    }

}