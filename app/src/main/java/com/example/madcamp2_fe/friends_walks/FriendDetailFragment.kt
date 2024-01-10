package com.example.madcamp2_fe.friends_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.madcamp2_fe.R

class FriendDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_detail, container, false)
    }

    companion object {
        fun newInstance(followListResponse: FollowListResponse) : FriendDetailFragment{
            val fragment = FriendDetailFragment()
            val args = Bundle()
            if(followListResponse.profileImgUrl==null){
                args.putString("friendProfile","default")
            }
            else{
                args.putString("friendProfile",followListResponse.profileImgUrl)
            }
            args.putString("friendEmail",followListResponse.email)
            args.putString("friendNickname",followListResponse.nickname)
            args.putLong("friendId",followListResponse.userId)

            return fragment
        }
    }
}