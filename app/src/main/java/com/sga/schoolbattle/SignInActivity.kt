package com.sga.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.*
import com.sga.schoolbattle.shop.locale_context
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_sign_in.*


var database = FirebaseDatabase.getInstance()
var myRef: DatabaseReference = database.reference

var SignIn: Activity = Activity()

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        CONTEXT = this
        locale_context = this

        when (Design) {
            "Normal" -> {
                sign_in_menu.setBackgroundResource(R.drawable.game_menu_normal);
                signInButton.setBackgroundResource(R.drawable.button)
                signUpButton.setBackgroundResource(R.drawable.button)
                nameText.setTextColor(Color.BLACK)
                passwordText.setTextColor(Color.BLACK)

            }
            "Egypt" -> {
                sign_in_menu.setBackgroundResource(R.drawable.sign_in_egypt);
                signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                signInButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))

                nameText.setTextColor(Color.BLACK)

                passwordText.setTextColor(Color.BLACK)
                signIpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            }
            "Casino" -> {
                sign_in_menu.setBackgroundResource(R.drawable.game_menu_casino);
                signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                signInButton.setBackgroundColor(argb(0, 0, 0, 0))
                signInButton.setTextColor(Color.YELLOW)

                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.YELLOW)

                nameText.setTextColor(Color.YELLOW)

                passwordText.setTextColor(Color.YELLOW)
                signIpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                signIpRegistrationText.setTextColor(Color.YELLOW)
            }
            "Rome" -> {
                sign_in_menu.setBackgroundResource(R.drawable.sign_in_rome);
                signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                signInButton.setBackgroundColor(argb(0, 0, 0, 0))
                signInButton.setTextColor(rgb(193, 150, 63))

                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(rgb(193, 150, 63))

                nameText.setTextColor(rgb(193, 150, 63))

                passwordText.setTextColor(rgb(193, 150, 63))
                signIpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                signIpRegistrationText.setTextColor(rgb(193, 150, 63))
            }
            "Gothic" -> {
                sign_in_menu.setBackgroundResource(R.drawable.sign_in_gothic);

                nameText.setTextColor(Color.WHITE)
                nameText.setBackgroundColor(rgb(30, 30, 30))

                passwordText.setTextColor(Color.WHITE)
                passwordText.setBackgroundColor(rgb(30, 30, 30))

                signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                signInButton.setTextColor(Color.WHITE)
                signInButton.setBackgroundColor(argb(0, 0, 0, 0))

                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.WHITE)
                signIpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                signIpRegistrationText.setTextColor(Color.WHITE)
            }
            "Japan" -> {
                sign_in_menu.setBackgroundResource(R.drawable.sign_in_japan);

                nameText.setTextColor(Color.BLACK)
                nameText.setBackgroundColor(Color.WHITE)

                passwordText.setTextColor(Color.BLACK)
                passwordText.setBackgroundColor(Color.WHITE)

                signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                signInButton.setTextColor(Color.BLACK)
                signInButton.setBackgroundColor(argb(0, 0, 0, 0))

                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.BLACK)
                signIpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                signIpRegistrationText.setTextColor(Color.BLACK)
            }
            "Noir" -> {
                sign_in_menu.setBackgroundResource(R.drawable.sign_in_noir);

                nameText.setTextColor(Color.WHITE)
                nameText.setBackgroundColor(rgb(30, 30, 30))

                passwordText.setTextColor(Color.WHITE)
                passwordText.setBackgroundColor(rgb(30, 30, 30))

                signInButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                signInButton.setTextColor(Color.WHITE)
                signInButton.setBackgroundColor(argb(0, 0, 0, 0))

                signUpButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                signUpButton.setBackgroundColor(argb(0, 0, 0, 0))
                signUpButton.setTextColor(Color.WHITE)
                signIpRegistrationText.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                signIpRegistrationText.setTextColor(Color.WHITE)
            }
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

            myRef.child("Users").child(name).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        if (password == snapshot.child("password").value) {
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

        showDialog()
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")

        if (username != "") {
            finish()
        }

    }

    private fun showDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog

        // Initialize an array of colors
        val array = arrayOf("Русский","English")

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(locale_context!!)

        // Set a title for alert dialog


        var checkedItem = 0
        if(LANGUAGE == "Russian")
        {
            builder.setTitle("Choose a language")
            checkedItem = 0
        }
        else
        {
            builder.setTitle("Выбор языка")
            checkedItem =1
        }
        builder.setSingleChoiceItems(array,checkedItem) { _, which->
            if(which==0)
            {
                LANGUAGE = "Russian"
                val editor =  locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("language","russian")
                editor.apply()
            }
            else
            {
                LANGUAGE = "English"
                val editor =  locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("language","english")
                editor.apply()
            }
        }
        builder.setPositiveButton(
            "OK"
        ) { dialog, _ ->
            dialog.dismiss()
        }

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}
