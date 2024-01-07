package com.example.madcamp2_fe.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkActivity
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.kakao.vectormap.GestureType
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import java.lang.Exception


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private var lon : Double = 999.0
    private var lat : Double = 999.0
    private lateinit var kakaoMap : KakaoMap

    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        Log.d("called location client","success")
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
            .load(walkViewModel.getUserProfileImg())
            .into(binding.profile)


        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
            }

            override fun onMapError(error: Exception?) {
            }
        }, object: KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {
                kakaoMap = map
                getLocation()
                kakaoMap.setGestureEnable(GestureType.OneFingerDoubleTap, false)
                kakaoMap.setGestureEnable(GestureType.TwoFingerSingleTap, false)
                kakaoMap.setGestureEnable(GestureType.Zoom, false)
                kakaoMap.setGestureEnable(GestureType.OneFingerZoom, false)
                kakaoMap.setGestureEnable(GestureType.Pan, false)
                kakaoMap.setGestureEnable(GestureType.Tilt, false)
                kakaoMap.setGestureEnable(GestureType.Rotate, false)
                kakaoMap.setGestureEnable(GestureType.RotateZoom, false)
                kakaoMap.setGestureEnable(GestureType.LongTapAndDrag, false)
            }
            override fun getZoomLevel(): Int {
                return 15
            }
        })


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

    @SuppressLint("MissingPermission")
    fun getLocation(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location:Location? ->
                if(location == null){
                    Log.d("location", "Location is null")
                }
                else{
                    lat = location.latitude
                    lon = location.longitude
                    Log.d("lon", "lon is $lon")
                    Log.d("lat", "lat is $lat")
                    kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(LatLng.from(lat,lon)))
                }
            }
    }

    companion object {
        fun newInstance(userAccessToken:String, userRefreshToken:String, userIsRegistered:Boolean){
        }
    }
}