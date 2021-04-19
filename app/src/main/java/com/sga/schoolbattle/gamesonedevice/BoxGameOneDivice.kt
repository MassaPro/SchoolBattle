package com.sga.schoolbattle.gamesonedevice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_one_device_games_template.*


class BoxGameOneDivice : AppCompatActivity() {
    fun encode(h: MutableList<MutableList<Int>>):String
    {
        var answer: String = ""
        for(i in 0 until h.size)
        {
            answer = answer + h[i][0] + 'a' + h[i][1] + 'a' + h[i][2] + 'a' + h[i][3] + 'a'
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
    fun decode(s : String) : MutableList<MutableList<Int>>
    {
        var answer: MutableList<MutableList<Int>> = mutableListOf()
        var i : Int = 0
        var a: Int = 0
        var b: Int = 0
        var c: Int = 0
        var d: Int = 0
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
            s1 = ""
            i++
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            d = string_to_int(s1)
            answer.add(mutableListOf(a,b,c,d))
            i++
        }
        return answer
    }

    private var dialog_parametrs: Show_parametr_one_divice_one_Device? = null
    private var dialog_rules: Show_rules? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_device_games_template)
        signature_canvas_box_one_device.visibility = (View.VISIBLE)
        signature_canvas_box_one_device.activity = this


        if(!PREMIUM)
        {
            mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())
        }
        signature_canvas_box_one_device.t1 = findViewById(R.id.name_player1_one_divice) as TextView
        signature_canvas_box_one_device.t2 = findViewById(R.id.name_player2_one_divice) as TextView


        bottom_navigation_one_divice.itemIconTintList = generateColorStateList()
        bottom_navigation_one_divice.itemTextColor = generateColorStateList()
        if(LANGUAGE == "English")
        {
           bottom_navigation_one_divice.menu.getItem(0).title = "Rules"
           bottom_navigation_one_divice.menu.getItem(1).title = "Settings"
           bottom_navigation_one_divice.menu.getItem(2).title = "Return"
           bottom_navigation_one_divice.menu.getItem(3).title = "Back"
        }
        
        CONTEXT = this

        mSound.load(this, R.raw.xlup, 1)
        mSound2.load(this, R.raw.win, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator


        when (Design) {
            "Normal" ->{
                name_player1_one_divice.setTextColor(Color.RED)
                name_player2_one_divice.setTextColor(Color.BLUE)
                button_player_1_one_divice.setBackgroundResource(R.drawable.box1_normal);
                button_player_2_one_divice.setBackgroundResource(R.drawable.box2_normal);
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_normal)
            }
            "Egypt" -> {
                name_player1_one_divice.setTextColor(Color.BLACK)
                name_player2_one_divice.setTextColor(Color.BLACK)
                name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_one_divice.setTextSize(20f)
                name_player1_one_divice.setTextSize(20f)
                button_player_1_one_divice.setBackgroundResource(R.drawable.player1_egypt);
                button_player_2_one_divice.setBackgroundResource(R.drawable.player2_egypt);
                player_1_icon_one_divice.setBackgroundResource(R.drawable.box2_egypt);
                player_2_icon_one_divice.setBackgroundResource(R.drawable.box1_egypt)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                label_one_device.setBackgroundResource(R.drawable.background_egypt);
                bottom_navigation_one_divice.setBackgroundColor(Color.rgb(255, 230, 163))
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_normal)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Casino" -> {
                name_player1_one_divice.setTextColor(Color.RED)
                name_player2_one_divice.setTextColor(Color.BLACK)
                name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_one_divice.setTextSize(20f)
                name_player1_one_divice.setTextSize(20f)
                button_player_1_one_divice.setBackgroundResource(R.drawable.tower1_casino);
                button_player_2_one_divice.setBackgroundResource(R.drawable.tower2_casino);
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                label_one_device.setBackgroundResource(R.drawable.background2_casino);
                bottom_navigation_one_divice.setBackgroundColor(argb(0,224, 164, 103))
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_casino)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Rome" -> {
                name_player1_one_divice.setTextColor(Color.BLACK)
                name_player2_one_divice.setTextColor(Color.rgb(193, 150, 63))
                name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_one_divice.setTextSize(20f)
                name_player1_one_divice.setTextSize(20f)
                button_player_1_one_divice.setBackgroundResource(R.drawable.box1_rome);
                button_player_2_one_divice.setBackgroundResource(R.drawable.box2_rome);
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                label_one_device.setBackgroundResource(R.drawable.background_rome);
                bottom_navigation_one_divice.setBackgroundColor(argb(0,224, 164, 103))
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_rome)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Gothic" -> {
                name_player1_one_divice.setTextColor(Color.WHITE)
                name_player2_one_divice.setTextColor(Color.WHITE)
                name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                name_player2_one_divice.setTextSize(20f)
                name_player1_one_divice.setTextSize(20f)
                button_player_1_one_divice.setBackgroundResource(R.drawable.box1_gothic);
                button_player_2_one_divice.setBackgroundResource(R.drawable.box2_gothic);
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                label_one_device.setBackgroundResource(R.drawable.background_gothic);
                bottom_navigation_one_divice.setBackgroundColor(argb(0,0,0,0))
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_gothic)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Japan" -> {
                name_player1_one_divice.setTextColor(Color.RED)
                name_player2_one_divice.setTextColor(rgb(37,103,28))
                name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                name_player2_one_divice.setTextSize(20f)
                name_player1_one_divice.setTextSize(20f)
                button_player_1_one_divice.setBackgroundResource(R.drawable.box1_japan);
                button_player_2_one_divice.setBackgroundResource(R.drawable.box2_japan);
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                label_one_device.setBackgroundResource(R.drawable.background_japan);
                bottom_navigation_one_divice.setBackgroundColor(argb(0,0,0,0))
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_normal)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            }
            "Noir" -> {
                name_player1_one_divice.setTextColor(Color.WHITE)
                name_player2_one_divice.setTextColor(Color.WHITE)
                name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                name_player2_one_divice.setTextSize(20f)
                name_player1_one_divice.setTextSize(20f)
                button_player_1_one_divice.setBackgroundResource(R.drawable.box1_noir);
                button_player_2_one_divice.setBackgroundResource(R.drawable.box2_noir);
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
                label_one_device.setBackgroundResource(R.drawable.background_noir);
                bottom_navigation_one_divice.setBackgroundColor(argb(0,0,0,0))
                to_back_one_divice.setBackgroundResource(R.drawable.back_arrow_gothic)
                toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            }
        }

        to_back_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
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

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("box_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_box_one_device.History = decode(prefs.getString("box_one_divice", "").toString())
        if(signature_canvas_box_one_device.History.size > 0)
        {
            signature_canvas_box_one_device.red_or_blue = "red"
            for(i in 0 until signature_canvas_box_one_device.VERTICAL_RIB.size)
            {
                for(j in 0 until signature_canvas_box_one_device.VERTICAL_RIB[0].size)
                {
                    signature_canvas_box_one_device.VERTICAL_RIB[i][j] = 0
                }
            }
            for(i in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB.size)
            {
                for(j in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB[0].size)
                {
                    signature_canvas_box_one_device.HORIZONTAL_RIB[i][j] = 0
                }
            }
            for(i in 0 until signature_canvas_box_one_device.FIELD.size)
            {
                for(j in 0 until signature_canvas_box_one_device.FIELD[0].size)
                {
                    signature_canvas_box_one_device.FIELD[i][j] = 0
                }
            }

            for(i in signature_canvas_box_one_device.History)
            {
                if(i[1] == 1)
                {
                    signature_canvas_box_one_device.VERTICAL_RIB[i[2]][i[3]] = i[0]
                }
                if(i[1] == 2)
                {
                    signature_canvas_box_one_device.HORIZONTAL_RIB[i[2]][i[3]] = i[0]
                }

                if(signature_canvas_box_one_device.red_or_blue == "red")
                {
                    signature_canvas_box_one_device.red_or_blue = "blue"
                }
                else
                {
                    signature_canvas_box_one_device.red_or_blue = "red"
                }
                var flag: Boolean = false
                for(i in 0..6)
                {
                    for(j in 0..6)
                    {
                        if(signature_canvas_box_one_device.VERTICAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j+1]>0 && signature_canvas_box_one_device.VERTICAL_RIB[i+1][j]>0 && signature_canvas_box_one_device.FIELD[i][j]==0) //если образовался квадратик
                        {
                            if(flag == false)
                            {
                                flag = true
                                if(signature_canvas_box_one_device.red_or_blue == "red")            //снова ходит тот же игрок
                                {
                                    signature_canvas_box_one_device.red_or_blue = "blue"
                                }
                                else
                                {
                                    signature_canvas_box_one_device.red_or_blue = "red"
                                }
                            }
                            if(signature_canvas_box_one_device.red_or_blue == "red")
                            {
                                signature_canvas_box_one_device.FIELD[i][j] = 1
                            }
                            else
                            {
                                signature_canvas_box_one_device.FIELD[i][j] = 2
                            }
                        }
                    }
                }
            }
            signature_canvas_box_one_device.invalidate()
        }


        bottom_navigation_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@BoxGameOneDivice
                        )
                    dialog_rules?.show("BoxGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_one_divice_one_Device(
                            this@BoxGameOneDivice
                        )
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, BoxGameOneDivice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if(signature_canvas_box_one_device.History.size > 0)
                    {
                        signature_canvas_box_one_device.History.removeLast()
                        var data_from_memory = encode(signature_canvas_box_one_device.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("box_one_divice", data_from_memory)
                        editor.apply()
                        signature_canvas_box_one_device.red_or_blue = "red"
                        for(i in 0 until signature_canvas_box_one_device.VERTICAL_RIB.size)
                        {
                            for(j in 0 until signature_canvas_box_one_device.VERTICAL_RIB[0].size)
                            {
                                signature_canvas_box_one_device.VERTICAL_RIB[i][j] = 0
                            }
                        }
                        for(i in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB.size)
                        {
                            for(j in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB[0].size)
                            {
                                signature_canvas_box_one_device.HORIZONTAL_RIB[i][j] = 0
                            }
                        }
                        for(i in 0 until signature_canvas_box_one_device.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_box_one_device.FIELD[0].size)
                            {
                                signature_canvas_box_one_device.FIELD[i][j] = 0
                            }
                        }

                        for(i in signature_canvas_box_one_device.History)
                        {
                            if(i[1] == 1)
                            {
                                signature_canvas_box_one_device.VERTICAL_RIB[i[2]][i[3]] = i[0]
                            }
                            if(i[1] == 2)
                            {
                                signature_canvas_box_one_device.HORIZONTAL_RIB[i[2]][i[3]] = i[0]
                            }

                            if(signature_canvas_box_one_device.red_or_blue == "red")
                            {
                                signature_canvas_box_one_device.red_or_blue = "blue"
                            }
                            else
                            {
                                signature_canvas_box_one_device.red_or_blue = "red"
                            }
                            var flag: Boolean = false
                            for(i in 0..6)
                            {
                                for(j in 0..6)
                                {
                                    if(signature_canvas_box_one_device.VERTICAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j+1]>0 && signature_canvas_box_one_device.VERTICAL_RIB[i+1][j]>0 && signature_canvas_box_one_device.FIELD[i][j]==0) //если образовался квадратик
                                    {
                                        if(flag == false)
                                        {
                                            flag = true
                                            if(signature_canvas_box_one_device.red_or_blue == "red")            //снова ходит тот же игрок
                                            {
                                                signature_canvas_box_one_device.red_or_blue = "blue"
                                            }
                                            else
                                            {
                                                signature_canvas_box_one_device.red_or_blue = "red"
                                            }
                                        }
                                        if(signature_canvas_box_one_device.red_or_blue == "red")
                                        {
                                            signature_canvas_box_one_device.FIELD[i][j] = 1
                                        }
                                        else
                                        {
                                            signature_canvas_box_one_device.FIELD[i][j] = 2
                                        }
                                    }
                                }
                            }
                        }
                        signature_canvas_box_one_device.invalidate()
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
        intent.putExtra("playType", 2)
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


class CanvasView_Boxs(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    fun encode(h: MutableList<MutableList<Int>>):String
    {
        var answer: String = ""
        for(i in 0 until h.size)
        {
            answer = answer + h[i][0] + 'a' + h[i][1] + 'a' + h[i][2] + 'a' + h[i][3] + 'a'
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
    fun decode(s : String) : MutableList<MutableList<Int>>
    {
        var answer: MutableList<MutableList<Int>> = mutableListOf()
        var i : Int = 0
        var a: Int = 0
        var b: Int = 0
        var c: Int = 0
        var d: Int = 0
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
            s1 = ""
            i++
            while(s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            d = string_to_int(s1)
            answer.add(mutableListOf(a,b,c,d))
            i++
        }
        return answer
    }
    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (width/2f/size_field_x.toFloat())*(width/2f/size_field_x.toFloat())/2f)
        {
            return true
        }
        return false
    }

    fun check_win() : Int
    {
        var cnt1: Int = 0
        var cnt2: Int = 0
        for(i in FIELD)
        {
            for(j in i)
            {
                if(j == 0)
                {
                    return 0
                }
                if(j == 1)
                {
                    cnt1++
                }
                else
                {
                    cnt2++
                }
            }
        }
        if(cnt1>cnt2)
        {
            return 1
        }
        else
        {
            if(cnt2>cnt1)
            {
                return 2
            }
            else
            {
                return 3
            }
        }
    }

    lateinit var t1: TextView
    lateinit var t2: TextView

    lateinit var activity: Activity

    var History: MutableList<MutableList<Int>> = mutableListOf()

    var lastx: Int = 0
    var lasty: Int = 0
    var red_or_blue: String
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var indent: Float = 0f      //оступ
    var paint_circle : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()

    var paint_rib_1: Paint = Paint()
    var paint_rib_2: Paint = Paint()
    var FIELD = Array(7){IntArray(7)}     //для фишеК
    var VERTICAL_RIB = Array(8){IntArray(7)}    //для ребер
    var HORIZONTAL_RIB = Array(7){IntArray(8)}

    var radius_of_point: Float = 0f
    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f


    var line_who_do_move : Paint = Paint()

    init{

        red_or_blue = "red"
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.BLACK)     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(7f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(7f)

        line_who_do_move.strokeWidth = 14f

        when (Design) {
            "Normal" -> {
                line_who_do_move.strokeWidth = 14f
                line_who_do_move.color = Color.GREEN
            }
            "Casino" -> {

                paint_circle.setColor(rgb(217,217,217))     //цвета для точек
                paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
                paint_rib_2.setColor(Color.BLACK)
                line_who_do_move.color = Color.YELLOW
            }
            "Egypt" -> {
                Line_paint.setColor(argb(0, 0,0,0))          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED
            }
            "Rome" -> {

                paint_circle.setColor(Color.BLACK)     //цвета для точек
                paint_rib_2.setColor(Color.rgb(193,150,63))          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.BLACK)
                line_who_do_move.color = Color.BLACK
            }
            "Gothic" -> {
                Line_paint.setColor(argb(0,0,0,0))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.WHITE)     //цвета для точек
                paint_rib_2.setColor(Color.BLUE)          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.RED)
                line_who_do_move.color = Color.WHITE
            }
            "Japan" -> {
                paint_circle.setColor(Color.BLACK)     //цвета для точек
                paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
                paint_rib_2.setColor(rgb(37,103,28))
                line_who_do_move.color = Color.RED
            }
            "Noir" -> {
                Line_paint.setColor(argb(0,0,0,0))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(rgb(100,100,100))     //цвета для точек
                paint_rib_2.setColor(rgb(193, 150, 63))          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.WHITE)
                line_who_do_move.color = Color.RED
            }
        }

        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..6) {
            for(j in 0 ..6) {
                FIELD[i][j] = 0  //не заполненный
            }
        }

        for(i in 0..7)
        {
            for(j in 0..6)
            {
                VERTICAL_RIB[i][j] = 0
                HORIZONTAL_RIB[j][i] = 0
            }
        }



    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_normal);       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_normal);

    var box1_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_egypt);
    var box2_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_egypt);

    var box1_casino : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_casino);
    var box2_casino : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_casino);

    var box1_rome : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_rome);
    var box2_rome : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_rome);

    var box1_gothic : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_gothic);
    var box2_gothic : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_gothic);

    var box1_japan : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_japan);
    var box2_japan : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_japan);

    var box1_noir : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_noir);
    var box2_noir : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_noir);

    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        when(LANGUAGE) {
            "Russian" -> {
                if(red_or_blue == "red")
                {
                    t1.text ="Игрок 1 думает..."
                    t2.text  = "Игрок 2"
                    canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)

                }
                else
                {
                    t1.text = "Игрок 1"
                    t2.text = "Игрок 2 думает..."
                    canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
                }
            }
            "English" -> {

                if(red_or_blue == "red")
                {
                    t1.text = "Player 1 thinks..."
                    t2.text = "Player 2"
                    canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)

                }
                else
                {
                    t1.text = "Player 1"
                    t2.text = "Player 2 thinks..."
                    canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
                }
            }
        }

        radius_of_point = 10f
        size_field_x  = 7
        size_field_y  = 7
        indent = (getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)


        step = width/size_field_x
        advertising_line = ( height - 7*step)/2         //полоска для рекламы
        k = height-width-advertising_line


        var right_red: Bitmap
        var right_blue: Bitmap


        right_red = Bitmap.createScaledBitmap(red,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        right_blue = Bitmap.createScaledBitmap(blue,width.toInt()/size_field_x, width.toInt()/size_field_x, true);

        when (Design) {
            "Egypt" -> {
                right_red = Bitmap.createScaledBitmap(box1_egypt,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
                right_blue = Bitmap.createScaledBitmap(box2_egypt,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            }
            "Casino" -> {
                right_red = Bitmap.createScaledBitmap(box1_casino,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
                right_blue = Bitmap.createScaledBitmap(box2_casino,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            }
            "Rome" -> {
                right_red = Bitmap.createScaledBitmap(box1_rome,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
                right_blue = Bitmap.createScaledBitmap(box2_rome,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            }
            "Gothic" -> {
                right_red = Bitmap.createScaledBitmap(box1_gothic,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
                right_blue = Bitmap.createScaledBitmap(box2_gothic,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            }
            "Japan" -> {
                right_red = Bitmap.createScaledBitmap(box1_japan,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
                right_blue = Bitmap.createScaledBitmap(box2_japan,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            }
            "Noir" -> {
                right_red = Bitmap.createScaledBitmap(box1_noir,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
                right_blue = Bitmap.createScaledBitmap(box2_noir,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            }
        }

        for(i in 0 until size_field_x+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            k += step
        }
        k = indent
        for(i in 0 until size_field_y+2)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k,height-advertising_line-width,k,height-advertising_line,Line_paint)
            k += step
        }

        var x: Float;
        var y: Float
        x = indent
        y = height - advertising_line - width
        for(i in 0..7)                    //вырисовка точек
        {
            for(j in 0..7)
            {
                canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                x += step
            }
            x = indent
            y += step
        }

        x = indent
        y = height - advertising_line - width
        for(i in 0..7)               //вырисовка вертикальных ребер
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j] == 1)
                {
                    canvas?.drawLine(x,y+radius_of_point,x,y+step-radius_of_point,paint_rib_1)
                }
                if(VERTICAL_RIB[i][j] == 2)
                {
                    canvas?.drawLine(x,y+radius_of_point,x,y+step-radius_of_point,paint_rib_2)
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }

        x = indent
        y = height - advertising_line - width
        for(i in 0..6)                 //вырисовка горизонтальных ребер
        {
            for(j in 0..7)
            {
                if(HORIZONTAL_RIB[i][j] == 1)
                {
                    canvas?.drawLine(x + radius_of_point,y,x+step - radius_of_point,y,paint_rib_1)
                }
                if(HORIZONTAL_RIB[i][j] == 2)
                {
                    canvas?.drawLine(x + radius_of_point,y,x+step - radius_of_point,y,paint_rib_2)
                }
                y += step
            }
            x += step
            y =  height - advertising_line - width
        }

        x = indent
        y = height - width - advertising_line
        for(i in 0..6)
        {
            for(j in 0..6)
            {
                if(FIELD[i][j] == 1)
                {
                    canvas?.drawBitmap(right_red, x ,y,paint_circle)
                }
                if(FIELD[i][j] == 2)
                {
                    canvas?.drawBitmap(right_blue, x ,y,paint_circle)
                }
                y += step
            }
            x += step
            y = height - width - advertising_line
        }




    }

    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        circlex = event!!.x
        circley = event!!.y
        if(check_win()<=0)
        {
            blocked = false
        }
        if(check_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
        }
        if(check_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked)
        {
            if(SOUND)
            {
                mSound2.play(1, 1F, 1F, 1, 0, 1F)
            }
            blocked = !blocked
            var dialog: Show_Result_one_Device? = null
            dialog = Show_Result_one_Device(activity)
            if(check_win()==1)
            {
                dialog?.showResult_one_device("Игрок 1 победил","BoxGame",activity)
            }
            if(check_win()==2)
            {
                dialog?.showResult_one_device("Игрок 2 победил","BoxGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_one_device("НИЧЬЯ","BoxGame",activity)
            }
        }
        x = indent               //проверка по вертикальным линиям
        y = height - advertising_line - width
        for(i in 0..7)
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j] == 0)
                {
                    if(red_or_blue == "red")
                    {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 1
                            History.add(mutableListOf(1,1,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "blue"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
                            invalidate()
                        }
                    }
                    else
                    {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 2
                            History.add(mutableListOf(2,1,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "red"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
                            invalidate()
                        }
                    }
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }
        x = 0f
        y = 0f

        x = indent               //проверка по горизонтальным линиям
        y = height - advertising_line - width
        for(i in 0..6)
        {
            for(j in 0..7)
            {
                if(HORIZONTAL_RIB[i][j] == 0)
                {
                    if(red_or_blue == "red")
                    {
                        if (correction_touch(x+step/2f,y))
                        {
                            HORIZONTAL_RIB[i][j] = 1
                            History.add(mutableListOf(1,2,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "blue"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
                            invalidate()
                        }
                    }
                    else
                    {
                        if (correction_touch(x+step/2f,y))
                        {
                            HORIZONTAL_RIB[i][j] = 2
                            History.add(mutableListOf(2,2,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "red"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
                            invalidate()
                        }
                    }
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }
        var flag: Boolean = false
        for(i in 0..6)
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j]>0 && HORIZONTAL_RIB[i][j]>0 && HORIZONTAL_RIB[i][j+1]>0 && VERTICAL_RIB[i+1][j]>0 && FIELD[i][j]==0) //если образовался квадратик
                {
                    if(flag == false)
                    {
                        flag = true
                        if(red_or_blue == "red")            //снова ходит тот же игрок
                        {
                            red_or_blue = "blue"
                        }
                        else
                        {
                            red_or_blue = "red"
                        }

                    }
                    if(red_or_blue == "red")
                    {
                        FIELD[i][j] = 1
                    }
                    else
                    {
                        FIELD[i][j] = 2
                    }
                    invalidate()

                }
            }
        }
        x = 0f
        y = 0f


        return true
    }

}
