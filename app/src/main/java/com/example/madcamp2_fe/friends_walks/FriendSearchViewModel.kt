package com.example.madcamp2_fe.friends_walks

import androidx.lifecycle.ViewModel

class FriendSearchViewModel:ViewModel() {
    private var friendSearchList = listOf<FriendBySearchResponse>()

    fun getFriendSearchList():List<FriendBySearchResponse>{
        return friendSearchList
    }

    fun setFriendSearchList(list : List<FriendBySearchResponse>){
        friendSearchList = list
    }
}