package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Show_parametr_with_computer(activity: Activity) {
    private val dialog_with_computer = Dialog(activity)
    fun showResult_with_computer(type_activity: Activity) {
        dialog_with_computer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_with_computer.setCancelable(false)
        dialog_with_computer.setCanceledOnTouchOutside(true)
        dialog_with_computer.setContentView(R.layout.parametrs_with_computer)

        val button_revanshe = dialog_with_computer.findViewById(R.id.parametrs_with_computer_configuring) as Button
        button_revanshe.setOnClickListener{
            Toast.makeText(type_activity,"Настройки", Toast.LENGTH_LONG).show()
        }

        dialog_with_computer.show()
    }
    fun delete() {
        dialog_with_computer.dismiss()
    }
}
