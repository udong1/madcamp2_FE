package com.example.madcamp2_fe.friends_walks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentFriendSearchBinding
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants


class FriendSearchFragment : Fragment() {

    private var _binding : FragmentFriendSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var walkViewModel: WalkViewModel
    private lateinit var friendSearchAdapter: FriendSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendSearchBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
        this.friendSearchAdapter = FriendSearchAdapter(listOf(), walkViewModel)
        binding.searchResultBoard.adapter = friendSearchAdapter
        binding.searchResultBoard.layoutManager = LinearLayoutManager(this.context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var searchTerm : String = ""
        var searchList : List<FriendBySearchResponse>

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(edit: Editable?){
                searchTerm = edit.toString()
            }
        })
        binding.returnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.searchButton.setOnClickListener {
            //디비에 정보 요청 후 결과에 따라 recyclerview에 연결

            UserClientManager.instance.searchFriends(walkViewModel.getUserAccessToken(),
                search = searchTerm,
                completion = {
                        responseState, responseBody ->
                    when(responseState){
                        RESPONSE_STATE.OKAY ->{
                            Log.d(Constants.TAG, "검색 성공 : $responseBody")
//                            friendSearchViewModel.setFriendSearchList(responseBody)
                            searchList = responseBody
//                            this.friendSearchAdapter.friendSearchList = friendSearchViewModel.getFriendSearchList()
                            this.friendSearchAdapter.friendSearchList = searchList
                            friendSearchAdapter.notifyDataSetChanged()
                        }
                        RESPONSE_STATE.FAIL ->{
                            //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                            Log.d(Constants.TAG, "검색 실패")
                        }
                    }
                })
        }


    }

    companion object {
        fun newInstance():FriendSearchFragment{
            return FriendSearchFragment()
        }
    }
}