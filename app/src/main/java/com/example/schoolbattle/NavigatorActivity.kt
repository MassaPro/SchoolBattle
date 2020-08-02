package com.example.schoolbattle

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.schoolbattle.engine.initCatchPlayersListenerForBlitzGame
import com.example.schoolbattle.engine.updateRecycler
import com.example.schoolbattle.engine.updateRecyclerBlitz
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_navigator.*


var now: Context? = null

class NavigatorActivity : AppCompatActivity() {



    override fun onResume() {
        super.onResume()
        CONTEXT = this
        now = this
        if (Design == "Normal"){
            nav_view.setBackgroundColor(Color.WHITE);
        }
        if (Design == "Egypt"){
            nav_view.setBackgroundColor(rgb(224, 164, 103));
        }
        if (Design == "Casino"){
            nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
            //navigation_dashboard.setBackground
        }
        if (Design == "Rome"){
            nav_view.setBackgroundResource(R.drawable.bottom_navigation_rome)
            //navigation_dashboard.setBackground
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)



        Log.d("VISIT","121212121")
        CONTEXT = this



        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        now = this
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
          //  R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        var f : Fragment = SettingsFragmentActivity()
        val t = supportFragmentManager.beginTransaction()
        navView.setupWithNavController(navController)

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")
        initCatchPlayersListenerForBlitzGame(username!!, this)
        updateRecycler(username)
        updateRecyclerBlitz(username)
        myRef.child("Users").child(username.toString()).child("Revanches").addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.hasChildren()) {
                        for (i in p0.children) {
                            val rev = now?.let { Dialog(it) }
                            val window: Window? = rev?.window
                            val wlp = window?.attributes
                            wlp?.gravity = Gravity.TOP
                            rev?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            rev?.setCancelable(false)
                            rev?.setCanceledOnTouchOutside(true)
                            rev?.setContentView(R.layout.revanche_dialog)
                            rev?.show()
                            (rev?.findViewById(R.id.yesButton) as Button).setOnClickListener {

                                    val gameName = i.child("gameName").value.toString()
                                    myRef.child("Users").child(i.key.toString()).child("Games")
                                        .child(username.toString() + ' ' + gameName)
                                        .setValue("StupidGame")

                                    //add opponent to current user games list
                                    myRef.child("Users").child(username.toString()).child("Games")
                                        .child(i.key.toString() + ' ' + gameName).setValue("StupidGame")

                                    //add game to games
                                    myRef.child(gameName + "s").child(
                                        if (i.key.toString() < username.toString())
                                            i.key + '_' + username else username + '_' + i.key
                                    ).child("Move").setValue("0")
                                myRef.child("Users").child(username.toString()).child("Revanches").
                                child(i.key.toString()).removeValue()
                                rev.dismiss()
                            }
                            (rev.findViewById(R.id.noButton) as Button).setOnClickListener {
                                myRef.child("Users").child(username.toString()).child("Revanches").
                                child(i.key.toString()).removeValue()
                                rev.dismiss()
                            }
                            break
                        }
                    }
                }
            })

        myRef.child("Users").child(username.toString()).child("Friends").addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    FRIENDS.clear()
                    for (i in p0.children) {
                        FRIENDS.add(i.key.toString())
                    }
                    //friends_list.adapter?.notifyDataSetChanged()
                }
            })

        myRef.child("Users").child(username.toString()).child("FriendsRequests").addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.hasChildren()) {
                        for (i in p0.children) {
                            val rev = now?.let { Dialog(it) }
                            val window: Window? = rev?.window
                            val wlp = window?.attributes
                            wlp?.gravity = Gravity.TOP
                            rev?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            rev?.setCancelable(false)
                            rev?.setCanceledOnTouchOutside(true)
                            rev?.setContentView(R.layout.friend_request_dialog)
                            rev?.show()
                            (rev?.findViewById(R.id.yesButtonF) as Button).setOnClickListener {
                                myRef.child("Users").child(i.key.toString()).child("Friends")
                                    .child(username.toString()).setValue("1")
                                myRef.child("Users").child(username.toString()).child("Friends")
                                    .child(i.key.toString()).setValue("1")
                                myRef.child("Users").child(username.toString()).child("FriendsRequests").
                                child(i.key.toString()).removeValue()
                                rev.dismiss()
                            }
                            (rev.findViewById(R.id.noButtonF) as Button).setOnClickListener {
                                myRef.child("Users").child(username.toString()).child("FriendsRequests").
                                child(i.key.toString()).removeValue()
                                rev.dismiss()
                            }
                            break
                        }
                    }
                }
            })



    }


}
