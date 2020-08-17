package com.example.schoolbattle

import android.app.Activity
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import kotlinx.android.synthetic.main.activity_online_games_temlate.*

var  locale_activity_for_emotion : Activity? = null

fun show_my_emotion()
{
    locale_activity_for_emotion?.button_emotion?.alpha = 1f
    PICTURE_EMOTION[EMOTION]?.let { it1 -> locale_activity_for_emotion?.button_emotion?.setBackgroundResource(it1)}
    EMOTION = -1
    locale_activity_for_emotion?.button_emotion?.animate()?.alpha(0f)?.duration = 1000;
    locale_activity_for_emotion?.button_emotion?.animate()?.alpha(0f)?.startDelay = 1000
    locale_activity_for_emotion?. button_emotion?.setOnClickListener {
        locale_activity_for_emotion?.button_emotion?.setBackgroundResource(R.drawable.nulevoe)
    }

    //TODO передать переменную "EMOTION" в firebase
}

fun show_emotion_from_rival()
{
    var number_emotion: Int = 1 //TODO вызывай эту функцию и передавац значение эмоции из firebase
    locale_activity_for_emotion?.button_emotion_rival?.alpha = 1f
    PICTURE_EMOTION[number_emotion]?.let { it1 ->  locale_activity_for_emotion?.button_emotion_rival?.setBackgroundResource(it1)}
    locale_activity_for_emotion?.button_emotion_rival?.animate()?.alpha(0f)?.duration = 1000;
    locale_activity_for_emotion?.button_emotion_rival?.animate()?.alpha(0f)?.startDelay = 1000
    locale_activity_for_emotion?.button_emotion_rival?.setOnClickListener {
    locale_activity_for_emotion?.button_emotion_rival?.setBackgroundResource(R.drawable.nulevoe)
    }
}