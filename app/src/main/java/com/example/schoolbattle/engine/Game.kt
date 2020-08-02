package com.example.schoolbattle.engine

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.example.schoolbattle.R
import com.example.schoolbattle.myRef
import com.example.schoolbattle.now
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {
    override fun toString(): String {
        return "$name $type"
    }
}

class ShowResult(activity: Activity) {
    private val dialog = Dialog(activity)
    private var state = false
    private var con = activity

    fun showResult(result: String, gameType: String, globalName: String, oppName: String) {
        now = con
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.activity_game_over)

        val ng = dialog.findViewById(R.id.restart) as Button
        val rv = dialog.findViewById(R.id.revanche) as Button
        var eventListener: ValueEventListener? = null
        is_pressed = true

        rv.setOnClickListener {
            myRef.child("Users").child(oppName).child("Revanches").child(globalName).child("gameName").setValue(gameType)
        }

        ng.setOnClickListener {
            ng.isEnabled = false
            state = true

            eventListener = myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    var flag = true
                    if (snapshot.child(gameType + "Users").hasChildren()) {
                        for (i in snapshot.child(gameType + "Users").children) {
                            if (i.key.toString() == globalName.toString() || snapshot.child("Users")
                                    .child(globalName.toString()).child("Games")
                                    .hasChild(i.key.toString() + ' ' + gameType)
                            ) {
                                continue
                            }
                            //delete user from wait-list
                            myRef.child(gameType + "Users").child(i.key.toString()).removeValue()
                            myRef.child(gameType + "Users").child(globalName.toString())
                                .removeValue()

                            //add current user to opponents games list
                            myRef.child("Users").child(i.key.toString()).child("Games")
                                .child(globalName.toString() + ' ' + gameType)
                                .setValue("StupidGame")

                            //add opponent to current user games list
                            myRef.child("Users").child(globalName.toString()).child("Games")
                                .child(i.key.toString() + ' ' + gameType).setValue("StupidGame")

                            //add game to games
                            myRef.child(gameType + "s").child(
                                if (i.key.toString() < globalName.toString())
                                    i.key + '_' + globalName else globalName + '_' + i.key
                            ).child("Move").setValue((0..1).random().toString())

                            myRef.removeEventListener(this)
                            flag = false
                            break
                        }
                    }
                    if (flag && state) {
                        myRef.child(gameType + "Users").child(globalName).setValue(globalName)
                    }
                }
            })
        }

        dialog.setOnDismissListener {
            is_pressed = false
            state = false
            eventListener?.let { myRef.removeEventListener(it) }
            myRef.child(gameType + "Users").child(globalName).removeValue()
        }
        val body = dialog.findViewById(R.id.resultText) as TextView
        body.text = result
        dialog.show()
    }

    fun delete() {
        dialog.dismiss()
    }
}
