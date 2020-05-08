package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cross_nul_activity.*

class Cross_nul_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_nul_menu)

        button1.setOnClickListener(){
            val intent = Intent(this, Cross_null_activity::class.java)
            startActivity(intent)
        }

    }
}
