package com.example.schoolbattle.gamesonedevice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_one_device_games_template.*
import java.lang.Math.abs

class SnakeGameOneDivice : AppCompatActivity() {

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

    private var dialog_parametrs: Show_parametr_one_divice_one_Device? = null
    private var dialog_rules: Show_rules? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())

        setContentView(R.layout.activity_one_device_games_template)
        signature_canvas_snake_one_device.visibility = View.VISIBLE
        CONTEXT = this

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        signature_canvas_snake_one_device.activity = this


        if(Design == "Egypt" ) {

            name_player1_one_divice.setTextColor(Color.BLACK)
            name_player2_one_divice.setTextColor(Color.BLACK)
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_one_divice.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_one_divice.setBackgroundResource(R.drawable.chip1_egypt);
            player_2_icon_one_divice.setBackgroundResource(R.drawable.chip2_egypt)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))

            label_one_device.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_one_divice.setBackgroundColor(Color.rgb(224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Casino" ) {
            name_player1_one_divice.setTextColor(Color.YELLOW)
            name_player2_one_divice.setTextColor(Color.YELLOW)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.chip2_casino);
            button_player_2_one_divice.setBackgroundResource(R.drawable.chip1_casino);
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_one_divice.setBackgroundColor(Color.argb(0, 224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Rome" ) {
            name_player1_one_divice.setTextColor(Color.rgb(193, 150, 63))
            name_player2_one_divice.setTextColor(Color.rgb(193, 150, 63))
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.chip1_rome);
            button_player_2_one_divice.setBackgroundResource(R.drawable.chip2_rome);
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_rome);
            bottom_navigation_one_divice.setBackgroundColor(Color.argb(0, 224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Gothic" ) {
            name_player1_one_divice.setTextColor(Color.WHITE)
            name_player2_one_divice.setTextColor(Color.YELLOW)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            //button_player_1_one_divice.setBackgroundResource(R.drawable.cross_gothic);
            //button_player_2_one_divice.setBackgroundResource(R.drawable.null_gothic);
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_gothic);
            bottom_navigation_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Japan" ) {
            name_player1_one_divice.setTextColor(Color.BLACK)
            name_player2_one_divice.setTextColor(Color.BLACK)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            //button_player_1_one_divice.setBackgroundResource(R.drawable.chip1_japan);
            //button_player_2_one_divice.setBackgroundResource(R.drawable.chip2_japan);
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_japan);
            bottom_navigation_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Noir" ) {
            name_player1_one_divice.setTextColor(Color.WHITE)
            name_player2_one_divice.setTextColor(Color.RED)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            //button_player_1_one_divice.setBackgroundResource(R.drawable.cross_gothic);
            //button_player_2_one_divice.setBackgroundResource(R.drawable.null_gothic);
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_noir);
            bottom_navigation_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("snake_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_snake_one_device.History = decode(prefs.getString("snake_one_divice", "").toString())
        if (signature_canvas_snake_one_device.History.size > 0) {
            signature_canvas_snake_one_device.red_or_blue = "red"
            signature_canvas_snake_one_device.Snake_1.clear()
            signature_canvas_snake_one_device.Snake_2.clear()
            if (signature_canvas_snake_one_device.History.size > 0) {
                for (i in 0 until signature_canvas_snake_one_device.FIELD.size) {
                    for (j in 0 until signature_canvas_snake_one_device.FIELD[0].size) {
                        signature_canvas_snake_one_device.FIELD[i][j] = 0
                    }
                }
                for (i in signature_canvas_snake_one_device.History) {
                    signature_canvas_snake_one_device.FIELD[i.first][i.second] = i.third
                    if (signature_canvas_snake_one_device.red_or_blue == "red") {
                        signature_canvas_snake_one_device.Snake_1.add(Pair(i.first, i.second))
                        signature_canvas_snake_one_device.red_or_blue = "blue"
                    } else {
                        signature_canvas_snake_one_device.Snake_2.add(Pair(i.first, i.second))
                        signature_canvas_snake_one_device.red_or_blue = "red"
                    }

                }
            }
            signature_canvas_snake_one_device.invalidate()
        }

        bottom_navigation_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@SnakeGameOneDivice
                        )
                    dialog_rules?.show("SnakeGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_one_divice_one_Device(
                            this@SnakeGameOneDivice
                        )
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, SnakeGameOneDivice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_snake_one_device.History.size > 0) {
                        signature_canvas_snake_one_device.History.removeLast()
                        var data_from_memory = encode( signature_canvas_snake_one_device.History)
                        val editor =getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("snake_one_divice", data_from_memory)
                        editor.apply()
                        signature_canvas_snake_one_device.red_or_blue = "red"
                        signature_canvas_snake_one_device.Snake_1.clear()
                        signature_canvas_snake_one_device.Snake_2.clear()
                        if (signature_canvas_snake_one_device.History.size > 0) {
                            for (i in 0 until signature_canvas_snake_one_device.FIELD.size) {
                                for (j in 0 until signature_canvas_snake_one_device.FIELD[0].size) {
                                    signature_canvas_snake_one_device.FIELD[i][j] = 0
                                }
                            }
                            for (i in signature_canvas_snake_one_device.History) {
                                signature_canvas_snake_one_device.FIELD[i.first][i.second] = i.third
                                if (signature_canvas_snake_one_device.red_or_blue == "red") {
                                    signature_canvas_snake_one_device.Snake_1.add(Pair(i.first, i.second))
                                    signature_canvas_snake_one_device.red_or_blue = "blue"
                                } else {
                                    signature_canvas_snake_one_device.Snake_2.add(Pair(i.first, i.second))
                                    signature_canvas_snake_one_device.red_or_blue = "red"
                                }

                            }
                        }
                        signature_canvas_snake_one_device.invalidate()
                    }
                }

            }
            true
        }

        to_back_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
            if(mInterstitialAd_in_offline_games.isLoaded)
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
        intent.putExtra("playType", 2)
        if(mInterstitialAd_in_offline_games.isLoaded)
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

class CanvasView_SNAKE(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (step/2f)*(step/2f))
        {
            return true
        }
        return false
    }

    fun check_win() : Int
    {
        if(red_or_blue == "red" && Snake_1.size > 0)
        {
            var X : Int = Snake_1.last().first
            var Y: Int = Snake_1.last().second
            if(X == 0)
            {
                if(FIELD[10][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 0)
            {
                if(FIELD[X][10] == 0)
                {
                    return 0
                }
            }
            if(X == 10)
            {
                if(FIELD[0][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 10)
            {
                if(FIELD[X][0] == 0)
                {
                    return 0
                }
            }
            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<10)
            {
                if(FIELD[X+1][Y] == 0)
                {
                    return 0
                }
            }
            if(Y>0)
            {
                if(FIELD[X][Y-1] == 0)
                {
                    return 0
                }
            }
            if(Y<10)
            {
                if(FIELD[X][Y+1] == 0)
                {
                    return 0
                }
            }
            return 2
        }
        if(red_or_blue == "blue"  && Snake_2.size > 0)
        {
            var X : Int = Snake_2.last().first
            var Y: Int = Snake_2.last().second

            if(X == 0)
            {
                if(FIELD[10][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 0)
            {
                if(FIELD[X][10] == 0)
                {
                    return 0
                }
            }
            if(X == 10)
            {
                if(FIELD[0][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 10)
            {
                if(FIELD[X][0] == 0)
                {
                    return 0
                }
            }
            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<10)
            {
                if(FIELD[X+1][Y] == 0)
                {
                    return 0
                }
            }
            if(Y>0)
            {
                if(FIELD[X][Y-1] == 0)
                {
                    return 0
                }
            }
            if(Y<10)
            {
                if(FIELD[X][Y+1] == 0)
                {
                    return 0
                }
            }
            return 1
        }
        return -1
    }

    lateinit var activity: Activity

    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var Snake_1: MutableList<Pair<Int,Int>>  =  mutableListOf()
    var Snake_2: MutableList<Pair<Int,Int>>  =  mutableListOf()         //векторы координат путей
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
    var border_1: Paint = Paint()
    var border_2: Paint = Paint()

    var FIELD = Array(11){IntArray(11)}     //для фишеК
    var A: MutableList<Pair<Int,Int>> = mutableListOf()
    var TREE_OF_WAYS: MutableList<MutableList<Pair<Int,Int>>> = mutableListOf()
    var CELLS = Array(10){Array(15){IntArray(6)} }            //массив клеток в которых мы будем проводить ребра


    var radius_of_point: Float = 0f
    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f







    init{

        red_or_blue = "red"
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(10f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(10f)

        border_1.setColor(Color.GRAY)
        border_1.setStrokeWidth(10f)
        border_2.setColor(Color.GRAY)
        border_2.setStrokeWidth(20f)

        if(Design == "Egypt"){

            Line_paint.setColor(Color.rgb(100, 100, 100))          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)

            paint_circle.setColor(Color.rgb(100, 100, 100))     //цвета для точек

            paint_rib_1.setColor(Color.BLACK)          //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(10f)
            paint_rib_2.setColor(Color.WHITE)
            paint_rib_2.setStrokeWidth(10f)

            border_1.setColor(Color.rgb(100, 100, 100))
            border_1.setStrokeWidth(10f)
        }
        else if(Design == "Casino"){

            Line_paint.setColor(Color.WHITE)          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)

            paint_circle.setColor(Color.WHITE)     //цвета для точек

            paint_rib_1.setColor(Color.BLACK)          //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(10f)
            paint_rib_2.setColor(Color.RED)
            paint_rib_2.setStrokeWidth(10f)

            border_1.setColor(Color.WHITE)
            border_1.setStrokeWidth(10f)
        }

        else if(Design == "Rome"){

            Line_paint.setColor(Color.rgb(180, 180, 180))          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)

            paint_circle.setColor(Color.rgb(180, 180, 180))     //цвета для точек

            paint_rib_2.setColor(Color.BLACK)          //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(10f)
            paint_rib_1.setColor(Color.rgb(193,150,63))
            paint_rib_2.setStrokeWidth(10f)

            border_1.setColor(Color.rgb(180, 180, 180))
            border_1.setStrokeWidth(10f)
        }
        else if(Design == "Gothic") {

            Line_paint.setColor(Color.rgb(100, 100, 100))          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)

            paint_circle.setColor(Color.rgb(180, 180, 180))     //цвета для точек

            paint_rib_2.setColor(Color.WHITE)          //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(10f)
            paint_rib_1.setColor(Color.YELLOW)
            paint_rib_2.setStrokeWidth(10f)

        }
        else if(Design == "Japan") {

            Line_paint.setColor(Color.rgb(160,160,160))          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)

            paint_circle.setColor(Color.rgb(160,160,160))     //цвета для точек

            paint_rib_2.setColor(Color.RED)          //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(10f)
            paint_rib_1.setColor(Color.rgb(37,103,28))
            paint_rib_2.setStrokeWidth(10f)

        }
        else if(Design == "Noir") {

            Line_paint.setColor(Color.rgb(100, 100, 100))          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)

            paint_circle.setColor(Color.rgb(180, 180, 180))     //цвета для точек

            paint_rib_2.setColor(Color.RED)          //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(10f)
            paint_rib_1.setColor(Color.WHITE)
            paint_rib_2.setStrokeWidth(10f)

        }

        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[i].size)
            {
                FIELD[i][j] = 0
            }
        }

    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.red
    );       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.blue
    );



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        radius_of_point = 8f
        size_field_x  = 10
        size_field_y  = 10
        indent = 20f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        step = width/size_field_x
        advertising_line =  (height - 10*step)/2          //полоска для рекламы


        k = height-width*(size_field_y.toFloat()/size_field_x.toFloat())-advertising_line

       // canvas?.drawColor(Color.WHITE)

        for(i in 0 until size_field_y+1)          //вырисовка горизонтальных линий
        {

            canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            k = k + step
        }

        k = indent
        for(i in 0 until size_field_x+1)         //вырисовка вертикальных линий
        {

            canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())+ 5f,k,height-advertising_line-5f,Line_paint)
            k = k + step
        }

        var x: Float;
        var y: Float
        x = indent
        y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        for(i in 0..size_field_x)                    //вырисовка точек
        {
            for(j in 0..size_field_y)
            {
                canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                y += step
            }
            x += step
            y  = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }

        for(i in 0 until Snake_1.size - 1)     //вырисовка ребер змеи
        {
            var X: Float = indent + step*Snake_1[i].first
            var Y: Float =  height - advertising_line - width + step*Snake_1[i].second
            var X1: Float = indent + step*Snake_1[i+1].first
            var Y1: Float =  height - advertising_line - width + step*Snake_1[i+1].second
            if(abs(Snake_1[i].first - Snake_1[i+1].first) + abs(Snake_1[i].second - Snake_1[i+1].second) <= 2)
            {
               canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
            }

            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first < Snake_1[i+1].first )
            {
                if(abs(Snake_1[i].first - Snake_1[i+1].first) + abs(Snake_1[i].second - Snake_1[i+1].second) <= 2)
                {
                    canvas?.drawLine(X - 5, Y, X1 + 5, Y1, paint_rib_1)
                }
            }
            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first > Snake_1[i+1].first )
            {
                if(abs(Snake_1[i].first - Snake_1[i+1].first) + abs(Snake_1[i].second - Snake_1[i+1].second) <= 2)
                {
                    canvas?.drawLine(X+5,Y,X1-5,Y1,paint_rib_1)
                }
            }
        }
        for(i in 0 until Snake_2.size - 1)      //вырисовка ребер змеи
        {
            var X: Float = indent + step*Snake_2[i].first
            var Y: Float =  height - advertising_line - width + step*Snake_2[i].second
            var X1: Float = indent + step*Snake_2[i+1].first
            var Y1: Float =  height - advertising_line - width + step*Snake_2[i+1].second
            if(abs(Snake_2[i].first - Snake_2[i+1].first) + abs(Snake_2[i].second - Snake_2[i+1].second) <= 2)
            {
                canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first < Snake_2[i+1].first )
            {
                if(abs(Snake_2[i].first - Snake_2[i+1].first) + abs(Snake_2[i].second - Snake_2[i+1].second) <= 2)
                {
                    canvas?.drawLine(X - 5, Y, X1 + 5, Y1, paint_rib_2)
                }
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first > Snake_2[i+1].first )
            {
                if(abs(Snake_2[i].first - Snake_2[i+1].first) + abs(Snake_2[i].second - Snake_2[i+1].second) <= 2)
                {
                    canvas?.drawLine(X+5,Y,X1-5,Y1,paint_rib_2)
                }
            }
        }

        if(Snake_1.size>0)
        {
            var X: Float = indent + step * Snake_1[0].first  - step / 5f
            var X1: Float = indent + step * Snake_1[0].first  + step / 5f
            var Y: Float =
                height - advertising_line - width + step * Snake_1[0].second  - step / 5f
            var Y1: Float =
                height - advertising_line - width + step * Snake_1[0].second  + step / 5f
            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
        }

        if(Snake_2.size>0)
        {
            var _X: Float = indent + step * Snake_2[0].first - step / 5f
            var _X1: Float = indent + step * Snake_2[0].first + step / 5f
            var _Y: Float =
                height - advertising_line - width + step * Snake_2[0].second  - step / 5f
            var _Y1: Float =
                height - advertising_line - width + step * Snake_2[0].second  + step / 5f
            canvas?.drawLine(_X, _Y, _X1, _Y1, paint_rib_2)
            canvas?.drawLine(_X, _Y1, _X1, _Y, paint_rib_2)
        }

        if(Snake_1.size > 1)
        {
            var X: Float = indent + step*Snake_1.last().first
            var Y: Float = height - width - advertising_line + step*Snake_1.last().second
            canvas?.drawCircle(X,Y,radius_of_point*2,paint_rib_1)
        }
        if(Snake_2.size > 1)
        {
            var X: Float = indent + step*Snake_2.last().first
            var Y: Float = height - width - advertising_line + step*Snake_2.last().second
            canvas?.drawCircle(X,Y,radius_of_point*2,paint_rib_2)
        }



    }

    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        indent = 20f //оступ, чтобы можно было тыкнуть в границу
        advertising_line =  (height - 10*step)/2
        if(check_win()<=0)
        {
            blocked = false
        }
        if(check_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
        }
        if(check_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked) {
            blocked = !blocked
            var dialog: Show_Result_one_Device? = null
            dialog = Show_Result_one_Device(activity)
            if(check_win() == 1)
            {
                dialog?.showResult_one_device("Игрок 1 победил","SnakeGame",activity)
                return true
            }
            if(check_win() == 2)
            {
                dialog?.showResult_one_device("Игрок 2 победил","SnakeGame",activity)
                return true
            }
        }
        circlex = event!!.x
        circley = event!!.y

        x = indent
        y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        for(i in 0..size_field_x)
        {
            for(j in 0..size_field_y)
            {
                if(correction_touch(x,y))
                {
                    if(FIELD[i][j] == 0)
                    {
                        if(red_or_blue == "red")
                        {
                            if(Snake_1.size == 0)
                            {
                                Snake_1.add(Pair(i,j))
                                FIELD[i][j] = 1
                                red_or_blue = "blue"
                                History.add(Triple(i,j,FIELD[i][j]))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("snake_one_divice", data_from_memory)
                                editor.apply()
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
                            else
                            {
                                if((i == Snake_1.last().first && abs(j -Snake_1.last().second)%9 == 1) || (j == Snake_1.last().second && abs(i -Snake_1.last().first)%9 == 1))
                                {
                                    Snake_1.add(Pair(i,j))
                                    FIELD[i][j] = 1
                                    red_or_blue = "blue"
                                    History.add(Triple(i,j,FIELD[i][j]))
                                    var data_from_memory = encode(History)
                                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("snake_one_divice", data_from_memory)
                                    editor.apply()
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
                        else
                        {
                            if(Snake_2.size == 0)
                            {
                                Snake_2.add(Pair(i,j))
                                FIELD[i][j] = 2
                                red_or_blue = "red"
                                History.add(Triple(i,j,FIELD[i][j]))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("snake_one_divice", data_from_memory)
                                editor.apply()

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
                            else
                            {
                                if((i == Snake_2.last().first && abs(j -Snake_2.last().second)%9 == 1) || (j == Snake_2.last().second && abs(i -Snake_2.last().first)%9 == 1))
                                {
                                    Snake_2.add(Pair(i,j))
                                    FIELD[i][j] = 2
                                    red_or_blue = "red"
                                    History.add(Triple(i,j,FIELD[i][j]))
                                    var data_from_memory = encode(History)
                                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("snake_one_divice", data_from_memory)
                                    editor.apply()

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
                    }
                }
                y += step
            }
            x  += step
            y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }
        x = 0f
        y = 0f
        return true
    }

}
