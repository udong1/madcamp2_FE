package com.example.madcamp2_fe.friends_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.databinding.FragmentFriendDetailBinding
import com.example.madcamp2_fe.databinding.FragmentHomeBinding
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FriendDetailFragment : Fragment() {

    private var _binding : FragmentFriendDetailBinding? = null
    private lateinit var friendName : String
    private lateinit var friendProfile : String
    private lateinit var friendEmail : String
    private lateinit var friendRecentWalk : String
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendDetailBinding.inflate(inflater, container, false)
        friendProfile = arguments?.getString("friendProfile")!!
        friendName = arguments?.getString("friendNickname")!!
        friendRecentWalk = arguments?.getString("recentWalk")!!
        if(friendProfile!="default"){
            Glide.with(this)
                .load(friendProfile)
                .into(binding.friendProfile)
        }
        binding.friendName.text = friendName
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            if(followListResponse.recentWalkTime==null) {
                args.putString("recentWalk","산책 기록이 없어요")
            }
            else{
                val recent = LocalDateTime.parse(followListResponse.recentWalkTime, DateTimeFormatter.ISO_DATE_TIME)
                val current = LocalDateTime.now()
                val duration = Duration.between(recent, current)
                val difference = duration.toDays()
                val recentWalk = if(difference == 0L){
                    "오늘"
                }else{
                    "어슬렁 ${difference}일 전 "
                }
                args.putString("recentWalk",recentWalk)
            }

            args.putString("friendEmail",followListResponse.email)
            args.putString("friendNickname",followListResponse.nickname)
            args.putLong("friendId",followListResponse.userId)
            fragment.arguments = args
            return fragment
        }
    }

}