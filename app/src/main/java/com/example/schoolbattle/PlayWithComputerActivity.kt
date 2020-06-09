package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schoolbattle.gameswithcomp.*

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
            val intent = Intent(this,XOGame_withComputer::class.java)
            startActivity(intent)
            finish()
        }

        if(gameType == "AngleGame")
        {
            val intent = Intent(this,
                ConersWithComputer::class.java)
            startActivity(intent)
            finish()
        }

        if(gameType == "DotGame")
        {
            val intent = Intent(this,
                DotGameWithComputer::class.java)
            startActivity(intent)
            finish()
        }
        if(gameType == "SnakeGame")
        {
            val intent = Intent(this,
                SnakeGameWithComputer::class.java)
            startActivity(intent)
            finish()
        }
        if(gameType == "BoxGame")
        {
            val intent = Intent(this,
                BoxGameWithComputer::class.java)
            startActivity(intent)
            finish()
        }
        /*if(gameType == "VirusGame")       // ADD
        {
            val intent = Intent(this,VirusWithComputer::class.java)
            startActivity(intent)
            finish()
        }*/

        if(gameType == "Reversi")
        {
            val intent = Intent(this,
                ReversiWithComputer::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}
