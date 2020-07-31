package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.schoolbattle.gamesonedevice.*
import kotlinx.android.synthetic.main.show_rules.*

class Show_rules(activity: Activity) {
    private val dialog_one_device = Dialog(activity)
    fun show(Gametype:String) {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.show_rules)


        if(Gametype == "XOGame")
        {
            dialog_one_device.header_rules.setText("Крестики-Нолики на торе")
            dialog_one_device.text_rules.setText(R.string.rules_xog_Russia)
        }
        if(Gametype == "AngleGame")
        {
            dialog_one_device.header_rules.setText("Уголки")
            dialog_one_device.text_rules.setText(R.string.rules_corner_Russia)
        }
        if(Gametype == "SnakeGame")
        {
            dialog_one_device.header_rules.setText("Змейка")
            dialog_one_device.text_rules.setText(R.string.rules_snake_Russia)
        }
        if(Gametype == "GoGame")
        {
            dialog_one_device.header_rules.setText("ГО")
            dialog_one_device.text_rules.setText(R.string.rules_go_Russia)
        }
        if(Gametype == "BoxGame")
        {
            dialog_one_device.header_rules.setText("КОРОБКИ")
            dialog_one_device.text_rules.setText(R.string.rules_box_Russia)
        }
        if(Gametype == "VirusGame")
        {
            dialog_one_device.header_rules.setText("ВОЙНА ВИРУСОВ")
            dialog_one_device.text_rules.setText(R.string.rules_virus_Russia)
        }
        if(Gametype == "StupidGame")
        {
            dialog_one_device.header_rules.setText("КАМЕНЬ-НОЖНИЦЫ-БУМАГА")
            dialog_one_device.text_rules.setText(R.string.rules_stupid_Russia)
        }
        if(Gametype == "ReversiGame")
        {
            dialog_one_device.header_rules.setText("РЕВЕРСИ")
            dialog_one_device.text_rules.setText(R.string.rules_reversi_Russia)
        }
        if(Gametype == "DotGame")
        {
            dialog_one_device.header_rules.setText("ТОЧКИ")
            dialog_one_device.text_rules.setText(R.string.rules_dot_Russia)
        }


        val button_close =  dialog_one_device.findViewById(R.id.button_close_rules_one_device) as Button
        if (Design == "Egypt"){
            dialog_one_device.rules.setBackgroundResource(R.drawable.background_egypt);
            button_close.setBackgroundResource(R.drawable.close_cross)
            dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
        }
        else if (Design == "Casino"){
            dialog_one_device.rules.setBackgroundResource(R.drawable.background2_casino);
            button_close.setBackgroundResource(R.drawable.close_cross)
            dialog_one_device.text_rules.setTextColor(Color.YELLOW)
            dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
        }
        else if (Design == "Rome"){
            dialog_one_device.rules.setBackgroundResource(R.drawable.background_rome);
            button_close.setBackgroundResource(R.drawable.close_cross)
            dialog_one_device.text_rules.setTextColor(Color.rgb(193, 150, 63))
            dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
        }
        else if (Design == "Gothic"){
            dialog_one_device.rules.setBackgroundResource(R.drawable.background_gothic);
            button_close.setBackgroundResource(R.drawable.close_cross)
            dialog_one_device.text_rules.setTextColor(Color.WHITE)
            dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
        }
        else if (Design == "Japan"){
            dialog_one_device.rules.setBackgroundResource(R.drawable.background_japan);
            button_close.setBackgroundResource(R.drawable.close_cross)
            dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
        }

        button_close.setOnClickListener {
            dialog_one_device.dismiss()
        }
        dialog_one_device.show()
    }
    fun delete() {
        dialog_one_device.dismiss()
    }
}