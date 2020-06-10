package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.schoolbattle.gamesonline.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


class RecyclerSet {
    private var s: MutableSet<String> = mutableSetOf()
    fun add(el: Game, isOld: Boolean, username: String) {
        if (!s.contains(el.toString())) {
            s.add(el.toString())
            GAMES.add(el)
            Log.w("FFF", el.toString())
            //Toast.makeText(CONTEXT,  "od" + CONTEXT.toSv bbtring(), Toast.LENGTH_LONG).show()
            if (!isOld) {
                Toast.makeText(CONTEXT, StupidGameActivity::getApplicationContext.toString(), Toast.LENGTH_LONG).show()
                Toast.makeText(CONTEXT, CONTEXT.applicationContext.toString(), Toast.LENGTH_LONG).show()
                val intent = if (el.name.contains(" StupidGame")) {
                    Intent(CONTEXT, StupidGameActivityTwoPlayers::class.java)
                } else if (el.name.contains(" XOGame")) {
                    Intent(CONTEXT, XOGameActivity::class.java)
                } else if (el.name.contains("DotGame")) {
                    Intent(CONTEXT, DotGameActivity::class.java)
                } else if (el.name.contains("BoxGame")) {
                    Intent(CONTEXT, BoxGameActivity::class.java)
                } else if (el.name.contains("SnakeGame")) {
                    Intent(CONTEXT, SnakeGameActivity::class.java)
                } else {
                    Intent(CONTEXT, StupidGameActivity::class.java)
                }
                intent.putExtra("opponentName", el.name)
                CONTEXT.startActivity(intent)
                myRef.child("Users").child(username).child("Games").child(el.name.toString()).child("old").setValue("old")

                //myRef.child("StupidGames").child("")
                StupidGame.finish()
            }
            gamesRecycler.adapter?.notifyDataSetChanged()

        }
    }

    fun erase(el: Game) {
        Log.w("KKK", el.toString())
        for (i in GAMES) {
            //TODO modify exception
            if (i.name == el.name && i.type == el.type) {
                GAMES.remove(i)
                break
            }
        }
        s.remove(el.toString())
        gamesRecycler.adapter?.notifyDataSetChanged()
    }

    fun clear() {
        s.clear()
        GAMES.clear()
        gamesRecycler.adapter?.notifyDataSetChanged()
    }
}

fun updateRecycler(username: String) {
    if (username != "" ) {
        listener = myRef.child("Users").child(username).child("Games").addChildEventListener(object :
            ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                gamesRecycler.adapter?.notifyDataSetChanged()
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                gamesRecycler.adapter?.notifyDataSetChanged()
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                gamesRecycler.adapter?.notifyDataSetChanged()
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                var fl = false
                if (p0.hasChild("old")) {
                    fl = true
                }
                recyclerSet.add(Game(p0.key.toString()), fl, username)
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                recyclerSet.erase(Game(p0.key.toString()))
            }
        })
    }
}