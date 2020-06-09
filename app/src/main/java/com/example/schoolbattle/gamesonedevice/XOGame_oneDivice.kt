package com.example.schoolbattle.gamesonedevice

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_x_o_game_one_divice.*



class XOGame_oneDivice : AppCompatActivity() {
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
    private var dialog: Show_Result_one_Device? = null
    private var dialog_parametrs: Show_parametr_one_divice_one_Device? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        CONTEXT = this
        //var h : MutableList<Triple<Int,Int,Int>> =  mutableListOf(Triple(231,231,777),Triple(231,231,777),Triple(231,231,777))
        //Log.w("momlol",decode(encode(h)).toString())
        setContentView(R.layout.activity_x_o_game_one_divice)

        if(Design == "Egypt" ) {
            name_player1_one_divice_xog.setTextColor(Color.BLACK)
            name_player2_one_divice_xog.setTextColor(Color.BLACK)
            name_player2_one_divice_xog.setTextSize(20f)
            name_player1_one_divice_xog.setTextSize(20f)
            button_player_1_xog_one_divice.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_xog_one_divice.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_xog_one_divice.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_xog_one_divice.setBackgroundResource(R.drawable.circle_egypt);
            label_one_device.setBackgroundResource(R.drawable.back_ground_egypt);
            bottom_navigation_xog_one_divice.setBackgroundColor(rgb(224,164,103))
            to_back_xog_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_xog_one_divice.setBackgroundColor(argb(0,0,0,0))
            toolbar2_xog_one_divice.setBackgroundColor(argb(0,0,0,0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("xog_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_xog_one_device.History = decode(prefs.getString("xog_one_divice", "").toString())
        for (i in 0 until signature_canvas_xog_one_device.FIELD.size) {
            for (j in 0 until signature_canvas_xog_one_device.FIELD[0].size) {
                signature_canvas_xog_one_device.FIELD[i][j] = 0
            }
        }
        signature_canvas_xog_one_device.cross_or_nul = "cross"
        for (i in signature_canvas_xog_one_device.History) {
            signature_canvas_xog_one_device.FIELD[i.first][i.second] = i.third
            if (i.third == 1) {
                signature_canvas_xog_one_device.cross_or_nul = "null"
            } else {
                signature_canvas_xog_one_device.cross_or_nul = "cross"
            }
        }
        signature_canvas_xog_one_device.invalidate()

        signature_canvas_xog_one_device.setOnClickListener{
            if(signature_canvas_xog_one_device.EXODUS == 1)
            {
                dialog =
                    Show_Result_one_Device(this@XOGame_oneDivice)
                dialog?.showResult_one_device("КРЕСТИКИ ПОБЕДИЛИ","XOGame",this)

            }
            if(signature_canvas_xog_one_device.EXODUS == 2)
            {
                dialog =
                    Show_Result_one_Device(this@XOGame_oneDivice)
                dialog?.showResult_one_device("НОЛИКИ ПОБЕДИЛИ","XOGame",this)
            }
        }
        to_back_xog_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
            startActivity(intent)
        }

        bottom_navigation_xog_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{

                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_one_divice_one_Device(
                            this@XOGame_oneDivice
                        )
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, XOGame_oneDivice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{

                        if (signature_canvas_xog_one_device.History.size > 0)            //TODO дописать когда самый первый ход убираем
                        {
                            signature_canvas_xog_one_device.History.removeLast()
                            var data_from_memory = encode(signature_canvas_xog_one_device.History)
                            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("xog_one_divice", data_from_memory)
                            editor.apply()
                            for (i in 0 until signature_canvas_xog_one_device.FIELD.size) {
                                for (j in 0 until signature_canvas_xog_one_device.FIELD[0].size) {
                                    signature_canvas_xog_one_device.FIELD[i][j] = 0
                                }
                            }
                            signature_canvas_xog_one_device.cross_or_nul = "cross"
                            for (i in signature_canvas_xog_one_device.History) {
                                signature_canvas_xog_one_device.FIELD[i.first][i.second] = i.third
                                if (i.third == 1) {
                                    signature_canvas_xog_one_device.cross_or_nul = "null"
                                } else {
                                    signature_canvas_xog_one_device.cross_or_nul = "cross"
                                }
                            }
                            signature_canvas_xog_one_device.invalidate()
                        }
                    }

            }
            true
        }


    }
}




class CanvasView_xog_one_device(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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


    init {

        if(Design == "Egypt")
        {
            Line_paint.color = Color.BLACK          //ресур для линий (ширина и цвет)
            Line_paint.strokeWidth = 7f

            Line_paint_1.color = Color.BLACK          //ресур для линий (ширина и цвет)
            Line_paint_1.strokeWidth = 20f
        }
        else
        {
            Line_paint.color = Color.RED          //ресур для линий (ширина и цвет)
            Line_paint.strokeWidth = 10f

            Line_paint_1.color = Color.RED          //ресур для линий (ширина и цвет)
            Line_paint_1.strokeWidth = 20f
        }


        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..6) {
            for(j in 0 ..5) {
                FIELD[i][j] = 0 //не заполненный
            }
        }
        cross_or_nul  = "cross"
    }


    var icon_cross_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.cross_egypt
    )       //картинки крестиков и нулей
    var icon_null_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.circle_egypt
    )

   // var BackgroundColor_Egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_egypt)
    var icon_green : Bitmap = BitmapFactory.decodeResource(context.getResources(),
       R.drawable.illumination
   )
    var icon_grenn_Egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.ram_egypt_xog
    )



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        //TODO() take field from database

        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)

        size_field_x  = 7
        size_field_y  = 6
        step = (width-2*indent)/size_field_x
        var advertising_line: Float = (height - step * 6) / 2
        var k: Float = height-(width-2*indent)-advertising_line


        val right_icon_cross: Bitmap  //подгоняем картинку под размеры экрана телефона
        val right_icon_null: Bitmap
        val right_icon_green: Bitmap
        if(Design == "Egypt")
        {
            right_icon_cross = Bitmap.createScaledBitmap(icon_cross_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_null = Bitmap.createScaledBitmap(icon_null_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_green = Bitmap.createScaledBitmap(
                icon_grenn_Egypt,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                true
            )
        }
        else {
            right_icon_cross = Bitmap.createScaledBitmap(icon_cross_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_null = Bitmap.createScaledBitmap(icon_null_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
             right_icon_green = Bitmap.createScaledBitmap(
                icon_green,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                true
            )
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



        for( i in 0..6) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..5) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(right_icon_cross, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    canvas?.drawBitmap(right_icon_null, translate_from_Array_to_Graphics_X(indent,i,step),
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
                canvas?.drawBitmap(right_icon_green,a_1,a_2,paint)
                counter += 2
            }

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
            blocked = false
        }
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
                if(FIELD[X][Y] == 0 && Y == 5) {
                    if (cross_or_nul == "cross") {
                        FIELD[X][Y] = 1
                        History.add(Triple(X,Y,1))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_one_divice", data_from_memory)
                        editor.apply()
                        cross_or_nul = "null"
                    } else {
                        FIELD[X][Y] = 2
                        History.add(Triple(X,Y,2))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_one_divice", data_from_memory)
                        editor.apply()
                        cross_or_nul = "cross"
                    }
                    Log.w("ppppppp", FIELD[0][0].toString())
                    invalidate()
                }
                else
                {
                    if(FIELD[X][Y] == 0 && FIELD[X][Y+1]!=0)
                    {
                        if (cross_or_nul == "cross") {
                            FIELD[X][Y] = 1
                            History.add(Triple(X,Y,1))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("xog_one_divice", data_from_memory)
                            editor.apply()
                            cross_or_nul = "null"
                        } else {
                            FIELD[X][Y] = 2
                            History.add(Triple(X,Y,2))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("xog_one_divice", data_from_memory)
                            editor.apply()
                            cross_or_nul = "cross"
                        }
                        Log.w("ppppppp", FIELD[0][0].toString())
                        invalidate()
                    }
                }
            }
        }
        return true
    }
}