package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val intent = Intent(this, StupidGameActivity::class.java)
        startActivity(intent)
    }
}
