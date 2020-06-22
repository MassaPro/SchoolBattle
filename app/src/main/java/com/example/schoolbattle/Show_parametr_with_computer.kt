package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.Toast
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.gameswithcomp.ReversiWithComputer
import com.example.schoolbattle.gameswithcomp.XOGame_withComputer
import kotlinx.android.synthetic.main.parametrs_with_computer.*

class Show_parametr_with_computer(activity: Activity) {
    var c = activity
    private val dialog_with_computer = Dialog(activity)
    fun showResult_with_computer(type_activity: Activity, gameType : String) {
        dialog_with_computer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_with_computer.setCancelable(false)
        dialog_with_computer.setCanceledOnTouchOutside(true)
        dialog_with_computer.setContentView(R.layout.parametrs_with_computer)


        if(Design == "Egypt")
        {
            dialog_with_computer.parametrs_with_computer_configuring.setTypeface(ResourcesCompat.getFont(c, R.font.s))
            dialog_with_computer.parametrs_with_computer_rules.setTypeface(ResourcesCompat.getFont(c, R.font.s))
            dialog_with_computer.parametrs_with_computer_xs.setTypeface(ResourcesCompat.getFont(c, R.font.s))
            dialog_with_computer.linearLayout_parametrs_with_computer.setBackgroundResource(R.drawable.background_egypt);
            dialog_with_computer.parametrs_with_computer_configuring.setBackgroundColor(
                Color.argb(0,
                    224,
                    164,
                    103
                )
            )
            dialog_with_computer.parametrs_with_computer_rules.setBackgroundColor(
                Color.argb(0,
                    224,
                    164,
                    103
                )
            )
            dialog_with_computer.parametrs_with_computer_xs.setBackgroundColor(
                Color.argb(0,
                    224,
                    164,
                    103
                )
            )
        }


        if (gameType == "Reversi") {
            val changeModeButton = dialog_with_computer.findViewById(R.id.changeModeWithComputer) as Button
            changeModeButton.setOnClickListener{
                val prefs2 = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                var ReversiMode = prefs2.getInt("ReversiMode", 0)
                val editor = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putInt("ReversiMode", 3 - ReversiMode)
                editor.apply()

                val intent = Intent(type_activity, ReversiWithComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }
        }
        if (gameType == "XOGame") {
            val changeModeButton = dialog_with_computer.findViewById(R.id.changeModeWithComputer) as Button
            changeModeButton.setOnClickListener{
                val prefs2 = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                var XOGameMode = prefs2.getInt("XOGameMode", 0)
                val editor = type_activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putInt("XOGameMode", 3 - XOGameMode)
                editor.apply()

                val intent = Intent(type_activity, XOGame_withComputer::class.java).apply {
                    putExtra("usedToClear", "clear")
                }
                type_activity.finish()
                type_activity.startActivity(intent)
            }
        }


        val button_revanshe = dialog_with_computer.findViewById(R.id.parametrs_with_computer_configuring) as Button
        button_revanshe.setOnClickListener{
            Toast.makeText(type_activity,"Настройки", Toast.LENGTH_LONG).show()
        }

        dialog_with_computer.show()
    }
    fun delete() {
        dialog_with_computer.dismiss()
    }
}
