package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.schoolbattle.*
import com.example.schoolbattle.engine.BlitzGameEngine
import com.example.schoolbattle.engine.LongGameEngine
import com.example.schoolbattle.engine.ShowResult
import com.example.schoolbattle.engine.StupidGame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import kotlinx.android.synthetic.main.activity_snake_game.*
import kotlinx.android.synthetic.main.activity_snake_game.signature_canvas_snake_online
import java.util.*

class SnakeGameActivity : AppCompatActivity() {
    private var isRun = false
    private var engine: BlitzGameEngine? = null
    var yourName = ""
    var opponentsName = ""
    var type = ""
    lateinit var gameData: DatabaseReference
    private var engineLong: LongGameEngine? = null

    override fun onResume() {
        super.onResume()
        currentContext = this
        isRun = true
        CONTEXT = this
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_online_games_temlate)
        signature_canvas_snake_online.visibility = View.VISIBLE

        currentContext = this
        CONTEXT = this
        isRun = true

        if (StupidGame != Activity()) StupidGame.finish()
        if (NewGame != Activity()) NewGame.finish()
        yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()
        val opponentsName_: String = intent?.getStringExtra("opponent").toString()
        type = intent.getStringExtra("type")!!
        opponentsName = ""
        for (i in opponentsName_) {
            if (i == ' ') break
            opponentsName += i
        }
        val yu = if (opponentsName < yourName) '1' else '0'
        val op = if (opponentsName < yourName) '0' else '1'
//        players.text = yourName + " VS " + opponentsName
        //      youName.text = yourName
        //    opponentName.text = opponentsName

        gameData = if (intent.getStringExtra("key") != null) {
            myRef.child(type).child("SnakeGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName + intent.getStringExtra("key")!!  else yourName + '_' + opponentsName + intent.getStringExtra("key")!!)
            )
        } else {
            myRef.child(type).child("SnakeGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName  else yourName + '_' + opponentsName)
            )
        }
        signature_canvas_snake_online.isFirstMove = intent.getStringExtra("move") == "1"
        button_player_1_online_xog.text = yourName
        button_player_2_online_xog.text = opponentsName
        signature_canvas_snake_online.username = yourName
        signature_canvas_snake_online.blocked = true
        signature_canvas_snake_online.positionData = gameData

        if (type == "blitz") {
            engine = object : BlitzGameEngine {
                override var timer = Timer(true)
                override var cntUser = 0
                override var cntOpponent = 0
                override val userT = timer2_xog_online
                override val opponentT = timer_xog_online
                override val user = yourName
                override val opponent = opponentsName
                override var move = intent.getStringExtra("move") == "1"
                override var positionData = gameData
                override var activity: Activity = this@SnakeGameActivity
                override var cnt = 0
                override var type = "SnakeGame"
                override var isFinished = false
            }
            engine?.init()
            signature_canvas_snake_online.engine = engine
        } else {
            engineLong = object : LongGameEngine {
                override val userT = timer2_xog_online
                override val opponentT = timer_xog_online
                override val user = yourName
                override val opponent = opponentsName
                override var move = intent.getStringExtra("move") == "1"
                override var positionData = gameData
                override var activity: Activity = this@SnakeGameActivity
                override var type = "XOGame"
                override var key = intent.getStringExtra("key")
            }
            Toast.makeText(this, engineLong?.key.toString(), Toast.LENGTH_LONG).show()
            engineLong?.init()
        }
        signature_canvas_snake_online.username = yourName
        //button_player_1_online_snake.text = yourName
        //button_player_2_online_snake.text = opponentsName
        if(Design == "Egypt" ) {
            button_player_1_online_snake.setTextColor(Color.BLACK)
            button_player_2_online_snake.setTextColor(Color.BLACK)
            button_player_1_online_snake.setTextSize(20f)
            button_player_2_online_snake.setTextSize(20f)
            timer_snake_online.setTextSize(15f)
            timer_snake_online.setTextColor(Color.GREEN)
            timer2_snake_online.setTextSize(15f)
            timer2_snake_online.setTextColor(Color.GREEN)

            icon_player_1_snake_online.setBackgroundResource(R.drawable.player1_egypt);
            icon_player_2_snake_online.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_snake_online.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_snake_online.setBackgroundResource(R.drawable.circle_egypt);
            label_online_snake.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_snake_online.setBackgroundColor(rgb(224,164,103))
            to_back_snake_online.setBackgroundResource(R.drawable.arrow_back)
            toolbar_snake_online.setBackgroundColor(argb(0,0,0,0))
            toolbar2_snake_online.setBackgroundColor(argb(0,0,0,0))
        }
        initMenuFunctions(this, bottom_navigation_xog_online, intent, yourName, opponentsName)
        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                signature_canvas_snake_online.blocked = true
                if (p0.childrenCount >= 2) {
                    engine?.changeMoveAndSyncTimer(p0)
                }
                Log.w("LLLLL", "JOP")
                var count = 0
                for (i in 0..signature_canvas_snake_online.FIELD.size - 1) {
                    for (j in 0..signature_canvas_snake_online.FIELD[i].size - 1) {
                        if (p0.child("FIELD").child("$i").hasChild("$j")) {
                            signature_canvas_snake_online.FIELD[i][j] =
                                p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
                            count++
                        }
                    }
                }
                var cnt = 0

                for (i in p0.child("Snake_1").children) {
                    Log.w("SSSS", Pair(i.child("first").value.toString(),
                        i.child("second").value.toString()).toString())
                    if (cnt >= signature_canvas_snake_online.Snake_1.size && i.hasChild("second")) {
                        signature_canvas_snake_online.Snake_1.add(
                            Pair(i.child("first").value.toString().toInt(),
                                i.child("second").value.toString().toInt()))

                    }
                    cnt++
                }
                cnt = 0
                for (i in p0.child("Snake_2").children) {
                    if (cnt >= signature_canvas_snake_online.Snake_2.size && i.hasChild("second")) {
                        signature_canvas_snake_online.Snake_2.add(
                            Pair(i.child("first").value.toString().toInt(),
                                i.child("second").value.toString().toInt()))

                    }
                    cnt++
                }
                if (p0.hasChild("red_or_blue")) {
                    signature_canvas_snake_online.red_or_blue = p0.child("red_or_blue").value.toString()
                }
                if (signature_canvas_snake_online.isFirstMove == (signature_canvas_snake_online.red_or_blue == "red")) {
                    signature_canvas_snake_online.blocked = false
                }
                //if (signature_canvas_snake_online.red_or_blue == "red" && signature_canvas_snake_online.isFirstMove ||
                  //  signature_canvas_snake_online.red_or_blue == "blue" && !signature_canvas_snake_online.isFirstMove) {
                    //signature_canvas_snake_online.blocked = false
                //}
                Log.w("red_or_blue", signature_canvas_snake_online.red_or_blue)
                signature_canvas_snake_online.invalidate()

                val ch = signature_canvas_snake_online.check_win()
                if (ch != -1 && ch != 0 || p0.hasChild("winner")) {
                    engine?.stopTimer()
                    signature_canvas_snake_online.blocked = true
                    var res = if (ch == 2 && yu == '0' || ch == 1 && yu == '1') {
                        "Победа"
                    } else if (ch == 2 && yu == '1' || ch == 1 && yu == '0') {
                        "Поражение"
                    } else {
                        "Ничья"
                    }
                    if (p0.child("winner").value.toString() == yourName) {
                        res = "Победа"
                    }
                    if (p0.child("winner").value.toString() == opponentsName) {
                        res = "Поражение"
                    }
                    engine?.finish(res, this@SnakeGameActivity, isRun)
                    engineLong?.finish(res, this@SnakeGameActivity, isRun)
                    gameData.removeEventListener(this)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        isRun = false
        currentContext = null
        engine?.finish("Поражение", this@SnakeGameActivity, isRun)
        finish()
    }
}

class CanvasView_SNAKE_online(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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

    var isFirstMove = false
    var username = ""
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

    var engine: BlitzGameEngine? = null

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
    lateinit var positionData: DatabaseReference


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
            if(Math.abs(Snake_1[i].first - Snake_1[i + 1].first) + Math.abs(Snake_1[i].second - Snake_1[i + 1].second) <= 2)
            {
                canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
            }

            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first < Snake_1[i+1].first )
            {
                if(Math.abs(Snake_1[i].first - Snake_1[i + 1].first) + Math.abs(Snake_1[i].second - Snake_1[i + 1].second) <= 2)
                {
                    canvas?.drawLine(X - 5, Y, X1 + 5, Y1, paint_rib_1)
                }
            }
            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first > Snake_1[i+1].first )
            {
                if(Math.abs(Snake_1[i].first - Snake_1[i + 1].first) + Math.abs(Snake_1[i].second - Snake_1[i + 1].second) <= 2)
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
            if(Math.abs(Snake_2[i].first - Snake_2[i + 1].first) + Math.abs(Snake_2[i].second - Snake_2[i + 1].second) <= 2)
            {
                canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first < Snake_2[i+1].first )
            {
                if(Math.abs(Snake_2[i].first - Snake_2[i + 1].first) + Math.abs(Snake_2[i].second - Snake_2[i + 1].second) <= 2)
                {
                    canvas?.drawLine(X - 5, Y, X1 + 5, Y1, paint_rib_2)
                }
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first > Snake_2[i+1].first )
            {
                if(Math.abs(Snake_2[i].first - Snake_2[i + 1].first) + Math.abs(Snake_2[i].second - Snake_2[i + 1].second) <= 2)
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

    var blocked : Boolean = true
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (blocked) {
            return true
        }
        indent = 20f //оступ, чтобы можно было тыкнуть в границу
        advertising_line =  (height - 10*step)/2
        circlex = event!!.x
        circley = event!!.y

        x = indent
        y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        val upd = mutableMapOf<String, Any>()
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
                                upd["red_or_blue"] = red_or_blue
                                upd["FIELD/$i/$j"] = FIELD[i][j]
                                upd["Snake_1/" + (Snake_1.size - 1) + "/first"] = Snake_1.last().first
                                upd["Snake_1/" + (Snake_1.size - 1) + "/second"] = Snake_1.last().second
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
                                if((i == Snake_1.last().first && Math.abs(j - Snake_1.last().second) %9 == 1) || (j == Snake_1.last().second && Math.abs(
                                        i - Snake_1.last().first
                                    ) %9 == 1))
                                {
                                    Snake_1.add(Pair(i,j))
                                    FIELD[i][j] = 1
                                    red_or_blue = "blue"
                                    upd["red_or_blue"] = red_or_blue
                                    upd["FIELD/$i/$j"] = FIELD[i][j]
                                    upd["Snake_1/" + (Snake_1.size - 1) + "/first"] = Snake_1.last().first
                                    upd["Snake_1/" + (Snake_1.size - 1) + "/second"] = Snake_1.last().second
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
                                upd["red_or_blue"] = red_or_blue
                                upd["FIELD/$i/$j"] = FIELD[i][j]
                                upd["Snake_2/" + (Snake_2.size - 1) + "/first"] = Snake_2.last().first
                                upd["Snake_2/" + (Snake_2.size - 1) + "/second"] = Snake_2.last().second

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
                                if((i == Snake_2.last().first && Math.abs(j - Snake_2.last().second) %9 == 1) || (j == Snake_2.last().second && Math.abs(
                                        i - Snake_2.last().first
                                    ) %9 == 1))
                                {
                                    Snake_2.add(Pair(i,j))
                                    FIELD[i][j] = 2
                                    red_or_blue = "red"
                                    upd["red_or_blue"] = red_or_blue
                                    upd["FIELD/$i/$j"] = FIELD[i][j]
                                    upd["Snake_2/" + (Snake_2.size - 1) + "/first"] = Snake_2.last().first
                                    upd["Snake_2/" + (Snake_2.size - 1) + "/second"] = Snake_2.last().second

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
        if (upd.isNotEmpty()) {
            upd["time/$username"] = engine?.cntUser.toString()
            positionData.updateChildren(upd)
            upd.clear()
            blocked = true
        }
        return true
    }

}
