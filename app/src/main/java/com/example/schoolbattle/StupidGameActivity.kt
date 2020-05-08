package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_game_list.*
import kotlinx.android.synthetic.main.activity_stupid_game.*

var StupidGame: Activity = Activity()

class StupidGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stupid_game)
        StupidGame = this
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")

        fun goPlay() {
            val intent = Intent(this, StupidGameActivityTwoPlayers::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            myRef.child("StupidGameUsers").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        for (i in snapshot.children) {
                            myRef.child("StupidGameUsers").child(i.key.toString()).removeValue()
                            GAMES.add(Game(i.key.toString()))
                            for (to in GAMES) {
                                if (to.name == i.key.toString() && to.type == "StupidGame") {
                                    GAMES.removeAt(GAMES.size - 1)
                                    break
                                }
                            }

                            myRef.child("Users").child(i.key.toString()).child("Games").child(globalName.toString()).setValue("StupidGame")
                            myRef.child("Users").child(globalName.toString()).child("Games").child(i.key.toString()).setValue("StupidGame")
                            gamesRecycler.adapter?.notifyDataSetChanged()
                            goPlay()
                            break
                        }

                    } else {
                        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                        val globalName = prefs.getString("username", "")
                        if (globalName != null) {
                            myRef.child("StupidGameUsers").child(globalName).setValue(globalName)
                        }
                    }
                }
            })
        }
    }
}
