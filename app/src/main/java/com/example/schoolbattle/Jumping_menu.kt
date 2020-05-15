package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_jumping.*
import kotlinx.android.synthetic.main.jumping_menu.*

class Jumping_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jumping_menu)

        button_jumping_two_players.setOnClickListener {
            val intent = Intent(this, Jumping_activity::class.java)
            startActivity(intent)
        }
    }
}
