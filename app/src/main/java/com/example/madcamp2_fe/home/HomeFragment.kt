package com.example.madcamp2_fe.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkActivity
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        walkViewModel = ViewModelProvider(requireActivity()).get(WalkViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(R.drawable.background)
            .into(binding.background)

        Glide.with(this)
            .load(walkViewModel.getUserProfileImg())
            .into(binding.profile)

        Glide.with(this)
            .load(R.drawable.white_background)
            .into(binding.lowerPartBack)

        binding.homeName.text=walkViewModel.getUserName()

        binding.settingButton.setOnClickListener{
            val profileEditFragment = ProfileEditFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame, profileEditFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance(userAccessToken:String, userRefreshToken:String, userIsRegistered:Boolean){
        }
    }
}