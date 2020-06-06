package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_x_o_game_one_divice.*
import kotlin.math.E

class ConersOneDevice : AppCompatActivity() {
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
    private var dialog_parametrs: Show_parametr_one_divice_one_Device? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coners_one_device)
        signature_canvas_corners_one_device.activity = this

        if(Design == "Egypt" ) {
            label_one_device_corner.setBackgroundResource(R.drawable.back_ground_egypt);
            bottom_navigation_corner_one_divice.setBackgroundColor(Color.rgb(224, 164, 103))
            to_back_corner_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_corner_one_divice.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("corner_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_corners_one_device.History = decode(prefs.getString("corner_one_divice", "").toString())
        if (signature_canvas_corners_one_device.History.size > 0)
        {

            for(i in 0 until signature_canvas_corners_one_device.FIELD.size)
            {
                for(j in 0 until signature_canvas_corners_one_device.FIELD[0].size)
                {
                    signature_canvas_corners_one_device.FIELD[i][j] = 0
                }
            }
            signature_canvas_corners_one_device.FIELD[0][5] = 1;signature_canvas_corners_one_device.FIELD[1][5] = 1;signature_canvas_corners_one_device.FIELD[2][5] = 1;
            signature_canvas_corners_one_device.FIELD[0][6] = 1;signature_canvas_corners_one_device.FIELD[1][6] = 1;signature_canvas_corners_one_device.FIELD[2][6] = 1;
            signature_canvas_corners_one_device.FIELD[0][7] = 1;signature_canvas_corners_one_device.FIELD[1][7] = 1;signature_canvas_corners_one_device.FIELD[2][7] = 1;


            signature_canvas_corners_one_device.FIELD[5][0] = 2;signature_canvas_corners_one_device.FIELD[5][1] = 2;signature_canvas_corners_one_device.FIELD[5][2] = 2;
            signature_canvas_corners_one_device.FIELD[6][0] = 2;signature_canvas_corners_one_device.FIELD[6][1] = 2;signature_canvas_corners_one_device.FIELD[6][2] = 2;
            signature_canvas_corners_one_device.FIELD[7][0] = 2;signature_canvas_corners_one_device.FIELD[7][1] = 2;signature_canvas_corners_one_device.FIELD[7][2] = 2;

            signature_canvas_corners_one_device.Black_or_grey_chip = "black"
            for (i in signature_canvas_corners_one_device.History) {
                signature_canvas_corners_one_device.FIELD[i[2]][i[3]] = signature_canvas_corners_one_device.FIELD[i[0]][i[1]]
                signature_canvas_corners_one_device.FIELD[i[0]][i[1]] = 0
                if(signature_canvas_corners_one_device.Black_or_grey_chip == "black")
                {
                    signature_canvas_corners_one_device.Black_or_grey_chip =  "grey"
                }
                else
                {
                    signature_canvas_corners_one_device.Black_or_grey_chip =  "black"
                }
            }
            signature_canvas_corners_one_device.PHASE = true
            for(i in  0 until signature_canvas_corners_one_device.Array_of_illumination.size)
            {
                for(j in 0 until signature_canvas_corners_one_device.Array_of_illumination[0].size)
                {
                    signature_canvas_corners_one_device.Array_of_illumination[i][j] =0
                }
            }
            signature_canvas_corners_one_device.invalidate()
        }

        bottom_navigation_corner_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{

                }
                R.id.page_2 ->{
                    dialog_parametrs = Show_parametr_one_divice_one_Device(this@ConersOneDevice)
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, ConersOneDevice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_corners_one_device.History.size > 0)
                    {
                        signature_canvas_corners_one_device.History.removeLast()
                        var data_from_memory = encode(signature_canvas_corners_one_device.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("corner_one_divice", data_from_memory)
                        editor.apply()
                        for(i in 0 until signature_canvas_corners_one_device.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_corners_one_device.FIELD[0].size)
                            {
                                signature_canvas_corners_one_device.FIELD[i][j] = 0
                            }
                        }
                        signature_canvas_corners_one_device.FIELD[0][5] = 1;signature_canvas_corners_one_device.FIELD[1][5] = 1;signature_canvas_corners_one_device.FIELD[2][5] = 1;
                        signature_canvas_corners_one_device.FIELD[0][6] = 1;signature_canvas_corners_one_device.FIELD[1][6] = 1;signature_canvas_corners_one_device.FIELD[2][6] = 1;
                        signature_canvas_corners_one_device.FIELD[0][7] = 1;signature_canvas_corners_one_device.FIELD[1][7] = 1;signature_canvas_corners_one_device.FIELD[2][7] = 1;


                        signature_canvas_corners_one_device.FIELD[5][0] = 2;signature_canvas_corners_one_device.FIELD[5][1] = 2;signature_canvas_corners_one_device.FIELD[5][2] = 2;
                        signature_canvas_corners_one_device.FIELD[6][0] = 2;signature_canvas_corners_one_device.FIELD[6][1] = 2;signature_canvas_corners_one_device.FIELD[6][2] = 2;
                        signature_canvas_corners_one_device.FIELD[7][0] = 2;signature_canvas_corners_one_device.FIELD[7][1] = 2;signature_canvas_corners_one_device.FIELD[7][2] = 2;

                        signature_canvas_corners_one_device.Black_or_grey_chip = "black"
                        for (i in signature_canvas_corners_one_device.History) {
                            signature_canvas_corners_one_device.FIELD[i[2]][i[3]] = signature_canvas_corners_one_device.FIELD[i[0]][i[1]]
                            signature_canvas_corners_one_device.FIELD[i[0]][i[1]] = 0
                            if(signature_canvas_corners_one_device.Black_or_grey_chip == "black")
                            {
                                signature_canvas_corners_one_device.Black_or_grey_chip =  "grey"
                            }
                            else
                            {
                                signature_canvas_corners_one_device.Black_or_grey_chip =  "black"
                            }
                        }
                        signature_canvas_corners_one_device.PHASE = true
                        for(i in  0 until signature_canvas_corners_one_device.Array_of_illumination.size)
                        {
                            for(j in 0 until signature_canvas_corners_one_device.Array_of_illumination[0].size)
                            {
                                signature_canvas_corners_one_device.Array_of_illumination[i][j] =0
                            }
                        }
                        signature_canvas_corners_one_device.invalidate()
                    }
                }

            }
            true
        }
        to_back_corner_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
            startActivity(intent)
        }
    }
}

class CanvasView_corners_one_device (context: Context, attrs: AttributeSet?) : View(context, attrs) {

    fun check_block_corners(X: Int,Y: Int) : Boolean
    {
        if(Black_or_grey_chip == "black")
        {
            if(FIELD[X][Y]==1)
            {                                //ПРОВЕРЯЕМ ПРОТИВОПОЛОЖНЫЙ УГОЛ, ЕСЛИ ОН ПУСТ а твой нет, ТО ЗАПРЕЩАЕМ ДВИГАТЬ ФИШКУ НЕ ИЗ СВОЕГО УГЛА
                if(FIELD[5][0] !=2 && FIELD[5][1] !=2 && FIELD[5][2] !=2 &&
                    FIELD[6][0] !=2&& FIELD[6][1] !=2&& FIELD[6][2] !=2 &&
                    FIELD[7][0] !=2 && FIELD[7][1]!=2  && FIELD[7][2] !=2 && (FIELD[0][5] ==1 || FIELD[1][5] ==1 || FIELD[2][5] ==1 ||
                            FIELD[0][6] ==1 || FIELD[1][6] ==1 || FIELD[2][6] ==1 ||
                            FIELD[0][7] ==1 || FIELD[1][7]==1 || FIELD[2][7] ==1)){
                    if(X>2)
                    {
                        return false
                    }
                    if(Y<5)
                    {
                        return false
                    }
                }
                return true
            }
            else
            {
                return false
            }
        }
        else
        {
            if(FIELD[X][Y]==2)
            {
                if(FIELD[0][5] !=1 && FIELD[1][5] !=1 && FIELD[2][5] !=1 &&
                    FIELD[0][6] !=1 && FIELD[1][6] !=1 && FIELD[2][6] !=1 &&
                    FIELD[0][7] !=1 && FIELD[1][7]!=1 && FIELD[2][7] !=1 &&(FIELD[5][0] ==2 || FIELD[5][1] ==2 || FIELD[5][2] ==2 ||
                            FIELD[6][0] ==2 || FIELD[6][1] ==2 || FIELD[6][2] ==2 ||
                            FIELD[7][0] ==2 || FIELD[7][1]==2  || FIELD[7][2] ==2)){
                    if(X<5)
                    {
                        return false
                    }
                    if(Y>2)
                    {
                        return false
                    }
                }
                return true
            }
            else
            {
                return false
            }
        }
    }
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

    fun chek_win(): Int{
        if(FIELD[0][5] == 2 && FIELD[1][5] == 2 && FIELD[2][5] == 2 &&
            FIELD[0][6] == 2 && FIELD[1][6] == 2 && FIELD[2][6] == 2 &&
            FIELD[0][7] == 2 && FIELD[1][7] == 2 && FIELD[2][7] == 2)
        {
            return 2
        }
        if(FIELD[5][0] == 1 && FIELD[5][1] ==1 && FIELD[5][2] ==1 &&
            FIELD[6][0] ==1 && FIELD[6][1] ==1 && FIELD[6][2] ==1 &&
            FIELD[7][0] ==1 && FIELD[7][1] ==1  && FIELD[7][2] ==1)
        {
            return 1
        }
        return 0

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
    var Array_of_illumination = Array(8) { IntArray(8) }  //для подсветки

    var PHASE: Boolean     //определяет выбрана ли клетка
    var motion_chip: Boolean      //надо ли делать перемещение

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


        PHASE = true
        motion_chip = false

        if(Design == "Egypt")
        {
            Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)
        }
        else
        {
            Line_paint.setColor(Color.RED)          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)
        }


        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..7) {
            for(j in 0 ..7) {
                FIELD[i][j] = 0  //не заполненный
            }
        }

        for (i in 0..7) {
            for (j in 0..7) {
                Array_of_illumination[i][j] = 0
            }
        }
        FIELD[0][5] = 1;FIELD[1][5] = 1;FIELD[2][5] = 1;
        FIELD[0][6] = 1;FIELD[1][6] = 1;FIELD[2][6] = 1;
        FIELD[0][7] = 1;FIELD[1][7] = 1;FIELD[2][7] = 1;


        FIELD[5][0] = 2;FIELD[5][1] = 2;FIELD[5][2] = 2;
        FIELD[6][0] = 2;FIELD[6][1] = 2;FIELD[6][2] = 2;
        FIELD[7][0] = 2;FIELD[7][1] = 2;FIELD[7][2] = 2;

    }




    var black_chip : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);       //картинки фишек и подсветки
    var grey_chip: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grey);
    var black_chip_egypt:Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_chip_egypt);
    var white_chip_egypt:Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_chip_egypt);
    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        size_field_x  = 8
        size_field_y  = 8
        step = (width-2*indent)/size_field_x
        advertising_line =  (height - 8*step)/2         //полоска для рекламы
        k = height-(width-2*indent)-advertising_line




        //TODO() take field from database



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


        val rigth_black_chip: Bitmap
        val right_grey_chip: Bitmap
        val right_illumination:Bitmap
        val right_green:Bitmap

        if(Design == "Egypt")
        {
            rigth_black_chip  = Bitmap.createScaledBitmap(black_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
            right_grey_chip  = Bitmap.createScaledBitmap(white_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_green = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        }
        else
        {
            rigth_black_chip  = Bitmap.createScaledBitmap(black_chip,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
            right_grey_chip  = Bitmap.createScaledBitmap(grey_chip,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_green  = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        }

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
        for (i in 0..7) {             //расстановка подсветки
            for (j in 0..7) {
                if (Array_of_illumination[i][j] == 1 || Array_of_illumination[i][j] == 2) {
                    canvas?.drawBitmap(
                        right_green, translate_from_Array_to_Graphics_X(indent,i, step),
                        translate_from_Array_to_Graphics_Y(indent,
                            j,
                            height,
                            size_field_y,
                            step,
                            advertising_line
                        ), paint
                    )
                }
            }
        } //расстановка подсветки

        if(PHASE == false)
        {
            if(circley> height - advertising_line - width+2*indent && y < height - advertising_line){
                if( touch_refinement_X (indent,circlex,width,size_field_x)>0 && touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line)>=0f)
                {
                    canvas?.drawBitmap( right_illumination, touch_refinement_X(indent,circlex, width, size_field_x), touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line), paint)
                }
            }

        }

    }

    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        advertising_line = (height - 8*step)/2
        if(chek_win()<=0)
        {
            blocked = false
        }
        if(chek_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
            return true
        }
        var dialog: Show_Result_one_Device? = null

        if(chek_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked) {
            if (chek_win() == 2) {3
                dialog = Show_Result_one_Device(activity)
                dialog?.showResult_one_device("СЕРЫЕ ПОБЕДИЛИ", "AngleGame", activity)
                return true
            }
            if (chek_win() == 1) {
                dialog = Show_Result_one_Device(activity)
                dialog?.showResult_one_device("ЧЕРНЫЕ ПОБЕДИЛИ", "AngleGame", activity)
                return true
            }
        }
        circlex = event!!.x
        circley = event!!.y
        var X: Int = touch_refinement_for_Array_X(indent,circlex, step)
        var Y: Int = touch_refinement_for_Array_Y(indent,circley, height, size_field_y, step, advertising_line)      //перевод последнего нажатия в координаты массива
        if (X in 0..7 && Y in 0..7 && touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line) > 0 && touch_refinement_X(indent,circlex,width,size_field_x)>0)     //постановка нового обЪекта, проверка что на поле
        {
            if ((X != lastX || Y != lastY ) || (exception == true) )   //если касание в новую область
            {
                if(exception == true)
                {
                    exception = false
                }
                PHASE = !PHASE   //смена фазы TODO добавить условие проверки
                if (PHASE == true)
                {
                    if (Array_of_illumination[X][Y] == 1 || Array_of_illumination[X][Y] == 2)     //если подсвечена область
                    {
                        FIELD[X][Y] = FIELD[lastX][lastY]         //перемещение фишки
                        History.add(mutableListOf(lastX,lastY,X,Y))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("corner_one_divice", data_from_memory)
                        editor.apply()
                        FIELD[lastX][lastY] = 0
                        if(Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                        {
                            Black_or_grey_chip = "grey"
                        }
                        else
                        {
                            Black_or_grey_chip = "black"
                        }
                    }
                    else
                    {
                        exception = true     //описывает тот случай, когда мы не поставили на зеленую область а тыкнули в другую фишку, а потом решили походить фишкой в которую мы тыкнули
                    }
                    for (i in 0..7) {
                        for (j in 0..7) {
                            Array_of_illumination[i][j] = 0   //обнуляем массива подсветки, чтобы он не оображался
                        }
                    }

                }
                else
                {
                    var s: Int = 0
                    //   Log.d("DOPO",X.toString()+" "+ Y.toString() + " " + lastX.toString() + " " + lastY.toString())
                    if ( ((FIELD[X][Y] == 1 && Black_or_grey_chip == "black") || (FIELD[X][Y] == 2 && Black_or_grey_chip == "grey"))  &&   check_block_corners(X,Y))       //если подсвечивается фишка
                    {

                        Array_of_illumination[X][Y] = -1
                        if(X-1>=0)
                        {
                            if(FIELD[X-1][Y] == 0)
                            {
                                Array_of_illumination[X-1][Y] = 2
                                s++
                            }
                        }
                        if(X+1<8)
                        {
                            if(FIELD[X+1][Y] == 0)
                            {
                                Array_of_illumination[X+1][Y] = 2
                                s++
                            }
                        }
                        if(Y-1>=0)
                        {
                            if(FIELD[X][Y-1] == 0)
                            {
                                Array_of_illumination[X][Y-1] = 2
                                s++
                            }
                        }
                        if(Y+1<8)
                        {
                            if(FIELD[X][Y+1] == 0)
                            {
                                Array_of_illumination[X][Y+1] = 2
                                s++
                            }
                        }
                        for (t in 0..8) {
                            for (i in 0..7) {
                                for (j in 0..7) {
                                    if (Array_of_illumination[i][j] == -1 || Array_of_illumination[i][j] == 1)
                                    {
                                        if (i > 1) {
                                            if (FIELD[i - 2][j] == 0 && FIELD[i - 1][j] != 0) {
                                                Array_of_illumination[i - 2][j] = 1
                                                s++

                                            }
                                        }
                                        if (i < 6) {
                                            if (FIELD[i + 2][j] == 0 && FIELD[i + 1][j] != 0) {
                                                Array_of_illumination[i + 2][j] = 1
                                                s++
                                            }
                                        }
                                        if (j > 1) {
                                            if (FIELD[i][j - 2] == 0 && FIELD[i][j - 1] != 0) {
                                                Array_of_illumination[i][j - 2] = 1
                                                s ++
                                            }
                                        }
                                        if (j < 6) {
                                            if (FIELD[i][j + 2] == 0 && FIELD[i][j + 1] != 0) {
                                                Array_of_illumination[i][j + 2] = 1
                                                s ++
                                            }
                                        }
                                    }
                                }
                            }
                        }     //создание массива подсветки

                    }
                    else
                    {
                        for(i in 0..7)
                        {
                            for(j in 0..7)
                            {
                                Array_of_illumination[i][j] = 0
                            }
                        }
                    }

                    if (s == 0)
                    {
                        PHASE = true
                    }
                }
            }
            lastX = X
            lastY = Y
        }
        Log.w("DOPO",Black_or_grey_chip)

        invalidate()
        return true
    }

}