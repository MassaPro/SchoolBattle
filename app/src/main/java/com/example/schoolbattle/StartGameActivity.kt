package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_stupid_game.*

var StupidGame: Activity = Activity()

var is_pressed = false

class StupidGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stupid_game)
        StupidGame = this
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()





        button.setOnClickListener {
            is_pressed = true
           myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    var flag = true
                    if (snapshot.child(gameName + "Users").hasChildren()) {
                        for (i in snapshot.child(gameName + "Users").children) {
                            if (i.key.toString() == globalName.toString() || snapshot.child("Users").child(globalName.toString()).child("Games").hasChild(i.key.toString())) {
                                continue
                            }
                            //delete user from wait-list
                            myRef.child(gameName + "Users").child(i.key.toString()).removeValue()

                            //add current user to opponents games list
                            myRef.child("Users").child(i.key.toString()).child("Games")
                                .child(globalName.toString() + ' ' + gameName).setValue("StupidGame")

                            //add opponent to current user games list
                            myRef.child("Users").child(globalName.toString()).child("Games")
                                .child(i.key.toString() + ' ' + gameName).setValue("StupidGame")

                            //add game to games
                            myRef.child(gameName + "s").child(if (i.key.toString() < globalName.toString())
                            i.key + '_' + globalName else globalName + '_' + i.key).child("Move").setValue("0")

                            flag = false
                            break
                        }
                    }
                    if (flag) {
                        myRef.child(gameName + "Users").child(globalName.toString()).setValue(globalName)
                    }
                    //myRef.removeEventListener(this)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        is_pressed = false

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()
        myRef.child(gameName + "Users").child(globalName.toString()).removeValue()
    }
}
