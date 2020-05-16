package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.boxs_menu.*

class Boxs_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boxs_menu)

        button_boxs_for_two_players.setOnClickListener {
            val intent = Intent(this, Box_activity::class.java)
            startActivity(intent)
        }
    }
}
