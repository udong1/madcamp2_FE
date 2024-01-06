package com.example.madcamp2_fe.my_walks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentFriendsWalksBinding


class MyWalksFragment : Fragment() {

    private var _binding : FragmentFriendsWalksBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFriendsWalksBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
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