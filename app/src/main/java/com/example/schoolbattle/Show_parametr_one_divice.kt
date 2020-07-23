package com.example.schoolbattle


import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.Window
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.parametrs_one_divice.*


class Show_parametr_one_divice_one_Device(activity: Activity) {
    var c = activity
    private val dialog_one_device = Dialog(activity)
    fun showResult_one_device() {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.parametrs_one_divice)

        if(Design == "Egypt")
        {
            dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_egypt)

            dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.s))
            dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))

            dialog_one_device.parametrs_one_device_rules.setTypeface(ResourcesCompat.getFont(c, R.font.s))
            dialog_one_device.parametrs_one_device_rules.setBackgroundColor(Color.argb(0, 0,0,0))

            dialog_one_device.parametrs_one_device_xs.setTypeface(ResourcesCompat.getFont(c, R.font.s))
            dialog_one_device.parametrs_one_device_xs.setBackgroundColor(Color.argb(0, 0,0,0))
        }
        else if(Design == "Casino")
        {
            dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background2_casino)

            dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.casino))
            dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.YELLOW)

            dialog_one_device.parametrs_one_device_rules.setTypeface(ResourcesCompat.getFont(c, R.font.casino))
            dialog_one_device.parametrs_one_device_rules.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_rules.setTextColor(Color.YELLOW)

            dialog_one_device.parametrs_one_device_xs.setTypeface(ResourcesCompat.getFont(c, R.font.casino))
            dialog_one_device.parametrs_one_device_xs.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_xs.setTextColor(Color.YELLOW)
        }
        else if(Design == "Rome")
        {
            dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_rome)

            dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.rome))
            dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.rgb(193, 150, 63))

            dialog_one_device.parametrs_one_device_rules.setTypeface(ResourcesCompat.getFont(c, R.font.rome))
            dialog_one_device.parametrs_one_device_rules.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_rules.setTextColor(Color.rgb(193, 150, 63))

            dialog_one_device.parametrs_one_device_xs.setTypeface(ResourcesCompat.getFont(c, R.font.rome))
            dialog_one_device.parametrs_one_device_xs.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_xs.setTextColor(Color.rgb(193, 150, 63))
        }
        else if(Design == "Gothic")
        {
            dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_gothic)

            dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.gothic))
            dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.WHITE)

            dialog_one_device.parametrs_one_device_rules.setTypeface(ResourcesCompat.getFont(c, R.font.gothic))
            dialog_one_device.parametrs_one_device_rules.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_rules.setTextColor(Color.WHITE)

            dialog_one_device.parametrs_one_device_xs.setTypeface(ResourcesCompat.getFont(c, R.font.gothic))
            dialog_one_device.parametrs_one_device_xs.setBackgroundColor(Color.argb(0, 0,0,0))
            dialog_one_device.parametrs_one_device_xs.setTextColor(Color.WHITE)
        }


        dialog_one_device.show()
    }
    fun delete() {
        dialog_one_device.dismiss()
    }
}
