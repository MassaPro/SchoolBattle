package com.example.schoolbattle.engine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.schoolbattle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BlitzActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blitz)
        val globalName = getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()
        myRef.child("blitz-wait-list").child(gameName).child(globalName!!).onDisconnect().removeValue()
        myRef.child("blitz-wait-list").child(gameName).child(globalName).setValue(if (RATING == -1) 1000 else RATING)
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }

    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()
        myRef.child("blitz-wait-list").child(gameName).child(globalName!!).removeValue()
        finish()
    }
}
