package com.example.schoolbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_jumping.*


class Jumping_activity : AppCompatActivity() {

    private var customCanvas_jumping: CanvasView_JUMPING? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jumping)
        customCanvas_jumping = findViewById<View>(R.id.signature_canvas1) as CanvasView_JUMPING

        button_jump_1.setOnClickListener {

        }

    }


}
