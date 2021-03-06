package com.sga.schoolbattle.engine

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sga.schoolbattle.CONTEXT
import com.sga.schoolbattle.R
import com.sga.schoolbattle.currentContext
import com.sga.schoolbattle.myRef
import com.google.firebase.database.ValueEventListener

var StupidGame: Activity = Activity()

var is_pressed = false

class StupidGameActivity : AppCompatActivity() {

    private var eventListener: ValueEventListener? = null
    private var state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CONTEXT = this@StupidGameActivity
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this@StupidGameActivity

        setContentView(R.layout.activity_stupid_game)
        state = true
        StupidGame = this
        currentContext = this
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()
        is_pressed = true
        //button.setOnClickListener {
          //  button.isEnabled = false
            Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show()

            /*eventListener = myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(snapshot: DataSnapshot) {
                    var flag = true
                    if (snapshot.child(gameName + "Users").hasChildren()) {
                        for (i in snapshot.child(gameName + "Users").children) {
                            if (i.key.toString() == globalName.toString() || snapshot.child("Users").child(globalName.toString()).child("Games").hasChild(i.key.toString() + ' ' + gameName)) {
                                continue
                            }
                            //delete user from wait-list
                            myRef.child(gameName + "Users").child(i.key.toString()).removeValue()
                            myRef.child(gameName + "Users").child(globalName.toString()).removeValue()

                            //add current user to opponents games list
                            myRef.child("Users").child(i.key.toString()).child("Games")
                                .child(globalName.toString() + ' ' + gameName).setValue("StupidGame")

                            //add opponent to current user games list
                            myRef.child("Users").child(globalName.toString()).child("Games")
                                .child(i.key.toString() + ' ' + gameName).setValue("StupidGame")

                            //add game to games
                            myRef.child(gameName + "s").child(if (i.key.toString() < globalName.toString())
                            i.key + '_' + globalName else globalName + '_' + i.key).child("Move").setValue("0")

                            myRef.removeEventListener(this)
                            flag = false
                            break
                        }
                    }
                    if (flag && state) {
                        myRef.child(gameName + "Users").child(globalName.toString()).setValue(globalName)
                    }
                }
            })*/
        //}
    }

    override fun onPause() {
        super.onPause()
        currentContext = null
        is_pressed = false
        state = false

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()
        eventListener?.let { myRef.removeEventListener(it) }
        myRef.child(gameName + "Users").child(globalName.toString()).removeValue()
    }
}
