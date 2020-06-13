package com.example.schoolbattle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_chouse_design.*

class Chouse_design_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chouse_design)

        design_big_button_0.text = "Нормл стайл"
        design_big_button_1.text = "Казино стайл"
        design_big_button_2.text = "Египет стайл"


        design_big_button_0.setOnClickListener {
            Design = "Normal"
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("design", "Normal")
            editor.apply()
        }

        design_big_button_1.setOnClickListener {
            Design = "Casino"
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("design","Casino")
            editor.apply()
        }

        design_big_button_2.setOnClickListener {
            Design = "Egypt"
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("design", "Egypt")
            editor.apply()
        }

    }
}
