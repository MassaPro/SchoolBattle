package com.example.schoolbattle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
fun touch_refinement_X (x : Float,width1: Float):Float        //уточняет касания по оси x
{
    if(x<width1/3)
    {
        return 0f
    }
    if(x>width1/3 && x<width1*2/3f)
    {
        return width1/3f
    }
    return 2*width1/3f
}

fun touch_refinement_Y (y : Float,width1: Float,height1: Float):Float      //уточняет касания по оси Y
{
    if(y < height1 - width1)
    {
        return -1f        //если не в область тыкнули
    }
    if(y > height1 - width1/3f)
    {
        return height1 - width1/3f
    }
    if(y > height1 - width1*2/3f && y < height1 - width1/3f)
    {
        return height1 - width1*2/3f
    }
    return height1 - width1
}

fun touch_refinement_for_Array_X (x : Float,width1: Float):Int        //уточняет координаты в массиве 3*3 при касании
{
    if(x < width1/3f)
    {
        return 0
    }
    if(x > width1/3f && x < width1*2/3f)
    {
        return 1
    }
    return 2
}

fun touch_refinement_for_Array_Y (y : Float,width1: Float,height1: Float):Int      //уточняет координаты в массиве 3*3 при касании
{
    if(y < height1 - width1)
    {
        return 0      //если не в область тыкнули
    }
    if(y > height1 - width1/3f)
    {
        return 2
    }
    if(y > height1 - width1*2/3f && y < height1 - width1/3f)
    {
        return 1
    }
    return 0
}

fun translate_from_Array_to_Graphics_X(x:Int,width1: Float ):Float    //переводит массивные координаты в графически
{
    if(x == 0)
    {
        return 0f
    }
    if(x==1)
    {
        return width1/3f
    }
    return width1*2/3f
}

fun translate_from_Array_to_Graphics_Y(y:Int,width1: Float,height1: Float ):Float    //переводит массивные координаты в графически
{
    if (y==0)
    {
        return height1 - width1
    }
    if (y==1)
    {
        return height1 - width1*2/3f
    }
    return height1 - width1/3f
}

class CanvasView( context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var Exit : Int = 0
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f

    var paint : Paint  = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var FIELD = Array(3){IntArray(3)}


    init{
        Line_paint.setColor(Color.RED)          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(10f)

        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..2) {
            for(j in 0 ..2) {
                FIELD[i][j] = 0  //не заполненный
            }
        }
        FIELD[2][2] = 1
        FIELD[2][0] = 2
        FIELD[0][0] = 1
        FIELD[0][2] = 2
        var cross_or_nul : String = "Cross"
    }




    var icon_cross : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross);       //картинки крестиков и нулей
    var icon_null: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle_null);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawColor(Color.WHITE)
        var width = getWidth().toFloat()
        var height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)

        canvas?.drawLine(width/3,height,width/3,height - width,Line_paint)      //рисуем линии (поле)
        canvas?.drawLine(width*2/3,height,width*2/3,height - width,Line_paint)
        canvas?.drawLine(0f,height - 2*width/3,width,height - 2*width/3,Line_paint)
        canvas?.drawLine(0f,height - 1*width/3,width,height - 1*width/3,Line_paint)

        val right_icon_cross: Bitmap = Bitmap.createScaledBitmap(icon_cross,width.toInt()/3, width.toInt()/3, true); //подгоняем картинку под размеры экрана телефона
        val right_icon_null: Bitmap = Bitmap.createScaledBitmap(icon_null,width.toInt()/3, width.toInt()/3, true);

        for( i in 0..2) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..2) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(right_icon_cross, translate_from_Array_to_Graphics_X(i,width),
                        translate_from_Array_to_Graphics_Y(j,width,height),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    canvas?.drawBitmap(right_icon_null, translate_from_Array_to_Graphics_X(i,width),
                        translate_from_Array_to_Graphics_Y(j,width,height),paint)
                }
            }
        }
        if (touch_refinement_Y(circley,width,height)!=-1f)     //постановка нового обЪекта
        {
            var X: Int = touch_refinement_for_Array_X(circlex,width)
            var Y: Int = touch_refinement_for_Array_Y(circley,width,height)    //координаты нажимаего для массива

            if (FIELD[X][Y]==0)
            {
                canvas?.drawBitmap(right_icon_cross,touch_refinement_X(circlex,width),touch_refinement_Y(circley,width,height),paint)
            }
        }

        if(FIELD[0][0]==1 && FIELD[1][1]==1 && FIELD[2][2] ==1)
        {
            Exit = 1
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        circlex =  event!!.x
        circley =  event!!.y

        invalidate()
        return true
    }
}