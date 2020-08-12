package com.example.schoolbattle.engine

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.schoolbattle.CURRENTGAMES
import com.example.schoolbattle.LongGame
import com.example.schoolbattle.currentGamesRecycler
import com.example.schoolbattle.gamesonline.*
import com.example.schoolbattle.myRef
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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

fun initCatchPlayersListenerForLongGame(username: String, context: Context) {
    val s: MutableSet<String> = mutableSetOf()
    myRef.child("Users").child(username).child("long").addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onCancelled(p: DatabaseError) {}
        override fun onDataChange(p: DataSnapshot) {
            CURRENTGAMES.clear()
            for (i in p.children) {
                s.add(i.key.toString())
                Log.w("asasa", s.toString())
                for (j in i.children) {
                    for (k in j.children) {
                        CURRENTGAMES.add(LongGame(i.key!!, j.key!!, k.key!!, k.value.toString()))
                        currentGamesRecycler?.adapter?.notifyDataSetChanged()
                    }
                }
            }
            myRef.child("Users").child(username).child("long").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    if (s.contains(p0.key.toString())) {
                        return
                    }
                    s.add(p0.key.toString())
                    for (j in p0.children) {
                        for (k in j.children) {
                            CURRENTGAMES.add(LongGame(p0.key!!, j.key!!, k.key!!, k.value.toString()))
                            currentGamesRecycler?.adapter?.notifyDataSetChanged()
                        }
                    }
                    var intent = Intent(context, TEST::class.java)
                    for (i in p0.children) {
                        intent = if (i.key.toString() == "XOGame") {
                            Intent(context, XOGameActivity::class.java)
                        } else if (i.key.toString() == "DotGame") {
                            Intent(context, DotGameActivity::class.java)
                        } else if (i.key.toString() == "SnakeGame") {
                            Intent(context, SnakeGameActivity::class.java)
                        } else if (i.key.toString() == "BoxGame") {
                            Intent(context, BoxGameActivity::class.java)
                        } else if (i.key.toString() == "Reversi") {
                            Intent(context, ReversiGameActivity::class.java)
                        } else {
                            Intent(context, TEST::class.java)
                        }
                        break
                    }
                    intent.putExtra("type", "long")
                    intent.putExtra("key", p0.key.toString())
                    for (i in p0.children) {
                        for (j in i.children) {
                            intent.putExtra("opponent", j.key.toString())
                            intent.putExtra("move", j.value.toString())
                            break
                        }
                        break
                    }
                    context.startActivity(intent)
                }
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot) {
                    s.remove(p0.key.toString())
                    for (j in p0.children) {
                        for (k in j.children) {
                            CURRENTGAMES.remove(LongGame(p0.key!!, j.key!!, k.key!!, k.value.toString()))
                            currentGamesRecycler?.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            })

        }
    })
}