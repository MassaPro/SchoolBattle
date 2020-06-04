package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_box_game_one_divice.*
import kotlinx.android.synthetic.main.activity_coners_one_device.*
import kotlinx.android.synthetic.main.activity_reversi_one_divice.*
import kotlinx.android.synthetic.main.activity_x_o_game_one_divice.*
import kotlin.math.E

class ReversiOneDivice : AppCompatActivity() {
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

    private var dialog: Show_Result_one_Device? = null

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reversi_one_divice)
        signature_canvas_reversi_one_device.activity = this

        val usedToClear = intent.getStringExtra("usedToClear") // тип иг
    }

}

class CanvasView_reversi_one_device(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                Array_of_illumination[i][j] = 1
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
                                if (FIELD[p][y] == 0 || FIELD[p][y] == FIELD[p][y]) {
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
                                if (FIELD[x + p][j + p] == 0 || FIELD[x + p][j + p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in 1 until i - x) {
                                    FIELD[x + p][j + p] = FIELD[x][y]
                                }
                            }
                        }
                        if (i + 1 < x) {
                            for (p in 1 until x - i) {
                                if (FIELD[i + p][y + p] == 0 || FIELD[i + p][y + p] == FIELD[x][y]) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                for (p in 1 until x - i) {
                                    FIELD[i + p][y + p] = FIELD[x][y]
                                }
                            }
                        }
                    }
                }


            }
        }
    }


    lateinit var activity: Activity

    var History: MutableList<MutableList<Int>> = mutableListOf()
    var EXODUS : Int = 0
    var indent : Float = 0f
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var Black_or_grey_chip: String = "black"
    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var FIELD = Array(8){IntArray(8)}     //для фишек
    var Array_of_illumination = Array(8) { IntArray(8) }



    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f

    var lastX = -1
    var lastY = -1

    var exception: Boolean = false

    init{
        Line_paint.setColor(Color.RED)          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(10f)

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




    var black_chip : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);       //картинки фишек и подсветки
    var grey_chip: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grey);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = 150f           //полоска для рекламы
        size_field_x  = 8
        size_field_y  = 8
        step = (width-2*indent)/size_field_x
        k = height-(width-2*indent)-advertising_line



        //TODO() take field from database
        canvas?.drawColor(Color.WHITE)


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


        val rigth_black_chip: Bitmap = Bitmap.createScaledBitmap(black_chip,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
        val right_grey_chip: Bitmap = Bitmap.createScaledBitmap(grey_chip,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        val right_green:Bitmap = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);

        for( i in 0..7) // расстановка фишек
        {
            for(j in 0..7) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(rigth_black_chip, translate_from_Array_to_Graphics_X(indent,i,step),
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
                if (Array_of_illumination[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(right_green, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        circlex = event!!.x
        circley = event!!.y
        var X: Int = touch_refinement_for_Array_X(indent,circlex, step)
        var Y: Int = touch_refinement_for_Array_Y(indent,circley, height, size_field_y, step, advertising_line)      //перевод последнего нажатия // в координаты массива
        if(Black_or_grey_chip == "black")
        {
            illumination(1);
        }
        else
        {
            illumination(2);
        }
        if(X in 0..7 && Y in 0..7 )
        {
            if(FIELD[X][Y] == 0 && Array_of_illumination[X][Y] == 1)
            {
                if (Black_or_grey_chip == "black") {
                    FIELD[X][Y] = 1
                    Black_or_grey_chip = "grey"
                } else {
                    FIELD[X][Y] = 2
                    Black_or_grey_chip = "black"
                }
                for( i in 0..7) {
                    for(j in 0 ..7) {
                        Array_of_illumination[i][j] = 0
                    }
                }
                change_array(X,Y);
                if(Black_or_grey_chip == "black")
                {
                    illumination(1);
                }
                else
                {
                    illumination(2);
                }
                invalidate()
            }
        }
        return true
    }

}