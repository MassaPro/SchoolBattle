package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.schoolbattle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import kotlinx.android.synthetic.main.dialog_for_losers.*
import kotlinx.android.synthetic.main.find_emotion.*

fun initMenuFunctions(activity: Activity,
                      bottom_navigation_xog_online: BottomNavigationView,
                      intent: Intent,
                      user: String, opponent: String, positionData: DatabaseReference) {
    val dialog_find_emotion = Dialog(activity)
    val loseDialog = Dialog(activity)

    val emotions = object: ShowingEmotion {
        override var locale_activity_for_emotion: Activity? = activity
        override var opponentPath = myRef.child("Users").child(opponent).child("emotions")
        override var userPath = myRef.child("Users").child(user).child("emotions")
        override var key = if (intent.getStringExtra("key") == null) "_" else intent.getStringExtra("key")
        override var flag = true
    }
    emotions.init()
    var dialog_rules: Show_rules? = null
    bottom_navigation_xog_online.setOnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.page_online_1 ->{
                dialog_rules = Show_rules(activity)
                if (activity.toString().contains ("XOGameActivity"))
                {
                    dialog_rules?.show("XOGame")
                }
                if (activity.toString().contains ("BoxGameActivity"))
                {
                    dialog_rules?.show("BoxGame")
                }
                if (activity.toString().contains ("DotGameActivity"))
                {
                    dialog_rules?.show("DotGame")
                }
                if (activity.toString().contains ("ReversiGameActivity"))
                {
                    dialog_rules?.show("ReversiGame")
                }
                if (activity.toString().contains ("SnakeGameActivity"))
                {
                    dialog_rules?.show("SnakeGame")
                }
            }
            R.id.page_online_2 ->{
                loseDialog.setContentView(R.layout.dialog_for_losers)
                loseDialog.dialog_for_losers_lose.setOnClickListener {
                    positionData.child("winner").setValue(opponent)
                    loseDialog.dismiss()
                }
                loseDialog.show()
            }
            R.id.page_online_3 ->{
                dialog_find_emotion!!.setContentView(R.layout.find_emotion)
                Emotion_in_game(dialog_find_emotion!!.item_profile_emotion, dialog_find_emotion)
                gamesRecycler = dialog_find_emotion!!.item_profile_emotion
                gamesRecycler.isNestedScrollingEnabled = false
                gamesRecycler.layoutManager = GridLayoutManager(activity, 3)
                dialog_find_emotion!!.show()
                val d: Drawable = ColorDrawable(Color.BLACK)
                d.alpha = 130
                dialog_find_emotion!!.window!!.setBackgroundDrawable(d)
                val display = activity.windowManager.defaultDisplay
                val size = Point()
                display.getSize(size)
                val width = size.x
                val height = size.y
                dialog_find_emotion!!.window!!.setLayout(width*11/12, height*5/6);
                // dialog_find_emotion.window!!.setGravity()
                dialog_find_emotion!!.setOnDismissListener {
                    if(EMOTION !=-1)
                    {
                        emotions.show_my_emotion()
                    }
                }

            }
            R.id.page_online_4 -> {
                if (activity.toString().contains ("DotGameActivity"))
                {
                    CONDITION_DOT++
                    Log.d(CONDITION_DOT.toString(),"CONDITION_DOT")
                    Toast.makeText(activity,CONDITION_DOT.toString(), Toast.LENGTH_SHORT).show()
                    activity.signature_canvas3.invalidate()
                }
            }
            R.id.page_online_5 -> {
                if (activity.toString().contains ("DotGameActivity"))
                {
                    if(CONDITION_DOT>0)
                    {
                        CONDITION_DOT--
                        Log.d(CONDITION_DOT.toString(),"CONDITION_DOT")
                    }
                    activity.signature_canvas3.invalidate()
                    Toast.makeText(activity,CONDITION_DOT.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        true
    }
}