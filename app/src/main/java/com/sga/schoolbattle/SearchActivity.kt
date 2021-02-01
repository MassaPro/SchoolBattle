package com.sga.schoolbattle

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.sga.schoolbattle.social.ProfileUserActivity
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
                val sorted = myRef.child("Users").orderByChild("name").startAt(query).limitToFirst(30)  //sorted - это имя запроса
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
                                //Photos.add(i.child("photo")).value
                                rec?.adapter?.notifyDataSetChanged()
                            }
                        }
                        rec?.adapter?.notifyDataSetChanged()
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
                val intent = Intent(v.context, ProfileUserActivity::class.java)
                intent.putExtra("name", v.tag as String)
                v.context.startActivity(intent)
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
