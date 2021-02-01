package com.sga.schoolbattle.engine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sga.schoolbattle.CONTEXT
import com.sga.schoolbattle.R
import com.sga.schoolbattle.myRef

class LongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blitz)
        val globalName = getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
        val gameName = intent?.getStringExtra("gameName").toString()
        myRef.child("long-wait-list").child(gameName).child(globalName!!).onDisconnect().removeValue()
        myRef.child("long-wait-list").child(gameName).child(globalName).setValue(globalName)
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
        myRef.child("long-wait-list").child(gameName).child(globalName!!).removeValue()
        finish()
    }
}
