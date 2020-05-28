package com.example.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random


var GAMES: MutableList<Game> = mutableListOf()
var CHOOSE_GAMES: MutableList<String> = mutableListOf("StupidGame", "XOGame", "DotGame","Corners")
var currentContext: Context? = null

class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {
    override fun toString(): String {
        return "$name $type"
    }
}

class ShowResult(activity: Activity)  : AppCompatActivity(){

    private val dialog = Dialog(activity)
    private var state = false

    private val dialog_one_device = Dialog(activity)

    var exodus : String = "a"

    fun showResult(result: String, gameType: String, globalName: String) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.activity_game_over)

        val ng = dialog.findViewById(R.id.restart) as Button
        var eventListener: ValueEventListener? = null
        is_pressed = true

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
            //myRef.child(gameType + "Users").child(globalName).setValue(globalName)
            //myRef.addValueEventListener(NewGameListener)
        }

        dialog.setOnDismissListener {
            is_pressed = false
            state = false
            eventListener?.let { myRef.removeEventListener(it) }
            myRef.child(gameType + "Users").child(globalName).removeValue()
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

    fun showResult_one_device(result: String,Game_Type: String) {
        dialog_one_device.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_one_device.setCancelable(false)
        dialog_one_device.setCanceledOnTouchOutside(true)
        dialog_one_device.setContentView(R.layout.activity_game_over_one_device)

        val button_revanshe = dialog_one_device.findViewById(R.id.restart_one_device) as Button
        button_revanshe.setOnClickListener{
            if(Game_Type == "XOGame")
            {
                val intent = Intent(this, XOGame_oneDivice::class.java)
                startActivity(intent)
            }
        }
        val body = dialog_one_device.findViewById(R.id.resultText_one_device) as TextView
        body.text = result
        dialog_one_device.show()
    }
    fun delete() {
        dialog.dismiss()
        dialog_one_device.dismiss()
    }
}

