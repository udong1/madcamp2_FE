package com.example.madcamp2_fe.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
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
import android.widget.ImageView
import android.widget.TextView
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
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.shape.DotPoints
import com.kakao.vectormap.shape.PolygonOptions
import com.kakao.vectormap.shape.ShapeManager
import java.lang.Exception
import java.util.Timer
import kotlin.concurrent.timer


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private lateinit var walkViewModel : WalkViewModel
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private var lon : Double = 999.0
    private var lat : Double = 999.0
    private lateinit var kakaoMap : KakaoMap
    private var shapeManager : ShapeManager? = null
    private var labelManager : LabelManager? = null
    private var time0 : Int = 0
    private var time1 : Int = 0
    private var time2 : Int = 0
    private var time3 : Int = 0


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
        Glide.with(this)
            .load(walkViewModel.getUserProfileImg())
            .into(binding.profile)
        binding.homeName.text=walkViewModel.getUserName()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        walkViewModel.getProfileChanged().observe(requireActivity(), Observer {
            if (it){
                Glide.with(this)
                    .load(walkViewModel.getUserProfileImg())
                    .into(binding.profile)
                binding.homeName.text=walkViewModel.getUserName()
                walkViewModel.setProfileChanged(false)
            }
        })
        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
            }

            override fun onMapError(error: Exception?) {
            }
        }, object: KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {
                kakaoMap = map
                getLocation()
                shapeManager = kakaoMap.shapeManager
                labelManager = kakaoMap.labelManager
//                kakaoMap.setGestureEnable(GestureType.OneFingerDoubleTap, false)
//                kakaoMap.setGestureEnable(GestureType.TwoFingerSingleTap, false)
//                kakaoMap.setGestureEnable(GestureType.Zoom, false)
//                kakaoMap.setGestureEnable(GestureType.OneFingerZoom, false)
//                kakaoMap.setGestureEnable(GestureType.Pan, false)
//                kakaoMap.setGestureEnable(GestureType.Tilt, false)
//                kakaoMap.setGestureEnable(GestureType.Rotate, false)
//                kakaoMap.setGestureEnable(GestureType.RotateZoom, false)
//                kakaoMap.setGestureEnable(GestureType.LongTapAndDrag, false)
            }
            override fun getZoomLevel(): Int {
                return 15
            }
        })


        binding.settingButton.setOnClickListener{
            val profileEditFragment = ProfileEditFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame, profileEditFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.startButton.setOnClickListener {
            binding.upperProfile.visibility = View.GONE
            binding.startButton.visibility = View.GONE
            binding.menu.visibility = View.VISIBLE
            val currentMarker = labelManager!!.addLabelStyles(
                LabelStyles.from("currentMarker", LabelStyle.from(R.drawable.play))
            )
            labelManager!!.layer!!.addLabel(LabelOptions.from("label", LatLng.from(lat, lon)).setStyles(currentMarker))
        }


        binding.terminate.isEnabled = false
        binding.terminate.visibility = View.INVISIBLE
        binding.start.setOnClickListener{
            binding.start.visibility = View.GONE
            binding.terminate.visibility = View.VISIBLE
            binding.terminate.isEnabled = true
            walkViewModel.stopwatch.start()
            Log.d("stopwatch start","start button touched")
        }

        binding.terminate.setOnClickListener {
            //산책 종료

            binding.terminate.visibility = View.GONE
            walkViewModel.pauseStopwatch()
            Log.d("stopwatch stop","terminate button touched")
        }

        walkViewModel.getTime().observe(requireActivity(), Observer {
            var timeCounter = walkViewModel.getTime().value!!
            time0 = timeCounter / 600
            timeCounter %= 600
            time1 = timeCounter / 60
            timeCounter %= 60
            time2 = timeCounter / 10
            timeCounter %= 10
            time3 = timeCounter
            binding.time0.text = time0.toString()
            binding.time1.text = time1.toString()
            binding.time2.text = time2.toString()
            binding.time3.text = time3.toString()
        })

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