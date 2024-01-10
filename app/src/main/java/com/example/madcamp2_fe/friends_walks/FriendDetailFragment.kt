package com.example.madcamp2_fe.friends_walks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentFriendDetailBinding
import com.example.madcamp2_fe.databinding.FragmentHomeBinding
import com.example.madcamp2_fe.home.WalkResponse
import com.example.madcamp2_fe.my_walks.WalkAdapter
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FriendDetailFragment : Fragment() {

    private var _binding : FragmentFriendDetailBinding? = null
    private lateinit var friendName : String
    private lateinit var friendProfile : String
    private lateinit var friendEmail : String
    private lateinit var friendRecentWalk : String
    private var friendId : Long = 0L
    private lateinit var friendAdapter: FriendAdapter
    private lateinit var walkAdapter: WalkAdapter
    private var friendWalkList = arrayListOf<WalkResponse>()
    private lateinit var walkViewModel : WalkViewModel

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
        friendEmail = arguments?.getString("friendEmail")!!
        friendId = arguments?.getLong("friendId",0L)!!
        binding.friendName.text = friendName
        binding.friendEmail.text = friendEmail
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.friendAdapter = FriendAdapter(arrayListOf())

        friendWalkList = arrayListOf()
        walkAdapter = WalkAdapter(friendWalkList)
        binding.friendWalkBoard.adapter = walkAdapter
        binding.friendWalkBoard.layoutManager = LinearLayoutManager(this.context)
        binding.friendDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        UserClientManager.instance.getWalkOfFriend(
            token = walkViewModel.getUserAccessToken(),
            followedUserId = friendId,
            completion = {
                    responseState, responseBody ->
                when(responseState){
                    RESPONSE_STATE.OKAY ->{
                        Log.d(Constants.TAG, "산책 정보 가져오기 성공 : $responseBody")
                        for(position in responseBody.indices){
                            friendWalkList.add(responseBody[position])
                        }
                        Log.d("가져온 walkList 정보","$friendWalkList")
                        friendWalkList.sortByDescending { it.walkStartDateTime }
                        this.walkAdapter.walkList = friendWalkList
                        walkAdapter.notifyDataSetChanged()
                    }
                    RESPONSE_STATE.FAIL ->{
                        //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "산책정보 가져오기 실패")
                    }
                }
            })
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