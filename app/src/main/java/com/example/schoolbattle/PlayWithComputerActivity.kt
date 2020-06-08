package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schoolbattle.gameswithcomp.XOGame_withComputer

class PlayWithComputerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_with_computer)
        CONTEXT = this

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val yourName = prefs.getString("username", "") // имя пользователя
        val gameType = intent.getStringExtra("gameName") // тип игры

        if(gameType == "XOGame")
        {
            finish()
            val intent = Intent(this,
                XOGame_withComputer::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}
