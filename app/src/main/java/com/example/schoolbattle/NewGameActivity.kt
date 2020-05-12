package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

var NewGame: Activity = Activity()

class NewGameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        //val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        //val globalName = prefs.getString("username", "")
        val intent = Intent(applicationContext, StupidGameActivity::class.java)
        startActivity(intent)
        finish()
    }
}

