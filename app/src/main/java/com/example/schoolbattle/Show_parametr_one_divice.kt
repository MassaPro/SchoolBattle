package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView

class Show_parametr_one_divice_one_Device(activity: Activity) {
    private val dialog_one_device = Dialog(activity)
    fun showResult_one_device() {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.parametrs_one_divice)

        dialog_one_device.show()
    }
    fun delete() {
        dialog_one_device.dismiss()
    }
}
