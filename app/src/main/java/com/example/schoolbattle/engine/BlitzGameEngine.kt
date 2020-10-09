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
    var userRating: Int
    var opponentRating: Int

    fun init() {
        val loseUpd = mapOf(
            "winner" to opponent,
            "loser" to user,
            "winnerRating" to updateRating(opponentRating, userRating, 1.0).first,
            "loserRating" to updateRating(opponentRating, userRating, 1.0).second
        )
        val winUpd = mapOf(
            "loser" to opponent,
            "winner" to user,
            "winnerRating" to updateRating(userRating, opponentRating, 1.0).first,
            "loserRating" to updateRating(userRating, opponentRating, 1.0).second
        )
        positionData.onDisconnect().updateChildren(loseUpd)
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
                        positionData.child("winner").setValue(loseUpd)
                        this.cancel()
                    }
                    if (cntOpponent >= 500L) {
                        positionData.child("winner").setValue(winUpd)
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
        val loseUpd = mapOf(
            "winner" to opponent,
            "loser" to user,
            "winnerRating" to updateRating(opponentRating, userRating, 1.0).first,
            "loserRating" to updateRating(opponentRating, userRating, 1.0).second
        )
        val winUpd = mapOf(
            "loser" to opponent,
            "winner" to user,
            "winnerRating" to updateRating(userRating, opponentRating, 1.0).first,
            "loserRating" to updateRating(userRating, opponentRating, 1.0).second
        )

        if (!isFinished) {
            timer.cancel()
            positionData.onDisconnect().cancel()
            if (res == "Победа") {
                positionData.updateChildren(winUpd)
            } else if (res == "Поражение") {
                positionData.updateChildren(loseUpd)
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