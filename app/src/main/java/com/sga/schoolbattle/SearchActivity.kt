package com.sga.schoolbattle

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.sga.schoolbattle.social.ProfileUserActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_item.*
import kotlinx.android.synthetic.main.activity_search_item.view.*


class SearchActivity : AppCompatActivity() {

    private var USERS: MutableList<String> = mutableListOf()
    private var IMAGES: MutableList<Int> = mutableListOf()
    var rec: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val searchView = findViewById<SearchView>(R.id.search_field)
        searchView.onActionViewExpanded()

        recyclerViewSearch.adapter = ItemRecyclerViewAdapter(USERS,IMAGES)
        rec = recyclerViewSearch
        rec?.adapter?.notifyDataSetChanged()


        var blocked = false


        when (Design) {
            "Normal" -> {

            }
            "Egypt" -> {
                recyclerViewSearch.setBackgroundResource(R.drawable.background_egypt)
                search_field.setBackgroundColor(rgb(255, 230, 163))
            }
            "Casino" -> {
                recyclerViewSearch.setBackgroundResource(R.drawable.background2_casino)
                search_field.setBackgroundResource(R.drawable.bottom_navigation_casino)
            }
            "Rome" -> {
                recyclerViewSearch.setBackgroundResource(R.drawable.background_rome)
                search_field.setBackgroundResource(R.drawable.bottom_navigation_rome)
            }
            "Gothic" -> {
                recyclerViewSearch.setBackgroundResource(R.drawable.background_gothic)
                search_field.setBackgroundResource(R.drawable.bottom_navigation_gothic)
            }
            "Japan" -> {
                recyclerViewSearch.setBackgroundResource(R.drawable.background_japan)
                search_field.setBackgroundColor(Color.WHITE)
            }
            "Noir" -> {
                recyclerViewSearch.setBackgroundResource(R.drawable.background_noir)
                search_field.setBackgroundColor(Color.BLACK)
            }
        }


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
                        IMAGES.clear()
                        for (i in p0.children) {
                            if (!blocked && i.key.toString().startsWith(query) && i.key.toString().contains(query)) {
                                USERS.add(i.key.toString())
                                if (i.hasChild("image")) IMAGES.add(i.child("image").value.toString().toInt())
                                else IMAGES.add(0)
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

    class ItemRecyclerViewAdapter(private val ITEMS: MutableList<String>,
                                  private val IMAGES: MutableList<Int>
    ):
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
            PICTURE_AVATAR[IMAGES[position]]?.let { holder.imView.setBackgroundResource(it) }
            when (Design) {
                "Normal" -> {
                    holder.idView.setTextColor(Color.BLACK)
                }
                "Egypt" -> {
                    holder.idView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                    holder.idView.setTextColor(Color.BLACK)
                }
                "Casino" -> {
                    holder.idView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                    holder.idView.setTextColor(Color.YELLOW)
                }
                "Rome" -> {
                    holder.idView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                    holder.idView.setTextColor(rgb(193,150,63))
                }
                "Gothic" -> {
                    holder.idView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    holder.idView.setTextColor(Color.WHITE)
                }
                "Japan" -> {
                    holder.idView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                    holder.idView.setTextColor(Color.BLACK)
                }
                "Noir" -> {
                    holder.idView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                    holder.idView.setTextColor(Color.WHITE)
                }
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
            val idView: TextView = view.textViewSearch
            val imView: ImageView = view.imageViewSearch
        }
    }

}
