package com.example.chatting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var user:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth= FirebaseAuth.getInstance()

        button2.setOnClickListener(View.OnClickListener {
            PerformAuth()
            startActivity(Intent(this,MainActivity::class.java))
        })
    }
    private fun PerformAuth() { if ( etPassword.text.length < 8
        ) etPassword.setError("Enter Proper Password Format") else {
            mAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@register, "Regidter SuccesFull", Toast.LENGTH_LONG).show()
                    addUsersToDB(etEmail.text.toString(),mAuth.currentUser?.uid)
                    login()
                } else {
                    Toast.makeText(this@register, "" + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun login() {
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun addUsersToDB(email: String, uid: String?) {
        FirebaseDatabase.getInstance().reference.child("Users").child("$uid").setValue("$email")
    }
}