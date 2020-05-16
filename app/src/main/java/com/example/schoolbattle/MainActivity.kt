package com.example.schoolbattle

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_item.view.*
import kotlinx.android.synthetic.main.activity_game_list.*

lateinit var gamesRecycler: RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs.getString("username", "")
        toolbarName.text = globalName
        updateRecycler(globalName.toString())

        setupRecyclerView(item_list)
        gamesRecycler = item_list

        logOut.setOnClickListener {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("username", "")
            editor.apply()
            recyclerSet.clear()
            myRef.child("Users").child(globalName.toString()).child("Games").removeEventListener(
                listener)
            val intent = Intent(this, NullActivity::class.java)
            startActivity(intent)
            finish()
        }

        newGameButton.setOnClickListener {
            val intent = Intent(applicationContext, NewGameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(GAMES)
    }

    class SimpleItemRecyclerViewAdapter(private val ITEMS: MutableList<Game>):
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Game
                val intent = if (item.name.contains("StupidGame")) {
                    Intent(v.context, StupidGameActivityTwoPlayers::class.java).apply {
                        putExtra("opponentName", item.name)
                    }
                } else {
                    Intent(v.context, XOGameActivity::class.java).apply {
                        putExtra("opponentName", item.name)
                    }
                }

                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_game_item, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.idView.text = ITEMS[position].type + ": You vs"
            holder.contentView.text = ITEMS[position].name
            with(holder.itemView) {
                tag = ITEMS[position]
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount(): Int {
            return ITEMS.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
