package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
                val intent = Intent(type_activity, XOGame_oneDivice::class.java)
                type_activity.startActivity(intent)
            }
            if(Game_Type == "AngleGame")
            {
                val intent = Intent(type_activity,ConersOneDevice::class.java)
                type_activity.startActivity(intent)
            }
            if(Game_Type == "DotGame")
            {
                val intent = Intent(type_activity,DotGameOneDivice::class.java)
                type_activity.startActivity(intent)
            }
            if(Game_Type == "SnakeGame")
            {
                val intent = Intent(type_activity,SnakeGameOneDivice::class.java)
                type_activity.startActivity(intent)
            }
            if(Game_Type == "BoxGame")
            {
                val intent = Intent(type_activity,BoxGameOneDivice::class.java)
                type_activity.startActivity(intent)
            }
        }

        val body = dialog_one_device.findViewById(R.id.resultText_one_device) as TextView
        body.text = result
        dialog_one_device.show()
    }
    fun delete() {
        dialog_one_device.dismiss()
    }
}
