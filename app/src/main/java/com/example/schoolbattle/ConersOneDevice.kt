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
import kotlinx.android.synthetic.main.activity_coners_one_device.*
import kotlinx.android.synthetic.main.activity_x_o_game_one_divice.*
import kotlin.math.E

class ConersOneDevice : AppCompatActivity() {
    private var dialog: Show_Result_one_Device? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coners_one_device)

        signature_canvas_corners_one_device.isEnabled = true
        signature_canvas_corners_one_device.isClickable = true

        signature_canvas_corners_one_device.activity = this

        comback_corner_one_divice.setOnClickListener {
            Log.w("GOGOGO",signature_canvas_corners_one_device.History.toString())
            if (signature_canvas_corners_one_device.History.size > 0)
            {
                signature_canvas_corners_one_device.History.removeLast()

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
}

class CanvasView_corners_one_device (context: Context, attrs: AttributeSet?) : View(context, attrs) {

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

        Line_paint.setColor(Color.RED)          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(10f)

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
    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
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
        val right_illumination:Bitmap = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
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
                if( touch_refinement_X (indent,circlex,width,size_field_x)>0)
                {
                    canvas?.drawBitmap( right_illumination, touch_refinement_X(indent,circlex, width, size_field_x), touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line), paint)
                }
            }

        }

    }

    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
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
            if (chek_win() == 2) {
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
        if (touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line) > 0 && touch_refinement_X(indent,circlex,width,size_field_x)>0)     //постановка нового обЪекта, проверка что на поле
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
                    if ((FIELD[X][Y] == 1 && Black_or_grey_chip == "black") || (FIELD[X][Y] == 2 && Black_or_grey_chip == "grey")   )       //если подсвечивается фишка
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