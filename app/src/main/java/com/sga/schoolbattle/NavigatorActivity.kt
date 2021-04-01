package com.sga.schoolbattle


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
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
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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
import com.sga.schoolbattle.engine.*
import com.sga.schoolbattle.shop.locale_context
import com.sga.schoolbattle.shop.mRewardedVideoAd
import kotlinx.android.synthetic.main.activity_game_menu.*
import kotlinx.android.synthetic.main.activity_navigator.*
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.activity_shop_fragment.*
import kotlinx.android.synthetic.main.activity_social.*
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





    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigator)
        locale_context = this
        if(!PREMIUM) {
            mInterstitialAd_in_offline_games = InterstitialAd(this)
            mInterstitialAd_in_offline_games.adUnitId = "ca-app-pub-8137188857901546/1455766259"
            mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())

            val prfs = this.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val username = prfs?.getString("username", "")

            Design = prfs?.getString("design", "Normal").toString()                 //дизайн
            SOUND = prfs?.getString("sound", "").toString() == "true"
            VIBRATION = prfs?.getString("vibration", "").toString() == "true"       //получаем из памяти звук
            AVATAR = prfs?.getString("avatar_number", 0.toString()).toString().toInt()
            MONEY = prfs?.getString("money", INITIAL_AMOUNT.toString()).toString().toInt()         //не забыть положить другую сумму если идет вход в аккаунт


            myRef.child("Users").child(username!!).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("money"))
                    {
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        MONEY = snapshot.child("money").value.toString().toInt()
                        editor.putString("money", MONEY.toString())
                        editor.apply()
                    }
                    if (snapshot.hasChild("array_of_emotions")){
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        ARRAY_OF_EMOTION = DECODE(snapshot.child("array_of_emotions").value.toString())
                        editor.putString("open_emotions", CODE(ARRAY_OF_EMOTION))
                        editor.apply()
                    }
                    if (snapshot.hasChild("array_of_avatars")) {
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        ARRAY_OF_AVATAR = DECODE(snapshot.child("array_of_avatars").value.toString())
                        editor.putString("open_avatars", CODE(ARRAY_OF_AVATAR))
                        editor.apply()
                    }
                    if (snapshot.hasChild("array_of_designs")){
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        ARRAY_OF_DESIGN = DECODE(snapshot.child("array_of_designs").value.toString())
                        editor.putString("open_design", CODE(ARRAY_OF_DESIGN))
                        editor.apply()
                    }
                    if (money != null) {
                        money.text = MONEY.toString()
                    }
                }
            })


            if(ARRAY_OF_DESIGN.size < DECODE(prfs?.getString("open_design", "0").toString()).size)
            {
                ARRAY_OF_DESIGN =  DECODE(prfs?.getString("open_design", 0.toString()).toString())
            }
            if(ARRAY_OF_AVATAR.size < DECODE(prfs?.getString("open_avatars", 0.toString()).toString()).size)
            {
                ARRAY_OF_AVATAR =  DECODE(prfs?.getString("open_avatars", 0.toString()).toString())
            }
            if(ARRAY_OF_EMOTION.size < DECODE(prfs?.getString("open_emotions", 0.toString()).toString()).size)
            {
                ARRAY_OF_EMOTION =  DECODE(prfs?.getString("open_emotions", 0.toString()).toString())
            }
            if(prfs?.getString("premium","0")=="1")
            {
                PREMIUM = true;
            }
            if(prfs?.getString("language","russian")=="english")
            {
                LANGUAGE = "English"
            }


            mInterstitialAd_in_offline_games.adListener = object : AdListener() {
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

        }
        Log.d("VISIT","121212121")
        CONTEXT = this



        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val prfs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if(prfs?.getString("language","russian")=="english")
        {
            LANGUAGE = "English"
        }
        if(LANGUAGE == "English")
        {
            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            navView.menu.getItem(0).title = "Menu"
            navView.menu.getItem(1).title = "Games"
            navView.menu.getItem(2).title = "Profile"
            navView.menu.getItem(3).title = "Shop"
            navView.menu.getItem(4).title = "Settings"
        }


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
                        if (RATING != -1)
                        {
                            CONTEXT.toolbarName2.text = "$username\n($RATING)"
                        }
                        else
                        {
                            CONTEXT.toolbarName2.text = "$username\n"
                        }

                        CONTEXT.toolbarName2.setTextColor(colorByRating(RATING))
                    }
                    if (CONTEXT.profileMyName != null) {
                        if (RATING != -1)
                        {
                            CONTEXT.profileMyName.text = "$username\n($RATING)"
                        }
                        else
                        {
                            CONTEXT.profileMyName.text = "$username\n"
                        }

                        CONTEXT.profileMyName.setTextColor(colorByRating(RATING))
                    }
                    if (CONTEXT.toolbarNameSettings != null) {
                        if (RATING != -1)
                        {
                            CONTEXT.toolbarNameSettings.text = "$username\n($RATING)"
                        }
                        else
                        {
                            CONTEXT.toolbarNameSettings.text = "$username\n"
                        }
                        CONTEXT.toolbarNameSettings.setTextColor(colorByRating(RATING))
                    }
                    if(CONTEXT.toolbarNameSettings != null)
                    {
                        if (RATING != -1)
                        {
                            CONTEXT.toolbarNameSettings.text = "$username\n($RATING)"
                        }
                        else
                        {
                            CONTEXT.toolbarNameSettings.text = "$username\n"
                        }
                        CONTEXT.toolbarNameSettings.setTextColor(colorByRating(RATING))
                    }
                    if(CONTEXT.button_shop_name != null)
                    {
                        if (RATING != -1)
                        {
                            CONTEXT.button_shop_name.text = "$username\n($RATING)"
                        }
                        else
                        {
                            CONTEXT.button_shop_name.text = "$username\n"
                        }
                        CONTEXT.button_shop_name.setTextColor(colorByRating(RATING))
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
        if(!PREMIUM)
        {
            loadRewardedVideoAd()
        }
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
        dialog_reward.price_reward.text = updateEconomyParams(this, "award").toString() //TODO ОБЯЗАТЕЛЬНО НЕ ЗАБЫТЬ ПОМЕНЯТЬ ЗДЕСЬ ЦЕНУ
        dialog_reward.close_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.ok_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
      //  Toast.makeText(this, "NIK", Toast.LENGTH_LONG).show()
        dialog_reward.show()
        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text = MONEY.toString()
    }

    fun loadRewardedVideoAd() {
        if(!PREMIUM)
        {
            mRewardedVideoAd.loadAd("ca-app-pub-8137188857901546/4769691002",          //TODO зменить на настоящий идентификатор
                AdRequest.Builder().build())
        }
    }


}
