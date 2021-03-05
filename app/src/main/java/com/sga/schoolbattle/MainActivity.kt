package com.sga.schoolbattle


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sga.schoolbattle.engine.colorByRating
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_game_menu.*


lateinit var gamesRecycler: RecyclerView

class MainActivity : Fragment() {

    /*override fun onBackPressed() {
        super.onBackPressed()
        //overridePendingTransition(0, 0)
        finishAffinity(ContextUtils.getActivity(this))
    }*/

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs?.getString("username", "")
        if (RATING != -1) {
            toolbarName2.text = "$username\n($RATING)"
           toolbarName2.setTextColor(colorByRating(RATING))
        }
        else
        {
            toolbarName2.text = "$username"
        }
        //val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //navView.selectedItemId = R.id.navigation_home
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_game_menu, container, false)
    }



    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        mSound1.load(activity, R.raw.money, 1);

        PICTURE_AVATAR[AVATAR]?.let { my_ava.setBackgroundResource(it) }

        val prfs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")


        blitz.text = translate("блиц онлайн")
        newGameButton.text = translate("долгая онлайн")
        oneDevice.text = translate("Игра на одном устройстве")
        playWithComp.text = translate("игра с компьютером")
        searchButton.text = translate("Поиск Соперника")


        money.text = MONEY.toString()
        myRef.child("Users").child(username!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("money")) MONEY = snapshot.child("money").value.toString().toInt()
                if (snapshot.hasChild("array_of_emotions")) ARRAY_OF_EMOTION = DECODE(snapshot.child("array_of_emotions").value.toString())
                if (snapshot.hasChild("array_of_avatars")) ARRAY_OF_AVATAR = DECODE(snapshot.child("array_of_avatars").value.toString())
                if (snapshot.hasChild("array_of_designs")) ARRAY_OF_DESIGN = DECODE(snapshot.child("array_of_designs").value.toString())
                if (money != null) {
                    money.text = MONEY.toString()
                }
            }
        })
        money_icon.setBackgroundResource(R.drawable.money)



        when (Design) {
            "Normal" -> {


                game_menu.setBackgroundResource(R.drawable.game_menu_normal)

                searchButton.setBackgroundResource(R.drawable.button)
                newGameButton.setBackgroundResource(R.drawable.button)
                oneDevice.setBackgroundResource(R.drawable.button)
                playWithComp.setBackgroundResource(R.drawable.button)
                blitz.setBackgroundResource(R.drawable.button)


                money.setTextColor(Color.BLACK)
            }
            "Egypt" -> {
                game_menu.setBackgroundResource(R.drawable.game_menu_egypt)
                //nav_view.setBackgroundColor(rgb(224, 164, 103));
                my_toolbar2.setBackgroundColor(rgb(255, 230, 163))
                searchButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                searchButton.setBackgroundColor(argb(0,0,0,0))
                newGameButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                newGameButton.setBackgroundColor(argb(0,0,0,0))
                oneDevice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                oneDevice.setBackgroundColor(argb(0,0,0,0))
                playWithComp.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                playWithComp.setBackgroundColor(argb(0,0,0,0))
                blitz.setBackgroundColor(argb(0,0,0,0))
                blitz.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                money.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                money.setTextColor(Color.BLACK)

                //id_shop_dialog.setBackgroundResource(R.drawable.game_menu_egypt)
                    //description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            }
            "Casino" -> {

                oneDevice.width = 330
                game_menu.setBackgroundResource(R.drawable.game_menu_casino)
                //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))

                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                toolbarName2.setTextColor(Color.YELLOW)


                newGameButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                newGameButton.setTextColor(Color.YELLOW)
                newGameButton.setTextSize(20f)
                newGameButton.setBackgroundColor(argb(0,0,0,0))

                blitz.setBackgroundColor(argb(0,0,0,0))
                blitz.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                blitz.setTextColor(Color.YELLOW)
                blitz.setTextSize(20f)

                oneDevice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                oneDevice.setTextColor(Color.YELLOW)
                oneDevice.setTextSize(19f)
                oneDevice.setBackgroundColor(argb(0,0,0,0))

                playWithComp.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                playWithComp.setTextColor(Color.YELLOW)
                playWithComp.setTextSize(20f)
                playWithComp.setBackgroundColor(argb(0,0,0,0))

                searchButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                searchButton.setTextColor(Color.YELLOW)
                searchButton.setTextSize(20f)
                searchButton.setBackgroundColor(argb(0,0,0,0))

                money.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                money.setTextColor(Color.YELLOW)
            }
            "Rome" -> {
                oneDevice.width = 330
                game_menu.setBackgroundResource(R.drawable.game_menu_rome)
                //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))

                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                toolbarName2.setTextColor(rgb(193,150,63))

                newGameButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                newGameButton.setTextColor(rgb(193,150,63))
                newGameButton.setTextSize(19f)
                newGameButton.setBackgroundColor(argb(0,0,0,0))

                blitz.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                blitz.setTextColor(rgb(193,150,63))
                blitz.setTextSize(20f)
                blitz.setBackgroundColor(argb(0,0,0,0))

                oneDevice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                oneDevice.setTextColor(rgb(193,150,63))
                oneDevice.setTextSize(19f)
                oneDevice.setBackgroundColor(argb(0,0,0,0))

                playWithComp.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                playWithComp.setTextColor(rgb(193,150,63))
                playWithComp.setTextSize(20f)
                playWithComp.setBackgroundColor(argb(0,0,0,0))

                searchButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                searchButton.setTextColor(rgb(193,150,63))
                searchButton.setTextSize(20f)
                searchButton.setBackgroundColor(argb(0,0,0,0))

                money.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                money.setTextColor(rgb(193,150,63))
            }
            "Gothic" -> {
                game_menu.setBackgroundResource(R.drawable.game_menu_gothic)
                //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))

                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                toolbarName2.setTextColor(Color.WHITE)


                newGameButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                newGameButton.setTextColor(Color.WHITE)
                newGameButton.setTextSize(19f)
                newGameButton.setBackgroundColor(argb(0,0,0,0))

                blitz.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                blitz.setTextColor(Color.WHITE)
                blitz.setTextSize(20f)
                blitz.setBackgroundColor(argb(0,0,0,0))

                oneDevice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                oneDevice.setTextColor(Color.WHITE)
                oneDevice.setTextSize(20f)
                oneDevice.setBackgroundColor(argb(0,0,0,0))

                playWithComp.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                playWithComp.setTextColor(Color.WHITE)
                playWithComp.setTextSize(20f)
                playWithComp.setBackgroundColor(argb(0,0,0,0))

                searchButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                searchButton.setTextColor(Color.WHITE)
                searchButton.setTextSize(20f)
                searchButton.setBackgroundColor(argb(0,0,0,0))

                money.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                money.setTextColor(Color.WHITE)
            }
            "Japan" -> {
                oneDevice.width = 330
                game_menu.setBackgroundResource(R.drawable.game_menu_japan)
                //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))

               toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                toolbarName2.setTextColor(Color.BLACK)


                newGameButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                newGameButton.setTextColor(Color.BLACK)
                newGameButton.setTextSize(16f)
                newGameButton.setBackgroundColor(argb(0,0,0,0))

                blitz.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                blitz.setTextColor(Color.BLACK)
                blitz.setTextSize(16f)
                blitz.setBackgroundColor(argb(0,0,0,0))

                oneDevice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                oneDevice.setTextColor(Color.BLACK)
                oneDevice.setTextSize(15f)
                oneDevice.setBackgroundColor(argb(0,0,0,0))

                playWithComp.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                playWithComp.setTextColor(Color.BLACK)
                playWithComp.setTextSize(17f)
                playWithComp.setBackgroundColor(argb(0,0,0,0))

                searchButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                searchButton.setTextColor(Color.BLACK)
                searchButton.setTextSize(17f)
                searchButton.setBackgroundColor(argb(0,0,0,0))

                money.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                money.setTextColor(Color.BLACK)

            }
            "Noir" -> {
                game_menu.setBackgroundResource(R.drawable.game_menu_noir)
                //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
                my_toolbar2.setBackgroundColor(argb(0,0,0,0))

                toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                toolbarName2.setTextColor(Color.WHITE)


                newGameButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                newGameButton.setTextColor(Color.WHITE)
                newGameButton.setTextSize(16f)
                newGameButton.setBackgroundColor(argb(0,0,0,0))

                blitz.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                blitz.setTextColor(Color.WHITE)
                blitz.setTextSize(16f)
                blitz.setBackgroundColor(argb(0,0,0,0))

                oneDevice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                oneDevice.setTextColor(Color.WHITE)
                oneDevice.setTextSize(16f)
                oneDevice.setBackgroundColor(argb(0,0,0,0))

                playWithComp.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                playWithComp.setTextColor(Color.WHITE)
                playWithComp.setTextSize(17f)
                playWithComp.setBackgroundColor(argb(0,0,0,0))

                searchButton.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                searchButton.setTextColor(Color.WHITE)
                searchButton.setTextSize(17f)
                searchButton.setBackgroundColor(argb(0,0,0,0))

               money.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                money.setTextColor(Color.WHITE)
            }
        }


        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar2)

        searchButton.setOnClickListener {
            val intent = Intent(this.activity, SearchActivity::class.java)
            activity?.startActivity(intent)
            activity?.overridePendingTransition(0, 0)


        }

        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar2)



        newGameButton.setOnClickListener {
            val intent = Intent(activity, NewGameActivity::class.java)
            //activity?.overridePendingTransition(0, 0)
            intent.putExtra("playType", 1)
            startActivity(intent)

        }
        blitz.setOnClickListener {
            myRef.child("Users/$username/rating").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        RATING = p0.value.toString().toInt()
                    } else {
                        RATING = 1000
                    }
                    val intent = Intent(activity, NewGameActivity::class.java)
                    //activity?.overridePendingTransition(0, 0)
                    intent.putExtra("playType", 0)
                    startActivity(intent)
                }
            })
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

