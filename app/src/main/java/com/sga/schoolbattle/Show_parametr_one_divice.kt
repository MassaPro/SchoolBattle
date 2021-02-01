package com.sga.schoolbattle


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Window
import android.widget.CompoundButton
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

        when (Design) {
            "Egypt" -> {
                dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_egypt)

                dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.egypt))
                dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))

                dialog_one_device.switch_parametrs_one_device_1.setTypeface(ResourcesCompat.getFont(c, R.font.egypt))
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))

                dialog_one_device.switch_parametrs_one_device_2.setTypeface(ResourcesCompat.getFont(c, R.font.egypt))
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))
            }
            "Casino" -> {
                dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background2_casino)

                dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.casino))
                dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.YELLOW)

                dialog_one_device.switch_parametrs_one_device_1.setTypeface(ResourcesCompat.getFont(c, R.font.casino))
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_1.setTextColor(Color.YELLOW)

                dialog_one_device.switch_parametrs_one_device_2.setTypeface(ResourcesCompat.getFont(c, R.font.casino))
                dialog_one_device.switch_parametrs_one_device_2.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_2.setTextColor(Color.YELLOW)
            }
            "Rome" -> {
                dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_rome)

                dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.rome))
                dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.rgb(193, 150, 63))

                dialog_one_device.switch_parametrs_one_device_1.typeface = ResourcesCompat.getFont(c, R.font.rome)
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_1.setTextColor(Color.rgb(193, 150, 63))

                dialog_one_device.switch_parametrs_one_device_2.setTypeface(ResourcesCompat.getFont(c, R.font.rome))
                dialog_one_device.switch_parametrs_one_device_2.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_2.setTextColor(Color.rgb(193, 150, 63))
            }
            "Gothic" -> {
                dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_gothic)

                dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.gothic))
                dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.WHITE)

                dialog_one_device.switch_parametrs_one_device_1.setTypeface(ResourcesCompat.getFont(c, R.font.gothic))
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_1.setTextColor(Color.WHITE)

                dialog_one_device.switch_parametrs_one_device_2.setTypeface(ResourcesCompat.getFont(c, R.font.gothic))
                dialog_one_device.switch_parametrs_one_device_2.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_2.setTextColor(Color.WHITE)
            }
            "Japan" -> {
                dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_japan)

                dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.japan))
                dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.BLACK)

                dialog_one_device.switch_parametrs_one_device_1.setTypeface(ResourcesCompat.getFont(c, R.font.japan))
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_1.setTextColor(Color.BLACK)

                dialog_one_device.switch_parametrs_one_device_2.setTypeface(ResourcesCompat.getFont(c, R.font.japan))
                dialog_one_device.switch_parametrs_one_device_2.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_2.setTextColor(Color.BLACK)
            }
            "Noir" -> {
                dialog_one_device.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_noir)

                dialog_one_device.parametrs_one_device_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.noir))
                dialog_one_device.parametrs_one_device_configuring.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.parametrs_one_device_configuring.setTextColor(Color.WHITE)

                dialog_one_device.switch_parametrs_one_device_1.setTypeface(ResourcesCompat.getFont(c, R.font.noir))
                dialog_one_device.switch_parametrs_one_device_1.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_1.setTextColor(Color.WHITE)

                dialog_one_device.switch_parametrs_one_device_2.setTypeface(ResourcesCompat.getFont(c, R.font.noir))
                dialog_one_device.switch_parametrs_one_device_2.setBackgroundColor(Color.argb(0, 0,0,0))
                dialog_one_device.switch_parametrs_one_device_2.setTextColor(Color.WHITE)
            }
            //настройка свитчера звука
            //настройка свитчера звука
        }


        dialog_one_device.switch_parametrs_one_device_2.isChecked = SOUND                //настройка свитчера звука
        dialog_one_device.switch_parametrs_one_device_2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            SOUND = isChecked
            val editor = c.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            if(SOUND)
            {
                editor?.putString("sound","true")
            }
            else
            {
                editor?.putString("sound","false")
            }
            editor?.apply()
        })

        dialog_one_device.switch_parametrs_one_device_1.isChecked = VIBRATION               //настройка свитчера звука
        dialog_one_device.switch_parametrs_one_device_1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            VIBRATION = isChecked
            val editor = c.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            if(VIBRATION)
            {
                editor?.putString("vibration","true")
            }
            else
            {
                editor?.putString("vibration","false")
            }
            editor?.apply()
        })

        dialog_one_device.close_parametrs_one_device.setOnClickListener {
            dialog_one_device.dismiss()
        }

        dialog_one_device.parametrs_one_device_configuring.setOnClickListener {
            val intent = Intent(c, NavigatorActivity::class.java)
            if(mInterstitialAd_in_offline_games.isLoaded)
            {
                Intent_for_offline_games = intent
                mInterstitialAd_in_offline_games.show()
            }
            else
            {
                c.startActivity(intent)
            }

        }
        dialog_one_device.show()
    }

}
