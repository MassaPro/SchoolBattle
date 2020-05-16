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
        customCanvas = findViewById<View>(R.id.signature_canvas) as CanvasView  //TODO НЕ УМЕЮ ОТСЮДА ВЛИЯТЬ НА КЛАСС :(
        signature_canvas
        if(signature_canvas.Exit == 1)
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signature_canvas.setOnClickListener {
            if(signature_canvas.FIELD[0][0] == 1)
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        button1.setOnClickListener {
            //val intent = Intent(this, Cross_null_activity::class.java)
            //startActivity(intent)
            signature_canvas.FIELD[2][3] = 1
        }

    }


}
