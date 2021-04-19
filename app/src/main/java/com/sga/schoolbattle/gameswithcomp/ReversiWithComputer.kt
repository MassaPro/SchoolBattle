package com.sga.schoolbattle.gameswithcomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.AdRequest
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*
import kotlinx.android.synthetic.main.activity_one_device_games_template.*


var ReversiMode = 0

class ReversiWithComputer : AppCompatActivity() {
    fun encode(h: MutableList<Triple<Int,Int,Int>>):String
    {
        var answer: String = ""
        for(i in 0 until h.size)
        {
            answer = answer + h[i].first.toString() + 'a' + h[i].second.toString() + 'a' + h[i].third.toString() + 'a'
        }
        return answer
    }
    fun string_to_int(s: String): Int
    {
        var i : Int = 0
        var k: Int = 1
        var answer: Int = 0
        while(i<s.length)
        {
            answer += (s[s.length-i-1].toInt() - '0'.toInt())*k
            k= k*10
            i++
        }
        return answer
    }
    fun decode(s : String) : MutableList<Triple<Int,Int,Int>>
    {
        var answer: MutableList<Triple<Int,Int,Int>> = mutableListOf()
        var i : Int = 0
        var a: Int = 0
        var b: Int = 0
        var c: Int = 0
        var s1: String = ""
        while(i<s.length)
        {
            s1 = ""
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            a = string_to_int(s1)
            s1 = ""
            i++
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            b = string_to_int(s1)
            s1 = ""
            i++
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            c = string_to_int(s1)
            answer.add(Triple(a,b,c))
            i++
        }
        return answer
    }

    private var dialog_parametrs: Show_parametr_with_computer? = null
    private var dialog_rules: Show_rules? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_games_template)
        signature_canvas_reversi_with_computer.visibility = (View.VISIBLE)

        signature_canvas_reversi_with_computer.activity = this
        CONTEXT = this

        if(!PREMIUM)
        {
            mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())
        }

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        val prefs2 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        ReversiMode = prefs2.getInt("ReversiMode", 0)
        if (ReversiMode == 0) {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putInt("ReversiMode", 1)
            editor.apply()
            ReversiMode = 1
        }
        signature_canvas_reversi_with_computer.blockedOnTouch = false

        val prefs_first = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_reversi_with_computer.History = decode(prefs_first.getString("reversi_with_computer", "").toString())
        if (ReversiMode == 2 && signature_canvas_reversi_with_computer.History.size == 0) {
            signature_canvas_reversi_with_computer.blockedOnTouch = true         // TODO check
        }

        signature_canvas_reversi_with_computer.t1 = findViewById(R.id.name_player1_with_computer_template) as TextView
        signature_canvas_reversi_with_computer.t2 = findViewById(R.id.name_player2_with_computer_template) as TextView

        when (Design) {
            "Normal" -> {
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_normal)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_normal)
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_normal)
            }
            "Egypt" -> {
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.player1_egypt)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.player2_egypt)
                player_1_icon_template_with_computer.setBackgroundResource(R.drawable.chip1_egypt)
                player_2_icon_template_with_computer.setBackgroundResource(R.drawable.chip2_egypt)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_egypt)
                bottom_navigation_with_computer_template.setBackgroundColor(Color.rgb(255, 230, 163))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_normal)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Casino" -> {
                name_player1_with_computer_template.setTextColor(Color.RED)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_casino)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_casino)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background2_casino)
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_casino)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Rome" -> {
                name_player1_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_rome)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_rome)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_rome)
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_rome)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Gothic" -> {
                name_player1_with_computer_template.setTextColor(Color.WHITE)
                name_player2_with_computer_template.setTextColor(Color.WHITE)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_gothic)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_gothic)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_gothic)
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_gothic)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Japan" -> {
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_japan)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_japan)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_japan)
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_normal)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Noir" -> {
                name_player1_with_computer_template.setTextColor(Color.WHITE)
                name_player2_with_computer_template.setTextColor(Color.WHITE)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_noir)
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_noir)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_noir)
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_gothic)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
        }



        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("reversi_with_computer", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_reversi_with_computer.History = decode(prefs.getString("reversi_with_computer", "").toString())

        for (i in 0 until signature_canvas_reversi_with_computer.FIELD.size) {
            for (j in 0 until signature_canvas_reversi_with_computer.FIELD[0].size) {
                signature_canvas_reversi_with_computer.FIELD[i][j] = 0
                signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
            }
        }
        signature_canvas_reversi_with_computer.FIELD[3][3] = 2
        signature_canvas_reversi_with_computer.FIELD[4][4] = 2
        signature_canvas_reversi_with_computer.FIELD[4][3] = 1
        signature_canvas_reversi_with_computer.FIELD[3][4] = 1
        signature_canvas_reversi_with_computer.Array_of_illumination[2][3] = 1
        signature_canvas_reversi_with_computer.Array_of_illumination[3][2] = 1
        signature_canvas_reversi_with_computer.Array_of_illumination[5][4] = 1
        signature_canvas_reversi_with_computer.Array_of_illumination[4][5] = 1

        signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
        for (t in signature_canvas_reversi_with_computer.History) {
            signature_canvas_reversi_with_computer.FIELD[t.first][t.second] = t.third
            if (t.third == 1) {
                signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
            } else {
                signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
            }
            signature_canvas_reversi_with_computer.change_array(t.first, t.second)
            for (i in 0 until signature_canvas_reversi_with_computer.FIELD.size) {
                for (j in 0 until signature_canvas_reversi_with_computer.FIELD[0].size) {
                    signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
                }
            }
            if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                signature_canvas_reversi_with_computer.illumination(1)
            } else {
                signature_canvas_reversi_with_computer.illumination(2)
            }
            var flag: Boolean = true
            for (i in 0 until signature_canvas_reversi_with_computer.Array_of_illumination.size) {
                for (j in 0 until signature_canvas_reversi_with_computer.Array_of_illumination[0].size) {
                    if (signature_canvas_reversi_with_computer.Array_of_illumination[i][j] != 0) {
                        flag = false
                    }
                }
            }
            if (flag)                                    //если игрок не может походить то ход переходит другому
            {
                if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
                    signature_canvas_reversi_with_computer.illumination(2)
                } else {
                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                    signature_canvas_reversi_with_computer.illumination(1)
                }
            }
        }
        signature_canvas_reversi_with_computer.invalidate()
        signature_canvas_reversi_with_computer.blocked = false

        if (ReversiMode == 2 && signature_canvas_reversi_with_computer.History.size == 0) {     // first computer move
            signature_canvas_reversi_with_computer.blockedOnTouch == true

            var fla = false
            val list_x: MutableList<Int> = mutableListOf(0, 7, 0, 7)
            val list_y: MutableList<Int> = mutableListOf(0, 0, 7, 7)

            val handler = android.os.Handler()
            handler.postDelayed({
                for (i2 in 0 until 3) {
                    if (signature_canvas_reversi_with_computer.Array_of_illumination[list_x[i2]][list_y[i2]] != 0) {
                        if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                            signature_canvas_reversi_with_computer.FIELD[list_x[i2]][list_y[i2]] = 1
                            signature_canvas_reversi_with_computer.History.add(Triple(list_x[i2], list_y[i2], 1))
                            val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                            val editor = getSharedPreferences(
                                "UserData",
                                Context.MODE_PRIVATE
                            ).edit()
                            editor.putString("reversi_with_computer", data_from_memory)
                            editor.apply()
                            signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
                        } else {
                            signature_canvas_reversi_with_computer.FIELD[list_x[i2]][list_y[i2]] = 2
                            signature_canvas_reversi_with_computer.History.add(Triple(list_x[i2], list_y[i2], 2))
                            val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                            val editor = getSharedPreferences(
                                "UserData",
                                Context.MODE_PRIVATE
                            ).edit()
                            editor.putString("reversi_with_computer", data_from_memory)
                            editor.apply()
                            signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                        }


                        ////////////
                        fla = true

                        for (i in 0..7) {
                            for (j in 0..7) {
                                signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
                            }
                        }
                        signature_canvas_reversi_with_computer.change_array(list_x[i2], list_y[i2])

                        if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                            signature_canvas_reversi_with_computer.illumination(1)
                        } else {
                            signature_canvas_reversi_with_computer.illumination(2)
                        }

                        break
                    }
                }


                if (fla == false) {
                    for (i2 in 2 until 5) {
                        for (j2 in 2 until 5) {
                            if (signature_canvas_reversi_with_computer.Array_of_illumination[i2][j2] != 0) {
                                if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                                    signature_canvas_reversi_with_computer.FIELD[i2][j2] = 1
                                    signature_canvas_reversi_with_computer.History.add(Triple(i2, j2, 1))
                                    val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                                    val editor = getSharedPreferences(
                                        "UserData",
                                        Context.MODE_PRIVATE
                                    ).edit()
                                    editor.putString("reversi_with_computer", data_from_memory)
                                    editor.apply()
                                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
                                } else {
                                    signature_canvas_reversi_with_computer.FIELD[i2][j2] = 2
                                    signature_canvas_reversi_with_computer.History.add(Triple(i2, j2, 2))
                                    val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                                    val editor = getSharedPreferences(
                                        "UserData",
                                        Context.MODE_PRIVATE
                                    ).edit()
                                    editor.putString("reversi_with_computer", data_from_memory)
                                    editor.apply()
                                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                                }


                                ////////////
                                fla = true

                                for (i in 0..7) {
                                    for (j in 0..7) {
                                        signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
                                    }
                                }
                                signature_canvas_reversi_with_computer.change_array(i2, j2)

                                if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                                    signature_canvas_reversi_with_computer.illumination(1)
                                } else {
                                    signature_canvas_reversi_with_computer.illumination(2)
                                }

                                /*for (i in 0 until Array_of_illumination.size) {
                                    for (j in 0 until Array_of_illumination[0].size) {
                                        if (Array_of_illumination[i][j] == 1) {
                                            flag = false
                                        }
                                    }
                                }*/
                                break
                            }
                        }
                        if (fla == true) {
                            break
                        }
                    }
                }

                if (fla == false) {
                    for (i2 in 0 until signature_canvas_reversi_with_computer.Array_of_illumination.size) {
                        for (j2 in 0 until signature_canvas_reversi_with_computer.Array_of_illumination[0].size) {
                            if (signature_canvas_reversi_with_computer.Array_of_illumination[i2][j2] != 0) {
                                if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                                    signature_canvas_reversi_with_computer.FIELD[i2][j2] = 1
                                    signature_canvas_reversi_with_computer.History.add(Triple(i2, j2, 1))
                                    val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                                    val editor = getSharedPreferences(
                                        "UserData",
                                        Context.MODE_PRIVATE
                                    ).edit()
                                    editor.putString("reversi_with_computer", data_from_memory)
                                    editor.apply()
                                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
                                } else {
                                    signature_canvas_reversi_with_computer.FIELD[i2][j2] = 2
                                    signature_canvas_reversi_with_computer.History.add(Triple(i2, j2, 2))
                                    val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                                    val editor = getSharedPreferences(
                                        "UserData",
                                        Context.MODE_PRIVATE
                                    ).edit()
                                    editor.putString("reversi_with_computer", data_from_memory)
                                    editor.apply()
                                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                                }


                                ////////////
                                fla = true

                                for (i in 0..7) {
                                    for (j in 0..7) {
                                        signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
                                    }
                                }
                                signature_canvas_reversi_with_computer.change_array(i2, j2)

                                if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                                    signature_canvas_reversi_with_computer.illumination(1)
                                } else {
                                    signature_canvas_reversi_with_computer.illumination(2)
                                }

                                /*for (i in 0 until Array_of_illumination.size) {
                                    for (j in 0 until Array_of_illumination[0].size) {
                                        if (Array_of_illumination[i][j] == 1) {
                                            flag = false
                                        }
                                    }
                                }*/
                                break
                            }
                        }
                        if (fla == true) {
                            break
                        }
                    }
                }
                signature_canvas_reversi_with_computer.blockedOnTouch = false
                signature_canvas_reversi_with_computer.invalidate()

            }, delayTime)

        }



        to_back_with_computer_template.setOnClickListener {
            to_back_one_divice.setOnClickListener {
                this.finish()
                val intent = Intent(this, NewGameActivity::class.java)
                intent.putExtra("playType", 3)
                if(mInterstitialAd_in_offline_games.isLoaded && !PREMIUM)
                {
                    Intent_for_offline_games = intent
                    mInterstitialAd_in_offline_games.show()
                }
                else
                {
                    this.startActivity(intent)
                }
            }
        }
        bottom_navigation_with_computer_template.itemIconTintList = generateColorStateList()
        bottom_navigation_with_computer_template.itemTextColor = generateColorStateList()
        if(LANGUAGE == "English")
        {
            bottom_navigation_with_computer_template.menu.getItem(0).title = "Rules"
            bottom_navigation_with_computer_template.menu.getItem(1).title = "Settings"
            bottom_navigation_with_computer_template.menu.getItem(2).title = "Return"
            bottom_navigation_with_computer_template.menu.getItem(3).title = "Back"
        }
        bottom_navigation_with_computer_template.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@ReversiWithComputer
                        )
                    dialog_rules?.show("ReversiGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs = Show_parametr_with_computer(this@ReversiWithComputer)
                    dialog_parametrs?.showResult_with_computer(this, "Reversi")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, ReversiWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{

                    if (signature_canvas_reversi_with_computer.History.size > 1)            //TODO дописать когда самый первый ход убираем
                    {
                        signature_canvas_reversi_with_computer.History.removeLast()
                        signature_canvas_reversi_with_computer.History.removeLast()
                        val data_from_memory = encode(signature_canvas_reversi_with_computer.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("reversi_with_computer", data_from_memory)
                        editor.apply()
                        for (i in 0 until signature_canvas_reversi_with_computer.FIELD.size) {
                            for (j in 0 until signature_canvas_reversi_with_computer.FIELD[0].size) {
                                signature_canvas_reversi_with_computer.FIELD[i][j] = 0
                                signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
                            }
                        }
                        signature_canvas_reversi_with_computer.FIELD[3][3] = 2
                        signature_canvas_reversi_with_computer.FIELD[4][4] = 2
                        signature_canvas_reversi_with_computer.FIELD[4][3] = 1
                        signature_canvas_reversi_with_computer.FIELD[3][4] = 1
                        signature_canvas_reversi_with_computer.Array_of_illumination[2][3] = 1
                        signature_canvas_reversi_with_computer.Array_of_illumination[3][2] = 1
                        signature_canvas_reversi_with_computer.Array_of_illumination[5][4] = 1
                        signature_canvas_reversi_with_computer.Array_of_illumination[4][5] = 1

                        signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                        for(t in signature_canvas_reversi_with_computer.History) {
                            signature_canvas_reversi_with_computer.FIELD[t.first][t.second] = t.third
                            if (t.third == 1) {
                                signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
                            } else {
                                signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                            }
                            signature_canvas_reversi_with_computer.change_array(t.first,t.second)
                            for (i in 0 until signature_canvas_reversi_with_computer.FIELD.size) {
                                for (j in 0 until signature_canvas_reversi_with_computer.FIELD[0].size) {
                                    signature_canvas_reversi_with_computer.Array_of_illumination[i][j] = 0
                                }
                            }
                            if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                                signature_canvas_reversi_with_computer.illumination(1)
                            } else {
                                signature_canvas_reversi_with_computer.illumination(2)
                            }
                            var flag: Boolean = true
                            for (i in 0 until signature_canvas_reversi_with_computer.Array_of_illumination.size) {
                                for (j in 0 until signature_canvas_reversi_with_computer.Array_of_illumination[0].size) {
                                    if (signature_canvas_reversi_with_computer.Array_of_illumination[i][j] != 0) {
                                        flag = false
                                    }
                                }
                            }
                            if (flag)                                    //если игрок не может походить то ход переходит другому
                            {
                                if (signature_canvas_reversi_with_computer.Black_or_grey_chip == "black") {
                                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "grey"
                                    signature_canvas_reversi_with_computer.illumination(2)
                                } else {
                                    signature_canvas_reversi_with_computer.Black_or_grey_chip = "black"
                                    signature_canvas_reversi_with_computer.illumination(1)
                                }
                            }


                        }

                        signature_canvas_reversi_with_computer.invalidate()
                    }
                }

            }
            true
        }

    }
    override fun onBackPressed()
    {
        super.onBackPressed()
        var intent = Intent(this, NewGameActivity::class.java)
        intent.putExtra("playType", 3)
        if(mInterstitialAd_in_offline_games.isLoaded && !PREMIUM)
        {
            Intent_for_offline_games = intent
            mInterstitialAd_in_offline_games.show()
        }
        else
        {
            this.startActivity(intent)
            this.finish()
        }
    }

}

class CanvasView_reversi_with_computer(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    fun encode(h: MutableList<Triple<Int,Int,Int>>):String
    {
        var answer: String = ""
        for(i in 0 until h.size)
        {
            answer = answer + h[i].first.toString() + 'a' + h[i].second.toString() + 'a' + h[i].third.toString() + 'a'
        }
        return answer
    }
    fun string_to_int(s: String): Int
    {
        var i : Int = 0
        var k: Int = 1
        var answer: Int = 0
        while(i<s.length)
        {
            answer += (s[s.length-i-1].toInt() - '0'.toInt())*k
            k= k*10
            i++
        }
        return answer
    }
    fun decode(s : String) : MutableList<Triple<Int,Int,Int>>
    {
        var answer: MutableList<Triple<Int,Int,Int>> = mutableListOf()
        var i : Int = 0
        var a: Int = 0
        var b: Int = 0
        var c: Int = 0
        var s1: String = ""
        while(i<s.length)
        {
            s1 = ""
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            a = string_to_int(s1)
            s1 = ""
            i++
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            b = string_to_int(s1)
            s1 = ""
            i++
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            c = string_to_int(s1)
            answer.add(Triple(a,b,c))
            i++
        }
        return answer
    }

    fun touch_refinement_X (indent: Float,x : Float,width1: Float,size_field_x1:Int ):Float        //уточняет касания по оси x
    {
        if(x<indent)
        {
            return -1f
        }
        if(x>width1-indent)
        {
            return -1f
        }
        var a : Float = indent
        while(x>a)
        {
            a+=step
        }
        return a - step
    }

    fun touch_refinement_Y (indent: Float,y : Float,height1: Float,size_field_y1:Int,step: Float,advertising_line_1:Float):Float      //уточняет касания по оси Y
    {
        if(y > height1 - advertising_line_1 ||  y < height1 - advertising_line_1 - step*size_field_y1)
        {
            return -1f
        }
        var a: Float = height1 - advertising_line_1 - step*size_field_y1
        while(y>a)
        {
            a+=step
        }
        return a - step
    }

    fun touch_refinement_for_Array_X (indent: Float,x : Float,step:Float):Int        //уточняет координаты в массиве  при касании
    {
        if(x<0)
        {
            return -1
        }
        return ((x-indent)/step).toInt()
    }

    fun touch_refinement_for_Array_Y (indent: Float,y : Float,height1: Float,size_field_y1: Int,step: Float,advertising_line_1:Float):Int      //уточняет координаты в массиве  при касании
    {
        if(y<0)
        {
            return -1
        }
        var a : Float = height1 - advertising_line_1 - step*size_field_y1
        var b : Int = 0
        while(y>a)
        {
            a+=step
            b+=1
        }
        return b-1
    }

    private fun translate_from_Array_to_Graphics_X(indent: Float,x:Int, step: Float):Float    //переводит массивные координаты в графически
    {
        return x*step+indent
    }

    fun translate_from_Array_to_Graphics_Y(indent: Float,y:Int,height1: Float,size_field_y1: Int,step: Float,advertising_line_1: Float):Float    //переводит массивные координаты в графически
    {
        return y*step + height1 - size_field_y1*step - advertising_line_1
    }

    fun illumination(color: Int) : Boolean
    {
        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[0].size)
            {
                if(FIELD[i][j] == 0)
                {
                    var flag: Boolean = true
                    if(i>1)
                    {
                        if(FIELD[i-1][j] == 2 - (color+1)%2)
                        {
                            var k :Int = i-1
                            flag = true
                            while(k>0)
                            {
                                if(FIELD[k][j] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[k][j] == color)
                                {
                                    break
                                }
                                k--
                            }
                            if(FIELD[k][j] == color && flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(j>1)
                    {
                        if(FIELD[i][j-1] == 2 - (color+1)%2)
                        {
                            var k :Int = j-1
                            flag = true
                            while(k>0)
                            {
                                if(FIELD[i][k] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[i][k] == color)
                                {
                                    break
                                }
                                k--
                            }
                            if(FIELD[i][k] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(i<6)
                    {
                        if(FIELD[i+1][j] == 2 - (color+1)%2)
                        {
                            var k :Int = i+1
                            flag = true
                            while(k<7)
                            {
                                if(FIELD[k][j] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[k][j] == color)
                                {
                                    break
                                }
                                k++
                            }
                            if(FIELD[k][j] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(j<6)
                    {
                        if(FIELD[i][j+1] == 2 - (color+1)%2)
                        {
                            var k :Int = j+1
                            flag = true
                            while(k<7)
                            {
                                if(FIELD[i][k] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[i][k] == color)
                                {
                                    break
                                }
                                k++
                            }
                            if(FIELD[i][k] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(i>1 &&  j>1)
                    {
                        if(FIELD[i-1][j-1] == 2 - (color+1)%2)
                        {
                            var k :Int = i -1
                            var m: Int = j -1
                            flag = true
                            while(k>0 && m>0)
                            {
                                if(FIELD[k][m] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[k][m] == color)
                                {
                                    break
                                }
                                k--
                                m--
                            }
                            if(FIELD[k][m] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(i>1 &&  j<6)
                    {
                        if(FIELD[i-1][j+1] == 2 - (color+1)%2)
                        {
                            var k :Int = i -1
                            var m: Int = j +1
                            flag = true
                            while(k>0 && m<7 && FIELD[k][m]!=color)
                            {
                                if(FIELD[k][m] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[k][m] == color)
                                {
                                    break
                                }
                                k--
                                m++
                            }
                            if(FIELD[k][m] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(i<6 &&  j>1)
                    {
                        if(FIELD[i+1][j-1] == 2 - (color+1)%2)
                        {
                            var k :Int = i + 1
                            var m: Int = j -1
                            flag = true
                            while(k<7 && m>0)
                            {
                                if(FIELD[k][m] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[k][m] == color)
                                {
                                    break
                                }
                                k++
                                m--
                            }
                            if(FIELD[k][m] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                    if(i<6 &&  j<6)
                    {
                        if(FIELD[i+1][j+1] == 2 - (color+1)%2)
                        {
                            flag = true
                            var k :Int = i +1
                            var m: Int = j +1
                            while(k<7 && m<7 )
                            {
                                if(FIELD[k][m] == 0)
                                {
                                    flag = false
                                }
                                if(FIELD[k][m] == color)
                                {
                                    break
                                }
                                k++
                                m++
                            }
                            if(FIELD[k][m] == color&& flag)
                            {
                                Array_of_illumination[i][j] = color
                            }
                        }
                    }
                }
            }
        }
        return true
    }

    fun change_array(x: Int,y : Int)
    {
        var flag: Boolean = true
        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[0].size)
            {
                flag = true
                if(FIELD[i][j] == FIELD[x][y]) {
                    if (i == x) {
                        if (j < y - 1) {
                            for (p in j + 1 until y) {
                                if (FIELD[x][p] == 0 || FIELD[x][p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in j + 1 until y) {
                                    FIELD[x][p] = FIELD[x][y]
                                }
                            }
                        }
                        if (j > y + 1) {
                            for (p in y + 1 until j) {
                                if (FIELD[x][p] == 0 || FIELD[x][p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in y + 1 until j) {
                                    FIELD[x][p] = FIELD[x][y]
                                }
                            }
                        }
                    }
                    if (j == y) {
                        if (i < x - 1) {
                            for (p in i + 1 until x) {
                                if (FIELD[p][y] == 0 || FIELD[p][y] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in i + 1 until x) {
                                    FIELD[p][y] = FIELD[x][y]
                                }
                            }
                        }
                        if (i > x + 1) {
                            Log.w("kokol","kokol")
                            for (p in x + 1 until i) {
                                if (FIELD[p][y] == 0 || FIELD[p][y] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in x + 1 until i) {
                                    FIELD[p][y] = FIELD[x][y]
                                }
                            }
                        }
                    }
                    if (i - x == j - y) {
                        if (i > x + 1) {
                            for (p in 1 until i - x) {
                                if (FIELD[x + p][y + p] == 0 || FIELD[x + p][y + p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in 1 until i - x) {
                                    FIELD[x + p][y + p] = FIELD[x][y]
                                }
                            }
                        }
                        if (i + 1 < x) {
                            for (p in 1 until x - i) {
                                if (FIELD[i + p][j + p] == 0 || FIELD[i + p][j + p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in 1 until x - i) {
                                    FIELD[i + p][j + p] = FIELD[x][y]
                                }
                            }
                        }
                    }
                    if (i - x == y - j)
                    {
                        if (i > x + 1) {
                            for (p in 1 until i - x) {
                                if (FIELD[x + p][y - p] == 0 || FIELD[x + p][y - p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in 1 until i - x) {
                                    FIELD[x + p][y - p] = FIELD[x][y]
                                }
                            }
                        }
                        if (i + 1 < x) {
                            for (p in 1 until x - i) {
                                if (FIELD[x - p][y + p] == 0 || FIELD[x - p][y + p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in 1 until x - i) {
                                    FIELD[x - p][y + p] = FIELD[x][y]
                                }
                            }
                        }
                    }
                }


            }
        }
    }

    fun check_win() : Int
    {
        var flag = false
        for(i in 0 until Array_of_illumination.size)
        {
            for(j in 0 until Array_of_illumination[0].size)
            {
                if(Array_of_illumination[i][j] != 0)
                {
                    flag = true
                }
            }
        }
        var cnt1 : Int = 0
        var cnt2: Int = 0
        if(flag)
        {
            return 0
        }
        else
        {
            for(i in 0 until FIELD.size)
            {
                for(j in 0 until  FIELD[0].size)
                {
                    if(FIELD[i][j] == 1)
                    {
                        cnt1++
                    }
                    if(FIELD[i][j] == 2)
                    {
                        cnt2++
                    }
                }
            }
            if (cnt1 + cnt2 == 64) {
                if (cnt1 > cnt2) {
                    return 1
                } else {
                    if (cnt1 == cnt2) {
                        return 3
                    } else {
                        return 2
                    }
                }
            } else {
                return 0
            }
        }
    }

    lateinit var t1: TextView
    lateinit var t2: TextView

    lateinit var activity: Activity

    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var EXODUS : Int = 0
    var indent : Float = 0f
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var Black_or_grey_chip: String = "black"
    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var Line_paint1: Paint = Paint()
    var FIELD = Array(8){IntArray(8)}     //для фишек
    var Array_of_illumination = Array(8) { IntArray(8) }


    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f
    var blockedOnTouch : Boolean = false

    var lastX = -1
    var lastY = -1

    var exception: Boolean = false

    var line_who_do_move : Paint = Paint()

    init{
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(7f)

        line_who_do_move.strokeWidth = 7f

        when (Design) {
            "Normal" ->{
                line_who_do_move.color =  Color.GREEN
                //line_who_do_move.strokeWidth = 14f
                Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
            }
            "Egypt" -> {
                Line_paint.color = Color.BLACK          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED
            }
            "Casino" -> {
                Line_paint.color = Color.rgb(217, 217, 217)          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.YELLOW            //
            }
            "Rome" -> {
                Line_paint.color = Color.BLACK  //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.BLACK
            }
            "Gothic" -> {
                Line_paint.color = Color.rgb(100, 100, 100)   //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.WHITE
            }
            "Japan" -> {
                Line_paint.color = Color.BLACK   //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED              //
            }
            "Noir" -> {
                Line_paint.color = Color.rgb(100, 100, 100)   //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED              //
            }
        }
        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..7) {
            for(j in 0 ..7) {
                FIELD[i][j] = 0  //не заполненный
                Array_of_illumination[i][j] = 0
            }
        }
        FIELD[3][3] = 2
        FIELD[4][4] = 2
        FIELD[4][3] = 1
        FIELD[3][4] = 1
        Array_of_illumination[2][3] = 1
        Array_of_illumination[3][2] = 1
        Array_of_illumination[5][4] = 1
        Array_of_illumination[4][5] = 1

    }




    var black_chip_normal : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_normal);       //картинки фишек и подсветки
    var grey_chip_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_normal);

    var black_chip_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_egypt);
    var grey_chip_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_egypt)

    var black_chip_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_casino);
    var grey_chip_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_casino);

    var black_chip_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_rome);
    var grey_chip_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_rome);

    var black_chip_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_gothic);
    var grey_chip_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_gothic);

    var black_chip_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_japan);
    var grey_chip_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_japan);

    var black_chip_noir: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_noir);
    var grey_chip_noir: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_noir);


    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);
    var romb1: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.romb);
    var romb2: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.romb2);
    var romb3: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.romb3);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        indent = 40f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        size_field_x  = 8
        size_field_y  = 8
        step = (width-2*indent)/size_field_x
        advertising_line =  (height - 8*step)/2           //полоска для рекламы
        k = height-(width-2*indent)-advertising_line

        when(LANGUAGE) {
            "Russian" -> {
                if(Black_or_grey_chip == "black")
                {
                    t1.text = "Игрок думает..."
                    t2.text = "Компьютер"
                    canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)

                }
                else
                {
                    t1.text = "Игрок"
                    t2.text = "Компьютер думает..."
                    canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
                }
            }
            "English" -> {

                if(Black_or_grey_chip == "black")
                {
                    t1.text = "Player thinks..."
                    t2.text = "Bot"
                    canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)

                }
                else
                {
                    t1.text = "Player"
                    t2.text = "Bot calculates..."
                    canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
                }
            }
        }



        if(Design == "Normal")
        {
            canvas?.drawColor(Color.WHITE)
        }
        else
        {

        }



        for(i in 0 until size_field_x+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width-indent,k,Line_paint)
            k = k + step
        }
        k = indent
        for(i in 0 until size_field_y+2)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k,height-advertising_line-width+2*indent,k,height-advertising_line,Line_paint)
            k = k + step
        }

        var right_black_chip: Bitmap
        var right_grey_chip: Bitmap
        var right_green:Bitmap

        right_black_chip = Bitmap.createScaledBitmap(black_chip_normal,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
        right_grey_chip = Bitmap.createScaledBitmap(grey_chip_normal,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        right_green = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);


        when (Design) {
            "Normal" ->{

            }
            "Egypt" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb3,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Casino" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_casino,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_casino,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb1,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Rome" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_rome,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_rome,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb2,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Gothic" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_gothic,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_gothic,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb1,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Japan" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_japan,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_japan,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb3,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Noir" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_noir,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_noir,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb1,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
        }

        for( i in 0..7) // расстановка фишек
        {
            for(j in 0..7) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(right_black_chip, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    canvas?.drawBitmap(right_grey_chip, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }

        for( i in 0..7) // расстановка фишек
        {
            for(j in 0..7) {
                if (Array_of_illumination[i][j] == ReversiMode)  //крестик
                {
                    canvas?.drawBitmap(right_green, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }

        if ((Black_or_grey_chip == "black" && ReversiMode == 2) || (Black_or_grey_chip == "grey" && ReversiMode == 1)) {
            blockedOnTouch = true


            if (Black_or_grey_chip == "black") {
                illumination(1)
            } else {
                illumination(2)
            }
            var flag: Boolean = true
            for (i in 0 until Array_of_illumination.size) {
                for (j in 0 until Array_of_illumination[0].size) {
                    if (Array_of_illumination[i][j] != 0) {
                        flag = false
                    }
                }
            }

            val handler = android.os.Handler()
            handler.postDelayed({
                if (!flag) {
                    var fla = false

                    val list_x: MutableList<Int> = mutableListOf(0, 7, 0, 7)
                    val list_y: MutableList<Int> = mutableListOf(0, 0, 7, 7)

                    for (i2 in 0 until 3) {
                        if (Array_of_illumination[list_x[i2]][list_y[i2]] != 0) {
                            if (Black_or_grey_chip == "black") {
                                FIELD[list_x[i2]][list_y[i2]] = 1
                                History.add(Triple(list_x[i2], list_y[i2], 1))
                                val data_from_memory = encode(History)
                                val editor = context.getSharedPreferences(
                                    "UserData",
                                    Context.MODE_PRIVATE
                                ).edit()
                                editor.putString(
                                    "reversi_with_computer",
                                    data_from_memory
                                )
                                editor.apply()
                                Black_or_grey_chip = "grey"
                            } else {
                                FIELD[list_x[i2]][list_y[i2]] = 2
                                History.add(Triple(list_x[i2], list_y[i2], 2))
                                val data_from_memory = encode(History)
                                val editor = context.getSharedPreferences(
                                    "UserData",
                                    Context.MODE_PRIVATE
                                ).edit()
                                editor.putString(
                                    "reversi_with_computer",
                                    data_from_memory
                                )
                                editor.apply()
                                Black_or_grey_chip = "black"
                            }


                            ////////////
                            fla = true

                            for (i in 0..7) {
                                for (j in 0..7) {
                                    Array_of_illumination[i][j] = 0
                                }
                            }
                            change_array(list_x[i2], list_y[i2])

                            if (Black_or_grey_chip == "black") {
                                illumination(1)
                            } else {
                                illumination(2)
                            }

                            break
                        }
                    }


                    if (fla == false) {
                        for (i2 in 2 until 5) {
                            for (j2 in 2 until 5) {
                                if (Array_of_illumination[i2][j2] != 0) {
                                    if (Black_or_grey_chip == "black") {
                                        FIELD[i2][j2] = 1
                                        History.add(Triple(i2, j2, 1))
                                        val data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString(
                                            "reversi_with_computer",
                                            data_from_memory
                                        )
                                        editor.apply()
                                        Black_or_grey_chip = "grey"
                                    } else {
                                        FIELD[i2][j2] = 2
                                        History.add(Triple(i2, j2, 2))
                                        val data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString(
                                            "reversi_with_computer",
                                            data_from_memory
                                        )
                                        editor.apply()
                                        Black_or_grey_chip = "black"
                                    }


                                    ////////////
                                    fla = true

                                    for (i in 0..7) {
                                        for (j in 0..7) {
                                            Array_of_illumination[i][j] = 0
                                        }
                                    }
                                    change_array(i2, j2)

                                    if (Black_or_grey_chip == "black") {
                                        illumination(1)
                                    } else {
                                        illumination(2)
                                    }

                                    /*for (i in 0 until Array_of_illumination.size) {
                                for (j in 0 until Array_of_illumination[0].size) {
                                    if (Array_of_illumination[i][j] == 1) {
                                        flag = false
                                    }
                                }
                            }*/
                                    break
                                }
                            }
                            if (fla == true) {
                                break
                            }
                        }
                    }

                    if (fla == false) {
                        for (i2 in 0 until Array_of_illumination.size) {
                            for (j2 in 0 until Array_of_illumination[0].size) {
                                if (Array_of_illumination[i2][j2] != 0) {
                                    if (Black_or_grey_chip == "black") {
                                        FIELD[i2][j2] = 1
                                        History.add(Triple(i2, j2, 1))
                                        val data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString(
                                            "reversi_with_computer",
                                            data_from_memory
                                        )
                                        editor.apply()
                                        Black_or_grey_chip = "grey"
                                    } else {
                                        FIELD[i2][j2] = 2
                                        History.add(Triple(i2, j2, 2))
                                        val data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString(
                                            "reversi_with_computer",
                                            data_from_memory
                                        )
                                        editor.apply()
                                        Black_or_grey_chip = "black"
                                    }


                                    ////////////
                                    fla = true

                                    for (i in 0..7) {
                                        for (j in 0..7) {
                                            Array_of_illumination[i][j] = 0
                                        }
                                    }
                                    change_array(i2, j2)

                                    if (Black_or_grey_chip == "black") {
                                        illumination(1)
                                    } else {
                                        illumination(2)
                                    }

                                    break
                                }
                            }
                            if (fla == true) {
                                break
                            }
                        }
                    }


                    blockedOnTouch = false
                    invalidate()
                }

                if (flag)                                    //если игрок не может походить то ход переходит другому
                {
                    blockedOnTouch = !blockedOnTouch

                    if (Black_or_grey_chip == "black") {
                        Black_or_grey_chip = "grey"
                        illumination(2)
                    } else {
                        Black_or_grey_chip = "black"
                        illumination(1)
                    }
                }


                var dialog: Show_Result_with_Computer? = null


                if (check_win() > 0) {       //TODO more check
                    if (check_win() == 2 && ReversiMode == 1) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Поражение", "Reversi", activity)
                    }
                    if (check_win() == 1 && ReversiMode == 1) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Победа", "Reversi", activity)
                    }
                    if (check_win() == 2 && ReversiMode == 2) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Победа", "Reversi", activity)
                    }
                    if (check_win() == 1 && ReversiMode == 2) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Поражение", "Reversi", activity)
                    }
                    if (check_win() == 3) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Ничья", "Reversi", activity)
                    }
                }


            }, delayTime)



        }
    }



    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        
        if(check_win()<=0)
        {
            blocked = false
        }
        if(check_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
            return true
        }
        var dialog: Show_Result_with_Computer? = null

        if(check_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked) {     //TODO more check
            if (check_win() == 2 && ReversiMode == 1) {
                dialog = Show_Result_with_Computer(activity)
                dialog.showResult_with_Computer("Поражение", "Reversi", activity)
                return true
            }
            if (check_win() == 1 && ReversiMode == 1) {
                dialog = Show_Result_with_Computer(activity)
                dialog.showResult_with_Computer("Победа", "Reversi", activity)
                return true
            }
            if (check_win() == 2 && ReversiMode == 2) {
                dialog = Show_Result_with_Computer(activity)
                dialog.showResult_with_Computer("Победа", "Reversi", activity)
                return true
            }
            if (check_win() == 1 && ReversiMode == 2) {
                dialog = Show_Result_with_Computer(activity)
                dialog.showResult_with_Computer("Поражение", "Reversi", activity)
                return true
            }
            if(check_win() == 3)
            {
                dialog = Show_Result_with_Computer(activity)
                dialog.showResult_with_Computer("Ничья", "Reversi", activity)
                return true
            }
        }

        if(blockedOnTouch)
        {
            return true
        }

        circlex = event!!.x
        circley = event!!.y
        var X: Int = touch_refinement_for_Array_X(indent,circlex, step)
        var Y: Int = touch_refinement_for_Array_Y(indent,circley, height, size_field_y, step, advertising_line)      //перевод последнего нажатия // в координаты массива
        if(Black_or_grey_chip == "black") {
            illumination(1)
        } else {
            illumination(2)
        }
        if(X in 0..7 && Y in 0..7 )
        {
            if(FIELD[X][Y] == 0 && Array_of_illumination[X][Y] != 0)
            {
                if (Black_or_grey_chip == "black") {
                    FIELD[X][Y] = 1
                    History.add(Triple(X,Y,1))
                    var data_from_memory = encode(History)
                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("reversi_with_computer", data_from_memory)
                    editor.apply()
                    Black_or_grey_chip = "grey"
                    if(SOUND)
                    {
                        mSound.play(1,1F,1F,1,0,1F)
                    }
                    if(VIBRATION)
                    {
                        vibratorService?.vibrate(70)
                    }
                } else {
                    FIELD[X][Y] = 2
                    History.add(Triple(X,Y,2))
                    var data_from_memory = encode(History)
                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("reversi_with_computer", data_from_memory)
                    editor.apply()
                    Black_or_grey_chip = "black"
                    if(SOUND)
                    {
                        mSound.play(1,1F,1F,1,0,1F)
                    }
                    if(VIBRATION)
                    {
                        vibratorService?.vibrate(70)
                    }
                }
                blockedOnTouch = true
                for( i in 0..7) {
                    for(j in 0 ..7) {
                        Array_of_illumination[i][j] = 0
                    }
                }
                change_array(X,Y)

                if(Black_or_grey_chip == "black") {
                    illumination(1)
                } else {
                    illumination(2)
                }
                var flag: Boolean = true
                for(i in 0 until Array_of_illumination.size)
                {
                    for(j in 0 until Array_of_illumination[0].size)
                    {
                        if(Array_of_illumination[i][j] != 0)
                        {
                            flag = false
                        }
                    }
                }

                if (flag) {                                   //если игрок не может походить то ход переходит другому
                    blockedOnTouch = !blockedOnTouch

                    if (Black_or_grey_chip == "black") {
                        Black_or_grey_chip = "grey"
                        illumination(2)
                    } else {
                        Black_or_grey_chip = "black"
                        illumination(1)
                    }
                }

                invalidate()


                if(check_win()>0) {       //TODO more check
                    if (check_win() == 2 && ReversiMode == 1) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Поражение", "Reversi", activity)
                        return true
                    }
                    if (check_win() == 1 && ReversiMode == 1) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Победа", "Reversi", activity)
                        return true
                    }
                    if (check_win() == 2 && ReversiMode == 2) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Победа", "Reversi", activity)
                        return true
                    }
                    if (check_win() == 1 && ReversiMode == 2) {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Поражение", "Reversi", activity)
                        return true
                    }
                    if(check_win() == 3)
                    {
                        dialog = Show_Result_with_Computer(activity)
                        dialog.showResult_with_Computer("Ничья", "Reversi", activity)
                        return true
                    }
                }





            }
        }
        return true
    }

}
