package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


//Здесь объявлена наша база данных

//var database = FirebaseDatabase.getInstance()
//var myRef = database.getReference("message")
lateinit var listener: ChildEventListener
var recyclerSet: RecyclerSet = RecyclerSet()

class RecyclerSet {
    private var s: MutableSet<String> = mutableSetOf()
    private var context: Context? = null
    fun add(el: Game) {
        if (!s.contains(el.toString())) {
            s.add(el.toString())
            GAMES.add(el)
            Log.w("FFF", el.toString())
            gamesRecycler.adapter?.notifyDataSetChanged()
        }
        if (is_pressed) {
            Log.w("CCC", "HI")
            //this.context!!.startActivity(Intent(this.context, StupidGameActivityTwoPlayers::class.java))

            /*this.context = context
            val intent = Intent(context, StupidGameActivityTwoPlayers::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)*/

            /*this.context = context
            val intent = Intent(this.context, StupidGameActivityTwoPlayers::class.java)
            startActivity(intent)*/
        }
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
                recyclerSet.add(Game(p0.key.toString()))
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                gamesRecycler.adapter?.notifyDataSetChanged()
            }
        })
    }
}