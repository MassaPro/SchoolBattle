package com.example.schoolbattle

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.os.PersistableBundle
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
import com.example.schoolbattle.engine.*
import kotlinx.android.synthetic.main.activity_navigator.*


import com.example.schoolbattle.shop.locale_context
import com.example.schoolbattle.shop.mRewardedVideoAd
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_navigator.*
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_game_menu.*
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.reward_dialog.*

var now: Context? = null

var Intent_for_offline_games : Intent? =  null
lateinit var mInterstitialAd_in_offline_games: InterstitialAd
class NavigatorActivity : AppCompatActivity() ,RewardedVideoAdListener{

    override fun onResume() {
        super.onResume()
        CONTEXT = this
        now = this
        if (Design == "Normal"){
            nav_view.setBackgroundColor(Color.WHITE);
        }
        if (Design == "Egypt"){
            nav_view.setBackgroundColor(rgb(255, 230, 163));
        }
        if (Design == "Casino"){
            nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
        }
        if (Design == "Rome"){
            nav_view.setBackgroundResource(R.drawable.bottom_navigation_rome)
        }
        if (Design == "Gothic"){
            //nav_view.setBackgroundResource(R.drawable.bottom_navigation_gothic)
            nav_view.setBackgroundColor(Color.BLACK)
        }
        if (Design == "Japan"){
            nav_view.setBackgroundColor(Color.WHITE)
        }
        if (Design == "Noir"){
            nav_view.setBackgroundColor(Color.BLACK)
        }
        nav_view.itemIconTintList = generateColorStateList()
        nav_view.itemTextColor = generateColorStateList()
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)

        mInterstitialAd_in_offline_games = InterstitialAd(this)
        mInterstitialAd_in_offline_games.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())




        mInterstitialAd_in_offline_games.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                //Код, который будет выполнен после завершения загрузки объявления.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                //Код, который будет выполняться при сбое рекламного запроса..
                startActivity(Intent_for_offline_games)
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
                //Код, который будет выполнен при показе объявления
            }

            override fun onAdClicked() {        //для норм пацанов функция
                // Code to be executed when the user clicks on an ad.
                //Код, который будет выполняться, когда пользователь нажимает на объявление.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                //Код, который будет выполнен, когда пользователь покинет приложение
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                //Код, который будет выполняться при закрытии интерстициального объявления.

                startActivity(Intent_for_offline_games)
                mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())
            }
        }



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

        myRef.child("Users/$username/rating").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            @SuppressLint("SetTextI18n")
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    RATING = p0.value.toString().toInt()
                    if (CONTEXT.toolbarName2 != null) {
                        Toast.makeText(CONTEXT, "DUB", Toast.LENGTH_LONG).show()
                        CONTEXT.toolbarName2.text = "$username ($RATING)"
                        CONTEXT.toolbarName2.setTextColor(colorByRating(RATING))
                    }
                    if (CONTEXT.profileMyName != null) {
                        CONTEXT.profileMyName.text = "$username ($RATING)"
                        CONTEXT.profileMyName.setTextColor(colorByRating(RATING))
                    }
                    if (CONTEXT.toolbarNameSettings != null) {
                        CONTEXT.toolbarNameSettings.text = "$username ($RATING)"
                        CONTEXT.toolbarNameSettings.setTextColor(colorByRating(RATING))
                    }
                }
            }
        })

        initCatchPlayersListenerForBlitzGame(username!!, this)
        initCatchPlayersListenerForLongGame(username, this)
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

                @SuppressLint("SetTextI18n")
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
        val editor =
            locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                .edit()
        editor.putString("money", MONEY.toString())
        editor.apply()
    }

    fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",          //TODO зменить на настоящий идентификатор
            AdRequest.Builder().build())
    }

}
