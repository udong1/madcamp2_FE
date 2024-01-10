package com.example.madcamp2_fe.my_walks

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.home.LocationData
import com.example.madcamp2_fe.home.Walk
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import java.text.DecimalFormat

class WalkAdapter(var walkList : ArrayList<Walk>):RecyclerView.Adapter<WalkAdapter.WalkViewHolder>(){
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
        holder.durationNum.text = String.format("%02d:%02d",walkList[position].walkingTime/60,walkList[position].walkingTime%60)
        holder.distNum.text = String.format("%.3fkm", walkList[position].distance/1000)
        val walkRoute = ArrayList<LatLng>(walkList[position].locList.size)
        for (index in 0 until walkList[position].locList.size){
            val walkRouteItem = walkList[position].locList[index]
            walkRoute.add(walkRouteItem.recordOrder.toInt(), LatLng.from(walkRouteItem.latitude, walkRouteItem.longitude))
        }
        Log.d("변환한 walkRoute","$walkRoute")
        holder.map.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
            }
            override fun onMapError(error: Exception?) {
            }
        }, object: KakaoMapReadyCallback() {
            override fun onMapReady(map: KakaoMap) {
                map.moveCamera(CameraUpdateFactory.newCenterPosition(LatLng.from(walkRoute.first().latitude, 127.366)))
                map.moveCamera(CameraUpdateFactory.zoomTo(15))
                val routeLineManager = map.routeLineManager
                val labelManager = map.labelManager
                val labelLayer = labelManager!!.layer
                val routeLayer = routeLineManager!!.layer
                val routeLineStyleSet = RouteLineStylesSet.from(
                    RouteLineStyles.from(
                        RouteLineStyle.from(20f, Color.parseColor("#f2d83c"), 0f, 0).setZoomLevel(5)))

                val segment = RouteLineSegment.from(walkRoute).setStyles(routeLineStyleSet.getStyles(0))
                val options = RouteLineOptions.from(segment).setStylesSet(routeLineStyleSet)
                routeLayer.addRouteLine(options)

                Log.d("route representation","called")

                val startMarker = labelManager!!.addLabelStyles(
                    LabelStyles.from("start", LabelStyle.from(R.drawable.start_mark).setAnchorPoint(0.5f, 0.5f))
                )
                val terminateMarker = labelManager!!.addLabelStyles(
                    LabelStyles.from("terminate", LabelStyle.from(R.drawable.terminate_mark).setAnchorPoint(0.5f, 0.5f))
                )
                labelLayer!!.addLabel(LabelOptions.from("start", LatLng.from(walkRoute.first().latitude, walkRoute.first().longitude)).setStyles(startMarker))
                labelLayer.addLabel(LabelOptions.from("terminate", LatLng.from(walkRoute.last().latitude, walkRoute.last().longitude)).setStyles(terminateMarker))
            }
            override fun getZoomLevel(): Int {
                return 15
            }
        })

    }
}