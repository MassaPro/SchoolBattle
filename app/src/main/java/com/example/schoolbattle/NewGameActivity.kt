package com.example.schoolbattle

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.activity_new_game_item.view.*

var NewGame: Activity = Activity()

class NewGameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        CONTEXT = this
        NewGame = this
        setupRecyclerView(game_list, intent.getIntExtra("playType", -1), this)




        //val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        //val globalName = prefs.getString("username", "")
        //val intent = Intent(applicationContext, StupidGameActivity::class.java)
        //startActivity(intent)
        //finish()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, type: Int, activity: Activity) {
        recyclerView.adapter = NewGameActivity.SimpleItemRecyclerViewAdapter(CHOOSE_GAMES, type, activity)
    }

    class SimpleItemRecyclerViewAdapter(private val ITEMS: MutableList<String>, type: Int, activity: Activity):
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as String

                if (type == 1) {
                    val intent = Intent(v.context, StupidGameActivity::class.java).apply {
                        putExtra("gameName", item)
                    }
                    v.context.startActivity(intent)
                    activity.overridePendingTransition(0 , 0)
                }
                if (type == 2) {
                    val intent = Intent(v.context, OneDevicePlayActivity::class.java).apply {
                        putExtra("gameName", item)
                    }
                    v.context.startActivity(intent)
                    activity.overridePendingTransition(0 , 0)
                }
                if (type == 3) {
                    val intent = Intent(v.context, PlayWithComputerActivity::class.java).apply {
                        putExtra("gameName", item)
                    }
                    v.context.startActivity(intent)
                    activity.overridePendingTransition(0 , 0)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_new_game_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.idView.text = ITEMS[position]
            with(holder.itemView) {
                tag = ITEMS[position]
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount(): Int {
            return ITEMS.size
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.textView2
        }
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}


