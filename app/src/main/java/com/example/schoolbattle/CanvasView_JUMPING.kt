package com.example.schoolbattle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View



class CanvasView_JUMPING(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var Black_or_grey_chip: String = "black"
    var paint : Paint  = Paint()          //ресурсы для рисования
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
        FIELD[0][4] = 1;FIELD[1][4] = 1;FIELD[2][4] = 1;FIELD[3][4] = 1;
        FIELD[0][5] = 1;FIELD[1][5] = 1;FIELD[2][5] = 1;FIELD[3][5] = 1;
        FIELD[0][6] = 1;FIELD[1][6] = 1;FIELD[2][6] = 1;FIELD[3][6] = 1;
        FIELD[0][7] = 1;FIELD[1][7] = 1;FIELD[2][7] = 1;FIELD[3][7] = 1;

        FIELD[4][0] = 2;FIELD[4][1] = 2;FIELD[4][2] = 2;FIELD[4][3] = 2;
        FIELD[5][0] = 2;FIELD[5][1] = 2;FIELD[5][2] = 2;FIELD[5][3] = 2;
        FIELD[6][0] = 2;FIELD[6][1] = 2;FIELD[6][2] = 2;FIELD[6][3] = 2;
        FIELD[7][0] = 2;FIELD[7][1] = 2;FIELD[7][2] = 2;FIELD[7][3] = 2;

    }




    var black_chip : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);       //картинки фишек и подсветки
    var grey_chip: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.grey);
    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = 150f           //полоска для рекламы
        size_field_x  = 8
        size_field_y  = 8
        step = width/size_field_x
        k = height-width-advertising_line

        //TODO() take field from database
        canvas?.drawColor(Color.WHITE)


        for(i in 0 until size_field_x+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(0f,k,width,k,Line_paint)
            k = k + step
        }
        k = 0f
        for(i in 0 until size_field_y+2)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k,height-advertising_line-width,k,height-advertising_line,Line_paint)
            k = k + step
        }


        val rigth_black_chip: Bitmap = Bitmap.createScaledBitmap(black_chip,width.toInt()/size_field_x, width.toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
        val right_grey_chip: Bitmap = Bitmap.createScaledBitmap(grey_chip,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        val right_illumination:Bitmap = Bitmap.createScaledBitmap(illumination,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        val right_green:Bitmap = Bitmap.createScaledBitmap(green,width.toInt()/size_field_x, width.toInt()/size_field_x, true);

        for( i in 0..7) // расстановка фишек
        {
            for(j in 0..7) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(rigth_black_chip, translate_from_Array_to_Graphics_X(i,step),
                        translate_from_Array_to_Graphics_Y(j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    canvas?.drawBitmap(right_grey_chip, translate_from_Array_to_Graphics_X(i,step),
                        translate_from_Array_to_Graphics_Y(j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }
        for (i in 0..7) {             //расстановка подсветки
            for (j in 0..7) {
                if (Array_of_illumination[i][j] == 1) {
                    canvas?.drawBitmap(
                        right_green, translate_from_Array_to_Graphics_X(i, step),
                        translate_from_Array_to_Graphics_Y(
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
            canvas?.drawBitmap( right_illumination, touch_refinement_X(circlex, width, size_field_x), touch_refinement_Y(circley, height, size_field_y, step, advertising_line), paint)
        }

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        circlex = event!!.x
        circley = event!!.y
        var X: Int = touch_refinement_for_Array_X(circlex, step)
        var Y: Int = touch_refinement_for_Array_Y(circley, height, size_field_y, step, advertising_line)      //перевод последнего нажатия в координаты массива
        if (touch_refinement_Y(circley, height, size_field_y, step, advertising_line) > 0)     //постановка нового обЪекта, проверка что на поле
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
                    if (Array_of_illumination[X][Y] == 1)     //если подсвечена область
                    {
                        FIELD[X][Y] = FIELD[lastX][lastY]         //перемещение фишки
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
        }
        Log.d("DOPO",PHASE.toString())
        lastX = X
        lastY = Y
        invalidate()
        return true
    }

}

//   canvas?.drawBitmap(    //подсветка
//        right_illumination, touch_refinement_X(circlex, width, size_field_x),
//          touch_refinement_Y(circley, height, size_field_y, step, advertising_line), paint
//     )