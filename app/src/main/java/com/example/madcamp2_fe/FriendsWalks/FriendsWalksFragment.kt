package com.example.madcamp2_fe.FriendsWalks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.madcamp2_fe.UserViewModel
import com.example.madcamp2_fe.databinding.FragmentFriendsWalksBinding
import com.example.madcamp2_fe.databinding.FragmentHomeBinding
import com.example.madcamp2_fe.databinding.FragmentMyWalksBinding

class FriendsWalksFragment : Fragment() {

    private val userViewModel : UserViewModel by activityViewModels()
    private var _binding : FragmentFriendsWalksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendsWalksBinding.inflate(inflater, container, false)
        return binding.root
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