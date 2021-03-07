package com.sga.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sga.schoolbattle.engine.BlitzActivity
import com.sga.schoolbattle.engine.LongActivity
import kotlinx.android.synthetic.main.activity_new_game.*
import kotlinx.android.synthetic.main.activity_new_game_item.view.*

var NewGame: Activity = Activity()


class NewGameActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        CONTEXT = this
        text.text = translate_games("Choose game")

        when (Design) {
            "Normal" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_normal)
            }
            "Egypt" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_normal)
                game_list_menu.setBackgroundResource(R.drawable.background_egypt);
                my_toolbar2.setBackgroundColor(rgb(255, 230, 163))
                text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                text.setTextColor(Color.BLACK)
            }
            "Casino" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_casino)
                game_list_menu.setBackgroundResource(R.drawable.background2_casino);
                my_toolbar2.setBackgroundColor(argb(0,224,164,103))
                text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                text.setTextColor(Color.YELLOW)
            }
            "Rome" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_rome)
                game_list_menu.setBackgroundResource(R.drawable.background_rome);
                my_toolbar2.setBackgroundColor(argb(0,224,164,103))
                text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                text.setTextColor(rgb(193,150,63))
            }
            "Gothic" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_gothic)
                game_list_menu.setBackgroundResource(R.drawable.background_gothic);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                text.setTextColor(Color.WHITE)
            }
            "Japan" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_normal)
                game_list_menu.setBackgroundResource(R.drawable.background_japan);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                text.setTextColor(Color.BLACK)
            }
            "Noir" -> {
                back_button.setBackgroundResource(R.drawable.back_arrow_gothic)
                game_list_menu.setBackgroundColor(rgb(20,20,20));
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                text.setTextColor(Color.WHITE)
            }
        }

        NewGame = this
        val opponent: String? = intent.getStringExtra("opponent")
        game_list.layoutManager = GridLayoutManager(this, 2)
        setupRecyclerView(game_list, intent.getIntExtra("playType", -1), this, opponent)


        back_button.setOnClickListener {
            var intent = Intent(this,NavigatorActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }

        //val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        //val globalName = prefs.getString("username", "")
        //val intent = Intent(applicationContext, StupidGameActivity::class.java)
        //startActivity(intent)
        //finish()
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        type: Int,
        activity: Activity,
        opponent: String?
    ) {
        recyclerView.adapter = NewGameActivity.SimpleItemRecyclerViewAdapter(CHOOSE_GAMES, type, activity, opponent)
    }

    class SimpleItemRecyclerViewAdapter(private val ITEMS: MutableList<String>, type: Int, activity: Activity, opponent: String?):
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as String

                when (type) {
                    1 -> {
                        val intent = Intent(v.context, LongActivity::class.java).apply {
                            putExtra("gameName", item)
                        }
                        v.context.startActivity(intent)
                        activity.overridePendingTransition(0 , 0)
                    }
                    2 -> {
                        val intent = Intent(v.context, OneDevicePlayActivity::class.java).apply {
                            putExtra("gameName", item)
                        }
                        v.context.startActivity(intent)
                        activity.overridePendingTransition(0 , 0)

                    }
                    3 -> {
                        val intent = Intent(v.context, PlayWithComputerActivity::class.java).apply {
                            putExtra("gameName", item)
                        }
                        v.context.startActivity(intent)
                        activity.overridePendingTransition(0 , 0)
                    }
                    0 -> {
                        val intent = Intent(v.context, BlitzActivity::class.java).apply {
                            putExtra("gameName", item)
                        }
                        v.context.startActivity(intent)
                        activity.overridePendingTransition(0 , 0)
                    }
                    5 -> {
                        val systemTime = System.currentTimeMillis()
                        val username = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")!!
                        myRef.child("Users").child(opponent!!).child("calls")
                            .child("9$systemTime").child(item)
                            .child(username).setValue(systemTime % 2).addOnSuccessListener {
                                Toast.makeText(v.context, "Вызов отправлен!", Toast.LENGTH_LONG).show()
                            }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_new_game_item, parent, false)
            when (Design) {
                "Normal" -> {
               //   view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.button)
                }
                "Egypt" -> {
                    view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.background_egypt)
                    view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                }
                "Casino" -> {
                    view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_casino)
                    view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                    view.findViewById<TextView>(R.id.textView2).setTextColor(Color.YELLOW)
                }
                "Rome" -> {
                    view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_rome)
                    view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                    view.findViewById<TextView>(R.id.textView2).setTextColor(Color.rgb(193, 150, 63))
                }
                "Gothic" -> {
                    view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_gothic)
                    view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                    view.findViewById<TextView>(R.id.textView2).setTextColor(Color.WHITE)
                }
                "Japan" -> {
                    view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_japan)
                    view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                }
                "Noir" -> {
                    view.findViewById<CardView>(R.id.card2).setBackgroundResource(R.drawable.new_game_item_noir)
                    view.findViewById<TextView>(R.id.textView2).setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                    view.findViewById<TextView>(R.id.textView2).setTextColor(Color.WHITE)
                }
            }

            //card2.setBackgroundResource(R.drawable.background_egypt)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.idView.text = translate_games(ITEMS[position])
            if(Design == "Normal" || Design == "Egypt" || Design == "Japan")
            {
                LIGTH_GAMES_ICONS[ITEMS[position]]?.let { holder.icon.setImageResource(it) }
            }
            else if(Design == "Gothic" || Design == "Noir")
            {
                DARK_GAMES_ICONS[ITEMS[position]]?.let { holder.icon.setImageResource(it) }
            }
            else if(Design == "Casino")
            {
                BROWN_GAMES_ICONS[ITEMS[position]]?.let { holder.icon.setImageResource(it) }
            }
            else if(Design == "Rome")
            {
                YELLOW_GAMES_ICONS[ITEMS[position]]?.let { holder.icon.setImageResource(it) }
            }

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
            val icon: ImageView = view.imageView9
        }
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }

    //TODO РАЗОБРАТЬСЯ С ВЫХОДОМ      - (я так и не понял, чем этот варик плох @альф)
    override fun onBackPressed()
    {
        super.onBackPressed()
        var intent = Intent(this,NavigatorActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}


