package com.example.schoolbattle

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_design.*

class Choose_design_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_design)

        design_big_button_0.text = "Нормл стайл"
        design_big_button_1.text = "Казино стайл"
        design_big_button_2.text = "Египет стайл"


        design_big_button_0.setOnClickListener {
            Design = "Normal"
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("design", "Normal")
            editor.apply()
        }
        if (Design == "Normal") {
            design_list_menu.setBackgroundColor(Color.WHITE)
        }

        design_big_button_1.setOnClickListener {
            Design = "Casino"
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("design","Casino")
            editor.apply()
        }
        if (Design == "Casino") {
            design_list_menu.setBackgroundResource(R.drawable.background_casino)
        }

        design_big_button_2.setOnClickListener {
            Design = "Egypt"
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("design", "Egypt")
            editor.apply()
        }
        if (Design == "Egypt") {
            design_list_menu.setBackgroundResource(R.drawable.background_egypt)
        }

    }
}
