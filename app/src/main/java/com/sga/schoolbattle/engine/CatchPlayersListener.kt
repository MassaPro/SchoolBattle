package com.sga.schoolbattle.engine

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.*
import com.sga.schoolbattle.gamesonline.*
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.call_dialog.*

fun initCatchPlayersListenerForBlitzGame(username: String, context: Context) {
    myRef.child("Users").child(username).child("blitz").addChildEventListener(object : ChildEventListener {
        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            myRef.child("Users").child(username).child("blitz").removeValue()


            val intent = when {
                p0.key.toString() == "XOGame" -> {
                    Intent(context, XOGameActivity::class.java)
                }
                p0.key.toString() == "DotGame" -> {
                    Intent(context, DotGameActivity::class.java)
                }
                p0.key.toString() == "SnakeGame" -> {
                    Intent(context, SnakeGameActivity::class.java)
                }
                p0.key.toString() == "BoxGame" -> {
                    Intent(context, BoxGameActivity::class.java)
                }
                p0.key.toString() == "Reversi" -> {
                    Intent(context, ReversiGameActivity::class.java)
                }
                else -> {
                    Intent(context, TEST::class.java)
                }
            }
            intent.putExtra("type", "blitz")
            for (i in p0.children) {
                intent.putExtra("opponent", i.key.toString())
                intent.putExtra("move", i.child("move").value.toString())
                intent.putExtra("rating", i.child("rating").value.toString())
                //Toast.makeText(context, i.child("rating").toString(), Toast.LENGTH_LONG).show()
                break
            }

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
    val isNeedInIntent: MutableSet<String> = mutableSetOf()
    val notNeedToWarn: MutableSet<String> = mutableSetOf()
    myRef.child("Users").child(username).child("long").addListenerForSingleValueEvent(object: ValueEventListener {
        override fun onCancelled(p: DatabaseError) {}
        override fun onDataChange(p: DataSnapshot) {
            CURRENTGAMES.clear()
            for (i in p.children) {
                s.add(i.key.toString())
                Log.w("asasa", s.toString())
                for (j in i.children) {
                    for (k in j.children) {
                        myRef.child("Users/" + k.key + "/image").addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {}
                            override fun onDataChange(p0: DataSnapshot) {
                                if (p0.exists()) {
                                    USERAVAS[k.key!!] = p0.value.toString()
                                }
                            }
                        })
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
                    lateinit var opponent: String
                    lateinit var type: String
                    for (j in p0.children) {
                        for (k in j.children) {
                            Log.w("USERAVAS", p0.value.toString())
                            myRef.child("Users/" + k.key + "/image").addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError) {}
                                override fun onDataChange(p0: DataSnapshot) {
                                    if (p0.exists()) {
                                        USERAVAS[k.key!!] = p0.value.toString()

                                    }
                                }
                            })
                            CURRENTGAMES.add(LongGame(p0.key!!, j.key!!, k.key!!, k.value.toString()))
                            opponent = k.key!!
                            type = j.key!!
                            currentGamesRecycler?.adapter?.notifyDataSetChanged()
                        }
                    }
                    var intent = Intent(context, TEST::class.java)
                    for (i in p0.children) {
                        intent = when {
                            i.key.toString() == "XOGame" -> {
                                Intent(context, XOGameActivity::class.java)
                            }
                            i.key.toString() == "DotGame" -> {
                                Intent(context, DotGameActivity::class.java)
                            }
                            i.key.toString() == "SnakeGame" -> {
                                Intent(context, SnakeGameActivity::class.java)
                            }
                            i.key.toString() == "BoxGame" -> {
                                Intent(context, BoxGameActivity::class.java)
                            }
                            i.key.toString() == "Reversi" -> {
                                Intent(context, ReversiGameActivity::class.java)
                            }
                            else -> {
                                Intent(context, TEST::class.java)
                            }
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
                    if (p0.key.toString()[0] == '9' && !isNeedInIntent.contains(p0.key.toString())) {
                        if (!notNeedToWarn.contains(p0.key.toString())) {
                            Toast.makeText(
                                CONTEXT,
                                "$opponent принял вызов в $type",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        context.startActivity(intent)
                    }
                }
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot) {
                    s.remove(p0.key.toString())
                    isNeedInIntent.remove(p0.key.toString())
                    notNeedToWarn.remove(p0.key.toString())
                    for (j in p0.children) {
                        for (k in j.children) {
                            CURRENTGAMES.remove(LongGame(p0.key!!, j.key!!, k.key!!, k.value.toString()))
                            currentGamesRecycler?.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            })

            var controlEqualCalls = mutableSetOf<String?>()

            myRef.child("Users").child(username).child("calls").addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildRemoved(p0: DataSnapshot) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                @SuppressLint("SetTextI18n")
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    for (j in p0.children) {
                        for (k in j.children) {
                            var data: LongGame? = null
                            for (i0 in p0.children) {
                                for (j0 in i0.children) {
                                    myRef.child("Users/" + j0.key + "/image").addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p00: DatabaseError) {}
                                        override fun onDataChange(p00: DataSnapshot) {
                                            if (p00.exists()) {
                                                USERAVAS[j0.key!!] = p00.value.toString()
                                            }
                                        }
                                    })
                                    data = LongGame(p0.key!!, i0.key!!, j0.key!!, j0.value.toString())
                                }
                            }
                            val callDialog = Dialog(CONTEXT)
                            callDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            callDialog.setCancelable(false)
                            callDialog.setCanceledOnTouchOutside(true)
                            callDialog.setContentView(R.layout.call_dialog)

                            when (Design) {
                                "Normal" -> {
                                    callDialog.call_dialog_add.setBackgroundResource(R.drawable.button)
                                    callDialog.call_dialog_play.setBackgroundResource(R.drawable.button)
                                    callDialog.call_dialog_reject.setBackgroundResource(R.drawable.button)
                                    callDialog.call_dialog_text.setTextColor(Color.BLACK)
                                }
                                "Egypt" -> {
                                    callDialog.fon.setBackgroundColor(Color.rgb(255, 230, 163))
                                    callDialog.call_dialog_add.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                                    callDialog.call_dialog_play.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                                    callDialog.call_dialog_reject.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                                    callDialog.call_dialog_text.setTextColor(Color.BLACK)
                                    callDialog.call_dialog_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                                }
                                "Casino" -> {
                                    callDialog.fon.setBackgroundResource(R.drawable.table)
                                    callDialog.call_dialog_add.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                                    callDialog.call_dialog_play.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                                    callDialog.call_dialog_reject.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)

                                    callDialog.call_dialog_add.setTextColor(Color.YELLOW)
                                    callDialog.call_dialog_play.setTextColor(Color.YELLOW)
                                    callDialog.call_dialog_reject.setTextColor(Color.YELLOW)

                                    callDialog.call_dialog_text.setTextColor(Color.YELLOW)
                                    callDialog.call_dialog_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                                }
                                "Rome" -> {
                                    callDialog.fon.setBackgroundResource(R.drawable.bottom_navigation_rome)
                                    callDialog.call_dialog_add.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                                    callDialog.call_dialog_play.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                                    callDialog.call_dialog_reject.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)

                                    callDialog.call_dialog_add.setTextColor(Color.rgb(193, 150, 63))
                                    callDialog.call_dialog_play.setTextColor((Color.rgb(193, 150, 63)) )
                                    callDialog.call_dialog_reject.setTextColor((Color.rgb(193, 150, 63)))
                                    callDialog.call_dialog_text.setTextColor(Color.rgb(193, 150, 63))
                                    callDialog.call_dialog_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                                }
                                "Gothic" -> {
                                    callDialog.fon.setBackgroundColor(Color.BLACK)
                                    callDialog.call_dialog_add.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                                    callDialog.call_dialog_play.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                                    callDialog.call_dialog_reject.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)

                                    callDialog.call_dialog_add.setTextColor(Color.WHITE)
                                    callDialog.call_dialog_play.setTextColor(Color.WHITE)
                                    callDialog.call_dialog_reject.setTextColor(Color.WHITE)

                                    callDialog.call_dialog_text.setTextColor(Color.WHITE)
                                    callDialog.call_dialog_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                                }
                                "Japan" -> {
                                    callDialog.call_dialog_add.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                                    callDialog.call_dialog_play.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                                    callDialog.call_dialog_reject.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)

                                    callDialog.call_dialog_add.setBackgroundResource(R.drawable.button)
                                    callDialog.call_dialog_play.setBackgroundResource(R.drawable.button)
                                    callDialog.call_dialog_reject.setBackgroundResource(R.drawable.button)
                                    callDialog.call_dialog_text.setTextColor(Color.BLACK)
                                    callDialog.call_dialog_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                                }
                                "Noir" -> {
                                    callDialog.fon.setBackgroundColor(Color.BLACK)
                                    callDialog.call_dialog_add.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                                    callDialog.call_dialog_play.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                                    callDialog.call_dialog_reject.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)

                                    callDialog.call_dialog_add.setTextColor(Color.WHITE)
                                    callDialog.call_dialog_play.setTextColor(Color.WHITE)
                                    callDialog.call_dialog_reject.setTextColor(Color.WHITE)

                                    callDialog.call_dialog_text.setTextColor(Color.WHITE)
                                    callDialog.call_dialog_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                                }
                            }
                            val callDialogAdd = callDialog.findViewById(R.id.call_dialog_add) as Button
                            val callDialogReject = callDialog.findViewById(R.id.call_dialog_reject) as Button
                            val callDialogPlay = callDialog.findViewById(R.id.call_dialog_play) as Button
                            val callDialogText = callDialog.findViewById(R.id.call_dialog_text) as TextView
                            callDialogText.text = "${data?.opponent} хочет поиграть в ${data?.type}"
                            if (data?.key in controlEqualCalls) {
                                myRef.child("Users").child(username).child("calls").child(p0.key.toString()).removeValue()
                                return
                            }
                            callDialog.show()
                            controlEqualCalls.add(data?.key)
                            callDialogReject.setOnClickListener {
                                myRef.child("Users").child(username).child("calls").child(p0.key.toString()).removeValue()
                                callDialog.dismiss()
                            }
                            callDialogPlay.setOnClickListener {
                                isNeedInIntent.add(data!!.key)
                                notNeedToWarn.add(data.key)
                                val upd = mutableMapOf<String, Any?>(
                                    "Users/$username/calls/" + data.key to null,
                                    "Users/$username/long/${data.key}/${data.type}/${data.opponent}" to data.move,
                                    "Users/${data.opponent}/long/${data.key}/${data.type}/$username" to 1 - data.move.toInt()
                                )
                                myRef.updateChildren(upd)
                                callDialog.dismiss()
                            }
                            callDialogAdd.setOnClickListener {
                                notNeedToWarn.add(data!!.key)
                                val upd = mutableMapOf<String, Any?>(
                                    "Users/$username/calls/" + data.key to null,
                                    "Users/$username/long/${data.key}/${data.type}/${data.opponent}" to data.move,
                                    "Users/${data.opponent}/long/${data.key}/${data.type}/$username" to 1 - data.move.toInt()
                                )
                                myRef.updateChildren(upd)
                                callDialog.dismiss()
                            }
                        }
                    }
                }
            })
        }
    })
}