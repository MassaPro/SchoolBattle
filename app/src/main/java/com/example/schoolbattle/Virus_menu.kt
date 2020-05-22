package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.boxs_menu.*
import kotlinx.android.synthetic.main.virus_menu.*

class Virus_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.virus_menu)

        button_virus_for_two_players.setOnClickListener {
            val intent = Intent(this, Virus_activity::class.java)
            startActivity(intent)
        }
    }
}
