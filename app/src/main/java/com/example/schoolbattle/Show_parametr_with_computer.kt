package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.CompoundButton
import com.example.schoolbattle.gameswithcomp.*
import kotlinx.android.synthetic.main.parametrs_with_computer.*

lateinit var intent : Intent
class Show_parametr_with_computer(activity: Activity) {
    var c = activity
    private val dialog_with_computer = Dialog(activity)
    fun showResult_with_computer(type_activity: Activity, gameType : String) {
        dialog_with_computer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_with_computer.setCancelable(false)
        dialog_with_computer.setCanceledOnTouchOutside(true)
        dialog_with_computer.setContentView(R.layout.parametrs_with_computer)


        val prefs2 = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()


        dialog_with_computer.changeModeWithComputer.setOnClickListener {
            when (gameType) {
                "BoxGame" -> {
                    var BoxGameMode = prefs2.getInt("BoxGameMode", 0)
                    editor.putInt("BoxGameMode", 3 - BoxGameMode)
                    editor.apply()
                    intent = Intent(type_activity, BoxGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "AngleGame" -> {
                    var AngleGameMode = prefs2.getInt("AngleGameMode", 0)
                    editor.putInt("AngleGameMode", 3 - AngleGameMode)
                    editor.apply()
                    intent = Intent(type_activity, ConersWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "DotGame" -> {
                    var DotGameMode = prefs2.getInt("DotGameMode", 0)
                    editor.putInt("DotGameMode", 3 - DotGameMode)
                    editor.apply()
                    intent = Intent(type_activity, DotGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "Reversi" -> {
                    var ReversiMode = prefs2.getInt("ReversiMode", 0)
                    editor.putInt("ReversiMode", 3 - ReversiMode)
                    editor.apply()
                    intent = Intent(type_activity, ReversiWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "SnakeGame" -> {
                    var SnakeGameMode = prefs2.getInt("SnakeGameMode", 0)
                    editor.putInt("SnakeGameMode", 3 - SnakeGameMode)
                    editor.apply()
                    intent = Intent(type_activity, SnakeGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }

                }
                "VirusGame" -> {
                    var VirusGameMode = prefs2.getInt("VirusGameMode", 0)
                    editor.putInt("VirusGameMode", 3 - VirusGameMode)
                    editor.apply()
                    intent = Intent(type_activity, VirusWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                }
                "XOGame" -> {
                    var XOGameMode = prefs2.getInt("XOGameMode", 0)
                    editor.putInt("XOGameMode", 3 - XOGameMode)
                    editor.apply()
                    intent = Intent(type_activity, XOGame_withComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
            }
            }
            type_activity.finish()
            type_activity.startActivity(intent)
         }

        dialog_with_computer.show()

        dialog_with_computer.switch_parametrs_computer_2.isChecked = SOUND                //настройка свитчера звука
        dialog_with_computer.switch_parametrs_computer_2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            SOUND = isChecked
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

        dialog_with_computer.switch_parametrs_computer_1.isChecked = VIBRATION               //настройка свитчера звука
        dialog_with_computer.switch_parametrs_computer_1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            VIBRATION = isChecked
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
        dialog_with_computer.close_parametrs_computer.setOnClickListener {
            dialog_with_computer.dismiss()
        }

        dialog_with_computer.go_to_menu_from_comp.setOnClickListener {
            intent = Intent(c, NavigatorActivity::class.java)
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
    }

}
