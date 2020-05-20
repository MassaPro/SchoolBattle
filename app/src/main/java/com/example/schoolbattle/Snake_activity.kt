package com.example.schoolbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Snake_activity : AppCompatActivity() {

        private var customCanvas_snake: CanvasView_SNAKE? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.snake_activity)
            customCanvas_snake = findViewById<View>(R.id.signature_canvas4) as CanvasView_SNAKE
        }


    }