package com.sga.schoolbattle.engine

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_game_menu.*
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
        newRating: Int,
        moneyChange: Int = 0
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
                    dialog.opponent_rating_win_dialog.text = oppName.toString() + '\n'+ p0.value.toString()
                } else {
                    dialog.opponent_rating_win_dialog.text = oppName.toString() + '\n'+"1000"
                }
            }
        })

        dialog.user_rating_win_dialog.text = globalName.toString() +'\n'+ newRating.toString().toString()
        if (newRating < 0) {
            if (RATING < 0) RATING = 1000
            dialog.user_rating_win_dialog.text = globalName.toString() +'\n'+ RATING.toString().toString()
        }
        dialog.textView8.text = moneyChange.toString()

        when (Design) {
            "Normal" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
                dialog.revanche.setBackgroundResource(R.drawable.button)
                dialog.restart.setBackgroundResource(R.drawable.button)

                dialog.imageView6.setImageResource(R.drawable.duelpic_normal)
            }
            "Egypt" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
                dialog.linearLayout.setBackgroundResource(R.drawable.background_egypt)
                dialog.user_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog.opponent_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog.revanche.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog.restart.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog.resultText.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog.textView8.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog.ratingChange.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.egypt)

                dialog.user_rating_win_dialog.setTextColor(Color.BLACK)
                dialog.opponent_rating_win_dialog.setTextColor(Color.BLACK)
                dialog.revanche.setTextColor(Color.BLACK)
                dialog.restart.setTextColor(Color.BLACK)
                dialog.resultText.setTextColor(Color.BLACK)
                dialog.textView8.setTextColor(Color.BLACK)
                dialog.ratingChange.setTextColor(Color.BLACK)

                dialog.imageView6.setImageResource(R.drawable.duelpic_egypt)

            }
            "Casino" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross2)
                dialog.linearLayout.setBackgroundResource(R.drawable.background2_casino)
                dialog.user_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog.opponent_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog.revanche.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog.restart.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog.resultText.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog.textView8.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog.ratingChange.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.casino)

                dialog.user_rating_win_dialog.setTextColor(Color.YELLOW)
                dialog.opponent_rating_win_dialog.setTextColor(Color.YELLOW)
                dialog.revanche.setTextColor(Color.YELLOW)
                dialog.restart.setTextColor(Color.YELLOW)
                dialog.resultText.setTextColor(Color.WHITE)
                dialog.textView8.setTextColor(Color.WHITE)
                dialog.ratingChange.setTextColor(Color.WHITE)
                dialog.imageView6.setImageResource(R.drawable.duelpic_casino)
            }
            "Rome" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross3)
                dialog.linearLayout.setBackgroundResource(R.drawable.background_rome)
                dialog.user_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog.opponent_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog.revanche.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog.restart.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog.resultText.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog.textView8.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog.ratingChange.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.rome)

                dialog.user_rating_win_dialog.setTextColor(Color.rgb(193, 150, 63))
                dialog.opponent_rating_win_dialog.setTextColor(Color.rgb(193, 150, 63))
                dialog.revanche.setTextColor(Color.rgb(193, 150, 63))
                dialog.restart.setTextColor(Color.rgb(193, 150, 63))
                dialog.resultText.setTextColor(Color.GRAY)
                dialog.textView8.setTextColor(Color.GRAY)
                dialog.ratingChange.setTextColor(Color.GRAY)

                dialog.imageView6.setImageResource(R.drawable.duelpic_rome2)

            }
            "Gothic" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross2)
                dialog.linearLayout.setBackgroundResource(R.drawable.background_gothic)
                dialog.user_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog.opponent_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog.revanche.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog.restart.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog.resultText.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog.textView8.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog.ratingChange.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.gothic)

                dialog.user_rating_win_dialog.setTextColor(Color.WHITE)
                dialog.opponent_rating_win_dialog.setTextColor(Color.WHITE)
                dialog.revanche.setTextColor(Color.WHITE)
                dialog.restart.setTextColor(Color.WHITE)
                dialog.resultText.setTextColor(Color.WHITE)
                dialog.textView8.setTextColor(Color.WHITE)
                dialog.ratingChange.setTextColor(Color.WHITE)

                dialog.imageView6.setImageResource(R.drawable.duelpic_gothic)
            }
            "Japan" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross)
                dialog.linearLayout.setBackgroundResource(R.drawable.background_japan)
                dialog.user_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                dialog.opponent_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                dialog.revanche.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                dialog.restart.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                dialog.resultText.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.japan)
                dialog.textView8.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.japan)

                dialog.imageView6.setImageResource(R.drawable.duelpic_japan)

            }
            "Noir" -> {
                dialog.close_button_online.setBackgroundResource(R.drawable.close_cross2)
                dialog.linearLayout.setBackgroundResource(R.drawable.background_noir)

                dialog.user_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog.opponent_rating_win_dialog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog.revanche.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog.restart.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog.resultText.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog.textView8.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog.ratingChange.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.noir)

                dialog.user_rating_win_dialog.setTextColor(Color.WHITE)
                dialog.opponent_rating_win_dialog.setTextColor(Color.WHITE)
                dialog.revanche.setTextColor(Color.WHITE)
                dialog.restart.setTextColor(Color.WHITE)
                dialog.resultText.setTextColor(Color.WHITE)
                dialog.ratingChange.setTextColor(Color.WHITE)

                dialog.imageView6.setImageResource(R.drawable.duelpic_noir)
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
        body.text = translate(result)
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
