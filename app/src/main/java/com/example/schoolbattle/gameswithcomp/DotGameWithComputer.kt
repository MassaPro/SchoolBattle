package com.example.schoolbattle.gameswithcomp

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
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*


var DotGameMode = 0

class DotGameWithComputer : AppCompatActivity() {


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
        signature_canvas_dots_with_computer.visibility = View.VISIBLE
        signature_canvas_dots_with_computer.activity = this
        CONTEXT = this

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator


        val prefs2 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        DotGameMode = prefs2.getInt("DotGameMode", 0)
        if (DotGameMode == 0) {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putInt("DotGameMode", 1)
            editor.apply()
            DotGameMode = 1
        }
        signature_canvas_dots_with_computer.blockedOnTouch = false
        val prefs_first = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_dots_with_computer.History = decode(prefs_first.getString("dot_with_computer", "").toString())
        if (DotGameMode == 2 && signature_canvas_dots_with_computer.History.size == 0) {
            signature_canvas_dots_with_computer.blockedOnTouch = true         // TODO check
        }

        signature_canvas_dots_with_computer.t1 = findViewById(R.id.name_player1_with_computer_template) as TextView
        signature_canvas_dots_with_computer.t2 = findViewById(R.id.name_player2_with_computer_template) as TextView

        if(Design == "Egypt" ) {
            name_player1_with_computer_template.setTextColor(Color.BLACK)
            name_player2_with_computer_template.setTextColor(Color.BLACK)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_template_with_computer.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_template_with_computer.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_template_with_computer.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_template_with_computer.setBackgroundResource(R.drawable.cross_egypt)
            label_with_computer.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_template_with_computer.setBackgroundColor(Color.rgb(224, 164, 103))
            to_back_template_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_template_with_computer.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_template_with_computer.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Casino" ) {
            name_player1_with_computer_template.setTextColor(Color.YELLOW)
            name_player2_with_computer_template.setTextColor(Color.YELLOW)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_template_with_computer.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_template_with_computer.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_template_with_computer.setBackgroundColor(argb(0,224, 164, 103))
            to_back_template_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Rome" ) {
            name_player1_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
            name_player2_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            //button_player_1_one_divice.setBackgroundResource(R.drawable.cross_rome);
            //button_player_2_one_divice.setBackgroundResource(R.drawable.null_rome);
            toolbar_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_rome);
            bottom_navigation_template_with_computer.setBackgroundColor(argb(0,224, 164, 103))
            to_back_template_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("dot_with_computer", "")
            editor.apply()
        }

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_dots_with_computer.History = decode(prefs.getString("dot_with_computer", "").toString())
        if (signature_canvas_dots_with_computer.History.size > 0) {
            signature_canvas_dots_with_computer.red_or_blue = 2
            for (i in 0 until signature_canvas_dots_with_computer.FIELD.size) {
                for (j in 0 until signature_canvas_dots_with_computer.FIELD[0].size) {
                    signature_canvas_dots_with_computer.FIELD[i][j] = 0
                }
            }
            signature_canvas_dots_with_computer.a.clear()
            for (i in 0 until 16) {
                signature_canvas_dots_with_computer.a.add(mutableListOf())
            }
            for (i in signature_canvas_dots_with_computer.a.indices) {
                for (j in 0 until 11) {
                    signature_canvas_dots_with_computer.a[i].add(0)
                }
            }
            for (i in signature_canvas_dots_with_computer.History) {
                signature_canvas_dots_with_computer.FIELD[i.first][i.second] = i.third
                signature_canvas_dots_with_computer.a[i.second][i.first] = i.third
                signature_canvas_dots_with_computer.find(
                    i.third,
                    signature_canvas_dots_with_computer.a,
                    16,
                    11
                )
                signature_canvas_dots_with_computer.red_or_blue =
                    2 - (signature_canvas_dots_with_computer.red_or_blue + 1) % 2
            }
            signature_canvas_dots_with_computer.invalidate()
        }
        //comback_dots_one_divice.setVisibility(View.GONE);


        //TODO first computer move

        if (DotGameMode == 2 && signature_canvas_dots_with_computer.History.size == 0)  {
            Log.d("QWWE", "hi")
            signature_canvas_dots_with_computer.blockedOnTouch = true

            val handler = android.os.Handler()
            handler.postDelayed({


                if (signature_canvas_dots_with_computer.red_or_blue == 1) {
                    signature_canvas_dots_with_computer.red_or_blue = 2
                    signature_canvas_dots_with_computer.FIELD[5][7] = 1
                    signature_canvas_dots_with_computer.a[7][5] = 1
                } else {
                    signature_canvas_dots_with_computer.red_or_blue = 1
                    signature_canvas_dots_with_computer.FIELD[5][7] = 2
                    signature_canvas_dots_with_computer.a[5][7] = 2
                }
                signature_canvas_dots_with_computer.History.add(Triple(5, 7, signature_canvas_dots_with_computer.FIELD[5][7]))
                var data_from_memory = encode(signature_canvas_dots_with_computer.History)
                val editor =
                    getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("dot_with_computer", data_from_memory)
                editor.apply()


                signature_canvas_dots_with_computer.blockedOnTouch = false
                signature_canvas_dots_with_computer.invalidate()
            }, delayTime)


        }



        bottom_navigation_template_with_computer.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@DotGameWithComputer
                        )
                    dialog_rules?.show("DotGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_with_computer(
                            this@DotGameWithComputer
                        )
                    dialog_parametrs?.showResult_with_computer(this, "DotGame")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, DotGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_dots_with_computer.History.size > 0) {
                        signature_canvas_dots_with_computer.History.removeLast()
                        var data_from_memory = encode(signature_canvas_dots_with_computer.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("dot_with_computer", data_from_memory)
                        editor.apply()
                        signature_canvas_dots_with_computer.red_or_blue = 2
                        for (i in 0 until signature_canvas_dots_with_computer.FIELD.size) {
                            for (j in 0 until signature_canvas_dots_with_computer.FIELD[0].size) {
                                signature_canvas_dots_with_computer.FIELD[i][j] = 0
                            }
                        }
                        signature_canvas_dots_with_computer.a.clear()
                        for (i in 0 until 16) {
                            signature_canvas_dots_with_computer.a.add(mutableListOf())
                        }
                        for (i in signature_canvas_dots_with_computer.a.indices) {
                            for (j in 0 until 11) {
                                signature_canvas_dots_with_computer.a[i].add(0)
                            }
                        }
                        for (i in signature_canvas_dots_with_computer.History) {
                            signature_canvas_dots_with_computer.FIELD[i.first][i.second] = i.third
                            signature_canvas_dots_with_computer.a[i.second][i.first] = i.third
                            signature_canvas_dots_with_computer.find(
                                i.third,
                                signature_canvas_dots_with_computer.a,
                                16,
                                11
                            )
                            signature_canvas_dots_with_computer.red_or_blue =
                                2 - (signature_canvas_dots_with_computer.red_or_blue + 1) % 2
                        }
                        signature_canvas_dots_with_computer.invalidate()
                    }
                    if (signature_canvas_dots_with_computer.History.size > 0 && signature_canvas_dots_with_computer.History[signature_canvas_dots_with_computer.History.size - 1].third != DotGameMode) {
                        signature_canvas_dots_with_computer.History.removeLast()
                        var data_from_memory = encode(signature_canvas_dots_with_computer.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("dot_with_computer", data_from_memory)
                        editor.apply()
                        signature_canvas_dots_with_computer.red_or_blue = 2
                        for (i in 0 until signature_canvas_dots_with_computer.FIELD.size) {
                            for (j in 0 until signature_canvas_dots_with_computer.FIELD[0].size) {
                                signature_canvas_dots_with_computer.FIELD[i][j] = 0
                            }
                        }
                        signature_canvas_dots_with_computer.a.clear()
                        for (i in 0 until 16) {
                            signature_canvas_dots_with_computer.a.add(mutableListOf())
                        }
                        for (i in signature_canvas_dots_with_computer.a.indices) {
                            for (j in 0 until 11) {
                                signature_canvas_dots_with_computer.a[i].add(0)
                            }
                        }
                        for (i in signature_canvas_dots_with_computer.History) {
                            signature_canvas_dots_with_computer.FIELD[i.first][i.second] = i.third
                            signature_canvas_dots_with_computer.a[i.second][i.first] = i.third
                            signature_canvas_dots_with_computer.find(
                                i.third,
                                signature_canvas_dots_with_computer.a,
                                16,
                                11
                            )
                            signature_canvas_dots_with_computer.red_or_blue =
                                2 - (signature_canvas_dots_with_computer.red_or_blue + 1) % 2
                        }
                        signature_canvas_dots_with_computer.invalidate()
                    }
                    if (DotGameMode == signature_canvas_dots_with_computer.red_or_blue) {
                        signature_canvas_dots_with_computer.blockedOnTouch = true
                    }
                }

            }
            true
        }

        to_back_template_with_computer.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 3)
            startActivity(intent)
        }

    }
}


class CanvasView_Dots_with_computer(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
        global_cnt1 = 0
        global_cnt2 = 0
        var cnt1 : Int = 0
        var cnt2 : Int = 0
        var ret0: Int = 0
        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[0].size)
            {
                if(a[j][i]==0)
                {
                    ret0 = 1
                }
                if(a[j][i]!= FIELD[i][j] && FIELD[i][j]!=0)
                {
                    if(a[j][i]==1)
                    {
                        cnt1++
                    }
                    else
                    {
                        cnt2++
                    }
                }
            }
        }
        global_cnt1 = cnt1
        global_cnt2 = cnt2
        if (ret0 == 1)
            return 0

        if(cnt1>cnt2)
        {
            return 1
        }
        else
        {
            if(cnt1 == cnt2 )
            {
                return 3
            }
            else
            {
                return 2
            }
        }
    }


    fun find(playerId: Int, g: MutableList<MutableList<Int>>,N: Int, M: Int): MutableList<Pair<Int, Int>> {
        val used: MutableList<MutableList<Boolean>> = mutableListOf()
        val res: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in 0 until N ){
            used.add(mutableListOf())
            for (j in 0 until M) {
                used[i].add(false)
            }
        }

        fun ch(i : Int, j: Int): Boolean {
            return (i in 0 until N) && (j in 0 until M) && (g[i][j] != playerId)
        }

        fun dfs(i : Int, j : Int) {
            used[i][j] = true
            for (x in -1 .. 1) {
                for (y in -1..1) {
                    if (ch(i + x, j + y) && !used[i + x][j + y]) {
                        if (x * y == 0 || g[i][j + y] != playerId || g[i + x][j] != playerId) {
                            dfs(i + x, j + y)
                        }
                    }
                }
            }
        }
        for (i in 0 until N) {
            if (ch(i, 0)) {
                dfs(i, 0)
            }
            if (ch(i, M - 1)) {
                dfs(i, M - 1)
            }
        }
        for (j in 0 until M) {
            if (ch(0, j)) {
                dfs(0, j)
            }
            if (ch(N - 1, j)) {
                dfs(N - 1, j)
            }
        }
        for (i in 0 until N) {
            for (j in 0 until M) {
                if (!used[i][j]) {
                    g[i][j] = playerId
                    res.add(Pair(i, j))
                }
            }
        }
        return res
    }



    var global_cnt1: Int = 0
    var global_cnt2: Int = 0

    lateinit var t1: TextView
    lateinit var t2: TextView

    lateinit var activity: Activity

    var blockedOnTouch: Boolean = false
    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var lastx: Int = -1
    var lasty: Int = -1
    var red_or_blue: Int
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var indent: Float = 0f      //оступ

    var paint_circle : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var paint_rib_1: Paint = Paint()
    var paint_rib_2: Paint = Paint()

    var shading_1 : Paint = Paint()
    var shading_2 : Paint = Paint()

    var FIELD = Array(11){IntArray(16)}     //для фишеК
    val a: MutableList<MutableList<Int>> = mutableListOf()     // для псевдо фишек
    var p: MutableList<Pair<Int,Int>> = mutableListOf()


    var radius_of_point: Float = 0f
    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f






    var Is_defined_TREE_OF_WAYS : Boolean = false
    init{

        for (i in 0 until 16) {
            a.add(mutableListOf())
        }
        for (i in a.indices) {
            for (j in 0 until 11) {
                a[i].add(0)
            }
        }
        red_or_blue = 0
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        paint_rib_1.setColor(Color.RED) //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(5f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(5f)

        shading_1.setColor(Color.RED)
        shading_2.setColor(Color.BLUE)
        shading_1.setStrokeWidth(2f)
        shading_2.setStrokeWidth(2f)


        if(Design == "Egypt") {
            Line_paint.setColor(Color.rgb(100, 100, 100))      //ресур для линий (ширина и цвет)
            paint_circle.setColor(Color.rgb(100, 100, 100))
            paint_rib_1.setColor(Color.BLACK) //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(5f)
            paint_rib_2.setColor(Color.WHITE)
            paint_rib_2.setStrokeWidth(5f)

            shading_1.setColor(Color.BLACK)
            shading_2.setColor(Color.WHITE)
            shading_1.setStrokeWidth(2f)
            shading_2.setStrokeWidth(2f)
        }
        else if(Design == "Casino") {
            paint_rib_1.setColor(Color.BLACK) //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(5f)
            paint_rib_2.setColor(Color.RED)
            paint_rib_2.setStrokeWidth(5f)

            shading_1.setColor(Color.BLACK)
            shading_2.setColor(Color.RED)
            shading_1.setStrokeWidth(2f)
            shading_2.setStrokeWidth(2f)
        }
        else if(Design == "Rome") {
            Line_paint.setColor(Color.rgb(180, 180, 180))      //ресур для линий (ширина и цвет)
            paint_circle.setColor(Color.rgb(180, 180, 180))
            paint_rib_1.setColor(Color.BLACK) //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(5f)
            paint_rib_2.setColor(Color.rgb(193,150,63))
            paint_rib_2.setStrokeWidth(5f)

            shading_1.setColor(Color.BLACK)
            shading_2.setColor(Color.rgb(193,150,63))
            shading_1.setStrokeWidth(2f)
            shading_2.setStrokeWidth(2f)
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



        check_win()
        if (DotGameMode == 1) {
            t1.text = "Игрок 1: $global_cnt1"         //TODO right check for win
            t2.text = "Игрок 2: $global_cnt2"
        } else {
            t1.text = "Игрок 1: $global_cnt2"
            t2.text = "Игрок 2: $global_cnt1"
        }


        radius_of_point = 8f
        size_field_x  = 10
        size_field_y  = 15
        indent = (getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line =(height - width/size_field_x*size_field_y)/2         //полоска для рекламы

        step = width/size_field_x
        k = height-width*(size_field_y.toFloat()/size_field_x.toFloat())-advertising_line

        if(Design == "Normal")
        {
            canvas?.drawColor(Color.WHITE)
        }
        else if(Design == "Egypt")
        {

        }
        else if(Design == "Casino")
        {

        }

        Log.d("Para",p.toString())

        for(i in 0 until size_field_y+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            k = k + step
        }

        k = indent
        for(i in 0 until size_field_x+1)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat()),k,height-advertising_line,Line_paint)
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
                if(FIELD[i][j] == 1)
                {
                    canvas?.drawCircle(x,y,radius_of_point*1.5f,paint_rib_1)
                }
                else
                {
                    if(FIELD[i][j] == 2)
                    {
                        canvas?.drawCircle(x,y,radius_of_point*1.5f,paint_rib_2)
                    }
                    else
                    {
                        canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                    }
                }

                y += step
            }
            x += step
            y  = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }


        p = find(1,a,16,11)
        for(i in 0..size_field_x)
        {
            for(j in 0..size_field_y)
            {
                if(Pair(j,i) in p)
                {
                    if(i-1>=0 && j-1>=0)
                    {
                        if(Pair(j,i-1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i - step/3*2
                            var X2: Float = indent + step*i - step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_1)
                            canvas?.drawLine(X2,Y,X,Y2,shading_1)
                        }
                    }
                    if(i+1<11 && j-1>=0)
                    {
                        if(Pair(j,i+1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i + step/6
                            var X2: Float = indent + step*i + step/3
                            var X3: Float = indent + step*i + step/2
                            var X4: Float = indent + step*i + step*2/3
                            var X5: Float = indent + step*i + step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j - step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j - step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_1)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_1)
                            canvas?.drawLine(X,Y,X3,Y3,shading_1)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_1)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_1)

                        }
                    }
                    if(i-1>=0 && j+1<16)
                    {
                        if(Pair(j,i-1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i - step/6
                            var X2: Float = indent + step*i - step/3
                            var X3: Float = indent + step*i - step/2
                            var X4: Float = indent + step*i - step*2/3
                            var X5: Float = indent + step*i - step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j + step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j + step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_1)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_1)
                            canvas?.drawLine(X,Y,X3,Y3,shading_1)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_1)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_1)

                        }
                    }
                    if(i+1<11 && j+1<16)
                    {
                        if(Pair(j,i+1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i + step/3*2
                            var X2: Float = indent + step*i + step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_1)
                            canvas?.drawLine(X2,Y,X,Y2,shading_1)

                        }
                    }
                }
            }
        }
        p = find(2,a,16,11)
        for(i in 0..size_field_x)
        {
            for(j in 0..size_field_y)
            {
                if(Pair(j,i) in p)
                {
                    if(i-1>=0 && j-1>=0)
                    {
                        if(Pair(j,i-1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i - step/3*2
                            var X2: Float = indent + step*i - step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_2)
                            canvas?.drawLine(X2,Y,X,Y2,shading_2)
                        }
                    }
                    if(i+1<11 && j-1>=0)
                    {
                        if(Pair(j,i+1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i + step/6
                            var X2: Float = indent + step*i + step/3
                            var X3: Float = indent + step*i + step/2
                            var X4: Float = indent + step*i + step*2/3
                            var X5: Float = indent + step*i + step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j - step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j - step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_2)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_2)
                            canvas?.drawLine(X,Y,X3,Y3,shading_2)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_2)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_2)

                        }
                    }
                    if(i-1>=0 && j+1<16)
                    {
                        if(Pair(j,i-1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i - step/6
                            var X2: Float = indent + step*i - step/3
                            var X3: Float = indent + step*i - step/2
                            var X4: Float = indent + step*i - step*2/3
                            var X5: Float = indent + step*i - step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j + step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j + step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_2)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_2)
                            canvas?.drawLine(X,Y,X3,Y3,shading_2)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_2)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_2)

                        }
                    }
                    if(i+1<11 && j+1<16)
                    {
                        if(Pair(j,i+1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i + step/3*2
                            var X2: Float = indent + step*i + step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_2)
                            canvas?.drawLine(X2,Y,X,Y2,shading_2)

                        }
                    }
                }
            }
        }



        for(i in 0..9)    //горизонтальные ребра
        {
            for(j in 0..15)
            {
                if(j==0)
                {
                    if(a[j][i]==a[j][i+1] && (a[j][i] == a[j+1][i] || a[j][i] == a[j+1][i+1]) && a[j][i] !=0 )
                    {
                        var X: Float = indent + i*step
                        var X1: Float = X+step
                        var Y: Float = height - advertising_line - step*size_field_y + step*j
                        var Y1 :Float =  Y + step
                        if(a[j][i]  == 1)
                        {
                            canvas?.drawLine(X,Y,X1,Y,paint_rib_1)
                        }
                        else
                        {
                            canvas?.drawLine(X,Y,X1,Y,paint_rib_2)
                        }
                    }
                }
                else
                {
                    if(j==15)
                    {
                        if(a[j][i]==a[j][i+1] && (a[j][i] == a[j-1][i] || a[j][i] == a[j-1][i+1]) && a[j][i] !=0)
                        {
                            var X: Float = indent + i*step
                            var X1: Float = X+step
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1 :Float =  Y + step
                            if(a[j][i]  == 1)
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_1)
                            }
                            else
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_2)
                            }
                        }
                    }
                    else
                    {
                        var k: Int = 0
                        if(a[j][i]==a[j][i+1] && (a[j][i] == a[j+1][i] || a[j][i] == a[j+1][i+1]) && a[j][i] !=0 )
                        {
                            k++
                        }
                        if(a[j][i]==a[j][i+1] && (a[j][i] == a[j-1][i] || a[j][i] == a[j-1][i+1])  && a[j][i] !=0)
                        {
                            k++
                        }
                        if(k==1)
                        {
                            var X: Float = indent + i*step
                            var X1: Float = X+step
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1 :Float =  Y + step
                            if(a[j][i]  == 1)
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_1)
                            }
                            else
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_2)
                            }
                        }
                    }
                }
            }
        }

        for(i in 0..10)     //вертикальные ребра
        {
            for(j in 0..14)
            {
                if(i == 0)
                {
                    if(a[j][i]==a[j+1][i] && (a[j][i+1]==a[j][i] || a[j+1][i+1]==a[j][i]) && a[j][i]!= 0 )
                    {
                        var X: Float = indent + i*step
                        var X1: Float = X+step
                        var Y: Float = height - advertising_line - step*size_field_y + step*j
                        var Y1 :Float =  Y + step
                        if(a[j][i]  == 1)
                        {
                            canvas?.drawLine(X,Y,X,Y1,paint_rib_1)
                        }
                        else
                        {
                            canvas?.drawLine(X,Y,X,Y1,paint_rib_2)
                        }
                    }
                }
                else
                {
                    if(i == 10)
                    {
                        if (a[j][i] == a[j + 1][i] && (a[j][i - 1] == a[j][i] || a[j + 1][i - 1] == a[j][i]) && a[j][i] != 0) {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a[j][i]  == 1) {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                            }
                        }
                    }
                    else
                    {
                        var k : Int = 0
                        if(a[j][i]==a[j+1][i] && (a[j][i+1]==a[j][i] || a[j+1][i+1]==a[j][i]) && a[j][i]!= 0 )
                        {
                            k++
                        }
                        if (a[j][i] == a[j + 1][i] && (a[j][i - 1] == a[j][i] || a[j + 1][i - 1] == a[j][i]) && a[j][i] != 0)
                        {
                            k++
                        }
                        if(k == 1)
                        {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a[j][i] == 1) {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                            }
                        }
                    }
                }
            }
        }

        for(i in 0..9)
        {
            for(j in 0..14)
            {
                if(a[j][i]!=0 && a[j][i]!=a[j+1][i+1] && a[j][i] == a[j][i+1] && a[j][i]==a[j+1][i])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j][i] == 1)
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_2)
                    }
                }
                if(a[j+1][i+1]!=0 && a[j][i]!=a[j+1][i+1] && a[j+1][i+1] == a[j][i+1] && a[j+1][i+1]==a[j+1][i])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j+1][i+1] == 1)
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_2)
                    }
                }

                if(a[j+1][i]!=0 && a[j+1][i]!=a[j][i+1] && a[j][i] == a[j+1][i] && a[j+1][i+1] == a[j+1][i])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j+1][i] == 1)
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
                    }
                }
                if(a[j][i+1]!=0 && a[j+1][i]!=a[j][i+1] && a[j][i] == a[j][i+1] && a[j+1][i+1] == a[j][i+1])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j][i+1] == 1)
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
                    }
                }
            }
        }



        if (red_or_blue == DotGameMode) {
            blockedOnTouch = true

            val handler = android.os.Handler()
            handler.postDelayed({

                var fla = false


                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            var X = i
                            var Y = j
                            if (FIELD[i][j] == 0 && a[j][i] == 0) {

                                check_win()
                                var save_cnt1 = global_cnt1
                                var save_cnt2 = global_cnt2
                                FIELD[i][j] = DotGameMode
                                a[j][i] = DotGameMode
                                check_win()
                                FIELD[i][j] = 0
                                a[j][i] = 0
                                Log.d("Qwww", "$save_cnt1 $global_cnt1 $save_cnt2 $global_cnt2")
                                if (save_cnt1 != global_cnt1 || save_cnt2 != global_cnt2) {
                                    if (red_or_blue == 1) {
                                        red_or_blue = 2
                                        FIELD[X][Y] = 1
                                        a[Y][X] = 1
                                    } else {
                                        red_or_blue = 1
                                        FIELD[X][Y] = 2
                                        a[Y][X] = 2
                                    }
                                    History.add(Triple(X, Y, FIELD[X][Y]))
                                    var data_from_memory = encode(History)
                                    val editor =
                                        context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("dot_with_computer", data_from_memory)
                                    editor.apply()

                                    fla = true
                                    break
                                }


                            }
                        }
                        if (fla)
                            break
                    }
                }

                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            var X = i
                            var Y = j
                            if (FIELD[i][j] == 0 && a[j][i] == 0) {

                                check_win()
                                var save_cnt1 = global_cnt1
                                var save_cnt2 = global_cnt2
                                FIELD[i][j] = 3 - DotGameMode
                                a[j][i] = 3 - DotGameMode
                                check_win()
                                FIELD[i][j] = 0
                                a[j][i] = 0
                                Log.d("Qwww", "$save_cnt1 $global_cnt1 $save_cnt2 $global_cnt2")
                                if (save_cnt1 != global_cnt1 || save_cnt2 != global_cnt2) {
                                    if (red_or_blue == 1) {
                                        red_or_blue = 2
                                        FIELD[X][Y] = 1
                                        a[Y][X] = 1
                                    } else {
                                        red_or_blue = 1
                                        FIELD[X][Y] = 2
                                        a[Y][X] = 2
                                    }
                                    History.add(Triple(X, Y, FIELD[X][Y]))
                                    var data_from_memory = encode(History)
                                    val editor =
                                        context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("dot_with_computer", data_from_memory)
                                    editor.apply()

                                    fla = true
                                    break
                                }


                            }
                        }
                        if (fla)
                            break
                    }
                }


                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            if (i + 2 <= size_field_x && j + 1 <= size_field_y && j > 0) {
                                var a1 = 0
                                if (FIELD[i][j] != 0)
                                    a1++
                                if (FIELD[i + 1][j + 1] != 0)
                                    a1++
                                if (FIELD[i + 1][j - 1] != 0)
                                    a1++
                                if (FIELD[i + 2][j] != 0)
                                    a1++

                                if (a1 == 3) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i + 1][j - 1] == 0) {
                                        X = i + 1
                                        Y = j - 1
                                    }
                                    if (FIELD[i + 1][j + 1] == 0) {
                                        X = i + 1
                                        Y = j + 1
                                    }
                                    if (FIELD[i + 2][j] == 0) {
                                        X = i + 2
                                        Y = j
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }
                        }
                        if (fla)
                            break
                    }
                }


                var varToCheck = red_or_blue

                if ((0..1).random() == 0)
                    varToCheck = 3 - varToCheck        //TODO time


                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            if (i + 1 <= size_field_x && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++
                                if (FIELD[i + 1][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }
                                    if (FIELD[i + 1][j + 1] == 0) {
                                        X = i + 1
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }

                            if (i > 0 && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++
                                if (FIELD[i - 1][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }
                                    if (FIELD[i - 1][j + 1] == 0) {
                                        X = i - 1
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }

                            if (i > 0 && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i - 1][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i - 1][j] == 0) {
                                        X = i - 1
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }

                            if (i + 1 <= size_field_x && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i + 1][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i + 1][j] == 0) {
                                        X = i + 1
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }
                        }
                        if (fla)
                            break
                    }
                }

                varToCheck = 3 - varToCheck

                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            if (i + 1 <= size_field_x && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++
                                if (FIELD[i + 1][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }
                                    if (FIELD[i + 1][j + 1] == 0) {
                                        X = i + 1
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }

                            if (i > 0 && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++
                                if (FIELD[i - 1][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }
                                    if (FIELD[i - 1][j + 1] == 0) {
                                        X = i - 1
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }

                            if (i > 0 && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i - 1][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i - 1][j] == 0) {
                                        X = i - 1
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }

                            if (i + 1 <= size_field_x && j + 1 <= size_field_y) {
                                var a1 = 0
                                if (FIELD[i][j] == varToCheck)
                                    a1++
                                if (FIELD[i + 1][j] == varToCheck)
                                    a1++
                                if (FIELD[i][j + 1] == varToCheck)
                                    a1++

                                if (a1 == 2) {
                                    var X = -1
                                    var Y = -1
                                    if (FIELD[i][j] == 0) {
                                        X = i
                                        Y = j
                                    }
                                    if (FIELD[i + 1][j] == 0) {
                                        X = i + 1
                                        Y = j
                                    }
                                    if (FIELD[i][j + 1] == 0) {
                                        X = i
                                        Y = j + 1
                                    }

                                    if (X != -1) {
                                        if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                            if (red_or_blue == 1) {
                                                red_or_blue = 2
                                                FIELD[X][Y] = 1
                                                a[Y][X] = 1
                                            } else {
                                                red_or_blue = 1
                                                FIELD[X][Y] = 2
                                                a[Y][X] = 2
                                            }
                                            History.add(Triple(X, Y, FIELD[X][Y]))
                                            var data_from_memory = encode(History)
                                            val editor =
                                                context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                            editor.putString("dot_with_computer", data_from_memory)
                                            editor.apply()

                                            fla = true
                                            break
                                        }
                                    }
                                }
                            }
                        }
                        if (fla)
                            break
                    }
                }


                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            val X = i
                            val Y = j

                            for (x_d in -1..1) {
                                for (y_d in -1..1) {
                                    if (i + x_d >= 0 && i + x_d <= size_field_x && j + y_d >= 0 && j + y_d <= size_field_y) {
                                        if (FIELD[i + x_d][j + y_d] != 0) {
                                            if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                                if (red_or_blue == 1) {
                                                    red_or_blue = 2
                                                    FIELD[X][Y] = 1
                                                    a[Y][X] = 1
                                                } else {
                                                    red_or_blue = 1
                                                    FIELD[X][Y] = 2
                                                    a[Y][X] = 2
                                                }
                                                History.add(Triple(X, Y, FIELD[X][Y]))
                                                var data_from_memory = encode(History)
                                                val editor =
                                                    context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                                editor.putString("dot_with_computer", data_from_memory)
                                                editor.apply()


                                                fla = true
                                                break
                                            }
                                        }
                                    }
                                }
                                if (fla)
                                    break
                            }
                            if (fla)
                                break

                        }
                        if (fla)
                            break
                    }
                }


                if (!fla) {
                    for (i in 0..size_field_x) {                   //вырисовка точек
                        for (j in 0..size_field_y) {
                            val X = i
                            val Y = j
                            if (FIELD[X][Y] == 0 && a[Y][X] == 0) {
                                if (red_or_blue == 1) {
                                    red_or_blue = 2
                                    FIELD[X][Y] = 1
                                    a[Y][X] = 1
                                } else {
                                    red_or_blue = 1
                                    FIELD[X][Y] = 2
                                    a[Y][X] = 2
                                }
                                History.add(Triple(X, Y, FIELD[X][Y]))
                                var data_from_memory = encode(History)
                                val editor =
                                    context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("dot_with_computer", data_from_memory)
                                editor.apply()


                                fla = true
                                break
                            }
                        }
                        if (fla)
                            break
                    }
                }


                blockedOnTouch = false
                invalidate()


            }, delayTime)
        }


        if(check_win() > 0)
        {       //TODO more check
            var dialog: Show_Result_with_Computer? = null
            dialog = Show_Result_with_Computer(activity)
            if(check_win()==1 && DotGameMode == 2)
            {
                dialog?.showResult_with_Computer("Победа","DotGame",activity)
            }
            if(check_win()==1 && DotGameMode == 1)
            {
                dialog?.showResult_with_Computer("Поражение","DotGame",activity)
            }
            if(check_win()==2 && DotGameMode == 2)
            {
                dialog?.showResult_with_Computer("Поражение","DotGame",activity)
            }
            if(check_win()==2 && DotGameMode == 1)
            {
                dialog?.showResult_with_Computer("Победа","DotGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_with_Computer("Ничья","DotGame",activity)
            }
        }

    }


    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Log.w("kolobok",check_win().toString())
        if(check_win()<=0)
        {
            blocked = false
        }
        if(check_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
        }
        if(check_win() >0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked)
        {       //TODO more check
            blocked = !blocked
            var dialog: Show_Result_with_Computer? = null
            dialog = Show_Result_with_Computer(activity)
            if(check_win()==1 && DotGameMode == 2)
            {
                dialog?.showResult_with_Computer("Победа","DotGame",activity)
            }
            if(check_win()==1 && DotGameMode == 1)
            {
                dialog?.showResult_with_Computer("Поражение","DotGame",activity)
            }
            if(check_win()==2 && DotGameMode == 2)
            {
                dialog?.showResult_with_Computer("Поражение","DotGame",activity)
            }
            if(check_win()==2 && DotGameMode == 1)
            {
                dialog?.showResult_with_Computer("Победа","DotGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_with_Computer("Ничья","DotGame",activity)
            }
            return true
        }

        if (blockedOnTouch) {
            return true
        }


        Is_defined_TREE_OF_WAYS = true
        circlex = event!!.x
        circley = event!!.y

        var x1: Float = indent
        var y1: Float = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        for(i in 0..size_field_x)                    //вырисовка точек
        {
            for(j in 0..size_field_y)
            {
                if(correction_touch(x1,y1))
                {
                    if(FIELD[i][j] == 0 && a[j][i] == 0)
                    {
                        if(red_or_blue == 1)
                        {
                            red_or_blue = 2
                            FIELD[i][j] = 1
                            a[j][i] = 1
                            p = find(1,a,16,11)
                        }
                        else
                        {
                            red_or_blue = 1
                            FIELD[i][j]  = 2
                            a[j][i]  = 2
                            p = find(2,a,16,11)
                        }
                        History.add(Triple(i,j,FIELD[i][j]))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("dot_with_computer", data_from_memory)
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
                y1+= step
            }
            x1  += step
            y1 = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }


        if(check_win() > 0)
        {       //TODO more check
            var dialog: Show_Result_with_Computer? = null
            dialog = Show_Result_with_Computer(activity)
            if(check_win()==1 && DotGameMode == 2)
            {
                dialog?.showResult_with_Computer("Победа","DotGame",activity)
            }
            if(check_win()==1 && DotGameMode == 1)
            {
                dialog?.showResult_with_Computer("Поражение","DotGame",activity)
            }
            if(check_win()==2 && DotGameMode == 2)
            {
                dialog?.showResult_with_Computer("Поражение","DotGame",activity)
            }
            if(check_win()==2 && DotGameMode == 1)
            {
                dialog?.showResult_with_Computer("Победа","DotGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_with_Computer("Ничья","DotGame",activity)
            }
            return true
        }


        return true
    }

}