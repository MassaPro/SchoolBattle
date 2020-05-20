package com.example.schoolbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_jumping.*


class point_activity : AppCompatActivity() {

    private var customCanvas_point: CanvasView_POINTS? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.point_activity)
        customCanvas_point = findViewById<View>(R.id.signature_canvas3) as CanvasView_POINTS
    }


}