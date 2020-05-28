package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PlayWithComputerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_with_computer)

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val yourName = prefs.getString("username", "") // имя пользователя
        val gameType = intent.getStringExtra("gameName") // тип игры

        if(gameType == "XOGame")
        {
            finish()
            val intent = Intent(this,XOGame_withComputer::class.java)
            startActivity(intent)
        }

    }
}
