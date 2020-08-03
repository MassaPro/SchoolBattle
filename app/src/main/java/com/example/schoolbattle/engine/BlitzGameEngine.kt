package com.example.schoolbattle.engine

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.Snapshot
import com.example.schoolbattle.myRef
import com.google.firebase.database.*
import java.util.*

interface BlitzGameEngine {
    var timer: Timer
    var cnt: Int
    var cntUser: Int
    var cntOpponent: Int
    val userT: TextView
    val opponentT: TextView
    val user: String
    val opponent: String
    var move: Boolean
    var positionData: DatabaseReference
    var activity: Activity
    var type: String
    var isFinished: Boolean

    fun init() {
        positionData.child("winner").onDisconnect().setValue(opponent)
        //positionData.onDisconnect().removeValue()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    if (move) {
                        cntUser++
                    } else {
                        cntOpponent++
                    }
                    userT.text = cntUser.toString()
                    opponentT.text = cntOpponent.toString()
                    if (cntUser >= 500L) {
                        positionData.child("winner").setValue(opponent)
                        this.cancel()
                    }
                    if (cntOpponent >= 500L) {
                        positionData.child("winner").setValue(user)
                        this.cancel()
                    }
                }
            }
        }, 0, 100L)
    }

    fun changeMoveAndSyncTimer(p0: DataSnapshot) {
        move = !move
        if (move) {
            val p = p0.child("time").child(opponent)
            val cur = if (p.exists()) p.value.toString().toInt() else 0
            cntOpponent = cur
        }
    }

    fun stopTimer() {
        timer.cancel()
    }

    fun finish(res: String, activity: Activity, isActivityRunning: Boolean) {
        if (!isFinished) {
            timer.cancel()
            positionData.child("winner").onDisconnect().cancel()
            if (res == "Победа") {
                positionData.child("winner").setValue(user)
            } else if (res == "Поражение") {
                positionData.child("winner").setValue(opponent)
            } else {
                positionData.child("winner").setValue("0")
            }
            if (isActivityRunning) {
                val dialog = ShowResult(activity)
                dialog.showResult(res, type, user, opponent)
            }
            isFinished = true
        }
    }
}