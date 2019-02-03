package com.example.vkapifriends.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vkapifriends.R
import com.example.vkapifriends.models.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mFriendsList: ArrayList<FriendModel> = ArrayList()
    fun setupFriends(friendList: ArrayList<FriendModel>){
        mFriendsList.clear()
        mFriendsList.addAll(friendList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, p0, false)
        return FriendsViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mFriendsList.count()
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if(p0 is FriendsViewHolder){
            p0.bind(friendModel = mFriendsList[p1])
        }
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.friend_civ_avatar)
        private var mTxtUsername: TextView = itemView.findViewById(R.id.friend_txt_username)

        fun bind(friendModel: FriendModel){
            friendModel.avatar?.let { url ->
                Picasso.get().load(url).into(mCivAvatar)
            }

            mTxtUsername.text = "${friendModel.name} ${friendModel.surname}"
        }
    }
}