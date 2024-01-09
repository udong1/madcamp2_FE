package com.example.madcamp2_fe.friends_walks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp2_fe.R
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import java.text.DecimalFormat

class FriendAdapter {

    class FriendViewHolder(view : View): RecyclerView.ViewHolder(view){
        val distNum = view.findViewById<TextView>(R.id.distNum)!!
        val durationNum = view.findViewById<TextView>(R.id.durationNum)!!
        val date = view.findViewById<TextView>(R.id.date)!!
        val map = view.findViewById<MapView>(R.id.mapViewRec)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        return
    }
}