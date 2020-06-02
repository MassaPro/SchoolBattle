package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Show_Result_with_Computer(activity: Activity) {
    private val dialog_with_computer = Dialog(activity)
    fun showResult_with_Computer(result: String,Game_Type: String,type_activity: Activity) {
        dialog_with_computer.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_with_computer.setCancelable(false)
        dialog_with_computer.setCanceledOnTouchOutside(true)
        dialog_with_computer.setContentView(R.layout.activity_game_over_with_computer)

        val button_revanshe = dialog_with_computer.findViewById(R.id.restart_with_computer) as Button
        button_revanshe.setOnClickListener{
            if(Game_Type == "XOGame")
            {
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
