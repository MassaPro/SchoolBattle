package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_settings_fragment.*

class Show_language_selection(activity: FragmentActivity)  {
    private val dialog_language = Dialog(activity)
    fun showResult_window(type_activity: Activity) {
        dialog_language.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_language.setCancelable(false)
        dialog_language.setCanceledOnTouchOutside(true)
        dialog_language.setContentView(R.layout.show_language_selection)


        val button_English = dialog_language.findViewById(R.id.languageSelected_English) as TextView    // на самом деле это текст, а не кнопки
        val button_Russia = dialog_language.findViewById(R.id.languageSelected_Russia) as TextView

        fun unselected() {
            button_English.setTypeface(null, Typeface.NORMAL)
            button_Russia.setTypeface(null, Typeface.NORMAL)
        }


        val prefs = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var data_from_memory = prefs.getString("language", "").toString()

        if (data_from_memory == "") {   // for safety
            val editor = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("language", "EN")
            editor.apply()
            data_from_memory = "EN"
        }
        if (data_from_memory == "EN") {
            button_English.setTypeface(null, Typeface.BOLD);
        }
        if (data_from_memory == "RU") {
            button_Russia.setTypeface(null, Typeface.BOLD);
        }


        button_English.setOnClickListener {
            unselected()
            val editor = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("language", "EN")
            editor.apply()
            button_English.setTypeface(null, Typeface.BOLD);
            val prefs = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val data_from_memory = prefs.getString("language", "").toString()
            val handler = android.os.Handler()
            handler.postDelayed({ dialog_language.dismiss() }, 300)
        }

        button_Russia.setOnClickListener {
            unselected()
            val editor = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("language", "RU")
            editor.apply()
            button_Russia.setTypeface(null, Typeface.BOLD);
            val prefs = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val data_from_memory = prefs.getString("language", "").toString()
            val handler = android.os.Handler()
            handler.postDelayed({ dialog_language.dismiss() }, 300)
        }

        dialog_language.show()


    }



    fun delete() {
        dialog_language.dismiss()
    }
}
