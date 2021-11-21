package com.example.chatting

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_layout.view.*

class UserAdapter(val context: Context,val userList:ArrayList<Users>):
    RecyclerView.Adapter<UserAdapter.userViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return userViewHolder(view)
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        holder.Setdata(userList[position])
        Log.d("TAG","bind")
        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("user",userList[position].name)
            intent.putExtra("uid",userList[position].uid)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
       return userList.size
    }
    class userViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        fun Setdata(users: Users)
        {
            itemView.chatUser.text=users.name
            Log.d("TAG","holder")
        }
    }
}