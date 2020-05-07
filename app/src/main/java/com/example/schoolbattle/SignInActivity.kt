package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*


var database = FirebaseDatabase.getInstance()
var myRef: DatabaseReference = database.getReference("SchoolBattle")

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        fun startMainActivity() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        fun wrongPassword() {
            Toast.makeText(this,"Wrong password", Toast.LENGTH_LONG).show()
        }

        fun wrongName() {
            Toast.makeText(this,"Wrong name", Toast.LENGTH_LONG).show()
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInButton.setOnClickListener {
            val name = nameText.text.toString()
            val password = passwordText.text.toString()

            if (name.isEmpty() || password.isEmpty())  {
                Toast.makeText(this,"Please check your name and password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child("Users").hasChild(name)) {
                        if (password == snapshot.child("Users").child(name).child("password").value) {
                            GlobalName = name
                            startMainActivity()
                        } else {
                            wrongPassword()
                        }
                    } else {
                        wrongName()
                    }
                }
            })
        }
    }
}
