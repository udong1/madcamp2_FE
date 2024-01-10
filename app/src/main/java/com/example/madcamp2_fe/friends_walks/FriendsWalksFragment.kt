package com.example.madcamp2_fe.friends_walks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentFriendsWalksBinding
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class FriendsWalksFragment : Fragment() {

    private var _binding : FragmentFriendsWalksBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private lateinit var friendViewModel : FriendViewModel
    private lateinit var friendAdapter: FriendAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendsWalksBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
        friendViewModel = ViewModelProvider(requireActivity()).get(FriendViewModel::class.java)
        this.friendAdapter = FriendAdapter(friendViewModel.getFriendList())
        binding.friendShow.adapter = friendAdapter
        binding.friendShow.layoutManager = LinearLayoutManager(this.context)
        friendViewModel.reset()
        UserClientManager.instance.getFollowList(
            token = walkViewModel.getUserAccessToken(),
            completion = {
                    responseState, responseBody ->
                when(responseState){
                    RESPONSE_STATE.OKAY ->{
                        Log.d(Constants.TAG, "업데이트 성공 : $responseBody")
                        for(position in responseBody.indices){
                            friendViewModel.addFriend(responseBody[position])
                        }
                        this.friendAdapter.friendList = friendViewModel.getFriendList()
                        friendAdapter.notifyDataSetChanged()
                    }
                    RESPONSE_STATE.FAIL ->{
                        //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "없데이트")
                    }
                }
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        friendAdapter.setFriendClickListener(object : FriendAdapter.OnFriendClickListener{
            override fun onClick(view: View, followListResponse: FollowListResponse) {
                val friendDetailFragment = FriendDetailFragment.newInstance(followListResponse)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame, friendDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

    }

    companion object {

        fun newInstance(){

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}