package com.sga.schoolbattle

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.view.Window
import kotlinx.android.synthetic.main.development_dialog.*

class Development(activity: Activity) {

    var TRANSLATE_DEVELOPMENT  = mapOf("Скоро появится" to "Coming soon","Основа всякой мудрости - есть спокойствие и терпение" to "The basis of all wisdom is calmness and patience",
        "Пнуть разработчиков" to  "Kick the developers", "Понять и простить" to "Understand and forgive")
    fun translate_development(s: String):String?
    {
        if(LANGUAGE == "Russian")
        {
            return s;
        }
        return TRANSLATE_DEVELOPMENT[s];
    }

    private var dialog_one_device = Dialog(activity)
    @SuppressLint("SetTextI18n")
    fun show() {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.development_dialog)

        dialog_one_device.button4.setBackgroundResource(R.drawable.cross_normal)
        dialog_one_device.button.setBackgroundResource(R.drawable.button)
        dialog_one_device.button3.setBackgroundResource(R.drawable.button)
        dialog_one_device.button.text = translate_development("Пнуть разработчиков")
        dialog_one_device.button3.text = translate_development("Понять и простить")

        dialog_one_device.button.setOnClickListener {
            dialog_one_device.dismiss()
        }

        dialog_one_device.button3.setOnClickListener {
            dialog_one_device.dismiss()
        }

        dialog_one_device.button4.setOnClickListener {
            dialog_one_device.dismiss()
        }


        dialog_one_device.textView10.text = translate_development("Скоро появится") +"\n" +  "P.S" + "\n" + translate_development("Основа всякой мудрости - есть спокойствие и терпение")

        dialog_one_device.show()
    }
}