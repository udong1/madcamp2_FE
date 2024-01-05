package com.example.madcamp2_fe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.madcamp2_fe.FriendsWalks.FriendsWalksFragment
import com.example.madcamp2_fe.Home.HomeFragment
import com.example.madcamp2_fe.MyWalks.MyWalksFragment
import com.example.madcamp2_fe.databinding.ActivityWalkBinding
import com.google.android.material.tabs.TabLayout

class WalkActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWalkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_walk)
        val home : Fragment = HomeFragment()
        val myWalks : Fragment = MyWalksFragment()
        val friendsWalks : Fragment = FriendsWalksFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, home)
            .commit()

        binding.tabBar.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab){
                when (tab?.text){
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