package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


lateinit var listener: ChildEventListener
var recyclerSet: RecyclerSet = RecyclerSet()

class RecyclerSet {
    private var s: MutableSet<String> = mutableSetOf()
    fun add(el: Game) {
        if (!s.contains(el.toString())) {
            s.add(el.toString())
            GAMES.add(el)
            Log.w("FFF", el.toString())
            gamesRecycler.adapter?.notifyDataSetChanged()
        }
        if (is_pressed) {
            is_pressed = false
            Log.w("CCC", "HI")
            val intent = if (el.name.contains(" StupidGame")) {
                Intent(currentContext, StupidGameActivityTwoPlayers::class.java)
            } else if (el.name.contains(" XOGame")) {
                Intent(currentContext, XOGameActivity::class.java)
            } else {
                Intent(currentContext, DotGameActivity::class.java)
            }

            intent.putExtra("opponentName", el.name)
            currentContext?.startActivity(intent)
            //myRef.child("StupidGames").child("")
            StupidGame.finish()
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
                Log.w("KKK", p0.key)
                gamesRecycler.adapter?.notifyDataSetChanged()
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                recyclerSet.add(Game(p0.key.toString()))
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                Log.w("KKK", p0.key)
                recyclerSet.erase(Game(p0.key.toString()))
            }
        })
    }
}