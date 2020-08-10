package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        CONTEXT = this


        if (Design == "Egypt"){
            sign_up_menu.setBackgroundResource(R.drawable.sign_up_egypt);
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if (Design == "Casino"){
            sign_up_menu.setBackgroundResource(R.drawable.sign_up_egypt);
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if (Design == "Rome"){
            sign_up_menu.setBackgroundResource(R.drawable.sign_in_rome);
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTextColor(Color.rgb(193, 150, 63))
        }
        else if (Design == "Gothic"){
            sign_up_menu.setBackgroundResource(R.drawable.sign_in_gothic);
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTextColor(Color.WHITE)

            nameTextInit.setTextColor(Color.WHITE)
            nameTextInit.setBackgroundColor(Color.rgb(30, 30, 30))

            passwordTextInit.setTextColor(Color.WHITE)
            passwordTextInit.setBackgroundColor(Color.rgb(30, 30, 30))

            repeatPassword.setTextColor(Color.WHITE)
            repeatPassword.setBackgroundColor(Color.rgb(30, 30, 30))
        }
        else if (Design == "Japan"){
            sign_up_menu.setBackgroundResource(R.drawable.sign_in_japan);
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTextColor(Color.BLACK)

            nameTextInit.setTextColor(Color.BLACK)
            nameTextInit.setBackgroundColor(Color.WHITE)

            passwordTextInit.setTextColor(Color.BLACK)
            passwordTextInit.setBackgroundColor(Color.WHITE)

            repeatPassword.setTextColor(Color.BLACK)
            repeatPassword.setBackgroundColor(Color.WHITE)
        }
        else if (Design == "Noir"){
            sign_up_menu.setBackgroundResource(R.drawable.sign_in_noir);
            signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            signUpButton.setBackgroundColor(Color.argb(0, 0, 0, 0))
            signUpButton.setTextColor(Color.WHITE)

            nameTextInit.setTextColor(Color.WHITE)
            nameTextInit.setBackgroundColor(Color.rgb(30, 30, 30))

            passwordTextInit.setTextColor(Color.WHITE)
            passwordTextInit.setBackgroundColor(Color.rgb(30, 30, 30))

            repeatPassword.setTextColor(Color.WHITE)
            repeatPassword.setBackgroundColor(Color.rgb(30, 30, 30))
        }

        fun noSpace() {
            Toast.makeText(this,"Name should not contain spaces", Toast.LENGTH_LONG).show()
        }

        nameTextInit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (p0.isNotEmpty() && p0.last() == ' ') {
                        noSpace()
                    }
                }
            }
        })


        signUpButton.setOnClickListener {
            val name = nameTextInit.text.toString()
            val password = passwordTextInit.text.toString()
            val repeatPassword = repeatPassword.text.toString()


            if (name.isEmpty() || password.isEmpty())  {
                Toast.makeText(this,"Please check your name and password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (name.contains(' '))  {
                noSpace()
                return@setOnClickListener
            }

            if (password != repeatPassword) {
                Toast.makeText(this,"Password mismatch", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.length > 20 || password.length < 5) {
                Toast.makeText(this,"Password length should be from 5 to 20", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (name.length > 10 || name.length < 3) {
                Toast.makeText(this,"Name length should be from 3 to 10", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            fun nextActivity() {
                val intent = Intent(this, NavigatorActivity::class.java)
                startActivity(intent)
                val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("username", name)
                editor.apply()
                SignIn.finish()
                finish()
            }

            fun exists() {
                Toast.makeText(this,"Name $name already exists", Toast.LENGTH_LONG).show()
            }

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child("Users").hasChild(name)) {
                        exists()
                    } else {
                        val newUser = UserClass(name, password)
                        myRef.child("Users").child(name).setValue(newUser)
                        nextActivity()
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this


    }
}
