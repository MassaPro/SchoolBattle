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

interface LongGameEngine {
    val userT: TextView
    val opponentT: TextView
    val user: String
    val opponent: String
    var move: Boolean
    var positionData: DatabaseReference
    var activity: Activity
    var type: String
    var key: String

    fun init() {
        userT.text = ""
        opponentT.text = ""
    }

    fun finish(res: String, activity: Activity, isActivityRunning: Boolean) {
        val upd = mutableMapOf<String, Any?>(
            "Users/$opponent/long/$key" to null,
            "Users/$opponent/long/$key" to null
        )
        myRef.updateChildren(upd)
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
    }
}