package com.example.madcamp2_fe.friends_walks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.databinding.FragmentFriendAddBinding
import com.example.madcamp2_fe.databinding.FragmentHomeBinding


class FriendAddFragment : Fragment() {

    private var _binding : FragmentFriendAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
    }
}