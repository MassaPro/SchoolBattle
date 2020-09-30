package com.example.schoolbattle


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
import com.example.schoolbattle.shop.locale_context
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_game_menu.*
import kotlinx.android.synthetic.main.activity_navigator.*


lateinit var gamesRecycler: RecyclerView

class MainActivity : Fragment() {

    /*override fun onBackPressed() {
        super.onBackPressed()
        //overridePendingTransition(0, 0)
        finishAffinity(ContextUtils.getActivity(this))
    }*/

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
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



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prfs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")
        CONTEXT = requireActivity()


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


        AVATAR = prfs?.getString("avatar_number", 0.toString()).toString().toInt()
        MONEY = prfs?.getString("money", INITIAL_AMOUNT.toString()).toString().toInt()         //не забыть положить другую сумму если идет вход в аккаунт
        money_icon.setBackgroundResource(R.drawable.money)
        money.text = MONEY.toString()
        Design = prfs?.getString("design", "Normal").toString()                 //дизайн
        SOUND = prfs?.getString("sound", "").toString() == "true"
        VIBRATION = prfs?.getString("vibration", "").toString() == "true"       //получаем из памяти звук

        if(Design == "Normal")
        {
            blitz.width = 320
            newGameButton.width = 320

            money.setTextColor(Color.BLACK)
        }
        else if (Design == "Egypt"){
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
        }
        else if (Design == "Casino"){
            blitz.width = 320
            newGameButton.width = 320
            game_menu.setBackgroundResource(R.drawable.game_menu_casino)
            //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))

            toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            toolbarName2.setTextColor(Color.YELLOW)
            toolbarName2.setTextSize(25f)

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
            oneDevice.setTextSize(20f)
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
        else if (Design == "Rome"){
            game_menu.setBackgroundResource(R.drawable.game_menu_rome)
            //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))

            toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            toolbarName2.setTextColor(rgb(193,150,63))
            toolbarName2.setTextSize(25f)

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
            oneDevice.setTextSize(20f)
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
        else if (Design == "Gothic"){
            game_menu.setBackgroundResource(R.drawable.game_menu_gothic)
            //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))

            toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            toolbarName2.setTextColor(Color.WHITE)
            toolbarName2.setTextSize(25f)

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
        else if (Design == "Japan"){
            game_menu.setBackgroundResource(R.drawable.game_menu_japan)
            //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))

            toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            toolbarName2.setTextColor(Color.BLACK)
            toolbarName2.setTextSize(25f)

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
            oneDevice.setTextSize(16f)
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
        else if (Design == "Noir"){
            game_menu.setBackgroundResource(R.drawable.game_menu_noir)
            //nav_view.setBackgroundResource(R.drawable.bottom_navigation_casino)
            my_toolbar2.setBackgroundColor(argb(0,0,0,0))

            toolbarName2.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            toolbarName2.setTextColor(Color.WHITE)
            toolbarName2.setTextSize(25f)

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

       // (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.my_toolbar))
        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar2)

        searchButton.setOnClickListener {
            val intent = Intent(this.activity, SearchActivity::class.java)
            activity?.startActivity(intent)
            activity?.overridePendingTransition(0, 0)
            /*val dialog = Dialog(this.requireContext())


            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(true)
            dialog.setContentView(R.layout.activity_search_game_dialog)
            val srch = dialog.findViewById(R.id.search_field) as SearchView
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
            dialog.show()*/

        }
       // (activity as AppCompatActivity).setSupportActionBar(findViewById(R.id.my_toolbar))
        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar2)

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "")
        toolbarName2.text = globalName

     //   money.setOnClickListener {
       //     val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
        //    editor?.putString("username", "")
      //      editor?.apply()
      //      recyclerSet.clear()
   //         myRef.child("Users").child(globalName.toString()).child("Games").removeEventListener(
     //           listener)
   //         val intent = Intent(activity, NullActivity::class.java)
  //          startActivity(intent)
   //         activity?.finish()
  //      }
        newGameButton.setOnClickListener {
            val intent = Intent(activity, NewGameActivity::class.java)
            //activity?.overridePendingTransition(0, 0)
            intent.putExtra("playType", 1)
            startActivity(intent)

        }
        blitz.setOnClickListener {
            val intent = Intent(activity, NewGameActivity::class.java)
            //activity?.overridePendingTransition(0, 0)
            intent.putExtra("playType", 0)
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

