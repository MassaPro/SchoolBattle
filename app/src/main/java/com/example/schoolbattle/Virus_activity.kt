package com.example.schoolbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_jumping.*

class Virus_activity : AppCompatActivity() {

    private var customCanvas_virus: CanvasView_VIRUS? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.virus_activity)
        customCanvas_virus = findViewById<View>(R.id.signature_canvas5) as CanvasView_VIRUS


    }
}
