package com.example.schoolbattle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var customCanvas: CanvasView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_cross_null.setOnClickListener {
            val intent = Intent(this, Cross_nul_menu::class.java)
            startActivity(intent)
        }
        button_jumping.setOnClickListener(){
            val intent = Intent(this, Jumping_menu::class.java)
            startActivity(intent)
        }
        button_boxs.setOnClickListener {
            val intent = Intent(this, Boxs_menu::class.java)
            startActivity(intent)
        }
        button_point.setOnClickListener {
            val intent = Intent(this, points_menu::class.java)
            startActivity(intent)
        }
    }
}