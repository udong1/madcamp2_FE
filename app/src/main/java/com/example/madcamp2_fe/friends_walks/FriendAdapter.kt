package com.example.madcamp2_fe.friends_walks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.kotlin.konan.util.DependencySource
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        holder.date.text = friendList[position].recentWalkTime
        if(friendList[position].recentWalkTime != null){
            val duration = Duration.between(LocalDateTime.parse(friendList[position].recentWalkTime, DateTimeFormatter.ISO_DATE_TIME),LocalDateTime.now()).toDays()
            val ago = if(duration == 0L){
                "오늘 어슬렁한 사람"
            }else{
                "${duration}일 전 어슬렁"
            }
            holder.date.text = ago
        }
        else{
            holder.date.text = "아직 어슬렁하지 않은 사람"
        }
    }

    interface OnFriendClickListener{
        fun onClick(view : View, followListResponse: FollowListResponse)
    }

    fun setFriendClickListener(onFriendClickListener: OnFriendClickListener){
        this.friendClickListener = onFriendClickListener
    }

    private lateinit var friendClickListener: OnFriendClickListener

}