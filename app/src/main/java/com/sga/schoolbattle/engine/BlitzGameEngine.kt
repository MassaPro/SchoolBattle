package com.sga.schoolbattle.engine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.widget.TextView
import com.sga.schoolbattle.myRef
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
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

        var numberOfSecondsBeforeEnd = 60*5L
        if(activity.toString().contains("DotGameActivity"))
        {
            numberOfSecondsBeforeEnd = 60*10L
        }

        timer.scheduleAtFixedRate(object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                activity.runOnUiThread {
                    if (move) {
                        cntUser++
                    } else {
                        cntOpponent++
                    }
                    if (cntUser % 10 == 0) {
                        userT.text = convert_to_right_time( numberOfSecondsBeforeEnd.toInt() - cntUser / 10)
                    }
                    if (cntOpponent % 10 == 0) {
                        opponentT.text = convert_to_right_time( numberOfSecondsBeforeEnd.toInt() - cntOpponent / 10)
                    }
                    if (cntUser >= 10L * numberOfSecondsBeforeEnd) {
                        positionData.setValue(loseUpd)
                        this.cancel()
                    }
                    if (cntOpponent >= 10 * numberOfSecondsBeforeEnd) {
                        positionData.setValue(winUpd)
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
            var newRating = 0
            timer.cancel()
            positionData.onDisconnect().cancel()
            var moneyChange = 0
            if (res == "Победа") {
                //TODO CHECK update money
                positionData.updateChildren(winUpd)
                newRating = updateRating(userRating, opponentRating, 1.0).first
                moneyChange = updateEconomyParams(activity, "blitz", "winner", newRating - userRating)
                myRef.child("Users/$user/rating_history").push().setValue(updateRating(userRating, opponentRating, 1.0).first)
            } else if (res == "Поражение") {
                //TODO update money
                positionData.updateChildren(loseUpd)
                newRating = updateRating(opponentRating, userRating, 1.0).second
                moneyChange = updateEconomyParams(activity, "blitz", "lose", newRating - userRating)
                myRef.child("Users/$user/rating_history").push().setValue(updateRating(userRating, opponentRating, 0.0).first)
            } else {
                positionData.child("winner").setValue("0")
                myRef.child("Users/$user/rating_history").push().setValue(updateRating(userRating, opponentRating, 0.5).first)
            }
            if (isActivityRunning) {
                var dialog = ShowResult(activity)
                dialog.showResult(res, type, user, opponent, userRating, newRating, moneyChange)
                activity.frameLayout4.setOnClickListener {
                    dialog = ShowResult(activity)
                    dialog.showResult(res, type, user, opponent, userRating, newRating, moneyChange)
                }
            }
            isFinished = true
        }
    }
}

fun convert_to_right_time(time:Int): String
{

    val seconds = time%60
    val minutes = (time - seconds)/60
    return ckop(minutes)+':'+ ckop(seconds)
}

fun ckop(n:Int):String
{
    val s = n.toString()
    if(s.length==1)
    {
        return "0$s"
    }
    return s
}