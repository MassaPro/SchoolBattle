package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.gamesonedevice.*
import kotlinx.android.synthetic.main.show_rules.*

class Show_rules(activity: Activity) {
    private val dialog_one_device = Dialog(activity)
    fun show(Gametype:Int) {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.show_rules)

        if (Design == "Egypt"){
            dialog_one_device.rules.setBackgroundResource(R.drawable.background_egypt);

        }

        dialog_one_device.show()
    }
    fun delete() {
        dialog_one_device.dismiss()
    }
}