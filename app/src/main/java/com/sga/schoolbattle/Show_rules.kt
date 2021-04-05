package com.sga.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.view.Window
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.show_rules.*

class Show_rules(activity: Activity) {
    private val dialog_one_device = Dialog(activity)
    fun show(Gametype:String) {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.show_rules)


        when (Gametype) {
            "XOGame" ->{

                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.header_rules.setText("Крестики-Нолики на торе")
                    dialog_one_device.text_rules.setText(R.string.rules_xog_Russia)
                }
                else{
                    dialog_one_device.header_rules.setText("XO Game")
                    dialog_one_device.text_rules.setText(R.string.rules_xog_Eng)
                }

            }
            "AngleGame" -> {

                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.header_rules.setText("Уголки")
                    dialog_one_device.text_rules.setText(R.string.rules_corner_Russia)
                }
                else{
                    dialog_one_device.header_rules.setText("Corners")
                    dialog_one_device.text_rules.setText(R.string.rules_corner_Eng)
                }
            }
            "SnakeGame" -> {
                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.text_rules.setText(R.string.rules_snake_Russia)
                    dialog_one_device.header_rules.setText("Змейка")
                }
                else{
                    dialog_one_device.text_rules.setText(R.string.rules_snake_Eng)
                    dialog_one_device.header_rules.setText("Snakes")
                }

            }
            "BoxGame" -> {

                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.header_rules.setText("Коробки")
                    dialog_one_device.text_rules.setText(R.string.rules_box_Russia)
                }
                else{
                    dialog_one_device.header_rules.setText("Boxes")
                    dialog_one_device.text_rules.setText(R.string.rules_box_Eng)
                }

            }
            "VirusGame" -> {

                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.text_rules.setText(R.string.rules_virus_Russia)
                    dialog_one_device.header_rules.setText("Война вирусов")
                }
                else{
                    dialog_one_device.text_rules.setText(R.string.rules_virus_Eng)
                    dialog_one_device.header_rules.setText("war of viruses")
                }

            }
            "ReversiGame" -> {

                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.text_rules.setText(R.string.rules_reversi_Russia)
                    dialog_one_device.header_rules.setText("Реверси")
                }
                else{
                    dialog_one_device.text_rules.setText(R.string.rules_reversi_Eng)
                    dialog_one_device.header_rules.setText("Reversi")
                }

            }
            "DotGame" -> {
                if(LANGUAGE == "Russian")
                {
                    dialog_one_device.text_rules.setText(R.string.rules_dot_Russia)
                    dialog_one_device.header_rules.setText("Точки")
                }
                else{
                    dialog_one_device.text_rules.setText(R.string.rules_dot_Eng)
                    dialog_one_device.header_rules.setText("Dots")
                }

            }
        }


        val button_close =  dialog_one_device.findViewById(R.id.button_close_rules_one_device) as Button
        when (Design) {
            "Normal" ->{
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.cross_normal)
                dialog_one_device.text_rules.setTextColor(Color.BLACK)
                dialog_one_device.header_rules.setTextColor(Color.BLACK)
                button_close.setBackgroundResource(R.drawable.close_cross)
            }
            "Egypt" -> {
                dialog_one_device.rules.setBackgroundResource(R.drawable.background_egypt);
                button_close.setBackgroundResource(R.drawable.close_cross)
                dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                dialog_one_device.header_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                dialog_one_device.text_rules.setTextSize(18f)
                dialog_one_device.text_rules.setTextColor(Color.BLACK)
                dialog_one_device.header_rules.setTextColor(Color.BLACK)
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.box1_egypt)
            }
            "Casino" -> {
                dialog_one_device.rules.setBackgroundResource(R.drawable.background2_casino);
                button_close.setBackgroundResource(R.drawable.close_cross4)
                dialog_one_device.text_rules.setTextColor(Color.YELLOW)
                dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                dialog_one_device.text_rules.setTextSize(18f)
                dialog_one_device.header_rules.setTextColor(Color.YELLOW)
                dialog_one_device.header_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.box1_casino)
            }
            "Rome" -> {
                dialog_one_device.rules.setBackgroundResource(R.drawable.background_rome);
                button_close.setBackgroundResource(R.drawable.close_cross3)
                dialog_one_device.text_rules.setTextColor(Color.rgb(193, 150, 63))
                dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                dialog_one_device.text_rules.setTextSize(22f)
                dialog_one_device.header_rules.setTextColor(Color.rgb(193, 150, 63))
                dialog_one_device.header_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.cross_rome)
            }
            "Gothic" -> {
                dialog_one_device.rules.setBackgroundResource(R.drawable.background_gothic);
                button_close.setBackgroundResource(R.drawable.close_cross2)
                dialog_one_device.text_rules.setTextColor(Color.WHITE)
                dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                dialog_one_device.header_rules.setTextColor(Color.WHITE)
                dialog_one_device.text_rules.setTextSize(26f)
                dialog_one_device.header_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.cross_gothic)
            }
            "Japan" -> {
                dialog_one_device.rules.setBackgroundResource(R.drawable.background_japan);
                button_close.setBackgroundResource(R.drawable.close_cross)
                dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                dialog_one_device.header_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                dialog_one_device.text_rules.setTextColor(Color.BLACK)
                dialog_one_device.header_rules.setTextColor(Color.BLACK)
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.box1_japan)

            }
            "Noir" -> {
                dialog_one_device.rules.setBackgroundResource(R.drawable.background_noir);
                button_close.setBackgroundResource(R.drawable.close_cross2)
                dialog_one_device.text_rules.setTextColor(Color.WHITE)
                dialog_one_device.text_rules.setTextSize(16f)
                dialog_one_device.text_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                dialog_one_device.header_rules.setTextColor(Color.WHITE)
                dialog_one_device.header_rules.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                dialog_one_device.rules_image.setBackgroundResource(R.drawable.cross_noir)
            }
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
