package com.example.schoolbattle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BlitzActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blitz)
    }

    private var eventListener: ValueEventListener? = null
    private var state = false

    override fun onResume() {
        super.onResume()
        CONTEXT = this

        setContentView(R.layout.activity_stupid_game)
        state = true
        StupidGame = this
        currentContext = this
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        var gameName = intent?.getStringExtra("gameName").toString()

        is_pressed = true
        //button.setOnClickListener {
        //  button.isEnabled = false
        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show()

        gameName
        eventListener = myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var flag = true
                if (snapshot.child("Blitz" + gameName + "Users").hasChildren()) {
                    for (i in snapshot.child("Blitz" + gameName + "Users").children) {
                        if (i.key.toString() == globalName.toString() || snapshot.child("Users").child(globalName.toString()).child("Games").child(i.key.toString() + ' ' + gameName).hasChild("Blitz")) {
                            continue
                        }
                        //delete user from wait-list
                        myRef.child("Blitz" + gameName + "Users").child(i.key.toString()).removeValue()
                        myRef.child("Blitz" + gameName + "Users").child(globalName.toString()).removeValue()

                        //add current user to opponents games list
                        myRef.child("Users").child(i.key.toString()).child("BlitzGames")
                            .child(globalName.toString() + ' ' + gameName).setValue("Blitz")

                        //add opponent to current user games list
                        myRef.child("Users").child(globalName.toString()).child("BlitzGames")
                            .child(i.key.toString() + ' ' + gameName).setValue("Blitz")

                        //add game to games
                        myRef.child("Blitz" + gameName + "s").child(if (i.key.toString() < globalName.toString())
                            i.key + '_' + globalName else globalName + '_' + i.key).child("Move").setValue("0")

                        myRef.removeEventListener(this)
                        flag = false
                        break
                    }
                }
                if (flag && state) {
                    myRef.child( "Blitz" + gameName + "Users").child(globalName.toString()).setValue(globalName)
                }
            }
        })
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
        myRef.child("Blitz" + gameName + "Users").child(globalName.toString()).removeValue()
    }
}
