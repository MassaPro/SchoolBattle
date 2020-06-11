package com.example.schoolbattle

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_item.view.*


class SearchActivity : AppCompatActivity() {

    private var USERS: MutableList<String> = mutableListOf()
    var rec: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val searchView = findViewById<SearchView>(R.id.search_field)
        searchView.onActionViewExpanded()

        recyclerViewSearch.adapter = ItemRecyclerViewAdapter(USERS)
        rec = recyclerViewSearch
        rec?.adapter?.notifyDataSetChanged()

        var blocked = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                if (query == null || query.isEmpty()) {
                    blocked = true
                    USERS.clear()
                    rec?.adapter?.notifyDataSetChanged()
                    return false
                }
                blocked = false
                //USERS.clear()
                //rec?.adapter?.notifyDataSetChanged()
                //Toast.makeText(this@SearchActivity, query, Toast.LENGTH_LONG).show()
                val sorted = myRef.child("Users").orderByChild("name").startAt(query).limitToFirst(30)
                var to = 0
                while (to <= USERS.size - 1) {
                    if (!USERS[to].startsWith(query) ||!USERS[to].contains(query)) {
                        USERS.removeAt(to)
                        to--
                    }
                    to++
                }
                rec?.adapter?.notifyDataSetChanged()
                sorted.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        Toast.makeText(this@SearchActivity, query + ' ' + blocked, Toast.LENGTH_LONG).show()
                        USERS.clear()
                        for (i in p0.children) {
                            if (!blocked && i.key.toString().startsWith(query) && i.key.toString().contains(query)) {
                                USERS.add(i.key.toString())
                                rec?.adapter?.notifyDataSetChanged()
                            }
                        }
                    }
                })
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }
        })
    }

    class ItemRecyclerViewAdapter(private val ITEMS: MutableList<String>):
        RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

      init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as String
                val rev = v.context?.let { Dialog(it) }
                val window: Window? = rev?.window
                val wlp = window?.attributes
                wlp?.gravity = Gravity.TOP
                rev?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                rev?.setCancelable(false)
                rev?.setCanceledOnTouchOutside(true)
                rev?.setContentView(R.layout.profile_dialog)
                (rev?.findViewById(R.id.profileName) as TextView).text = item

                val prfs = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                val username = prfs?.getString("username", "")
                //(rev?.findViewById(R.id.profile_play) as Button)
                val satView: CheckBox = rev.findViewById(R.id.checkBox) as CheckBox

                myRef.child("Users").child(username!!).child("Friends").addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        satView.isChecked = p0.hasChild(item)
                        rev?.show()
                        satView.setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked) {
                                myRef.child("Users").child(username!!).child("Friends").child(item).setValue(item)
                            } else {
                                myRef.child("Users").child(username!!).child("Friends").child(item).removeValue()
                            }
                        }
                    }
                })

                //v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_search_item, parent, false)
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
            val idView: TextView = view.textViewSearch
        }
    }

}