package com.example.schoolbattle

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_game_list.*

var NewGame: Activity = Activity()

class NewGameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        NewGame = this
        val intent = Intent(this, StupidGameActivity::class.java)
        startActivity(intent)
    }
}
