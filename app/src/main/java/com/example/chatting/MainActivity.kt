package com.example.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var mUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth= FirebaseAuth.getInstance()

        button.setOnClickListener(View.OnClickListener {
            if(etName.text.toString().length>2&&etNumber.text.toString().length>4) {
                PerformLogin()
            }
        })
        button2.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,register::class.java))
        })
    }
    private fun PerformLogin() {
        val email: String = etName.text.toString()
        val pass: String = etNumber.text.toString()
        if ( pass.length < 8
        ) etPassword.setError("Enter Proper Password Format")
        else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Login SuccesFull", Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this,Home::class.java))
                } else {
                    Toast.makeText(this, "" + task.exception, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}