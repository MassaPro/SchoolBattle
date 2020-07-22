package com.example.schoolbattle.engine

import android.content.Context
import android.content.Intent
import com.example.schoolbattle.encodeGame
import com.example.schoolbattle.gamesonline.TEST
import com.example.schoolbattle.myRef
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

fun initCatchPlayersListenerForBlitzGame(username: String, context: Context) {
    myRef.child("Users").child(username).child("blitz").addChildEventListener(object : ChildEventListener {
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            myRef.child("Users").child(username).child("blitz").removeValue()
            val intent = Intent(context, TEST::class.java)
            intent.putExtra("type", p0.value.toString())
            context.startActivity(intent)
        }
        override fun onCancelled(p0: DatabaseError) {}
        override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
        override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
        override fun onChildRemoved(p0: DataSnapshot) {}
    })
}