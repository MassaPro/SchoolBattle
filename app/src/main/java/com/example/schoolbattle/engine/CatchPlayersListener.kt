package com.example.schoolbattle.engine

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.schoolbattle.gamesonline.*
import com.example.schoolbattle.myRef
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

fun initCatchPlayersListenerForBlitzGame(username: String, context: Context) {
    myRef.child("Users").child(username).child("blitz").addChildEventListener(object : ChildEventListener {
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            myRef.child("Users").child(username).child("blitz").removeValue()
            val intent = if (p0.key.toString() == "XOGame") {
                Intent(context, XOGameActivity::class.java)
            } else if (p0.key.toString() == "DotGame") {
                Intent(context, DotGameActivity::class.java)
            } else if (p0.key.toString() == "SnakeGame") {
                Intent(context, SnakeGameActivity::class.java)
            } else if (p0.key.toString() == "BoxGame") {
                Intent(context, BoxGameActivity::class.java)
            }  else if (p0.key.toString() == "Reversi") {
                Intent(context, ReversiGameActivity::class.java)
            } else {
                Intent(context, TEST::class.java)
            }
            intent.putExtra("type", "blitz")
            for (i in p0.children) {
                intent.putExtra("opponent", i.key.toString())
                intent.putExtra("move", i.value.toString())
                break
            }
            Toast.makeText(context, p0.toString(), Toast.LENGTH_LONG).show()
            context.startActivity(intent)
        }
        override fun onCancelled(p0: DatabaseError) {}
        override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
        override fun onChildRemoved(p0: DataSnapshot) {}
    })
}