package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*


var database = FirebaseDatabase.getInstance()
var myRef: DatabaseReference = database.getReference("SchoolBattle")

var SignIn: Activity = Activity()

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        CONTEXT = this

        if (Design == "Egypt"){
            sign_in_menu.setBackgroundResource(R.drawable.sign_in_egypt);
            signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            signInButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if (Design == "Casino"){
            sign_in_menu.setBackgroundResource(R.drawable.sign_in_egypt);
            signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            signInButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        if (Design == "Rome"){
            //sign_in_menu.setBackgroundResource(R.drawable.sign_in_rome);
            signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            signInButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }

        SignIn = this
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")

        if (username != "" ) {
            val intent = Intent(this, NavigatorActivity::class.java)
            startActivity(intent)
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

            fun startMainActivity() {
                val intent = Intent(this, NavigatorActivity::class.java)
                startActivity(intent)
                val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("username", name)
                editor.apply()
                finish()
            }
            if (name.isEmpty() || password.isEmpty())  {
                Toast.makeText(this,"Please check your name and password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child("Users").hasChild(name)) {
                        if (password == snapshot.child("Users").child(name).child("password").value) {
                            //globalName = name
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

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")

        if (username != "") {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}
