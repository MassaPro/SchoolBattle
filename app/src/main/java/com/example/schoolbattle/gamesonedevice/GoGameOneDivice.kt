package com.example.schoolbattle.gamesonedevice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_one_device_games_template.*



class GoGameOneDivice : AppCompatActivity() {


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
        setContentView(R.layout.activity_one_device_games_template)
        signature_canvas_go_one_divice.visibility = View.VISIBLE
        signature_canvas_go_one_divice.activity = this

        if(Design == "Egypt")
        {
            name_player1_one_divice.setTextColor(Color.BLACK)
            name_player2_one_divice.setTextColor(Color.BLACK)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_one_divice.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_one_divice.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_one_divice.setBackgroundResource(R.drawable.circle_egypt)
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_egypt);
            toolbar_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
            bottom_navigation_one_divice.setBackgroundColor(Color.rgb(224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
        }
        else if(Design == "Casino" ) {
            name_player1_one_divice.setTextColor(Color.YELLOW)
            name_player2_one_divice.setTextColor(Color.YELLOW)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_one_divice.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_one_divice.setBackgroundColor(argb(0,224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Rome" ) {
            name_player1_one_divice.setTextColor(Color.rgb(193, 150, 63))
            name_player2_one_divice.setTextColor(Color.rgb(193, 150, 63))
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            //button_player_1_one_divice.setBackgroundResource(R.drawable.cross_rome);
            //button_player_2_one_divice.setBackgroundResource(R.drawable.null_rome);
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_rome);
            bottom_navigation_one_divice.setBackgroundColor(argb(0,224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("go_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_go_one_divice.History = decode(prefs.getString("go_one_divice", "").toString())
        if (signature_canvas_go_one_divice.History.size > 0) {
            signature_canvas_go_one_divice.red_or_blue = 2
            for (i in 0 until signature_canvas_go_one_divice.FIELD.size) {
                for (j in 0 until signature_canvas_go_one_divice.FIELD[0].size) {
                    signature_canvas_go_one_divice.FIELD[i][j] = 0
                }
            }
            signature_canvas_go_one_divice.a.clear()
            for (i in 0 until 16) {
                signature_canvas_go_one_divice.a.add(mutableListOf())
            }
            for (i in signature_canvas_go_one_divice.a.indices) {
                for (j in 0 until 11) {
                    signature_canvas_go_one_divice.a[i].add(0)
                }
            }
            for (i in signature_canvas_go_one_divice.History) {
                signature_canvas_go_one_divice.FIELD[i.first][i.second] = i.third
                signature_canvas_go_one_divice.a[i.second][i.first] = i.third
                signature_canvas_go_one_divice.find(
                    i.third,
                    signature_canvas_go_one_divice.a,
                    16,
                    11
                )
                signature_canvas_go_one_divice.red_or_blue =
                    2 - (signature_canvas_go_one_divice.red_or_blue + 1) % 2
            }
            for(i in 0 until  signature_canvas_go_one_divice.FIELD.size)
            {
                for(j in 0 until signature_canvas_go_one_divice.FIELD[0].size)
                {
                    if(signature_canvas_go_one_divice.FIELD[i][j]!=0 && signature_canvas_go_one_divice.FIELD[i][j]!=signature_canvas_go_one_divice.a[j][i])
                    {
                        signature_canvas_go_one_divice.FIELD[i][j] = 0
                    }
                }
            }
            signature_canvas_go_one_divice.invalidate()
        }
        //combacks_one_divice.setVisibility(View.GONE);
        bottom_navigation_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@GoGameOneDivice
                        )
                    dialog_rules?.show("GoGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_one_divice_one_Device(
                            this@GoGameOneDivice
                        )
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this,
                        GoGameOneDivice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_go_one_divice.History.size > 0) {
                        signature_canvas_go_one_divice.History.removeLast()
                        var data_from_memory = encode(signature_canvas_go_one_divice.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("go_one_divice", data_from_memory)
                        editor.apply()
                        signature_canvas_go_one_divice.red_or_blue = 2
                        for (i in 0 until signature_canvas_go_one_divice.FIELD.size) {
                            for (j in 0 until signature_canvas_go_one_divice.FIELD[0].size) {
                                signature_canvas_go_one_divice.FIELD[i][j] = 0
                            }
                        }
                        signature_canvas_go_one_divice.a.clear()
                        for (i in 0 until 16) {
                            signature_canvas_go_one_divice.a.add(mutableListOf())
                        }
                        for (i in signature_canvas_go_one_divice.a.indices) {
                            for (j in 0 until 11) {
                                signature_canvas_go_one_divice.a[i].add(0)
                            }
                        }
                        for (i in signature_canvas_go_one_divice.History) {
                            signature_canvas_go_one_divice.FIELD[i.first][i.second] = i.third
                            signature_canvas_go_one_divice.a[i.second][i.first] = i.third
                            signature_canvas_go_one_divice.find(
                                i.third,
                                signature_canvas_go_one_divice.a,
                                16,
                                11
                            )
                            signature_canvas_go_one_divice.red_or_blue =
                                2 - (signature_canvas_go_one_divice.red_or_blue + 1) % 2
                        }
                        for(i in 0 until  signature_canvas_go_one_divice.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_go_one_divice.FIELD[0].size)
                            {
                                if(signature_canvas_go_one_divice.FIELD[i][j]!=0 && signature_canvas_go_one_divice.FIELD[i][j]!=signature_canvas_go_one_divice.a[j][i])
                                {
                                    signature_canvas_go_one_divice.FIELD[i][j] = 0
                                }
                            }
                        }
                        signature_canvas_go_one_divice.invalidate()
                    }
                }

            }
            true
        }

        to_back_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
            startActivity(intent)
        }

    }
}


class CanvasView_Go_one_divice(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
        var cnt1 : Int = 0
        var cnt2 : Int = 0
        var flag = true
        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[0].size)
            {
                if(FIELD[i][j] == 0 && a[j][i]==0)
                {
                    return 0
                }
                if(a[j][i] == 1)
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

    lateinit var activity: Activity

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


        radius_of_point = 14f
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
        else
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
                }

                y += step
            }
            x += step
            y  = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }


        p = find(1,a,16,11)
        p = find(2,a,16,11)




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
        {
            blocked = !blocked
            var dialog: Show_Result_one_Device? = null
            dialog = Show_Result_one_Device(activity)
            if(check_win()==1)
            {
                dialog?.showResult_one_device("Игрок 1 победил","GoGame",activity)
            }
            if(check_win()==2)
            {
                dialog?.showResult_one_device("Игрок 2 победил","GoGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_one_device("НИЧЬЯ","GoGame",activity)
            }
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
                    if(FIELD[i][j] == 0 && (a[j][i] == 0 || (a[j][i]==red_or_blue)) )
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
                        editor.putString("go_one_divice", data_from_memory)
                        editor.apply()

                        for(i in 0 until  FIELD.size)
                        {
                            for(j in 0 until FIELD[0].size)
                            {
                                if(FIELD[i][j]!=0 && FIELD[i][j]!=a[j][i])
                                {
                                    FIELD[i][j] = 0
                                }
                            }
                        }
                        invalidate()


                    }
                }
                y1+= step
            }
            x1  += step
            y1 = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }

        return true
    }

}