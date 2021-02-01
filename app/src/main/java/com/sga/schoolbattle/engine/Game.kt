package com.sga.schoolbattle.engine

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.sga.schoolbattle.R
import com.sga.schoolbattle.myRef
import com.sga.schoolbattle.now
import com.google.firebase.database.ValueEventListener

class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {
    override fun toString(): String {
        return "$name $type"
    }
}

class ShowResult(activity: Activity) {
    private val dialog = Dialog(activity)
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.activity_game_over)

  //      PICTURE_AVATAR[picture1]?.let { dialog.icon_for_you.setBackgroundResource(it) } TODO заменить
  //      PICTURE_AVATAR[picture1]?.let { dialog.icon_for_you.setBackgroundResource(it) } TODO заменить

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
    }

    fun delete() {
        dialog.dismiss()
    }
}
