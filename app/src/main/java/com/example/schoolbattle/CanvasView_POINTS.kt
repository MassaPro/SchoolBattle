package com.example.schoolbattle

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

//TODO , рисовать ребра только один раз до этого узнав, также можно не включать в цепочки вершины которые окружены 6 такими же вершинами


class CanvasView_POINTS(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (step/2f)*(step/2f))
        {
            return true
        }
        return false
    }
    fun Is_suitable_for_the_chain(x :Int,y : Int): Boolean     //проверяет нужно ли включать вершину в цепочку
    {
        if(x ==  0)
        {
            if(FIELD[x][y]==FIELD[x+1][y])
            {
                return false
            }
            return true
        }
        if(x == 10)
        {
            if(FIELD[x][y] == FIELD[x-1][y])
            {
                return false
            }
            return true
        }
        if(y == 0)
        {
            if(FIELD[x][y] == FIELD[x][y+1])
            {
                return false
            }
            return true
        }
        if(y == 15)
        {
            if(FIELD[x][y] == FIELD[x][y-1])
            {
                return false
            }
            return true
        }
        var k :Int = 0
        for(i in -1..1)
            for(j in -1..1)
                if(FIELD[x][y] == FIELD[x+i][y+j])
                    k++ //узнаем количество соседей
        if(k>6)
        {
            return false
        }
        return true
    }
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

    var FIELD = Array(11){IntArray(16)}     //для фишеК
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






    var Is_defined_TREE_OF_WAYS : Boolean = false
    init{

        red_or_blue = "red"
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(5f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(5f)

       for(i in 0 until FIELD.size)
       {
           for(j in 0 until FIELD[i].size)
           {
               FIELD[i][j] = 0
           }
       }

        for(i in 0 until CELLS.size)
        {
            for(j in 0 until CELLS[i].size)
            {
                for(z in 0 until CELLS[i][j].size)
                {
                    CELLS[i][j][z] = 0
                }
            }
        }
    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue);



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        radius_of_point = 8f
        size_field_x  = 10
        size_field_y  = 15
        indent = (getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = 150f           //полоска для рекламы

        step = width/size_field_x
        k = height-width*(size_field_y.toFloat()/size_field_x.toFloat())-advertising_line

        canvas?.drawColor(Color.WHITE)

        for(i in 0 until size_field_y+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            k = k + step
        }

        k = indent
        for(i in 0 until size_field_x+1)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat()),k,height-advertising_line,Line_paint)
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
                if(FIELD[i][j] == 1)
                {
                    canvas?.drawCircle(x,y,radius_of_point*1.5f,paint_rib_1)
                }
                else
                {
                    if(FIELD[i][j] == 2)
                    {
                        canvas?.drawCircle(x,y,radius_of_point*1.5f,paint_rib_2)
                    }
                    else
                    {
                        canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                    }
                }

                y += step
            }
            x += step
            y  = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }



        Log.d("POP",TREE_OF_WAYS.size.toString())
        for (i in 0 until TREE_OF_WAYS.size)
        {
            if (TREE_OF_WAYS[i][TREE_OF_WAYS[i].size - 1] == TREE_OF_WAYS[i][0] && TREE_OF_WAYS[i].size > 4)  //если цепочка зациклилась
            {

                for (j in 0 until TREE_OF_WAYS[i].size - 1)
                {

                }

            }
        }


    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Is_defined_TREE_OF_WAYS = true
        circlex = event!!.x
        circley = event!!.y

        x = indent
        y = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        for(i in 0..size_field_x)                    //вырисовка точек
        {
            for(j in 0..size_field_y)
            {
                if(correction_touch(x,y))
                {
                    if(FIELD[i][j] == 0)
                    {
                        if(red_or_blue == "red")
                        {
                            red_or_blue = "blue"
                            FIELD[i][j] = 1
                            lastx = i
                            lasty = j
                        }
                        else
                        {
                            red_or_blue = "red"
                            FIELD[i][j]  = 2
                            lastx = i
                            lasty = j
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

        var p : Int = lastx       //начинаем строить цепочки в
        var q : Int = lasty

        A.clear()
        TREE_OF_WAYS.clear()
        A.add(Pair(p,q))
        TREE_OF_WAYS.add(A)


        Log.d("TODO", TREE_OF_WAYS[0][0].first.toString() + " " + TREE_OF_WAYS[0][0].second.toString() )
        var counter: Int = 1
        for(t in 0..20)
        {
            for(i in 0 until TREE_OF_WAYS.size)
            {

        //        Log.d("nok",TREE_OF_WAYS.size.toString())
                var a: Int = TREE_OF_WAYS[i][TREE_OF_WAYS[i].size-1].first    //координаты последней вершины в цепочку
                var b: Int = TREE_OF_WAYS[i][TREE_OF_WAYS[i].size-1].second
                //Log.d("nok",a.toString()+b.toString())
                if(TREE_OF_WAYS[i].size >= counter)
                {
                    if((a==lastx && b == lasty && counter == 1)||(a!=lastx || b!=lasty))
                    {
                        Log.d("counter",counter.toString())
                        if (a - 1 >= 0) {

                            if (FIELD[a - 1][b] == FIELD[lastx][lasty] && Is_suitable_for_the_chain(a-1,b))        //если вершина того же цвета, что и исходная
                            {
                                Log.d("hhhh","zero")
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a - 1,
                                            b
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a - 1 == lastx && b == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a - 1,
                                            b
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }


                            }
                        }
                        if (a + 1 < 11) {


                            if (FIELD[a + 1][b] == FIELD[lastx][lasty]  && Is_suitable_for_the_chain(a+1,b))        //если вершина того же цвета, что и исходная
                            {
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a + 1,
                                            b
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a + 1 == lastx && b == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a + 1,
                                            b
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }
                            }
                        }
                        if (b - 1 >= 0) {

                            if (FIELD[a][b - 1] == FIELD[lastx][lasty]  && Is_suitable_for_the_chain(a,b-1))        //если вершина того же цвета, что и исходная
                            {
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a,
                                            b - 1
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a == lastx && b - 1 == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a,
                                            b - 1
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }
                            }
                        }
                        if (b + 1 < 16) {

                            if (FIELD[a][b + 1] == FIELD[lastx][lasty]  && Is_suitable_for_the_chain(a,b+1))        //если вершина того же цвета, что и исходная
                            {
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a,
                                            b + 1
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a == lastx && b + 1 == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(Pair(a, b + 1))              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }
                            }
                        }
                        if (a - 1 >= 0 && b - 1>=0) {

                            if (FIELD[a - 1][b-1] == FIELD[lastx][lasty]  && Is_suitable_for_the_chain(a-1,b-1))        //если вершина того же цвета, что и исходная
                            {
                                Log.d("hhhh","zero")
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a - 1,
                                            b -1
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a - 1 == lastx && b-1 == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a - 1,
                                            b -1
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }


                            }
                        }
                        if (a - 1 >= 0 && b + 1<16) {

                            if (FIELD[a - 1][b+1] == FIELD[lastx][lasty]  && Is_suitable_for_the_chain(a-1,b+1))        //если вершина того же цвета, что и исходная
                            {
                                Log.d("hhhh","zero")
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a - 1,
                                            b + 1
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a - 1 == lastx && b+1 == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a - 1,
                                            b + 1
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }


                            }
                        }
                        if (a + 1 <11 && b -1>=0) {

                            if (FIELD[a + 1][b-1] == FIELD[lastx][lasty] && Is_suitable_for_the_chain(a+1,b-1))        //если вершина того же цвета, что и исходная
                            {
                                Log.d("hhhh","zero")
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a + 1,
                                            b -1
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a + 1 == lastx && b-1 == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a + 1,
                                            b -1
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }


                            }
                        }
                        if (a + 1 < 11 && b + 1 < 16) {

                            if (FIELD[a + 1][b+1] == FIELD[lastx][lasty]  && Is_suitable_for_the_chain(a+1,b+1))        //если вершина того же цвета, что и исходная
                            {
                                Log.d("hhhh","zero")
                                var flag: Boolean = true
                                for (j in 0 until TREE_OF_WAYS[i].size) {
                                    if (TREE_OF_WAYS[i][j] == Pair(
                                            a + 1,
                                            b + 1
                                        )
                                    )       //если  в цепочке была уже эта точка
                                    {
                                        flag = false
                                    }
                                }
                                if (a + 1 == lastx && b+1 == lasty && counter > 2) {
                                    flag = true
                                }
                                if (flag) {
                                    var NEW_CHAIN: MutableList<Pair<Int, Int>> = TREE_OF_WAYS[i].toMutableList()
                                    NEW_CHAIN.add(
                                        Pair(
                                            a + 1,
                                            b + 1
                                        )
                                    )              //состовляем новую вариацию цепочки
                                    TREE_OF_WAYS.add(NEW_CHAIN)           //добоваляем новую цепочку в список исходных
                                }


                            }
                        }

                    }
                }
            }
            counter++
            for(i in 0 until TREE_OF_WAYS.size)
            {
                if(TREE_OF_WAYS[0].size==1)
                {
                    TREE_OF_WAYS.removeAt(0)
                }
                else
                {
                    if(TREE_OF_WAYS[0][0]!=TREE_OF_WAYS[0][TREE_OF_WAYS[0].size-1] && TREE_OF_WAYS[0].size<counter)
                    {
                        TREE_OF_WAYS.removeAt(0)
                    }
                }
            }
        }

        var s: String = " "
        for(i in 0 until TREE_OF_WAYS.size)
        {
            for(j in 0 until TREE_OF_WAYS[i].size)
            {
                s+= "(" +TREE_OF_WAYS[i][j].first.toString()+ ", " + TREE_OF_WAYS[i][j].second.toString() + ") "
            }
            Log.d("Str",s)
            s = " "
        }
        Log.d("Str","jojojojojojojojojojojojojojojojojoj")
        Log.d("T_R",TREE_OF_WAYS.size.toString())
        invalidate()
        return true
    }

}