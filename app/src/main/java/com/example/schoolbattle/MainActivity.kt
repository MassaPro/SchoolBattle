package com.example.schoolbattle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.schoolbattle.Cross_null_activity
import com.example.schoolbattle.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    private var customCanvas: CanvasView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Button_new_game.setOnClickListener {
            val intent = Intent(this, Cross_null_activity::class.java)
            startActivity(intent)
        }
    }
}