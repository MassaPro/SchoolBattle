package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_stupid_game.*


class StupidGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stupid_game)


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
