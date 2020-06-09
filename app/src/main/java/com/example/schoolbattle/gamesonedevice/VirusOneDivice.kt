package com.example.schoolbattle.gamesonedevice

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.schoolbattle.CONTEXT
import com.example.schoolbattle.R
import com.example.schoolbattle.Show_Result_one_Device
import kotlinx.android.synthetic.main.activity_virus_one_divice.*

class VirusOneDivice : AppCompatActivity() {

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CONTEXT = this
        setContentView(R.layout.activity_virus_one_divice)
        signature_canvas_virus_one_device.activity = this
        comback_virus_one_divice.setOnClickListener{
            if(signature_canvas_virus_one_device.History.size>0)
            {
                signature_canvas_virus_one_device.History.removeLast()
                signature_canvas_virus_one_device.red_or_blue =0
                signature_canvas_virus_one_device.COUNTER_RED = 0
                signature_canvas_virus_one_device.COUNTER_BLUE = 0
                for(i in 0 until signature_canvas_virus_one_device.FIELD.size)
                {
                    for(j in 0 until signature_canvas_virus_one_device.FIELD[0].size)
                    {
                        signature_canvas_virus_one_device.FIELD[i][j] = 0
                    }
                }
                for(i in signature_canvas_virus_one_device.History)
                {
                    signature_canvas_virus_one_device.FIELD[i.first][i.second] = i.third
                    signature_canvas_virus_one_device.red_or_blue = (signature_canvas_virus_one_device.red_or_blue+1)%6
                    if(2- i.third%2 == 1)
                    {
                        signature_canvas_virus_one_device.COUNTER_RED++
                    }
                    else
                    {
                        signature_canvas_virus_one_device.COUNTER_BLUE++
                    }
                }
                signature_canvas_virus_one_device.invalidate()
            }
        }
    }
}

class CanvasView_VIRUS (context: Context, attrs: AttributeSet?) : View(context, attrs) {
    fun correction_touch(x: Float, y: Float): Boolean // если нажали примерно туда
    {
        if ((circlex - x) * (circlex - x) + (circley - y) * (circley - y) < (step / 2f) * (step / 2f)) {
            return true
        }
        return false
    }

    fun is_anround(x: Int,y: Int,z: Int): Boolean
    {
        var k: Int = 0
        if(x > 0)
        {
            if(FIELD[x-1][y] == z || FIELD[x-1][y] - 2 == z)
            {
                k++
            }
        }
        if(x<9)
        {
            if(FIELD[x+1][y] == z || FIELD[x+1][y] -  2 == z)
            {
                k++
            }
        }
        if(y > 0)
        {
            if(FIELD[x][y-1] == z || FIELD[x][y-1] - 2 == z)
            {
                k++
            }
        }
        if(y<9)
        {
            if(FIELD[x][y+1] == z || FIELD[x][y+1] - 2 == z)
            {
                k++
            }
        }
        if(k > 0)
        {
            return true
        }

        return false
    }

    fun check_win(): Int
    {
        if(COUNTER_RED==0 || COUNTER_BLUE==0)
        {
            return 0
        }
        var cnt1: Int = 0
        var cnt2: Int = 0
        for(i in FIELD)
        {
            for(j in i)
            {
                if(j!=0)
                {
                    if(j== 1)
                    {
                        cnt1++
                    }
                    if(j == 2)
                    {
                        cnt2++
                    }
                }
            }
        }
        if(cnt1 == 0 && COUNTER_BLUE>0 && COUNTER_RED>0)
        {
            return 2
        }
        if(cnt2 == 0 && COUNTER_BLUE>0 && COUNTER_RED>0)
        {
            return 1
        }
        if(red_or_blue<3)
        {
            for (i in 0 until FIELD.size) {
                for (j in 0 until FIELD[0].size)
                {
                    if(FIELD[i][j] == 1 || FIELD[i][j] == 3)
                    {
                        if(i>0)
                        {
                            if(FIELD[i-1][j]==0 ||FIELD[i-1][j] == 2)
                            {
                                return 0
                            }
                        }
                        if(i<FIELD.size-1)
                        {
                            if(FIELD[i+1][j]==0 ||FIELD[i+1][j] == 2)
                            {
                                return 0
                            }
                        }
                        if(j>0)
                        {
                            if(FIELD[i][j-1]==0 ||FIELD[i][j-1] == 2)
                            {
                                return 0
                            }
                        }
                        if(j<FIELD[0].size-1)
                        {
                            if(FIELD[i][j+1]==0 ||FIELD[i][j+1] == 2)
                            {
                                return 0
                            }
                        }
                    }
                }
            }
            return 2
        }
        else
        {
            for (i in 0 until FIELD.size) {
                for (j in 0 until FIELD[0].size)
                {
                    if(FIELD[i][j] == 1 || FIELD[i][j] == 3 )
                    {
                        if(i>0)
                        {
                            if(FIELD[i-1][j]==0 ||FIELD[i-1][j] == 1)
                            {
                                return 0
                            }
                        }
                        if(i<FIELD.size-1)
                        {
                            if(FIELD[i+1][j]==0 ||FIELD[i+1][j] == 1)
                            {
                                return 0
                            }
                        }
                        if(j>0)
                        {
                            if(FIELD[i][j-1]==0 ||FIELD[i][j-1] == 1)
                            {
                                return 0
                            }
                        }
                        if(j<FIELD[0].size-1)
                        {
                            if(FIELD[i][j+1]==0 ||FIELD[i][j+1] == 1)
                            {
                                return 0
                            }
                        }
                    }
                }
            }
            return 1
        }
    }



    lateinit var activity: Activity

    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()

    var red_or_blue: Int            // всего 6 фаз
    var circlex: Float = 0f   //координаты нажатия
    var circley: Float = 0f
    var indent: Float = 0f      //оступ


    var paint : Paint  = Paint()
    var paint_circle: Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var paint_rib_1: Paint = Paint()
    var paint_rib_2: Paint = Paint()


    var FIELD = Array(10) { IntArray(10) }     //для фишеК

    var COUNTER_RED : Int = 0
    var COUNTER_BLUE: Int = 0

    var width: Float = 0f
    var height: Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line: Float = 0f         //полоска для рекламы
    var size_field_x: Int = 0
    var size_field_y: Int = 0
    var step: Float = 0f


    init {

        red_or_blue = 0
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(5f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(5f)


        for (i in 0 until FIELD.size) {
            for (j in 0 until FIELD[i].size) {
                FIELD[i][j] = 0
            }
        }

    }


    var virus_1: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.virus1
    );
    var virus_2: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.virus2
    );
    var tower_1: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.tower1
    );
    var tower_2: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.tower2
    )


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawColor(Color.WHITE)

        size_field_x = 10
        size_field_y = 10
        indent = 0f
        //(getWidth().toFloat() / (size_field_x.toFloat() + 1f)) / 2f //оступ, чтобы можно было тыкнуть в границу (половина клетки)
        width = getWidth().toFloat() - 2 * indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = (height - width)/2           //полоска для рекламы
        step = width / size_field_x

        val rigth_virus_1: Bitmap = Bitmap.createScaledBitmap(virus_1,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        val rigth_virus_2: Bitmap = Bitmap.createScaledBitmap(virus_2,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        val rigth_tower_1: Bitmap = Bitmap.createScaledBitmap(tower_1,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        val rigth_tower_2: Bitmap = Bitmap.createScaledBitmap(tower_2,width.toInt()/size_field_x, width.toInt()/size_field_y, true);

        var k: Float = height - width  - advertising_line

        for (i in 0 until size_field_y + 1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent, k, width + indent, k, Line_paint)
            k = k + step
        }

        k = indent
        for (i in 0 until size_field_x + 1)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(
                k,
                height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat()),
                k,
                height - advertising_line,
                Line_paint
            )
            k = k + step
        }

        var X1 : Float = indent
        var Y1: Float = height - advertising_line -  width
        for(i in 0 until size_field_x)
        {
            for(j in 0 until size_field_y)
            {
                if(FIELD[i][j] == 1)
                {
                    canvas?.drawBitmap(rigth_virus_1,X1,Y1,paint)
                }
                if(FIELD[i][j] == 2)
                {
                    canvas?.drawBitmap(rigth_virus_2,X1,Y1,paint)
                }
                if(FIELD[i][j] == 3)
                {
                    canvas?.drawBitmap(rigth_tower_1,X1,Y1,paint)
                }
                if(FIELD[i][j] == 4)
                {
                    canvas?.drawBitmap(rigth_tower_2,X1,Y1,paint)
                }

                Y1 += step
            }
            X1 += step
            Y1 = height - advertising_line -  width
        }


    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        circlex = event!!.x
        circley = event!!.y

        if(check_win()>0)
        {
            var dialog: Show_Result_one_Device? = null
            dialog = Show_Result_one_Device(activity)
            if(check_win()==1)
            {
                dialog?.showResult_one_device("КРАСНЫЕ ПОБЕДИЛИ","VirusGame",activity)
                return true
            }
            if(check_win()==2)
            {
                dialog?.showResult_one_device("CИНИЕ ПОБЕДИЛИ","VirusGame",activity)
                return true
            }
        }

        if(event!!.getAction()  == MotionEvent.ACTION_UP) {


            var X1: Float = indent + step / 2
            var Y1: Float = height - advertising_line - width + step / 2

            for (i in 0 until size_field_x) {
                for (j in 0 until size_field_y) {
                    if (correction_touch(X1, Y1)) {
                        if (FIELD[i][j] == 0) {
                            if (red_or_blue < 3) {
                                if (COUNTER_RED == 0) {
                                    if ((i == 0 && j == 0) || (i == 9 && j == 0) || (i == 9 && j == 9) || (i == 0 && j == 9)) {
                                        FIELD[i][j] = 1
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_RED++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        invalidate()
                                    }
                                } else {
                                    if (is_anround(i, j, 1)) {
                                        FIELD[i][j] = 1
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_RED++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        invalidate()
                                    }
                                }

                            } else {
                                if (COUNTER_BLUE == 0) {
                                    if ((i == 0 && j == 0) || (i == 9 && j == 0) || (i == 9 && j == 9) || (i == 0 && j == 9)) {
                                        FIELD[i][j] = 2
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_BLUE++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        invalidate()
                                    }
                                } else {
                                    if (is_anround(i, j, 2)) {
                                        FIELD[i][j] = 2
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_BLUE++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        invalidate()
                                    }
                                }
                            }
                        } else {
                            if (FIELD[i][j] == 1 && red_or_blue > 2) {
                                if (is_anround(i, j, 2)) {
                                    FIELD[i][j] = 4
                                    History.add(Triple(i,j,FIELD[i][j]))
                                    COUNTER_BLUE++
                                    red_or_blue = (red_or_blue + 1) % 6
                                    invalidate()
                                }
                            } else {
                                if (FIELD[i][j] == 2 && red_or_blue < 3) {
                                    if (is_anround(i, j, 1)) {
                                        FIELD[i][j] = 3
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_RED++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        invalidate()
                                    }
                                }
                            }
                        }


                    }
                    Y1 += step
                }
                X1 += step
                Y1 = height - advertising_line - width + step / 2
            }
        }
        return true
    }


}