package com.example.madcamp2_fe.my_walks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.home.Walk
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import java.text.DecimalFormat

class WalkAdapter(private var walkList : ArrayList<Walk>):RecyclerView.Adapter<WalkAdapter.WalkViewHolder>(){
    class WalkViewHolder(view : View):RecyclerView.ViewHolder(view){
        val distNum = view.findViewById<TextView>(R.id.distNum)!!
        val durationNum = view.findViewById<TextView>(R.id.durationNum)!!
        val date = view.findViewById<TextView>(R.id.date)!!
        val map = view.findViewById<MapView>(R.id.mapViewRec)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.walk, parent, false)
        return WalkViewHolder(view)
    }

    override fun getItemCount(): Int {
        return walkList.size
    }

    override fun onBindViewHolder(holder: WalkViewHolder, position: Int) {
        holder.date.text = walkList[position].walkStartDateTime.split("T")[0]
        holder.durationNum.text = walkList[position].walkingTime.toString()
        holder.distNum.text = DecimalFormat("0:00").format(walkList[position].distance/1000)
        holder.map.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
            }
            override fun onMapError(error: Exception?) {
            }
        }, object: KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {
                val routeLineManager = map.routeLineManager
                val routeLayer = routeLineManager!!.layer
                val routeLineStyleSet = RouteLineStylesSet.from(
                    RouteLineStyles.from(
                        RouteLineStyle.from(20f, Color.parseColor("#f2d83c"), 0f, 0).setZoomLevel(5)))

                //walkList에서 LatLng로 이루어진 배열 생성해서 넣어주기

                val segment = RouteLineSegment.from().setStyles(routeLineStyleSet.getStyles(0))
                val options = RouteLineOptions.from(segment).setStylesSet(routeLineStyleSet)
                routeLayer.addRouteLine(options)
            }
            override fun getZoomLevel(): Int {
                return 15
            }
        })
    }
}