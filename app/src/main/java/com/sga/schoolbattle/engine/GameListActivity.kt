package com.sga.schoolbattle.engine

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sga.schoolbattle.*
import com.sga.schoolbattle.gamesonline.*
import kotlinx.android.synthetic.main.activity_game_item.*
import kotlinx.android.synthetic.main.activity_game_item.view.*
import kotlinx.android.synthetic.main.activity_list_of_current_games.*

class GameListActivity : Fragment() {

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        item_list.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_list_of_current_games, container, false)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CONTEXT = requireActivity()
        currentGamesRecycler = item_list
        setupRecyclerView(item_list)
        Toast.makeText(requireContext(), CURRENTGAMES.size.toString(), Toast.LENGTH_LONG).show()
        when (Design) {
            "Normal" ->{

            }
            "Egypt" -> {
                game_list_playing.setBackgroundResource(R.drawable.game_list_menu_egypt);
                my_toolbar2.setBackgroundColor(rgb(255, 230, 163))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                //holder.game_list_item.setBackgroundResource(R.drawable.background_egypt);
            }
            "Casino" -> {
                game_list_playing.setBackgroundResource(R.drawable.background2_casino);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                toolbarName2.setTextColor(Color.YELLOW)
                toolbarName2.setTextSize(20f)
                //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            }
            "Rome" -> {
                game_list_playing.setBackgroundResource(R.drawable.sign_in_rome);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                toolbarName2.setTextColor(rgb(193,150,63))
                toolbarName2.setTextSize(20f)
                //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            }
            "Gothic" -> {
                game_list_playing.setBackgroundResource(R.drawable.background_gothic);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                toolbarName2.setTextColor(Color.WHITE)
                toolbarName2.setTextSize(20f)
                //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            }
            "Japan" -> {
                game_list_playing.setBackgroundResource(R.drawable.sign_in_japan);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                toolbarName2.setTextColor(Color.BLACK)
                toolbarName2.setTextSize(20f)
                //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            }
            "Noir" -> {
                game_list_playing.setBackgroundResource(R.drawable.sign_in_noir);
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                toolbarName2.setTextColor(Color.WHITE)
                toolbarName2.setTextSize(20f)
                //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            }
        }

        // (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.my_toolbar))
        //setSupportActionBar(findViewById(R.id.my_toolbar))

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        //toolbarName.text = globalName
        updateRecycler(globalName.toString())


    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter =
            SimpleItemRecyclerViewAdapter(
                CURRENTGAMES
            )
    }

    class SimpleItemRecyclerViewAdapter(private val ITEMS: MutableList<LongGame>):
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as LongGame
                val intent = when {
                    item.type.contains("XOGame") -> {
                        Intent(v.context, XOGameActivity::class
                            .java).apply {
                        }
                    }
                    item.type.contains("DotGame") -> {
                        Intent(v.context, DotGameActivity::class
                            .java).apply {
                        }
                    }
                    item.type.contains("BoxGame") -> {
                        Intent(v.context, BoxGameActivity::class
                            .java).apply {
                        }
                    }
                    item.type.contains("SnakeGame") -> {
                        Intent(v.context, SnakeGameActivity::class
                            .java).apply {
                        }
                    }
                    item.type.contains("Reversi") -> {
                        Intent(v.context, ReversiGameActivity::class
                            .java).apply {
                        }
                    }
                    else -> {
                        Intent(v.context, TEST::class
                            .java).apply {
                        }
                    }
                }
                intent.putExtra("opponent", item.opponent)
                intent.putExtra("move", item.move)
                intent.putExtra("type", "long")
                intent.putExtra("key", item.key)
                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_game_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.type.text = ITEMS[position].type
            holder.name.text = ITEMS[position].opponent


            if (USERAVAS[ITEMS[position].opponent] == null) USERAVAS[ITEMS[position].opponent] = 0
            PICTURE_AVATAR[ARRAY_OF_AVATAR_SHOP[USERAVAS[ITEMS[position].opponent].toString().toInt()]]?.let {
                holder.avatar.setBackgroundResource(
                    it
                )
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
            val type: TextView = view.type
            val name: TextView = view.name
            val avatar: Button = view.avatar_in_gamelist
        }
    }
}
