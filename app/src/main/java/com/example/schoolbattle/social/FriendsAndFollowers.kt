package com.example.schoolbattle.social

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.R
import com.example.schoolbattle.myRef
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_friends_and_followers.*
import kotlinx.android.synthetic.main.activity_friends_and_followers_item.view.*

class FriendsAndFollowers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_and_followers)

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var username = prefs?.getString("username", "")
        if (intent.getStringExtra("curName") != null) {
            username = intent.getStringExtra("curName")
        }
        Toast.makeText(this, username, Toast.LENGTH_LONG).show()
        val friendsIn = mutableListOf<String>()
        val friendsOut = mutableListOf<String>()
        val type = intent.getStringExtra("friendsType")
        if (type == "in") {
            recyclerViewFriendsAndFollowers.adapter = ItemRecyclerViewAdapter(friendsIn)
            myRef.child("Users").child(username.toString()).child("FriendsIn").addChildEventListener(object:
                ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    friendsIn.add(p0.value as String)
                    (recyclerViewFriendsAndFollowers.adapter as ItemRecyclerViewAdapter).notifyItemInserted(friendsIn.size - 1)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    friendsIn.remove(p0.value as String)
                    recyclerViewFriendsAndFollowers.adapter?.notifyDataSetChanged()
                }
            })
        } else {
            recyclerViewFriendsAndFollowers.adapter = ItemRecyclerViewAdapter(friendsOut)
            myRef.child("Users").child(username.toString()).child("FriendsOut").addChildEventListener(object:
                ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    friendsOut.add(p0.value as String)
                    (recyclerViewFriendsAndFollowers.adapter as ItemRecyclerViewAdapter).notifyItemInserted(friendsIn.size - 1)
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    friendsOut.remove(p0.value as String)
                    recyclerViewFriendsAndFollowers.adapter?.notifyDataSetChanged()
                }
            })
        }
    }

    class ItemRecyclerViewAdapter(private val ITEMS: MutableList<String>):
        RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val intent = Intent(v.context, ProfileUserActivity::class.java)
                intent.putExtra("name", v.tag as String)
                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_friends_and_followers_item, parent, false)
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
            val idView: TextView = view.textViewFriendsAndFollowers
        }
    }
}
