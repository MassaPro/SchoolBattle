package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.activity_stupid_game.*
import java.util.*

var StupidGame: Activity = Activity()

//var opponentListener: ChildEventListener = TODO()
var is_pressed = false

class StupidGameActivity : AppCompatActivity() {

    fun goPlay() {
        val intent = Intent(applicationContext, StupidGameActivityTwoPlayers::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stupid_game)
        StupidGame = this
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")





        button.setOnClickListener {
            is_pressed = true
            /*opponentListener = myRef.child("Users").child(globalName.toString()).child("Games").addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    if (!currentGamesSet.contains(p0.key)) {
                        goPlay()
                    }
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                override fun onChildRemoved(p0: DataSnapshot) {}

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            })*/

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    var flag = true
                    if (snapshot.child("StupidGameUsers").hasChildren()) {
                        for (i in snapshot.child("StupidGameUsers").children) {
                            if (i.key.toString() == globalName.toString() || snapshot.child("Users").child(globalName.toString()).child("Games").hasChild(i.key.toString())) {
                                continue
                            }
                            myRef.child("StupidGameUsers").child(i.key.toString()).removeValue()
                            myRef.child("Users").child(i.key.toString()).child("Games")
                                .child(globalName.toString()).setValue("StupidGame")
                            myRef.child("Users").child(globalName.toString()).child("Games")
                                .child(i.key.toString()).setValue("StupidGame")
                            Toast.makeText(applicationContext, "WWW1", Toast.LENGTH_LONG).show()
                            flag = false
                            //goPlay()
                            break
                        }
                    }
                    if (flag) {
                        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                        val globalName = prefs.getString("username", "")
                        myRef.child("StupidGameUsers").child(globalName.toString()).setValue(globalName)
                    }
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        is_pressed = false

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        myRef.child("StupidGameUsers").child(globalName.toString()).removeValue()
    }
}
