package com.example.madcamp2_fe.friends_walks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madcamp2_fe.R
import com.example.madcamp2_fe.WalkViewModel
import com.example.madcamp2_fe.user_client.UserClientManager
import com.example.madcamp2_fe.utils.RESPONSE_STATE
import com.kakao.sdk.user.Constants
import de.hdodenhof.circleimageview.CircleImageView

class FriendSearchAdapter(var friendSearchList : List<FriendBySearchResponse> , val walkViewModel:WalkViewModel) :
    RecyclerView.Adapter<FriendSearchAdapter.FriendSearchViewHolder>(){

    class FriendSearchViewHolder(view : View): RecyclerView.ViewHolder(view){
        val friendProfile = view.findViewById<CircleImageView>(R.id.searchedFriendProfile)!!
        val friendName = view.findViewById<TextView>(R.id.searchedFriendName)!!
        val friendEmail = view.findViewById<TextView>(R.id.searchedFriendEmail)!!
        val add = view.findViewById<ImageButton>(R.id.addSearchedFriend!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.searched_friend, parent, false)
        return FriendSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendSearchList.size
    }

    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        holder.friendName.text = friendSearchList[position].nickname
//        holder.friendEmail.text = friendList[position].email
        if(friendSearchList[position].profileImg!=null){
            holder.apply{
                Glide.with(holder.itemView.context)
                    .load(friendSearchList[position].profileImg)
                    .into(friendProfile)
            }
        }
        holder.friendEmail.text = friendSearchList[position].email
        holder.add.setOnClickListener {
            Log.d("add button clicked", "position : $position")
            UserClientManager.instance.followFriend(walkViewModel.getUserAccessToken(),
                followedUserId = friendSearchList[position].userId,
                completion = {
                        responseState ->
                    when(responseState){
                        RESPONSE_STATE.OKAY ->{
                            Log.d(Constants.TAG, "팔로우 성공")
                        }
                        RESPONSE_STATE.FAIL ->{
                            //Toast.makeText(requireActivity(), "없데이트", Toast.LENGTH_SHORT).show()
                            Log.d(Constants.TAG, "팔로우 실패")
                        }
                    }
                })

        }
    }


}