package com.example.schoolbattle

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.schoolbattle.shop.loadRewardedVideoAd
import com.example.schoolbattle.shop.locale_context
import com.example.schoolbattle.shop.mRewardedVideoAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_navigator.*
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.reward_dialog.*


var now: Context? = null

class NavigatorActivity : AppCompatActivity() ,RewardedVideoAdListener{



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
        }
        if (Design == "Rome"){
            nav_view.setBackgroundResource(R.drawable.bottom_navigation_rome)
        }
        if (Design == "Gothic"){
            nav_view.setBackgroundResource(R.drawable.bottom_navigation_gothic)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()

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
        if (username != null) {
            updateRecycler(username)
            updateRecyclerBlitz(username)
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

    override fun onRewarded(reward: RewardItem) {

    }

    override fun onRewardedVideoAdLeftApplication() {

    }

    override fun onRewardedVideoAdClosed() {
        loadRewardedVideoAd()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {

    }

    override fun onRewardedVideoAdLoaded() {

    }

    override fun onRewardedVideoAdOpened() {

    }

    override fun onRewardedVideoStarted() {

    }

    override fun onRewardedVideoCompleted() {
        loadRewardedVideoAd()
        var dialog_reward : Dialog = locale_context!!.let { Dialog(it) }
        dialog_reward.setContentView(R.layout.reward_dialog)
        dialog_reward.price_reward.text = "10" //TODO ОБЯЗАТЕЛЬНО НЕ ЗАБЫТЬ ПОМЕНЯТЬ ЗДЕСЬ ЦЕНУ
        dialog_reward.close_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.ok_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.show()
        MONEY += 10
        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text = MONEY.toString()
    }


}
