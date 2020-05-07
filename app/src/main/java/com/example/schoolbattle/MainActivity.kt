package com.example.schoolbattle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game_item.view.*
import kotlinx.android.synthetic.main.activity_game_list.*


//var database = FirebaseDatabase.getInstance()
//var myRef: DatabaseReference = database.getReference("message")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)


        newGameButton.setOnClickListener {
            val intent = Intent(this, NewGameActivity::class.java)
            //intent.putExtra("user", UserClass)
            startActivity(intent)
        }

        setupRecyclerView(item_list)
        /*myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                //val post = dataSnapshot.getValue<Post>()
                // ...
                for (i in dataSnapshot.child("vector").children) {
                    cur.add(i.child("content").value.toString())
                }
                item_list.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })*/


        item_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Log.w("TAG", "NEWSTATE=$newState")
                }
            }
        })
        //window.navigationBarColor = Color.BLACK
        //window.decorView.setBackgroundColor(Color.BLACK)
    }



    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, cur)
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: MainActivity, val ITEMS: MutableList<String>):
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as String
                val intent = Intent(v.context, MainActivity::class.java).apply {
                    //putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
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
            val idView: TextView = view.id_text
        }
    }
}
