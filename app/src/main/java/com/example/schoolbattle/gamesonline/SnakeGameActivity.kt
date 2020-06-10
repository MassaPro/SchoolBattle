package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.schoolbattle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_snake_game.*

class SnakeGameActivity : AppCompatActivity() {
    private var isRun = false
    private var dialog: ShowResult? = null

    override fun onResume() {
        super.onResume()
        currentContext = this
        isRun = true
        CONTEXT = this
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_snake_game)

        currentContext = this
        CONTEXT = this
        isRun = true

        if (StupidGame != Activity()) StupidGame.finish()
        if (NewGame != Activity()) NewGame.finish()
        val yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()
        var opponentsName_: String = intent?.getStringExtra("opponentName").toString()
        var opponentsName = ""
        for (i in opponentsName_) {
            if (i == ' ') break
            opponentsName += i
        }
        val yu = if (opponentsName < yourName) '1' else '0'
        val op = if (opponentsName < yourName) '0' else '1'
//        players.text = yourName + " VS " + opponentsName
        //      youName.text = yourName
        //    opponentName.text = opponentsName

        val gameData = myRef.child("SnakeGames").child(
            if (opponentsName < yourName)
                opponentsName + '_' + yourName else yourName + '_' + opponentsName
        )
        signature_canvas_snake_online.blocked = true
        signature_canvas_snake_online.positionData = gameData

        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                for (i in 0..signature_canvas_snake_online.FIELD.size - 1) {
                    for (j in 0..signature_canvas_snake_online.FIELD[i].size - 1) {
                        if (p0.child("FIELD").child("$i").hasChild("$j")) {
                            signature_canvas_snake_online.FIELD[i][j] =
                                p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
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

                signature_canvas_snake_online.red_or_blue = if (p0.hasChild("red_or_blue")) p0.child("red_or_blue").value.toString() else "red"
                signature_canvas_snake_online.invalidate()
                if ((signature_canvas_snake_online.red_or_blue == "red") == (p0.child("Move").toString().toBoolean() == (yu == '0'))) signature_canvas_snake_online.blocked = false
                val ch = signature_canvas_snake_online.check_win()
                if (ch != 0) {
                    signature_canvas_snake_online.blocked = true
                    val res = if (ch == 2 && yu == '0' || ch == 1 && yu == '1') {
                        "Победа"
                    } else if (ch == 2 && yu == '1' || ch == 1 && yu == '0') {
                        "Поражение"
                    } else {
                        "Ничья"
                    }


                    myRef.child("SnakeGames").child(if (opponentsName < yourName)
                        opponentsName + '_' + yourName else yourName + '_' + opponentsName
                    ).removeValue()

                    myRef.child("Users").child(yourName).child("Games").child("$opponentsName SnakeGame").removeValue()
                    myRef.child("Users").child(opponentsName).child("Games").child("$yourName SnakeGame").removeValue()
                    dialog =
                        ShowResult(this@SnakeGameActivity)
                    if (isRun) {
                        dialog?.showResult(res, "SnakeGame", yourName, opponentsName)
                    }
                    gameData.removeEventListener(this)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        isRun = false
        currentContext = null
        if (dialog != null) {
            dialog?.delete()
            finish()
        } else {
            Log.w("HHH", "NONULL")
        }
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

            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<8)
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
            if(Y<8)
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

            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<8)
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
            if(Y<8)
            {
                if(FIELD[X][Y+1] == 0)
                {
                    return 0
                }
            }
            return 1
        }
        return 0
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

    var FIELD = Array(9){IntArray(9)}     //для фишеК
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
            if(i == 0 || i == size_field_y)
            {
                canvas?.drawLine(indent,k,width+indent,k,border_1)
            }
            else
            {
                canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            }
            k = k + step
        }

        k = indent
        for(i in 0 until size_field_x+1)         //вырисовка вертикальных линий
        {
            if(i == 0 ||  i == size_field_x)
            {
                canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())+ 5f,k,height-advertising_line-5f,border_1)
            }
            else
            {
                canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())+ 5f,k,height-advertising_line-5f,Line_paint)
            }

            k = k + step
        }

        var x: Float;
        var y: Float
        x = indent + step
        y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat()) + step
        for(i in 0..size_field_x-2)                    //вырисовка точек
        {
            for(j in 0..size_field_y-2)
            {
                canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                y += step
            }
            x += step
            y  = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat()) + step
        }

        for(i in 0 until Snake_1.size - 1)     //вырисовка ребер змеи
        {
            var X: Float = indent + step*Snake_1[i].first + step
            var Y: Float =  height - advertising_line - width + step*Snake_1[i].second + step
            var X1: Float = indent + step*Snake_1[i+1].first + step
            var Y1: Float =  height - advertising_line - width + step*Snake_1[i+1].second + step
            canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)


            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first < Snake_1[i+1].first )
            {
                canvas?.drawLine(X-5,Y,X1+5,Y1,paint_rib_1)
            }
            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first > Snake_1[i+1].first )
            {
                canvas?.drawLine(X+5,Y,X1-5,Y1,paint_rib_1)
            }
        }
        for(i in 0 until Snake_2.size - 1)      //вырисовка ребер змеи
        {
            var X: Float = indent + step*Snake_2[i].first + step
            var Y: Float =  height - advertising_line - width + step*Snake_2[i].second + step
            var X1: Float = indent + step*Snake_2[i+1].first + step
            var Y1: Float =  height - advertising_line - width + step*Snake_2[i+1].second + step
            canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first < Snake_2[i+1].first )
            {
                canvas?.drawLine(X-5,Y,X1+5,Y1,paint_rib_2)
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first > Snake_2[i+1].first )
            {
                canvas?.drawLine(X+5,Y,X1-5,Y1,paint_rib_2)
            }
        }

        if(Snake_1.size>0)
        {
            var X: Float = indent + step * Snake_1[0].first + step - step / 5f
            var X1: Float = indent + step * Snake_1[0].first + step + step / 5f
            var Y: Float =
                height - advertising_line - width + step * Snake_1[0].second + step - step / 5f
            var Y1: Float =
                height - advertising_line - width + step * Snake_1[0].second + step + step / 5f
            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
        }

        if(Snake_2.size>0)
        {
            var _X: Float = indent + step * Snake_2[0].first + step - step / 5f
            var _X1: Float = indent + step * Snake_2[0].first + step + step / 5f
            var _Y: Float =
                height - advertising_line - width + step * Snake_2[0].second + step - step / 5f
            var _Y1: Float =
                height - advertising_line - width + step * Snake_2[0].second + step + step / 5f
            canvas?.drawLine(_X, _Y, _X1, _Y1, paint_rib_2)
            canvas?.drawLine(_X, _Y1, _X1, _Y, paint_rib_2)
        }

        if(Snake_1.size > 1)
        {
            var X: Float = indent + step*Snake_1.last().first + step
            var Y: Float = height - width - advertising_line + step*Snake_1.last().second + step
            canvas?.drawCircle(X,Y,radius_of_point*2,paint_rib_1)
        }
        if(Snake_2.size > 1)
        {
            var X: Float = indent + step*Snake_2.last().first + step
            var Y: Float = height - width - advertising_line + step*Snake_2.last().second + step
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

        x = indent + step
        y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat()) + step
        for(i in 0..size_field_x - 2)
        {
            for(j in 0..size_field_y - 2)
            {
                if(correction_touch(x,y))
                {
                    //blocked = true
                    if(FIELD[i][j] == 0)
                    {
                        if(red_or_blue == "red")
                        {
                            if(Snake_1.size == 0)
                            {
                                Snake_1.add(Pair(i,j))
                                FIELD[i][j] = 1
                                red_or_blue = "blue"
                                blocked = true
                                positionData.child("red_or_blue").setValue(red_or_blue)
                                positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j])
                                positionData.child("Snake_1").child((Snake_1.size - 1).toString()).child("first").setValue(Snake_1.last().first.toString())
                                positionData.child("Snake_1").child((Snake_1.size - 1).toString()).child("second").setValue(Snake_1.last().second.toString())

                            }
                            else
                            {
                                if((i == Snake_1.last().first && Math.abs(j - Snake_1.last().second) == 1) || (j == Snake_1.last().second && Math.abs(
                                        i - Snake_1.last().first
                                    ) == 1))
                                {
                                    Snake_1.add(Pair(i,j))
                                    FIELD[i][j] = 1
                                    red_or_blue = "blue"
                                    blocked = true
                                    positionData.child("red_or_blue").setValue(red_or_blue)
                                    positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j])
                                    positionData.child("Snake_1").child((Snake_1.size - 1).toString()).child("first").setValue(Snake_1.last().first.toString())
                                    positionData.child("Snake_1").child((Snake_1.size - 1).toString()).child("second").setValue(Snake_1.last().second.toString())

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
                                blocked = true
                                positionData.child("red_or_blue").setValue(red_or_blue)
                                positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j])
                                positionData.child("Snake_2").child((Snake_2.size - 1).toString()).child("first").setValue(Snake_2.last().first.toString())
                                positionData.child("Snake_2").child((Snake_2.size - 1).toString()).child("second").setValue(Snake_2.last().second.toString())
                            }
                            else
                            {
                                if((i == Snake_2.last().first && Math.abs(j - Snake_2.last().second) == 1) || (j == Snake_2.last().second && Math.abs(
                                        i - Snake_2.last().first
                                    ) == 1))
                                {
                                    Snake_2.add(Pair(i,j))
                                    FIELD[i][j] = 2
                                    red_or_blue = "red"
                                    blocked = true
                                    positionData.child("red_or_blue").setValue(red_or_blue)
                                    positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j])
                                    positionData.child("Snake_2").child((Snake_2.size - 1).toString()).child("first").setValue(Snake_2.last().first.toString())
                                    positionData.child("Snake_2").child((Snake_2.size - 1).toString()).child("second").setValue(Snake_2.last().second.toString())

                                }
                            }
                        }
                    }
                }
                y += step
            }
            x  += step
            y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat()) + step
        }
        x = 0f
        y = 0f
        invalidate()
        return true
    }

}