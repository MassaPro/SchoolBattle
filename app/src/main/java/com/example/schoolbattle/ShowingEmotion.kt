package com.example.schoolbattle

import android.app.Activity
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.core.os.HandlerCompat.postDelayed
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import java.util.logging.Handler

interface ShowingEmotion {
    var  locale_activity_for_emotion : Activity?
    var opponentPath: DatabaseReference
    var userPath: DatabaseReference
    var key: String
    var flag: Boolean

    fun show_my_emotion() {
        locale_activity_for_emotion?.button_emotion?.visibility = View.VISIBLE
        locale_activity_for_emotion?.button_emotion?.alpha = 1f
        PICTURE_EMOTION[EMOTION]?.let { it1 ->
            locale_activity_for_emotion?.button_emotion?.setBackgroundResource(
                it1
            )
        }
        opponentPath.setValue(EMOTION.toString() + '_' + key + '_' + System.currentTimeMillis().toString())
        EMOTION = -1
        locale_activity_for_emotion?.button_emotion?.animate()?.alpha(0f)?.duration = 1000;
        locale_activity_for_emotion?.button_emotion?.animate()?.alpha(0f)?.startDelay = 1000


        locale_activity_for_emotion?.button_emotion?.setOnClickListener {
            locale_activity_for_emotion?.button_emotion?.visibility = View.INVISIBLE
            locale_activity_for_emotion?.button_emotion?.setBackgroundResource(R.drawable.nulevoe)
        }
        val r = Runnable {
            locale_activity_for_emotion?.button_emotion?.visibility = View.INVISIBLE
        }
        handler_for_emotion.postDelayed(r,2000)


    }

    fun show_emotion_from_rival(number_emotion: Int) {
        locale_activity_for_emotion?.button_emotion_rival?.alpha = 1f
        PICTURE_EMOTION[number_emotion]?.let { it1 ->
            locale_activity_for_emotion?.button_emotion_rival?.setBackgroundResource(
                it1
            )
        }
        locale_activity_for_emotion?.button_emotion_rival?.animate()?.alpha(0f)?.duration = 1000;
        locale_activity_for_emotion?.button_emotion_rival?.animate()?.alpha(0f)?.startDelay = 1000
        locale_activity_for_emotion?.button_emotion_rival?.setOnClickListener {
            locale_activity_for_emotion?.button_emotion_rival?.setBackgroundResource(R.drawable.nulevoe)
        }
    }

    fun init() {
        userPath.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (!flag && p0.value.toString().contains(key)) {
                    Log.w("Snap", p0.toString())
                    var num: Int = 0
                    for (i in p0.value.toString()) {
                        if (i == '_') break
                        num = num * 10 + i.toInt() - '0'.toInt()
                    }
                    Log.w("Snap2", num.toString())
                    show_emotion_from_rival(num)
                }
                flag = false
            }
        })
    }
}

