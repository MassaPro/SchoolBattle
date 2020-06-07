package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schoolbattle.gamesonedevice.*

class OneDevicePlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_device_play)

        CONTEXT = this

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val yourName = prefs.getString("username", "") // имя пользователя
        val gameType = intent.getStringExtra("gameName") // тип игры

        if(gameType == "XOGame")
        {
            val intent = Intent(this,
                XOGame_oneDivice::class.java)
            startActivity(intent)
            finish()
        }

        if(gameType == "AngleGame")
        {
            val intent = Intent(this,
                ConersOneDevice::class.java)
            startActivity(intent)
            finish()
        }

        if(gameType == "DotGame")
        {
            val intent = Intent(this,
                DotGameOneDivice::class.java)
            startActivity(intent)
            finish()
        }
        if(gameType == "SnakeGame")
        {
            val intent = Intent(this,
                SnakeGameOneDivice::class.java)
            startActivity(intent)
            finish()
        }
        if(gameType == "BoxGame")
        {
            val intent = Intent(this,
                BoxGameOneDivice::class.java)
            startActivity(intent)
            finish()
        }
        if(gameType == "VirusGame")
        {
            val intent = Intent(this,
                VirusOneDivice::class.java)
            startActivity(intent)
            finish()
        }
        if(gameType == "Reversi")
        {
            val intent = Intent(this,
                ReversiOneDivice::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}
