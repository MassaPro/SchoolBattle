package com.example.schoolbattle


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_jumping.*


class Box_activity : AppCompatActivity() {

    private var customCanvas_boxs: CanvasView_Boxs? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.box_activity)
        customCanvas_boxs = findViewById<View>(R.id.signature_canvas2) as CanvasView_Boxs


    }


}
