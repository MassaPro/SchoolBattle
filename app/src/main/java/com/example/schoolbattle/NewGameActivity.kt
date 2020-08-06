package com.example.schoolbattle

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.activity_new_game_item.view.*

var NewGame: Activity = Activity()

class NewGameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        CONTEXT = this

        if (Design == "Egypt"){
            game_list_menu.setBackgroundResource(R.drawable.background_egypt);
            my_toolbar2.setBackgroundColor(rgb(224,164,103))
            text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            text.setTextColor(Color.BLACK)
            //new_game_card.setBackgroundResource(R.drawable.background_egypt)
        }
        else if (Design == "Casino"){
            game_list_menu.setBackgroundResource(R.drawable.background2_casino);
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))
            text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            text.setTextColor(Color.YELLOW)
        }
        else if (Design == "Rome"){
            game_list_menu.setBackgroundResource(R.drawable.background_rome);
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))
            text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            text.setTextColor(rgb(193,150,63))
        }

        else if (Design == "Gothic"){
            game_list_menu.setBackgroundResource(R.drawable.background_gothic);
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))
            text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            text.setTextColor(Color.WHITE)
        }

        else if (Design == "Japan"){
            game_list_menu.setBackgroundResource(R.drawable.background_japan);
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))
            text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            text.setTextColor(Color.BLACK)
        }
        else if (Design == "Noir"){
            game_list_menu.setBackgroundColor(rgb(30,30,30));
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))
            text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            text.setTextColor(Color.WHITE)
        }

        NewGame = this
        game_list.layoutManager = GridLayoutManager(this, 2)
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
                if (type == 0) {
                    val intent = Intent(v.context, BlitzActivity::class.java).apply {
                        putExtra("gameName", item)
                    }
                    v.context.startActivity(intent)
                    activity.overridePendingTransition(0 , 0)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_new_game_item, parent, false)
            if (Design == "Egypt"){
                view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.background_egypt)
                view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            }
            else if (Design == "Casino"){
                view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_casino)
                view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                view.findViewById<TextView>(R.id.textView2).setTextColor(Color.YELLOW)
            }
            else if (Design == "Rome"){
                view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_rome)
                view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                view.findViewById<TextView>(R.id.textView2).setTextColor(Color.rgb(193, 150, 63))
            }

            else if (Design == "Gothic"){
                view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_gothic)
                view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                view.findViewById<TextView>(R.id.textView2).setTextColor(Color.WHITE)
            }

            else if (Design == "Japan"){
                view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_japan)
                view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            }
            else if (Design == "Noir"){
                view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_noir)
                view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                view.findViewById<TextView>(R.id.textView2).setTextColor(Color.WHITE)
            }

            //card2.setBackgroundResource(R.drawable.background_egypt)
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


