package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.gamesonline.*
import kotlinx.android.synthetic.main.activity_game_item.view.*
import kotlinx.android.synthetic.main.activity_settings.*

class GameListActivity : Fragment() {

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        //toolbarName.text = globalName
        updateRecycler(globalName.toString())
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //navView.selectedItemId = R.id.navigation_dashboard
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_settings, container, false)



    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CONTEXT = requireActivity()

        if (Design == "Egypt"){
            game_list_playing.setBackgroundResource(R.drawable.game_list_menu_egypt);
            my_toolbar2.setBackgroundColor(rgb(224,164,103))
            toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))

        }

        // (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.my_toolbar))
        //setSupportActionBar(findViewById(R.id.my_toolbar))

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        //toolbarName.text = globalName
        updateRecycler(globalName.toString())

        setupRecyclerView(item_list)
        gamesRecycler = item_list
        item_list.adapter?.notifyDataSetChanged()


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
                } else if (item.name.contains("XOGame")) {
                    Intent(v.context, XOGameActivity::class.java).apply {
                        putExtra("opponentName", item.name)
                    }
                } else if (item.name.contains("DotGame")){
                    Intent(v.context, DotGameActivity::class
                        .java).apply {
                        putExtra("opponentName", item.name)
                    }
                } else if (item.name.contains("BoxGame")){
                    Intent(v.context, BoxGameActivity::class
                        .java).apply {
                        putExtra("opponentName", item.name)
                    }
                } else if (item.name.contains("SnakeGame")){
                    Intent(v.context, SnakeGameActivity::class
                        .java).apply {
                        putExtra("opponentName", item.name)
                    }
                } else {
                    Intent(v.context, StupidGameActivity::class
                        .java).apply {
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
