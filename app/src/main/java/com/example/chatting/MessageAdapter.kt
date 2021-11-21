package com.example.chatting

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.recieve.view.*
import kotlinx.android.synthetic.main.send.view.*

class MessageAdapter(var context: Context,var list:ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var sent=2
    val rcv=1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1)
        {
            val view= LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return rcvHolder(view)
        }
        else
        {
            val view=LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return sentHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder::class.java==sentHolder::class.java) {
            val viewholder = holder as sentHolder
            viewholder.setData(list[position])
        }
        else
        {
            val viewholder = holder as rcvHolder
            viewholder.setData(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        var msg=list[position]
        Log.d("TAG", (((FirebaseAuth.getInstance().currentUser?.uid?.toString()+" "+ msg.senderId.toString()))))
        if(FirebaseAuth.getInstance().currentUser?.uid?.toString().equals(msg.senderId.toString()))
        {
            return sent
        }
        else {
            return rcv
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class sentHolder(itemview:View): RecyclerView.ViewHolder(itemview) {
        fun setData(message: Message)
        {
            itemView.tvSend.text=message.message.toString()
        }
    }
    class rcvHolder(itemview:View): RecyclerView.ViewHolder(itemview) {
        fun setData(message: Message)
        {
            itemView.tvRcv.text=message.message.toString()
        }
    }
}