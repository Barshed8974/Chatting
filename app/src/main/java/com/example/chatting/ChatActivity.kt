package com.example.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    var senderRoom:String?=null
    var receiverRoom:String?=null
    lateinit var messageAdapter: MessageAdapter
    lateinit var msglist:ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiverUid=intent.getStringExtra("uid")

        val senderUid=FirebaseAuth.getInstance().currentUser?.uid.toString()

        senderRoom= receiverUid+senderUid
        receiverRoom=senderUid+receiverUid
        msglist= ArrayList()
        messageAdapter= MessageAdapter(this,msglist)
        chatRecycler.adapter=messageAdapter
        chatRecycler.layoutManager=LinearLayoutManager(this)

        supportActionBar?.title=name
        Log.d("TAG","tittle $name")
           //adding data to recycler view

        addDataToRecyclerview(senderRoom!!)

        ivSend.setOnClickListener(View.OnClickListener {
            val msg=etChat.text.toString()
            val msgObj=Message(msg,senderUid)

            FirebaseDatabase.getInstance().reference.child("Chats").child(senderRoom!!)
                .child("messege").push().setValue(msgObj).addOnSuccessListener {
                    FirebaseDatabase.getInstance().reference.child("Chats").child(receiverRoom!!)
                        .child("messege").push().setValue(msgObj)
                }
            etChat.setText("")
        })
    }

    private fun addDataToRecyclerview(senderRoom: String) {

        FirebaseDatabase.getInstance().reference.child("Chats").child(senderRoom).child("messege")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    msglist.clear()
                    for (shot in snapshot.children)
                    {
                        val message=shot.getValue(Message::class.java)
                        msglist.add(message!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}