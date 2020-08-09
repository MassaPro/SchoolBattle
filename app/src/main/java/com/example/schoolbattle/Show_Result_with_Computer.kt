package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.gameswithcomp.*
import kotlinx.android.synthetic.main.activity_game_over_with_computer.*

class Show_Result_with_Computer(activity: Activity) {
    private val dialog_with_computer = Dialog(activity)
    fun showResult_with_Computer(result: String,Game_Type: String,type_activity: Activity) {
        dialog_with_computer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_with_computer.setCancelable(false)
        dialog_with_computer.setCanceledOnTouchOutside(true)
        dialog_with_computer.setContentView(R.layout.activity_game_over_with_computer)

        if (Design == "Egypt"){
            dialog_with_computer.linearLayout_with_computer.setBackgroundResource(R.drawable.win_egypt);
            dialog_with_computer.resultText_with_computer.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
        }

        val button_revanshe = dialog_with_computer.findViewById(R.id.restart_with_computer) as Button
        button_revanshe.setOnClickListener{

            if(Game_Type == "BoxGame") {
                val intent = Intent(type_activity, BoxGameWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "AngleGame") {
                val intent = Intent(type_activity, ConersWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "DotGame") {
                val intent = Intent(type_activity, DotGameWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "GoGame") {
                val intent = Intent(type_activity, GoGameWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "Reversi") {
                val intent = Intent(type_activity, ReversiWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "SnakeGame") {
                val intent = Intent(type_activity, SnakeGameWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "VirusGame") {
                val intent = Intent(type_activity, VirusWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }

            if(Game_Type == "XOGame") {
                val intent = Intent(type_activity, XOGame_withComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }
        }
        val body = dialog_with_computer.findViewById(R.id.resultText_with_computer) as TextView
        body.text = result
        dialog_with_computer.show()
    }
    fun delete() {
        dialog_with_computer.dismiss()
    }
}
