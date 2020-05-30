package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_snake_game_one_divice.*
import kotlinx.android.synthetic.main.activity_x_o_game_one_divice.*
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

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_game_one_divice)

        signature_canvas_snake_one_device.activity = this

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

        comback_snake_one_divice.setOnClickListener {
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

            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<9)
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
            if(Y<9)
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

            if(X<9)
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
            if(Y<9)
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

    var FIELD = Array(10){IntArray(10)}     //для фишеК
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

        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[i].size)
            {
                FIELD[i][j] = 0
            }
        }

    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue);



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        radius_of_point = 8f
        size_field_x  = 11
        size_field_y  = 11
        indent = 0f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = 150f           //полоска для рекламы

        step = width/size_field_x
        k = height-width*(size_field_y.toFloat()/size_field_x.toFloat())-advertising_line

        canvas?.drawColor(Color.WHITE)

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
                canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())+ 5f,k,height-advertising_line-5f,border_2)
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

    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {

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
                dialog?.showResult_one_device("КРАСНАЯ ПОБЕДИЛА","SnakeGame",activity)
                return true
            }
            if(check_win() == 2)
            {
                dialog?.showResult_one_device("СИНЯЯ ПОБЕДИЛА","SnakeGame",activity)
                return true
            }
        }
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
                            }
                            else
                            {
                                if((i == Snake_1.last().first && abs(j -Snake_1.last().second) == 1) || (j == Snake_1.last().second && abs(i -Snake_1.last().first) == 1))
                                {
                                    Snake_1.add(Pair(i,j))
                                    FIELD[i][j] = 1
                                    red_or_blue = "blue"
                                    History.add(Triple(i,j,FIELD[i][j]))
                                    var data_from_memory = encode(History)
                                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("snake_one_divice", data_from_memory)
                                    editor.apply()
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
                            }
                            else
                            {
                                if((i == Snake_2.last().first && abs(j -Snake_2.last().second) == 1) || (j == Snake_2.last().second && abs(i -Snake_2.last().first) == 1))
                                {
                                    Snake_2.add(Pair(i,j))
                                    FIELD[i][j] = 2
                                    red_or_blue = "red"
                                    History.add(Triple(i,j,FIELD[i][j]))
                                    var data_from_memory = encode(History)
                                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("snake_one_divice", data_from_memory)
                                    editor.apply()
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