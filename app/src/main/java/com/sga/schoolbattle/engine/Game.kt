package com.sga.schoolbattle.engine

import android.app.Activity
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.ValueEventListener
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_game_over.*


class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {
    override fun toString(): String {
        return "$name $type"
    }
}

class ShowResult(activity: Activity) {

    private val dialog = DDD
    private var state = false
    private var con = activity



    fun showResult(
        result: String,
        gameType: String,
        globalName: String,
        oppName: String,
        userRating: Int,
        newRating: Int
    ) {
        now = con


  //      PICTURE_AVATAR[picture1]?.let { dialog.icon_for_you.setBackgroundResource(it) } TODO заменить
  //      PICTURE_AVATAR[picture1]?.let { dialog.icon_for_you.setBackgroundResource(it) } TODO заменить

        when (Design) {
            "Normal" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
            }
            "Egypt" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
            }
            "Casino" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross2)
            }
            "Rome" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross3)
            }
            "Gothic" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross2)
            }
            "Japan" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
            }
            "Noir" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross2)
            }
        }

        dialog.close_button_online.setOnClickListener {
            dialog.dismiss()
        }

        val ng = dialog.findViewById(R.id.restart) as Button
        val rv = dialog.findViewById(R.id.revanche) as Button

        var eventListener: ValueEventListener? = null
        is_pressed = true

        rv.setOnClickListener {

        }

        ng.setOnClickListener {

        }

        dialog.setOnDismissListener {
            is_pressed = false
            state = false
            eventListener?.let { myRef.removeEventListener(it) }
            myRef.child(gameType + "Users").child(globalName).removeValue()
        }
        val body = dialog.findViewById(R.id.resultText) as TextView
        body.text = result
        if (userRating != -100000) {
            val changeRating = dialog.findViewById(R.id.ratingChange) as TextView
            changeRating.text = (if (newRating > userRating) "+" else "") + (newRating - userRating).toString()
        }
        dialog.show()



        if(Design == "Normal")
        {
            dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
        }
        if(Design == "")
        {
            dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
        }
    }

    fun delete() {
        dialog.dismiss()
    }

}
