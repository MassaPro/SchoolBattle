package com.sga.schoolbattle.engine

import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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


        myRef.child("Users").child(globalName).child("image").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    PICTURE_AVATAR[p0.value.toString().toInt()]?.let { dialog.icon_for_you.setBackgroundResource(it) }
                } else {
                    PICTURE_AVATAR[0]?.let { dialog.icon_for_you.setBackgroundResource(it) }
                }
            }
        })

        myRef.child("Users").child(oppName).child("image").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    PICTURE_AVATAR[p0.value.toString().toInt()]?.let { dialog.icon_for_two.setBackgroundResource(it) }
                } else {
                    PICTURE_AVATAR[0]?.let { dialog.icon_for_two.setBackgroundResource(it) }
                }
            }
        })

        myRef.child("Users").child(oppName).child("rating").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    dialog.opponent_rating_win_dialog.text = oppName.toString() + p0.value.toString()
                } else {
                    dialog.opponent_rating_win_dialog.text = oppName.toString() + "1000"
                }
            }
        })

        dialog.user_rating_win_dialog.text = globalName.toString() + newRating.toString().toString()



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
            con.finish()
        }

        ng.setOnClickListener {
            Toast.makeText(con, "kdslkj", Toast.LENGTH_LONG).show()
            myRef.child("Users/$globalName/rating").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        RATING = p0.value.toString().toInt()
                    } else {
                        RATING = 1000
                    }
                    val intent = Intent(con, NewGameActivity::class.java)
                    //activity?.overridePendingTransition(0, 0)
                    intent.putExtra("playType", 0)
                    con.startActivity(intent)
                }
            })
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
