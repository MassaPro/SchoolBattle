package com.example.schoolbattle.engine

import android.app.Activity
import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.solver.widgets.Snapshot
import com.example.schoolbattle.myRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

    fun init() {
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
}