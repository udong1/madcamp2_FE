package com.example.madcamp2_fe

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.madcamp2_fe.friends_walks.FriendsWalksFragment
import com.example.madcamp2_fe.home.HomeFragment
import com.example.madcamp2_fe.my_walks.MyWalksFragment
import com.example.madcamp2_fe.databinding.ActivityWalkBinding
import com.google.android.material.tabs.TabLayout

class WalkActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWalkBinding
    private lateinit var walkViewModel : WalkViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Call WalkActivity", "WalkActivity called")
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walkViewModel = ViewModelProvider(this).get(WalkViewModel::class.java)

        val home : Fragment = HomeFragment()
        val myWalks : Fragment = MyWalksFragment()
        val friendsWalks : Fragment = FriendsWalksFragment()
        val intent = intent
        val userNickname = intent.getStringExtra("nickname")
        val userEmail = intent.getStringExtra("email")
        val userAccessToken = intent.getStringExtra("accessToken")
        val userIsRegistered = intent.getBooleanExtra("isRegistered", false)
        val userProfileImg = intent.getStringExtra("profileImg")

        walkViewModel.setUserInfo(userNickname!!,userEmail!!, userAccessToken!!, userIsRegistered)
        if (userProfileImg == null){
            val assetImagePath = "images/default_profile.png"
            val assetUri:Uri = Uri.parse("file:///android_asset/$assetImagePath")
            val assetUriString:String = assetUri.toString()
            walkViewModel.setUserProfile(assetUriString)
            Log.d("assetUriString", assetUriString)
        }
        else{
            walkViewModel.setUserProfile(userProfileImg)
        }

        val readPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val writePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val finePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val coarsePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED ||
            cameraPermission == PackageManager.PERMISSION_DENIED ||
            finePermission == PackageManager.PERMISSION_DENIED ||
            coarsePermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        }



        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, home)
            .commit()

        binding.tabBar.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab){
                when (tab.text){
                    "Home"->{
                        supportFragmentManager.beginTransaction().replace(R.id.frame, home)
                            .commit()
                    }
                    "MyWalks"->{
                        supportFragmentManager.beginTransaction().replace(R.id.frame, myWalks)
                            .commit()
                    }
                    "FriendsWalks"->{
                        supportFragmentManager.beginTransaction().replace(R.id.frame, friendsWalks)
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }


}