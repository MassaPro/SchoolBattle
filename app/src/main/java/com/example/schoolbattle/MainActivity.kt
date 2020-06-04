package com.example.schoolbattle

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_game_list.*

public var Design: String = "Egypt"

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
        return inflater.inflate(R.layout.activity_game_list, container, false)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prfs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")


        searchButton.setOnClickListener {
            val dialog = Dialog(this.requireContext())


            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(R.layout.activity_search_game_dialog)
            val srch = dialog.findViewById(R.id.search) as SearchView
            srch.queryHint = "Поиск соперника"
            srch.setOnClickListener {
                srch.isIconified = false
            }
            srch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    dialog.findViewById<TextView>(R.id.res).text=""
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query == username) {
                        dialog.findViewById<TextView>(R.id.res).text="ТЫ ДУБ?"
                        dialog.findViewById<TextView>(R.id.res).setTextColor(Color.RED)
                        return false
                    }
                    myRef.child("Users").child(query).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.hasChildren()) {
                                dialog.findViewById<TextView>(R.id.res).text="ВЫЗОВ ОТПРАВЛЕН!"
                                dialog.findViewById<TextView>(R.id.res).setTextColor(Color.GREEN)
                                myRef.child("Users").child(query).child("Revanches").child(username!!).child("gameName").setValue("StupidGame")
                            } else {
                                dialog.findViewById<TextView>(R.id.res).text="НЕВЕРНОЕ ИМЯ"
                                dialog.findViewById<TextView>(R.id.res).setTextColor(Color.RED)
                            }
                        }
                    })
                    return false
                }

            })
            dialog.show()

        }
       // (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.my_toolbar))
        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar)

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        toolbarName.text = globalName

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
            //activity?.overridePendingTransition(0, 0)
            intent.putExtra("playType", 1)
            startActivity(intent)

        }
        oneDevice.setOnClickListener {
            val intent = Intent(activity, NewGameActivity::class.java)
            //activity?.overridePendingTransition(0, 0)
            intent.putExtra("playType", 2)
            startActivity(intent)
        }
        playWithComp.setOnClickListener {
            //activity?.overridePendingTransition(0, 0)
            val intent = Intent(activity, NewGameActivity::class.java)
            intent.putExtra("playType", 3)
            startActivity(intent)
        }
    }
}
