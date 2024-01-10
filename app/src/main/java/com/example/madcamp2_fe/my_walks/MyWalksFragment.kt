package com.example.madcamp2_fe.my_walks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentMyWalksBinding
import com.example.madcamp2_fe.home.WalkResponse
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants


class MyWalksFragment : Fragment() {

    private var _binding : FragmentMyWalksBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private lateinit var walkAdapter: WalkAdapter
    private var walkList = arrayListOf<WalkResponse>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyWalksBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        walkList = arrayListOf()
        walkAdapter = WalkAdapter(walkList)
        binding.walkShow.adapter = walkAdapter
        binding.walkShow.layoutManager = LinearLayoutManager(this.context)

        UserClientManager.instance.getWalk(
            token = walkViewModel.getUserAccessToken(),
            completion = {
                    responseState, responseBody ->
                when(responseState){
                    RESPONSE_STATE.OKAY ->{
                        Log.d(Constants.TAG, "산책 정보 가져오기 성공 : $responseBody")
                        for(position in responseBody.indices){
                            walkList.add(responseBody[position])
                        }
                        Log.d("가져온 walkList 정보","$walkList")
                        walkList.sortByDescending { it.walkStartDateTime }
                        this.walkAdapter.walkList = walkList
                            walkAdapter.notifyDataSetChanged()
                    }
                    RESPONSE_STATE.FAIL ->{
                        //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "산책정보 가져오기 실패")
                    }
                }
            })

        Log.d("adapter walklist", "${this.walkAdapter.walkList}")



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