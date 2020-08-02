package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.gamesonedevice.*
import kotlinx.android.synthetic.main.activity_game_over_one_device.*

class Show_Result_one_Device(activity: Activity) {
    private val dialog_one_device = Dialog(activity)
    fun showResult_one_device(result: String,Game_Type: String,type_activity: Activity) {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.activity_game_over_one_device)



        val button_revanshe = dialog_one_device.findViewById(R.id.restart_one_device) as Button
        button_revanshe.setOnClickListener{
            if(Game_Type == "XOGame")
            {
                val intent = Intent(type_activity, XOGame_oneDivice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.finish()
                type_activity.startActivity(intent)
            }
            if(Game_Type == "AngleGame")
            {
                val intent = Intent(type_activity,
                    ConersOneDevice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.finish()
                type_activity.startActivity(intent)
            }
            if(Game_Type == "DotGame")
            {
                val intent = Intent(type_activity,
                    DotGameOneDivice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.finish()
                type_activity.startActivity(intent)
            }
            if(Game_Type == "SnakeGame")
            {
                val intent = Intent(type_activity,
                    SnakeGameOneDivice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.startActivity(intent)
            }
            if(Game_Type == "BoxGame")
            {
                val intent = Intent(type_activity,
                    BoxGameOneDivice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.finish()
                type_activity.startActivity(intent)
            }
            if(Game_Type == "VirusGame")
            {
                val intent = Intent(type_activity,
                    VirusOneDivice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.finish()
                type_activity.startActivity(intent)
            }
            if(Game_Type == "Reversi")
            {
                val intent = Intent(type_activity,ReversiOneDivice::class.java).apply {
                    putExtra("usedToClear", "clear")}
                type_activity.finish()
                type_activity.startActivity(intent)
            }

        }

        val button_close =  dialog_one_device.findViewById(R.id.button_close_game_over_one_device) as Button


        if (Design == "Egypt"){
            dialog_one_device.linearLayout_one_device.setBackgroundResource(R.drawable.win_egypt);
            dialog_one_device.resultText_one_device.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            //dialog_one_device.resultText_one_device.setTextColor(Color.WHITE)
            button_close.setBackgroundResource(R.drawable.close_cross)
        }
        if (Design == "Casino"){
            dialog_one_device.linearLayout_one_device.setBackgroundResource(R.drawable.win_casino);
            dialog_one_device.resultText_one_device.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            dialog_one_device.resultText_one_device.setTextColor(Color.YELLOW)
            button_close.setBackgroundResource(R.drawable.close_cross)
        }
        if (Design == "Rome"){
            dialog_one_device.linearLayout_one_device.setBackgroundResource(R.drawable.win_rome);
            dialog_one_device.resultText_one_device.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            dialog_one_device.resultText_one_device.setTextColor(rgb(193, 150, 63))
            button_close.setBackgroundResource(R.drawable.close_cross)
        }
        
        button_close.setOnClickListener {
            dialog_one_device.dismiss()
        }
        val body = dialog_one_device.findViewById(R.id.resultText_one_device) as TextView
        body.text = result
        dialog_one_device.show()
    }
    fun delete() {
        dialog_one_device.dismiss()
    }
}
