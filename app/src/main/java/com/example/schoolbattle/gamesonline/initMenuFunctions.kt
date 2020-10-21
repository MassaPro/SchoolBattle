package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.schoolbattle.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_box_game.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.signature_canvas_box
import kotlinx.android.synthetic.main.dialog_for_losers.*
import kotlinx.android.synthetic.main.find_emotion.*
import kotlinx.android.synthetic.main.parametrs_one_divice.*

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
                when {
                    activity.toString().contains ("XOGameActivity") -> {
                        dialog_rules?.show("XOGame")
                    }
                    activity.toString().contains ("BoxGameActivity") -> {
                        dialog_rules?.show("BoxGame")
                    }
                    activity.toString().contains ("DotGameActivity") -> {
                        dialog_rules?.show("DotGame")
                    }
                    activity.toString().contains ("ReversiGameActivity") -> {
                        dialog_rules?.show("ReversiGame")
                    }
                    activity.toString().contains ("SnakeGameActivity") -> {
                        dialog_rules?.show("SnakeGame")
                    }
                }
            }
            R.id.page_online_2 ->{
                loseDialog.setContentView(R.layout.dialog_for_losers)
                loseDialog.dialog_for_losers_lose.setOnClickListener {
                    positionData.child("winner").setValue(opponent)
                    loseDialog.dismiss()
                }
                loseDialog.switch_parametrs_online_2.isChecked = SOUND
                loseDialog.switch_parametrs_online_2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
                    SOUND = isChecked
                    val editor = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
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

                loseDialog.switch_parametrs_online_1.isChecked = VIBRATION
                loseDialog.switch_parametrs_online_1.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
                    VIBRATION = isChecked
                    val editor = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
                    if(VIBRATION)
                    {
                        editor?.putString("sound","true")
                    }
                    else
                    {
                        editor?.putString("sound","false")
                    }
                    editor?.apply()
                })

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
                activity.button_see.visibility = View.VISIBLE
                when {
                    activity.toString().contains ("DotGameActivity") -> {
                        activity.signature_canvas3.CONDITION_DOT++
                        Log.d("CONDITION_DOT",activity.signature_canvas3.History.toString())
                        activity.signature_canvas3.invalidate()
                    }
                    activity.toString().contains ("XOGameActivity") -> {
                        activity.signature_canvas.CONDITION_XOG++
                        Log.d("CONDITION_XOG",activity.signature_canvas.History.toString())
                        activity.signature_canvas.invalidate()
                    }
                    activity.toString().contains ("BoxGameActivity") -> {
                        activity.signature_canvas_box.CONDITION_BOX++
                        Log.d("CONDITION_BOX",activity.signature_canvas_box.History.toString())
                        activity.signature_canvas_box.invalidate()
                    }
                    activity.toString().contains ("SnakeGameActivity") -> {
                        activity.signature_canvas_snake_online.CONDITION_SNAKE++
                        Log.d("CONDITION_BOX",activity.signature_canvas_snake_online.History.toString())
                        activity.signature_canvas_snake_online.invalidate()
                    }
                    activity.toString().contains ("ReversiGameActivity") -> {
                        activity.signature_canvas_reversi.CONDITION_REVERSI++
                        Log.d("CONDITION_REVERSI",activity.signature_canvas_reversi.History.toString())
                        activity.signature_canvas_reversi.invalidate()
                    }
                }
            }
            R.id.page_online_5 -> {
                when {
                    activity.toString().contains ("DotGameActivity") -> {
                        if(activity.signature_canvas3.CONDITION_DOT>0) {
                            activity.signature_canvas3.CONDITION_DOT--
                            Log.d("CONDITION_DOT",activity.signature_canvas3.History.toString())
                        }
                        if(activity.signature_canvas3.CONDITION_DOT == 0) {
                            activity.button_see.visibility = View.GONE
                        }
                        activity.signature_canvas3.invalidate()
                    }
                    activity.toString().contains ("XOGameActivity") -> {
                        if(activity.signature_canvas.CONDITION_XOG>0) {
                            activity.signature_canvas.CONDITION_XOG--
                        }
                        if(activity.signature_canvas.CONDITION_XOG==0) {
                            activity.button_see.visibility = View.GONE
                        }
                        activity.signature_canvas.invalidate()
                    }
                    activity.toString().contains ("BoxGameActivity") -> {
                        if(activity.signature_canvas_box.CONDITION_BOX>0) {
                            activity.signature_canvas_box.CONDITION_BOX--
                            Log.d("CONDITION_BOX",activity.signature_canvas_box.History.toString())
                        }
                        if(activity.signature_canvas_box.CONDITION_BOX==0) {
                            activity.button_see.visibility = View.GONE
                        }
                        activity.signature_canvas_box.invalidate()
                    }
                    activity.toString().contains ("SnakeGameActivity") -> {
                        if(activity.signature_canvas_snake_online.CONDITION_SNAKE>0) {
                            activity.signature_canvas_snake_online.CONDITION_SNAKE--
                            Log.d("CONDITION_SNAKE",activity.signature_canvas_snake_online.History.toString())
                        }
                        if(activity.signature_canvas_snake_online.CONDITION_SNAKE==0) {
                            activity.button_see.visibility = View.GONE
                        }
                        activity.signature_canvas_snake_online.invalidate()
                    }
                    activity.toString().contains ("ReversiGameActivity") -> {
                        if(activity.signature_canvas_reversi.CONDITION_REVERSI>0) {
                            activity.signature_canvas_reversi.CONDITION_REVERSI--
                            Log.d("CONDITION_REVERSI",activity.signature_canvas_reversi.History.toString())
                        }
                        if(activity.signature_canvas_reversi.CONDITION_REVERSI==0) {
                            activity.button_see.visibility = View.GONE
                        }
                        activity.signature_canvas_reversi.invalidate()
                    }
                }
            }
        }
        true
    }
}