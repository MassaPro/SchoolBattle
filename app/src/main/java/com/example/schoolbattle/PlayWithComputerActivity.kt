package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.example.schoolbattle.gameswithcomp.*

var delayTime : Long = 1000


class PlayWithComputerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_with_computer)
        CONTEXT = this

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val yourName = prefs.getString("username", "") // имя пользователя
        val gameType = intent.getStringExtra("gameName") // тип игры

        when (gameType) {
            "BoxGame" -> {
                val intent = Intent(this,
                    BoxGameWithComputer::class.java)
                startActivity(intent)
                finish()
            }
            "AngleGame" -> {
                val intent = Intent(this,
                    ConersWithComputer::class.java)
                startActivity(intent)
                finish()
            }
            "DotGame" -> {
                val intent = Intent(this,
                    DotGameWithComputer::class.java)
                startActivity(intent)
                finish()
            }
            "Reversi" -> {
                val intent = Intent(this,
                    ReversiWithComputer::class.java)
                startActivity(intent)
                finish()
            }
            "SnakeGame" -> {
                val intent = Intent(this,
                    SnakeGameWithComputer::class.java)
                startActivity(intent)
                finish()
            }
            "VirusGame" -> {
                val intent = Intent(this,
                    VirusWithComputer::class.java)
                startActivity(intent)
                finish()
            }
            "XOGame" -> {
                val intent = Intent(this,
                    XOGame_withComputer::class.java)
                startActivity(intent)
                finish()
            }
        }





    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}
