package com.example.schoolbattle.engine

import android.app.Activity
import android.content.Context
import java.util.*

interface BlitzGameEngine {
    var timer: Timer
    var cntUser: Int
    var cntOpponent: Int
    fun init(activity: Activity) {
        timer = Timer(true)
        cntUser = 0
        cntOpponent = 0
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {

                }
            }
        }, 0, 1000L)
    }
}