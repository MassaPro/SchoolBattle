package com.sga.schoolbattle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.engine.initEconomyParams
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.signUpButton

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        CONTEXT = this


        when (Design) {
            "Normal" -> {
                sign_up_menu.setBackgroundResource(R.drawable.game_menu_normal);
                signUpButton.setBackgroundResource(R.drawable.button)
                nameTextInit.setTextColor(Color.BLACK)
                passwordTextInit.setTextColor(Color.BLACK)
                repeatPassword.setTextColor(Color.BLACK)


            }
            "Egypt" -> {
                sign_up_menu.setBackgroundResource(R.drawable.sign_up_egypt);
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                nameTextInit.setTextColor(Color.BLACK)
                passwordTextInit.setTextColor(Color.BLACK)
                repeatPassword.setTextColor(Color.BLACK)
                signUpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                signUpRegistrationText.setTextColor(Color.BLACK)
            }
            "Casino" -> {
                sign_up_menu.setBackgroundResource(R.drawable.game_menu_casino);
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.YELLOW)
                nameTextInit.setTextColor(Color.YELLOW)
                passwordTextInit.setTextColor(Color.YELLOW)
                repeatPassword.setTextColor(Color.YELLOW)
                signUpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                signUpRegistrationText.setTextColor(Color.YELLOW)
            }
            "Rome" -> {
                sign_up_menu.setBackgroundResource(R.drawable.sign_in_rome);
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(rgb(193, 150, 63))
                nameTextInit.setTextColor(rgb(193, 150, 63))
                passwordTextInit.setTextColor(rgb(193, 150, 63))
                repeatPassword.setTextColor(rgb(193, 150, 63))
                signUpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                signUpRegistrationText.setTextColor(rgb(193, 150, 63))
            }
            "Gothic" -> {
                sign_up_menu.setBackgroundResource(R.drawable.sign_in_gothic);
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.WHITE)

                nameTextInit.setTextColor(Color.WHITE)
                nameTextInit.setBackgroundColor(rgb(30, 30, 30))

                passwordTextInit.setTextColor(Color.WHITE)
                passwordTextInit.setBackgroundColor(rgb(30, 30, 30))

                repeatPassword.setTextColor(Color.WHITE)
                repeatPassword.setBackgroundColor(rgb(30, 30, 30))

                signUpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                signUpRegistrationText.setTextColor(Color.WHITE)
            }
            "Japan" -> {
                sign_up_menu.setBackgroundResource(R.drawable.sign_in_japan);
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.BLACK)

                nameTextInit.setTextColor(Color.BLACK)
                nameTextInit.setBackgroundColor(Color.WHITE)

                passwordTextInit.setTextColor(Color.BLACK)
                passwordTextInit.setBackgroundColor(Color.WHITE)

                repeatPassword.setTextColor(Color.BLACK)
                repeatPassword.setBackgroundColor(Color.WHITE)
                signUpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                signUpRegistrationText.setTextColor(Color.BLACK)
            }
            "Noir" -> {
                sign_up_menu.setBackgroundResource(R.drawable.sign_in_noir);
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.WHITE)

                nameTextInit.setTextColor(Color.WHITE)
                nameTextInit.setBackgroundColor(rgb(30, 30, 30))

                passwordTextInit.setTextColor(Color.WHITE)
                passwordTextInit.setBackgroundColor(rgb(30, 30, 30))

                repeatPassword.setTextColor(Color.WHITE)
                repeatPassword.setBackgroundColor(rgb(30, 30, 30))
                signUpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                signUpRegistrationText.setTextColor(Color.WHITE)
            }
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

            for (i in name) {
                if ((i > 'z' || i < 'a') && (i > '9' || i < '0') && (i > 'Z' || i < 'A')) {
                    Toast.makeText(this,"Name must consist of A..Z, a..z or 0..9", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            for (i in password) {
                if ((i > 'z' || i < 'a') && (i > '9' || i < '0') && (i > 'Z' || i < 'A')) {
                    Toast.makeText(this,"Password must consist of A..Z, a..z or 0..9", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
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
                val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("username", name)
                editor.apply()
                initEconomyParams(this)
                val intent = Intent(this, NavigatorActivity::class.java)
                startActivity(intent)
                SignIn.finish()
                finish()
            }

            fun exists() {
                Toast.makeText(this,"Name $name already exists", Toast.LENGTH_LONG).show()
            }

            myRef.child("Users").child("name").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        exists()
                    } else {
                        val newUser = UserClass(name, password)
                        myRef.child("Users").child(name).setValue(newUser)
                        myRef.child("Users").child(name).child("image").setValue("1").addOnCompleteListener {
                            nextActivity()
                        }
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
