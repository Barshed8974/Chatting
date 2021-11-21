package com.example.chatting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    lateinit var userAdapter: UserAdapter
    lateinit var userList: ArrayList<Users>
    lateinit var mUser: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Log.d("TAG","1")
        userList=ArrayList()

        Log.d("TAG","2")
        userAdapter= UserAdapter(this,userList)



        Log.d("TAG","3")
        userRecycler.adapter=userAdapter

        userRecycler.layoutManager=LinearLayoutManager(this)

        Log.d("TAG","4")
        mUser= FirebaseAuth.getInstance()

        Log.d("TAG","5")
        FirebaseDatabase.getInstance().reference.child("Users").addValueEventListener(object :ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TAG","6")
                for (shot in snapshot.children)
                {
                    Log.d("TAG",shot.value.toString()+" "+mUser.currentUser?.uid)
                    Log.d("TAG", (mUser.currentUser?.email.toString() == shot.key.toString()).toString())
                    if(mUser.currentUser?.uid.toString() != shot.key.toString()) {
                        userList.add(Users(shot.value.toString(), "", shot.key.toString()))
                        Log.d("TAG","in for")
                    }
                }
                userAdapter.notifyDataSetChanged()
                var x=userList.size.toString()
                Log.d("TAG",x+"x")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        Log.d("TAG","${userList.size}iooihj")
    }
}