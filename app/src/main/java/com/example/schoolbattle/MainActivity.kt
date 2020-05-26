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
