package com.example.schoolbattle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_cross_nul_activity.*


class Cross_null_activity : AppCompatActivity() {

    private var customCanvas: CanvasView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cross_nul_activity)
        customCanvas = findViewById<View>(R.id.signature_canvas) as CanvasView

        if(signature_canvas.Exit == 1)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }


}
