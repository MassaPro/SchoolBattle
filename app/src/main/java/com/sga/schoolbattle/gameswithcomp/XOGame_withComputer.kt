package com.sga.schoolbattle.gameswithcomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.AdRequest
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*
import kotlinx.android.synthetic.main.activity_one_device_games_template.*

var XOGameMode = 0

class XOGame_withComputer : AppCompatActivity() {
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
    private var dialog_rules: Show_rules? = null
    private var dialog_parametrs: Show_parametr_with_computer? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_computer_games_template)
        signature_canvas_xog_with_computer.visibility = (View.VISIBLE)
        signature_canvas_xog_with_computer.activ = this
        CONTEXT = this

        if(!PREMIUM)
        {
            mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())
        }

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        val prefs2 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        XOGameMode = prefs2.getInt("XOGameMode", 0)
        if (XOGameMode == 0) {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putInt("XOGameMode", 1)
            editor.apply()
            XOGameMode = 1
        }
        signature_canvas_xog_with_computer.blocked = false

        val prefs_first = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_xog_with_computer.History = decode(prefs_first.getString("xog_with_computer", "").toString())
        if (XOGameMode == 2 && signature_canvas_xog_with_computer.History.size == 0) {
            signature_canvas_xog_with_computer.blocked = true
        }

        //var h : MutableList<Triple<Int,Int,Int>> =  mutableListOf(Triple(231,231,777),Triple(231,231,777),Triple(231,231,777))
        //Log.w("momlol",decode(encode(h)).toString())

        signature_canvas_xog_with_computer.t1 = findViewById(R.id.name_player1_with_computer_template) as TextView
        signature_canvas_xog_with_computer.t2 = findViewById(R.id.name_player2_with_computer_template) as TextView
        signature_canvas_xog_with_computer.t1.text = "Игрок 1"
        signature_canvas_xog_with_computer.t2.text = "Игрок 2"
        //signature_canvas_xog_with_computer.t1.set

        when (Design) {
            "Normal" ->{
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.virus1_normal);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.virus2_normal);
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_normal)
            }
            "Egypt" -> {
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.player1_egypt);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.player2_egypt);
                player_1_icon_template_with_computer.setBackgroundResource(R.drawable.cross_egypt);
                player_2_icon_template_with_computer.setBackgroundResource(R.drawable.circle_egypt)
                label_with_computer.setBackgroundResource(R.drawable.background_egypt);
                bottom_navigation_with_computer_template.setBackgroundColor(rgb(255, 230, 163))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_normal)
                toolbar_with_computer_template.setBackgroundColor(argb(0,0,0,0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0,0,0,0))
            }
            "Casino" -> {
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.RED)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_casino);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_casino);
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background2_casino);
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0,224, 164, 103))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_casino)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                bottom_navigation_with_computer_template.setBackgroundResource(R.drawable.bottom_navigation_casino)
            }
            "Rome" -> {
                name_player1_with_computer_template.setTextColor(rgb(193,150,63))
                name_player2_with_computer_template.setTextColor(rgb(193,150,63))
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_rome);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_rome);
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_rome);
                bottom_navigation_with_computer_template.setBackgroundResource(R.drawable.bottom_navigation_rome)
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
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_gothic);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_gothic);
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_gothic);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.BLACK)
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
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_japan);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_japan);
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_japan);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.WHITE)
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
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_noir);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_noir);
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_noir);
                bottom_navigation_with_computer_template.setBackgroundColor(argb(0,0,0,0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.back_arrow_gothic)
                toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            }
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("xog_with_computer", "")
            editor.apply()
        }

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_xog_with_computer.History = decode(prefs.getString("xog_with_computer", "").toString())
        for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
            for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                signature_canvas_xog_with_computer.FIELD[i][j] = 0
            }
        }
        signature_canvas_xog_with_computer.cross_or_nul = "cross"
        for (i in signature_canvas_xog_with_computer.History) {
            signature_canvas_xog_with_computer.FIELD[i.first][i.second] = i.third
            if (i.third == 1) {
                signature_canvas_xog_with_computer.cross_or_nul = "null"
            } else {
                signature_canvas_xog_with_computer.cross_or_nul = "cross"
            }
        }
        signature_canvas_xog_with_computer.invalidate()

        to_back_with_computer_template.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 3)
            startActivity(intent)
        }

        if (XOGameMode == 2 && signature_canvas_xog_with_computer.History.size == 0) {      // first computer move
            signature_canvas_xog_with_computer.blocked = true

            var find_x = 0
            var find_y = 0
            var fla = 0
            var list_x: MutableList<Int> = mutableListOf(1, 1, 1, 0, -1, -1, -1, 0)
            var list_y: MutableList<Int> = mutableListOf(-1, 0, 1, 1, 1, 0, -1, -1)

            val handler = android.os.Handler()
            handler.postDelayed(
                {

                for (j in 5 downTo 0) {
                    for (i in 0..6) {
                        if (signature_canvas_xog_with_computer.FIELD[i][j] == 0 && (j == 5 || signature_canvas_xog_with_computer.FIELD[i][j + 1] != 0)) {
                            signature_canvas_xog_with_computer.FIELD[i][j] = 2
                            if (signature_canvas_xog_with_computer.checkForWin_another_fun().size == 9) {
                                signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                find_x = i
                                find_y = j
                                fla = 1
                                break
                            }
                            signature_canvas_xog_with_computer.FIELD[i][j] = 0
                        }
                    }
                    if (fla == 1)
                        break
                }


                if (fla == 0) {
                    for (j in 5 downTo 0) {
                        for (i in 0..6) {
                            if (signature_canvas_xog_with_computer.FIELD[i][j] == 0 && (j == 5 || signature_canvas_xog_with_computer.FIELD[i][j + 1] != 0)) {
                                signature_canvas_xog_with_computer.FIELD[i][j] = 1
                                if (signature_canvas_xog_with_computer.checkForWin_another_fun().size == 9) {
                                    signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                    find_x = i
                                    find_y = j
                                    fla = 1
                                    break
                                }
                                signature_canvas_xog_with_computer.FIELD[i][j] = 0
                            }
                        }
                        if (fla == 1)
                            break
                    }
                }
                if (fla == 0) {
                    for (j in 5 downTo 0) {
                        for (i in 0..6) {
                            if (signature_canvas_xog_with_computer.FIELD[i][j] == 0 && (j == 5 || signature_canvas_xog_with_computer.FIELD[i][j + 1] != 0)) {
                                for (at in 0..7) {
                                    Log.w("TAG", "$j $i $at")
                                    var fl = 0
                                    for (k in 1..2) {
                                        if (signature_canvas_xog_with_computer.FIELD[(i + k * list_x[at] + 7) % 7][(j + k * list_y[at] + 6) % 6] != 2)
                                            fl = 1
                                    }
                                    if (fl == 0) {
                                        find_x = i
                                        find_y = j
                                        fla = 1
                                        break
                                    }
                                }
                                if (fla == 1) {
                                    break
                                }
                            }
                        }
                        if (fla == 1)
                            break
                    }
                }
                if (fla == 1) {
                    signature_canvas_xog_with_computer.FIELD[find_x][find_y] = 2
                    signature_canvas_xog_with_computer.History.add(Triple(find_x,find_y,2))
                    var data_from_memory = encode(signature_canvas_xog_with_computer.History)
                    val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("xog_with_computer", data_from_memory)
                    editor.apply()
                    signature_canvas_xog_with_computer.cross_or_nul = "cross"
                }

                if (fla == 0) {
                    for (j in 5 downTo 0) {
                        for (i in 0..6) {
                            if (signature_canvas_xog_with_computer.FIELD[i][j] == 0) {
                                signature_canvas_xog_with_computer.FIELD[i][j] = 2
                                signature_canvas_xog_with_computer.History.add(Triple(i,j,2))
                                var data_from_memory = encode(signature_canvas_xog_with_computer.History)
                                val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("xog_with_computer", data_from_memory)
                                editor.apply()
                                signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                fla = 1
                                break
                            }
                        }
                        if (fla == 1)
                            break
                    }
                }

                signature_canvas_xog_with_computer.blocked = false
                signature_canvas_xog_with_computer.invalidate()
            }, delayTime)
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
                            this@XOGame_withComputer
                        )
                    dialog_rules?.show("XOGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_with_computer(
                            this@XOGame_withComputer
                        )
                    dialog_parametrs?.showResult_with_computer(this, "XOGame")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, XOGame_withComputer::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_xog_with_computer.blocked == false || signature_canvas_xog_with_computer.EXODUS != 0) {
                        if (signature_canvas_xog_with_computer.EXODUS == 0) {
                            if (signature_canvas_xog_with_computer.History.size > 1) {            //TODO дописать когда самый первый ход убираем
                                signature_canvas_xog_with_computer.History.removeLast()
                                signature_canvas_xog_with_computer.History.removeLast()
                                var data_from_memory =
                                    encode(signature_canvas_xog_with_computer.History)
                                val editor =
                                    getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("xog_with_computer", data_from_memory)
                                editor.apply()
                                for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
                                    for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                                        signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                    }
                                }
                                signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                for (i in signature_canvas_xog_with_computer.History) {
                                    signature_canvas_xog_with_computer.FIELD[i.first][i.second] =
                                        i.third
                                    if (i.third == 1) {
                                        signature_canvas_xog_with_computer.cross_or_nul = "null"
                                    } else {
                                        signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                    }
                                }
                                signature_canvas_xog_with_computer.invalidate()
                            }
                        } else {
                            if (signature_canvas_xog_with_computer.EXODUS == 1) {
                                if (signature_canvas_xog_with_computer.History.size > 0) {
                                    signature_canvas_xog_with_computer.History.removeLast()
                                    var data_from_memory =
                                        encode(signature_canvas_xog_with_computer.History)
                                    val editor =
                                        getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("xog_with_computer", data_from_memory)
                                    editor.apply()
                                    for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
                                        for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                                            signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                        }
                                    }
                                    signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                    for (i in signature_canvas_xog_with_computer.History) {
                                        signature_canvas_xog_with_computer.FIELD[i.first][i.second] =
                                            i.third
                                        if (i.third == 1) {
                                            signature_canvas_xog_with_computer.cross_or_nul = "null"
                                        } else {
                                            signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                        }
                                    }
                                    signature_canvas_xog_with_computer.invalidate()
                                }
                            } else {
                                if (signature_canvas_xog_with_computer.History.size > 1) {
                                    signature_canvas_xog_with_computer.History.removeLast()
                                    signature_canvas_xog_with_computer.History.removeLast()
                                    var data_from_memory =
                                        encode(signature_canvas_xog_with_computer.History)
                                    val editor =
                                        getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("xog_with_computer", data_from_memory)
                                    editor.apply()
                                    for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
                                        for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                                            signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                        }
                                    }
                                    signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                    for (i in signature_canvas_xog_with_computer.History) {
                                        signature_canvas_xog_with_computer.FIELD[i.first][i.second] =
                                            i.third
                                        if (i.third == 1) {
                                            signature_canvas_xog_with_computer.cross_or_nul = "null"
                                        } else {
                                            signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                        }
                                    }
                                    signature_canvas_xog_with_computer.invalidate()
                                }
                            }

                        }
                        signature_canvas_xog_with_computer.EXODUS = 0
                        signature_canvas_xog_with_computer.blocked = false
                    }
                }

            }
            true
        }

        to_back_with_computer_template.setOnClickListener {
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




class CanvasView_xog_with_computer(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
        var a: Float = height1 - advertising_line_1 - step*size_field_y1
        var b :Int = 0
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
    fun checkForWin_another_fun(): MutableList<Int> {
        val list_x = mutableListOf(1, 1, 0, -1)
        val list_y = mutableListOf(0, 1, 1, 1)

        var ans = mutableListOf(0)
        for (i in 0..6) {
            for (j in 0..5) {
                if (FIELD[i][j] != 0) {
                    for (k in 0..3) {
                        var fl = 0
                        for (pos in 0..2) {
                            Log.w("TAG", "$i ${list_x[k]} $pos")
                            if (FIELD[(i + list_x[k] * pos + 7) % 7][(j + list_y[k] * pos + 6) % 6] != FIELD[(i + list_x[k] * (pos + 1) + 7) % 7][(j + list_y[k] * (pos + 1) + 6) % 6]) {
                                fl = 1

                            }
                        }
                        if (fl == 0) {
                            for (pos in 0..3) {
                                ans.add((i + list_x[k] * pos + 7) % 7)
                                ans.add((j + list_y[k] * pos + 6) % 6)
                            }
                            return ans
                        }
                    }
                }
            }
        }
        return ans
    }


    lateinit var activ : Activity

    lateinit var t1: TextView
    lateinit var t2: TextView
    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var width : Float = 0f
    var height: Float = 0f
    var indent : Float = 20f
    //ширина и высота экрана (от ширины в основном все зависит)

    var EXODUS : Int = 0
    var size_field_x: Int = 7
    var size_field_y: Int = 6
    var step: Float = width/size_field_x
    var blocked = false

    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f

    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var Line_paint_1: Paint = Paint()
    var FIELD = Array(7){IntArray(6)}
    var cross_or_nul: String

    var line_who_do_move : Paint = Paint()

    init {
        Line_paint.color = Color.RED          //ресур для линий (ширина и цвет)
        Line_paint.strokeWidth = 7f

        Line_paint_1.color = Color.RED          //ресур для линий (ширина и цвет)
        Line_paint_1.strokeWidth = 20f

        line_who_do_move.strokeWidth = 7f

        when (Design) {
            "Normal" ->{
                line_who_do_move.color =  Color.GREEN
                line_who_do_move.strokeWidth = 14f
                Line_paint.setColor(rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
            }
            "Egypt" -> {
                Line_paint.color = Color.BLACK          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED
            }
            "Casino" -> {
                Line_paint.color = Color.WHITE         //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.WHITE           //
            }
            "Rome" -> {
                Line_paint.color = rgb(193,150,63)    //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.BLACK
            }
            "Gothic" -> {
                Line_paint.color = rgb(100,100,100)   //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.WHITE
            }
            "Japan" -> {
                Line_paint.color = Color.BLACK   //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED              //
            }
            "Noir" -> {
                Line_paint.color = rgb(100,100,100)   //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED              //
            }
        }


        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..6) {
            for(j in 0 ..5) {
                FIELD[i][j] = 0 //не заполненный
            }
        }
        cross_or_nul  = "cross"
    }

    var cross_normal : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_normal)       //картинки крестиков и нулей
    var null_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_normal)

    var cross_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_egypt)       //картинки крестиков и нулей
    var null_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle_egypt)

    var cross_casino : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_casino)       //картинки крестиков и нулей
    var null_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_casino)

    var cross_rome : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_rome)       //картинки крестиков и нулей
    var null_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_rome)

    var cross_gothic : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_gothic)       //картинки крестиков и нулей
    var null_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_gothic)

    var cross_japan : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_japan)       //картинки крестиков и нулей
    var null_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_japan)

    var cross_noir : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_noir)       //картинки крестиков и нулей
    var null_noir: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_noir)

    // var BackgroundColor_Egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_egypt)
    var icon_green : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination)
    var icon_grenn_Egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination)



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        //TODO() take field from database

        if(cross_or_nul == "cross")
        {
            t1.text ="Игрок думает..."
            t2.text  = "Компьютер"
            canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)
        }
        else
        {
            t1.text ="Игрок"
            t2.text  = "Компьютер думает..."
            canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
        }
        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)

        size_field_x  = 7
        size_field_y  = 6
        step = (width-2*indent)/size_field_x
        var advertising_line: Float = (height - step * 6) / 2
        var k: Float = height-(width-2*indent)-advertising_line


        var right_cross: Bitmap  //подгоняем картинку под размеры экрана телефона
        var right_null: Bitmap
        var right_green: Bitmap

        right_cross = Bitmap.createScaledBitmap(cross_normal,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
        right_null = Bitmap.createScaledBitmap(null_normal,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
        right_green = Bitmap.createScaledBitmap(icon_green, (width.toInt() - 2 * indent.toInt()) / size_field_x, (width.toInt() - 2 * indent.toInt()) / size_field_x, true)
        when (Design) {
            "Egypt" -> {
                right_cross = Bitmap.createScaledBitmap(cross_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_null = Bitmap.createScaledBitmap(null_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(icon_grenn_Egypt, (width.toInt() - 2 * indent.toInt()) / size_field_x, (width.toInt() - 2 * indent.toInt()) / size_field_x, true)
            }
            "Casino" -> {
                right_cross = Bitmap.createScaledBitmap(cross_casino,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_null = Bitmap.createScaledBitmap(null_casino,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            }
            "Rome" -> {
                right_cross = Bitmap.createScaledBitmap(cross_rome,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_null = Bitmap.createScaledBitmap(null_rome,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            }
            "Gothic" -> {
                right_cross = Bitmap.createScaledBitmap(cross_gothic,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_null = Bitmap.createScaledBitmap(null_gothic,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            }
            "Japan" -> {
                right_cross = Bitmap.createScaledBitmap(cross_japan,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_null = Bitmap.createScaledBitmap(null_japan,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            }
            "Noir" -> {
                right_cross = Bitmap.createScaledBitmap(cross_noir,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
                right_null = Bitmap.createScaledBitmap(null_noir,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            }
        }


        k = height - (width-2*indent)*size_field_y/size_field_x - advertising_line
        for(i in 0 until 7)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width-indent,k,Line_paint)
            k = k + step
        }
        k  =  height-(width-2*indent)*size_field_y/size_field_x-advertising_line
        var t = indent
        for(i in 0 until 8)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(t,k,t,height-advertising_line,Line_paint)
            t = t + step
        }


        var countSimb = 0
        for( i in 0..6) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..5) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    countSimb++
                    canvas?.drawBitmap(right_cross, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    countSimb++
                    canvas?.drawBitmap(right_null, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }


        if(checkForWin_another_fun().size==9)
        {
            var counter: Int = 1
            blocked = true
            if(FIELD[checkForWin_another_fun()[counter]][checkForWin_another_fun()[counter+1]] == 1)
            {
                EXODUS = 1
            }
            else
            {
                EXODUS = 2
            }

            while(counter<9)
            {
                var a_1: Float =  translate_from_Array_to_Graphics_X(indent,checkForWin_another_fun()[counter],step)
                var a_2: Float = translate_from_Array_to_Graphics_Y(indent,checkForWin_another_fun()[counter+1].toInt(), height,size_field_y, step, advertising_line)
                canvas?.drawBitmap(right_green,a_1,a_2,paint)
                counter += 2
            }
        } else if (countSimb == 42) {
            EXODUS = 3
        }

        var dialog: Show_Result_with_Computer? = null

        if(EXODUS == 1) {
            dialog = Show_Result_with_Computer(activ)
            dialog.showResult_with_Computer("Победа","XOGame",activ)

        }
        if(EXODUS == 2) {
            dialog = Show_Result_with_Computer(activ)
            dialog.showResult_with_Computer("Поражение","XOGame",activ)
        }
        if (EXODUS == 3) {
            dialog = Show_Result_with_Computer(activ)
            dialog.showResult_with_Computer("Ничья","XOGame",activ)
        }





    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        var advertising_line: Float = (height - step * 6) / 2
        if(checkForWin_another_fun().size==9)
        {
            var counter: Int = 1
            blocked = true
            if(FIELD[checkForWin_another_fun()[counter]][checkForWin_another_fun()[counter+1]] == 1)
            {
                EXODUS = 1
            }
            else
            {
                EXODUS = 2
            }

        }
        else
        {
            EXODUS = 0
            //blocked = false
        }
        /*var dialog: Show_Result_with_Computer? = null

        if(EXODUS == 1)
        {
            dialog =
                Show_Result_with_Computer(activ)
            dialog?.showResult_with_Computer("КРЕСТИКИ ПОБЕДИЛИ","XOGame",activ)

        }
        if(EXODUS == 2)
        {
            dialog =
                Show_Result_with_Computer(activ)
            dialog?.showResult_with_Computer("НОЛИКИ ПОБЕДИЛИ","XOGame",activ)
        }*/

        if(blocked)
        {
            return true
        }

        circlex =  event!!.x
        circley =  event!!.y
        Log.w("ppppppp",circlex.toString() +" "+ circley.toString() + width.toString() + " " + touch_refinement_Y(indent,circley,height,size_field_y,step,advertising_line).toString())
        if (touch_refinement_Y(indent,circley,height,size_field_y,step,advertising_line)>0)     //постановка нового обЪекта
        {
            Log.w("ppppppp","LOL")
            var X: Int = touch_refinement_for_Array_X(indent,circlex,step)
            var Y: Int = touch_refinement_for_Array_Y(indent,circley,height,size_field_y,step,advertising_line)    //координаты нажимаего для массива
            if(X >= 0 && Y >= 0 && X<7 && Y<6)
            {
                if(FIELD[X][Y] == 0 && (Y == 5 || FIELD[X][Y+1]!=0)) {
                    if (cross_or_nul == "cross") {
                        FIELD[X][Y] = 1
                        History.add(Triple(X,Y,1))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_with_computer", data_from_memory)
                        editor.apply()
                        cross_or_nul = "null"
                    } else {
                        FIELD[X][Y] = 2
                        History.add(Triple(X,Y,2))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_with_computer", data_from_memory)
                        editor.apply()
                        cross_or_nul = "cross"
                    }
                    if(SOUND) {
                        mSound.play(1,1F,1F,1,0,1F)
                    }
                    if(VIBRATION) {
                        vibratorService?.vibrate(70)
                    }
                    Log.w("ppppppp", FIELD[0][0].toString())
                    invalidate()

                    var find_x = 0
                    var find_y = 0
                    var fla = 0
                    var list_x: MutableList<Int> = mutableListOf(1, 1, 1, 0, -1, -1, -1, 0)
                    var list_y: MutableList<Int> = mutableListOf(-1, 0, 1, 1, 1, 0, -1, -1)

                    blocked = true

                    if (checkForWin_another_fun().size==9) {
                        return true
                    }

                    val handler = android.os.Handler()
                    handler.postDelayed({
                        for (j in 5 downTo 0) {
                            for (i in 0..6) {
                                if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                    FIELD[i][j] = 2
                                    if (checkForWin_another_fun().size == 9) {
                                        FIELD[i][j] = 0
                                        find_x = i
                                        find_y = j
                                        fla = 1
                                        break
                                    }
                                    FIELD[i][j] = 0
                                }
                            }
                            if (fla == 1)
                                break
                        }


                        if (fla == 0) {
                            for (j in 5 downTo 0) {
                                for (i in 0..6) {
                                    if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                        FIELD[i][j] = 1
                                        if (checkForWin_another_fun().size == 9) {
                                            FIELD[i][j] = 0
                                            find_x = i
                                            find_y = j
                                            fla = 1
                                            break
                                        }
                                        FIELD[i][j] = 0
                                    }
                                }
                                if (fla == 1)
                                    break
                            }
                        }
                        if (fla == 0) {
                            for (j in 5 downTo 0) {
                                for (i in 0..6) {
                                    if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                        for (at in 0..7) {
                                            Log.w("TAG", "$j $i $at")
                                            var fl = 0
                                            for (k in 1..2) {
                                                if (FIELD[(i + k * list_x[at] + 7) % 7][(j + k * list_y[at] + 6) % 6] != 2)
                                                    fl = 1
                                            }
                                            if (fl == 0) {
                                                find_x = i
                                                find_y = j
                                                fla = 1
                                                break
                                            }
                                        }
                                        if (fla == 1) {
                                            break
                                        }
                                    }
                                }
                                if (fla == 1)
                                    break
                            }
                        }
                        if (fla == 1) {
                            FIELD[find_x][find_y] = 2
                            History.add(Triple(find_x,find_y,2))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("xog_with_computer", data_from_memory)
                            editor.apply()
                            cross_or_nul = "cross"
                        }

                        if (fla == 0) {
                            for (j in 5 downTo 0) {
                                for (i in 0..6) {
                                    if (FIELD[i][j] == 0) {
                                        FIELD[i][j] = 2
                                        History.add(Triple(i,j,2))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("xog_with_computer", data_from_memory)
                                        editor.apply()
                                        cross_or_nul = "cross"
                                        fla = 1
                                        break
                                    }
                                }
                                if (fla == 1)
                                    break
                            }
                        }

                        blocked = false
                        invalidate()
                    }, delayTime)
                }
            }
        }


        return true
    }
}
