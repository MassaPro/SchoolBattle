package com.example.schoolbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_game_list.*

class StupidGameActivityTwoPlayers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stupid_game_two_players)

        StupidGame.finish()
        NewGame.finish()
    }
}
