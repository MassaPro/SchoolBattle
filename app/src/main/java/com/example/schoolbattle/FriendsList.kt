package com.example.schoolbattle

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_friends_item.*
import kotlinx.android.synthetic.main.activity_friends_item.view.*
import kotlinx.android.synthetic.main.activity_friends_list.*


class FriendsList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_friends_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView(activity?.findViewById(R.id.friends_list) as RecyclerView)
        friends_list.adapter?.notifyDataSetChanged()
        if (Design == "Egypt"){
            friends_list.setBackgroundResource(R.drawable.background_egypt)
            //nav_view.setBackgroundColor(rgb(224, 164, 103));
            //my_toolbar2.setBackgroundColor(rgb(224,164,103))
            //id_text.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //content.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //id_text.setTextColor(Color.WHITE)
        }
        if (Design == "Casino"){
            friends_list.setBackgroundResource(R.drawable.background2_casino)
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(FRIENDS)
    }

    class SimpleItemRecyclerViewAdapter(private val ITEMS: MutableList<String>):
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as String
                val intent = Intent(v.context, ProfileUserActivity::class.java)
                v.context.startActivity(intent)
                /*val rev = v.context?.let { Dialog(it) }
                val window: Window? = rev?.window
                val wlp = window?.attributes
                wlp?.gravity = Gravity.TOP
                rev?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                rev?.setCancelable(false)
                rev?.setCanceledOnTouchOutside(true)
                rev?.setContentView(R.layout.profile_dialog)
                (rev?.findViewById(R.id.profileName) as TextView).text = item
                rev?.show()
                (rev?.findViewById(R.id.profile_play) as Button).setOnClickListener {
                    val prfs = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    val username = prfs?.getString("username", "")
                    myRef.child("Users").child(item).child("Revanches").child(username!!).child("gameName").setValue("StupidGame")
                }*/
                //v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_friends_item, parent, false)
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
            //val contentView: TextView = view.content
        }
    }
}
