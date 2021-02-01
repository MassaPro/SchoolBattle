package com.sga.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.gameswithcomp.*
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
            dialog_with_computer.resultText_with_computer.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
        }

        val button_revanshe = dialog_with_computer.findViewById(R.id.restart_with_computer) as Button
        button_revanshe.setOnClickListener{

            when (Game_Type) {
                "BoxGame" -> {
                    intent = Intent(type_activity, BoxGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "AngleGame" -> {
                    intent = Intent(type_activity, ConersWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "DotGame" -> {
                    intent = Intent(type_activity, DotGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "Reversi" -> {
                    intent = Intent(type_activity, ReversiWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "SnakeGame" -> {
                    intent = Intent(type_activity, SnakeGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "VirusGame" -> {
                    intent = Intent(type_activity, VirusWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "XOGame" -> {
                    intent = Intent(type_activity, XOGame_withComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
            }

            type_activity.finish()

            if(mInterstitialAd_in_offline_games.isLoaded)
            {
                Intent_for_offline_games = intent
                mInterstitialAd_in_offline_games.show()
            }
            else
            {
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
