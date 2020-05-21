package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ContextUtils
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.activity_game_item.view.*
import kotlinx.android.synthetic.main.activity_game_list.*

lateinit var gamesRecycler: RecyclerView

class MainActivity : Fragment() {

    /*override fun onBackPressed() {
        super.onBackPressed()
        //overridePendingTransition(0, 0)
        finishAffinity(ContextUtils.getActivity(this))
    }*/

    override fun onResume() {
        super.onResume()
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //navView.selectedItemId = R.id.navigation_home
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_game_list, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


       // (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.my_toolbar))
        //setSupportActionBar(findViewById(R.id.my_toolbar))

        /*val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.selectedItemId = R.id.navigation_home
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // put your code here
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    val intent = Intent(activity, SettingsActivity::class.java)
                    startActivity(intent)
                    //overridePendingTransition(0, 0)
                    // put your code here
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }*/
        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        toolbarName.text = globalName
        updateRecycler(globalName.toString())

        setupRecyclerView(item_list)
        gamesRecycler = item_list

        logOut.setOnClickListener {
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            editor?.putString("username", "")
            editor?.apply()
            recyclerSet.clear()
            myRef.child("Users").child(globalName.toString()).child("Games").removeEventListener(
                listener)
            val intent = Intent(activity, NullActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        newGameButton.setOnClickListener {
            val intent = Intent(activity, NewGameActivity::class.java)
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
                } else if (item.name.contains("XOGame")) {
                    Intent(v.context, XOGameActivity::class.java).apply {
                        putExtra("opponentName", item.name)
                    }
                } else {
                    Intent(v.context, DotGameActivity::class.java).apply {
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
