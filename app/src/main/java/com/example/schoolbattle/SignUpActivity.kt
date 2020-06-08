package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        CONTEXT = this

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
