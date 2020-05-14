package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


var GAMES: MutableList<Game> = mutableListOf()
var CHOOSE_GAMES: MutableList<String> = mutableListOf("StupidGame")

class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {
    override fun toString(): String {
        return "$name $type"
    }
}

fun showResult(result: String, activity: Activity, gameType: String, globalName: String) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(true)
    dialog.setContentView(R.layout.activity_game_over)

    val ng = dialog.findViewById(R.id.restart) as Button

    ng.setOnClickListener {
        is_pressed = true
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var flag = true
                if (snapshot.child(gameType + "Users").hasChildren()) {
                    for (i in snapshot.child(gameType + "Users").children) {
                        if (i.key.toString() == globalName.toString() || snapshot.child("Users").child(globalName.toString()).child("Games").hasChild(i.key.toString())) {
                            continue
                        }
                        //delete user from wait-list
                        myRef.child(gameType + "Users").child(i.key.toString()).removeValue()

                        //add current user to opponents games list
                        myRef.child("Users").child(i.key.toString()).child("Games")
                            .child(globalName.toString() + ' ' + gameType).setValue("StupidGame")

                        //add opponent to current user games list
                        myRef.child("Users").child(globalName.toString()).child("Games")
                            .child(i.key.toString() + ' ' + gameType).setValue("StupidGame")

                        //add game to games
                        myRef.child(gameType + "s").child(if (i.key.toString() < globalName.toString())
                            i.key + '_' + globalName else globalName + '_' + i.key).child("Move").setValue("0")

                        flag = false
                        break
                    }
                }
                if (flag) {
                    myRef.child(gameType + "Users").child(globalName.toString()).setValue(globalName)
                }
                //myRef.removeEventListener(this)
            }
        })
        //myRef.child(gameType + "Users").child(globalName).setValue(globalName)
        //myRef.addValueEventListener(NewGameListener)
    }

    dialog.setOnDismissListener {
        is_pressed = false
        //myRef.removeEventListener(NewGameListener)
    }
    val body = dialog.findViewById(R.id.resultText) as TextView
    body.text = result
    //val body = dialog .findViewById(R.id.body) as TextView
    //body.text = title
    //val yesBtn = dialog .findViewById(R.id.yesBtn) as Button
    //val noBtn = dialog .findViewById(R.id.noBtn) as TextView
    //yesBtn.setOnClickListener {
    //  dialog .dismiss()
    //}
    //noBtn.setOnClickListener { dialog .dismiss() }
    dialog.show()
}