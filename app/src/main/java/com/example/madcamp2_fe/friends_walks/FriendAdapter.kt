package com.example.madcamp2_fe.friends_walks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat

class FriendAdapter(var friendList : ArrayList<FollowListResponse> ) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>(){

    class FriendViewHolder(view : View): RecyclerView.ViewHolder(view){
        val friendProfile = view.findViewById<CircleImageView>(R.id.friendProfile)!!
        val friendName = view.findViewById<TextView>(R.id.friendName)!!
        val date = view.findViewById<TextView>(R.id.friendWalkDate)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.friendName.text = friendList[position].nickname
//        holder.friendEmail.text = friendList[position].email
        if(friendList[position].profileImgUrl!=null){
            holder.apply{
                Glide.with(holder.itemView.context)
                    .load(friendList[position].profileImgUrl)
                    .into(friendProfile)
            }
        }
    }
}