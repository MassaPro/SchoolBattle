package com.example.schoolbattle

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


var now: Context? = null

class NavigatorActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        CONTEXT = this
        now = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)
        CONTEXT = this
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        now = this
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
          //  R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")
        if (username != null) {
            updateRecycler(username)
        }
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
    }
}
