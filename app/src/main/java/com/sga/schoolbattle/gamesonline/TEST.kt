package com.sga.schoolbattle.gamesonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sga.schoolbattle.R

class TEST : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_games_temlate)

        //var c = object : Game {
          //  override fun get(): Int {
            //   return 6
            //}
        //}
    }
}

interface Game {
    //fun get(): Int {
      //  return 5
    //}
}
