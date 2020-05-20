package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_points_menu.*

class points_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_menu)

        button_points_for_two_players.setOnClickListener {
            val intent = Intent(this, point_activity::class.java)
            startActivity(intent)
        }
    }

}
