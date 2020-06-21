package com.example.schoolbattle.gameswithcomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.rgb
import android.graphics.Color.argb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_x_o_game_with_computer.*

val XOGame_withComp: Activity = Activity()

class XOGame_withComputer : AppCompatActivity() {
    private var dialog: Show_Result_with_Computer? = null
    private var dialog_rules: Show_rules? = null
    private var dialog_parametrs: Show_parametr_with_computer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x_o_game_with_computer)
        signature_canvas_xog_with_computer.activity = this
        CONTEXT = this

        signature_canvas_xog_with_computer.t1 = findViewById(R.id.name_player1_with_computer_xog) as TextView
        signature_canvas_xog_with_computer.t2 = findViewById(R.id.name_player2_with_computer_xog) as TextView
        signature_canvas_xog_with_computer.t1.text = "Игрок 1"
        signature_canvas_xog_with_computer.t2.text = "Игрок 2"

        if(Design == "Egypt" ) {
            name_player1_with_computer_xog.setTextColor(Color.BLACK)
            name_player2_with_computer_xog.setTextColor(Color.BLACK)
            name_player1_with_computer_xog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_xog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_xog.setTextSize(20f)
            name_player1_with_computer_xog.setTextSize(20f)
            button_player_1_xog_with_computer.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_xog_with_computer.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_xog_with_computer.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_xog_with_computer.setBackgroundResource(R.drawable.circle_egypt);
            label_with_computer.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_xog_with_computer.setBackgroundColor(rgb(224,164,103))
            to_back_xog_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_xog_with_computer.setBackgroundColor(argb(0,0,0,0))
            toolbar2_xog_with_computer.setBackgroundColor(argb(0,0,0,0))
        }
        else if(Design == "Casino" ) {
            name_player1_with_computer_xog.setTextColor(Color.YELLOW)
            name_player2_with_computer_xog.setTextColor(Color.YELLOW)
            name_player1_with_computer_xog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_xog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_xog.setTextSize(20f)
            name_player1_with_computer_xog.setTextSize(20f)
            button_player_1_xog_with_computer.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_xog_with_computer.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_xog_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_xog_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_xog_with_computer.setBackgroundColor(argb(0,224, 164, 103))
            to_back_xog_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_xog_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
        }


        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("xog_with_computer", "")
            editor.apply()
        }

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var data_from_memory = prefs.getString("xog_with_computer", "").toString()
        //Log.w("TAG123", data_from_memory)
        for (i in 0..data_from_memory.length - 1) {
            if (i % 2 == 0) {
                if (i % 4 == 0) {
                    signature_canvas_xog_with_computer.FIELD[data_from_memory[i] - '0'][data_from_memory[i + 1] - '0'] = 1
                } else {
                    signature_canvas_xog_with_computer.FIELD[data_from_memory[i] - '0'][data_from_memory[i + 1] - '0'] = 2
                }
            }
        }
        signature_canvas_xog_with_computer.invalidate()


        /*to_back_xog_with_computer.setOnClickListener {
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 3)
            this.finish()
            startActivity(intent)
        }*/


        bottom_navigation_xog_with_computer.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@XOGame_withComputer
                        )
                    dialog_rules?.show("XOGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_with_computer(this@XOGame_withComputer)
                    dialog_parametrs?.showResult_with_computer(this, "XOGame")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, XOGame_withComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    var data_from_memory = prefs.getString("xog_with_computer", "").toString()
                    var sz_of_delete = 4
                    if (signature_canvas_xog_with_computer.EXODUS == 1)
                        sz_of_delete = 2
                    if (data_from_memory.length >= 4) {
                        var new_data = ""
                        for (i in 0..data_from_memory.length - sz_of_delete - 1)
                            new_data += data_from_memory[i]

                        for (i in (data_from_memory.length - sz_of_delete - 1)..(data_from_memory.length - 1)) {
                            if (i % 2 == 0) {
                                signature_canvas_xog_with_computer.FIELD[data_from_memory[i] - '0'][data_from_memory[i + 1] - '0'] = 0
                            }
                        }

                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_with_computer", new_data)
                        editor.apply()

                        signature_canvas_xog_with_computer.invalidate()

                        signature_canvas_xog_with_computer.blocked = false
                        signature_canvas_xog_with_computer.EXODUS = 0
                        signature_canvas_xog_with_computer.cross_or_nul = "cross"
                    }
                }

            }
            true
        }

    }
}


class CanvasView_xog_with_computer(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    fun touch_refinement_X (x : Float,width1: Float,size_field_x1:Int ):Float        //уточняет касания по оси x
    {
        return ((x.toInt()/(width1/size_field_x1).toInt()).toFloat()*width1/size_field_x1).toFloat()
    }

    fun touch_refinement_Y (y : Float,height1: Float,size_field_y1:Int,step: Float,advertising_line_1:Float):Float      //уточняет касания по оси Y
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

    fun touch_refinement_for_Array_X (x : Float,step:Float):Int        //уточняет координаты в массиве  при касании
    {
        if(x<0)
        {
            return -1
        }
        return (x/step).toInt()
    }

    fun touch_refinement_for_Array_Y (y : Float,height1: Float,size_field_y1: Int,step: Float,advertising_line_1:Float):Int      //уточняет координаты в массиве  при касании
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

    private fun translate_from_Array_to_Graphics_X(x:Int, step: Float):Float    //переводит массивные координаты в графически
    {
        return x*step
    }

    fun translate_from_Array_to_Graphics_Y(y:Int,height1: Float,size_field_y1: Int,step: Float,advertising_line_1: Float):Float    //переводит массивные координаты в графически
    {
        return y*step + height1 - size_field_y1*step - advertising_line_1
    }
    fun checkForWin_another_fun(): MutableList<Int> {
        var list_x: MutableList<Int> = mutableListOf(1, 1, 1, 0, -1, -1, -1, 0)
        var list_y: MutableList<Int> = mutableListOf(-1, 0, 1, 1, 1, 0, -1, -1)

        var ans = mutableListOf(0)
        for (i in 0..6) {
            for (j in 0..5) {
                if (FIELD[i][j] != 0) {
                    for (k in 0..7) {
                        var fl = 0
                        for (pos in 0..2) {
                            //Log.w("TAG", "$i ${list_x[k]} $pos")
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

    lateinit var activity: Activity

    lateinit var t1: TextView
    lateinit var t2: TextView
    var width : Float = 0f
    var height: Float = 0f
    var indent : Float = 20f
    //ширина и высота экрана (от ширины в основном все зависит)

    var EXODUS : Int = 0
    var size_field_x: Int = 7
    var size_field_y: Int = 6
    var step: Float = width/size_field_x
    var advertising_line: Float = (height - step * 6) / 2

    var k: Float = height-width-advertising_line + step
    var blocked = false

    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f

    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var Line_paint_1: Paint = Paint()
    var FIELD = Array(7){IntArray(6)}
    var cross_or_nul: String

    init {
        Line_paint.color = Color.RED          //ресур для линий (ширина и цвет)
        Line_paint.strokeWidth = 10f

        Line_paint_1.color = Color.RED          //ресур для линий (ширина и цвет)
        Line_paint_1.strokeWidth = 20f

        if(Design == "Egypt")
        {
            Line_paint.color = Color.BLACK          //ресур для линий (ширина и цвет)
            Line_paint.strokeWidth = 7f

            Line_paint_1.color = Color.BLACK          //ресур для линий (ширина и цвет)
            Line_paint_1.strokeWidth = 20f
        }
        else if (Design == "Casino")
        {
            Line_paint.color = Color.WHITE          //ресур для линий (ширина и цвет)
            Line_paint.strokeWidth = 7f

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

    private fun moveChecker(x: Int, y: Int): Boolean {
        if (x > 6 || x < 0 || y > 5 || y < 0 || (y + 1 <= 5 && FIELD[x][y + 1] == 0)) {
            return false
        }
        return true
    }


    var cross_normal : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_normal)       //картинки крестиков и нулей
    var null_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_normal)

    var cross_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_egypt)       //картинки крестиков и нулей
    var null_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle_egypt)

    var cross_casino : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross_casino)       //картинки крестиков и нулей
    var null_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.null_casino)

    // var BackgroundColor_Egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_egypt)
    var icon_green : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination)
    var icon_grenn_Egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ram_egypt_xog)


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        //TODO() take field from database
        canvas?.drawColor(Color.WHITE)
        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()
        //ширина и высота экрана (от ширины в основном все зависит)

        size_field_x = 7
        size_field_y = 6
        step = width/size_field_x
        advertising_line = (height - step * 6) / 2

        var k: Float = height-width-advertising_line + step
        for(i in 0 until size_field_x)
        {
            canvas?.drawLine(0f,k,width,k,Line_paint)
            k += step
        }
        k = 0f
        for(i in 0 until size_field_y+2)
        {
            if(i == 0 || i == size_field_y+1)
            {
                canvas?.drawLine(k,height-advertising_line-width+step,k,height-advertising_line,Line_paint_1)
            }
            else
            {
                canvas?.drawLine(k,height-advertising_line-width+step,k,height-advertising_line,Line_paint)
            }
            k += step
        }

        var right_cross: Bitmap  //подгоняем картинку под размеры экрана телефона
        var right_null: Bitmap
        var right_green: Bitmap

        right_cross = Bitmap.createScaledBitmap(cross_normal,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
        right_null = Bitmap.createScaledBitmap(null_normal,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
        right_green = Bitmap.createScaledBitmap(icon_green, (width.toInt() - 2 * indent.toInt()) / size_field_x, (width.toInt() - 2 * indent.toInt()) / size_field_x, true)
        if(Design == "Egypt")
        {
            right_cross = Bitmap.createScaledBitmap(cross_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_null = Bitmap.createScaledBitmap(null_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_green = Bitmap.createScaledBitmap(icon_grenn_Egypt, (width.toInt() - 2 * indent.toInt()) / size_field_x, (width.toInt() - 2 * indent.toInt()) / size_field_x, true)
        }
        else if(Design == "Casino") {
            right_cross = Bitmap.createScaledBitmap(cross_casino,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_null = Bitmap.createScaledBitmap(null_casino,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
        }

        var countPos = 0
        for( i in 0..6) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..5) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    countPos++
                    canvas?.drawBitmap(right_cross, translate_from_Array_to_Graphics_X(i,step),
                        translate_from_Array_to_Graphics_Y(j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    countPos++
                    canvas?.drawBitmap(right_null, translate_from_Array_to_Graphics_X(i,step),
                        translate_from_Array_to_Graphics_Y(j,height,size_field_y,step,advertising_line),paint)
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
                var a_1: Float =  translate_from_Array_to_Graphics_X(checkForWin_another_fun()[counter],step)
                var a_2: Float = translate_from_Array_to_Graphics_Y(checkForWin_another_fun()[counter+1].toInt(), height,size_field_y, step, advertising_line)
                canvas?.drawBitmap(right_green,a_1,a_2,paint)
                counter += 2
            }

        }

        if (countPos == 42) {
            EXODUS = 3
        }


        var dialog: Show_Result_with_Computer? = null


        if(EXODUS == 1)
        {
            dialog =
                Show_Result_with_Computer(activity)
            dialog?.showResult_with_Computer("Победа","XOGame",activity)

        }
        if(EXODUS == 2)
        {
            dialog =
                Show_Result_with_Computer(activity)
            dialog?.showResult_with_Computer("Поражение","XOGame",activity)
        }
        if(EXODUS == 3)
        {
            dialog =
                Show_Result_with_Computer(activity)
            dialog?.showResult_with_Computer("Ничья","XOGame",activity)
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        if(blocked)
        {
            return true
        }
        circlex =  event!!.x
        circley =  event!!.y
        //Log.w("ppppppp",circlex.toString() +" "+ circley.toString() + width.toString() + " " + touch_refinement_Y(circley,height,size_field_y,step,advertising_line).toString())
        if (touch_refinement_Y(circley,height,size_field_y,step,advertising_line)>0) {    //постановка нового обЪекта
            //Log.w("ppppppp","LOL")
            var X: Int = touch_refinement_for_Array_X(circlex,step)
            var Y: Int = touch_refinement_for_Array_Y(circley,height,size_field_y,step,advertising_line)    //координаты нажимаего для массива

            if (moveChecker(X, Y) && FIELD[X][Y]==0)
            {
                blocked = true
                var a:Float = circlex
                var b:Float = circley
                if(cross_or_nul=="cross") {
                    FIELD[X][Y] = 1
                    //positionData.child("$X").child("$Y").setValue(1)
                    val prefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    var data_from_memory = prefs.getString("xog_with_computer", "")
                    data_from_memory += X.toString()
                    data_from_memory += Y.toString()
                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("xog_with_computer", data_from_memory)
                    editor.apply()
                    cross_or_nul = "null"
                    //Exit = 1
                } else {
                    Log.w("TAG666", "ban")
                    FIELD[X][Y] = 2
                    //positionData.child("$X").child("$Y").setValue(2)
                    val prefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    var data_from_memory = prefs.getString("xog_with_computer", "")
                    data_from_memory += X.toString()
                    data_from_memory += Y.toString()
                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("xog_with_computer", data_from_memory)
                    editor.apply()
                    //TODO setValue to database
                    cross_or_nul = "cross"
                }

                invalidate()
                if (checkForWin_another_fun().size != 9) {

                    var find_x = 0
                    var find_y = 0
                    var fla = 0
                    var list_x: MutableList<Int> = mutableListOf(1, 1, 1, 0, -1, -1, -1, 0)
                    var list_y: MutableList<Int> = mutableListOf(-1, 0, 1, 1, 1, 0, -1, -1)

                    fun if_fla_is_1() {
                        FIELD[find_x][find_y] = 2
                        val prefs =
                            context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                        var data_from_memory = prefs.getString("xog_with_computer", "")
                        data_from_memory += find_x.toString()
                        data_from_memory += find_y.toString()
                        val editor =
                            context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                                .edit()
                        editor.putString("xog_with_computer", data_from_memory)
                        editor.apply()
                        cross_or_nul = "cross"
                    }

                    val handler = android.os.Handler()
                    handler.postDelayed({
                        for (j in 5 downTo 0) {
                            for (i in 0..6) {
                                if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                    FIELD[i][j] = 2
                                    if (checkForWin_another_fun().size == 9) {
                                        FIELD[i][j] = 0
                                        find_x = i
                                        find_y = j
                                        fla = 1
                                        break
                                    }
                                    FIELD[i][j] = 0
                                }
                            }
                            if (fla == 1)
                                break
                        }


                        if (fla == 0) {
                            for (j in 5 downTo 0) {
                                for (i in 0..6) {
                                    if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                        FIELD[i][j] = 1
                                        if (checkForWin_another_fun().size == 9) {
                                            FIELD[i][j] = 0
                                            find_x = i
                                            find_y = j
                                            fla = 1
                                            break
                                        }
                                        FIELD[i][j] = 0
                                    }
                                }
                                if (fla == 1)
                                    break
                            }
                            /*for (j in 5 downTo 0) {
                            for (i in 0..6) {
                                if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                    for (at in 0..7) {
                                        Log.w("TAG", "$j $i $at")
                                        var fl = 0
                                        for (k in 1..3) {
                                            if (FIELD[(i + k * list_x[at] + 7) % 7][(j + k * list_y[at] + 6) % 6] != 1)
                                                fl = 1
                                        }
                                        if (fl == 0) {
                                            find_x = i
                                            find_y = j
                                            fla = 1
                                            break
                                        }
                                    }

                                    if (fla == 1) {
                                        break
                                    }
                                }
                            }
                            if (fla == 1)
                                break
                        }*/
                            if (fla == 0) {
                                for (j in 5 downTo 0) {
                                    for (i in 0..6) {
                                        if (FIELD[i][j] == 0 && (j == 5 || FIELD[i][j + 1] != 0)) {
                                            for (at in 0..7) {
                                                Log.w("TAG", "$j $i $at")
                                                var fl = 0
                                                for (k in 1..2) {
                                                    if (FIELD[(i + k * list_x[at] + 7) % 7][(j + k * list_y[at] + 6) % 6] != 2)
                                                        fl = 1
                                                }
                                                if (fl == 0) {
                                                    find_x = i
                                                    find_y = j
                                                    fla = 1
                                                    break
                                                }
                                            }
                                            if (fla == 1) {
                                                break
                                            }
                                        }
                                    }
                                    if (fla == 1)
                                        break
                                }
                                if (fla == 0) {
                                    for (j in 5 downTo 0) {
                                        for (i in 0..6) {
                                            if (FIELD[i][j] == 0) {
                                                FIELD[i][j] = 2
                                                val prefs =
                                                    context.getSharedPreferences(
                                                        "UserData",
                                                        Context.MODE_PRIVATE
                                                    )
                                                var data_from_memory =
                                                    prefs.getString("xog_with_computer", "")
                                                data_from_memory += i.toString()
                                                data_from_memory += j.toString()
                                                val editor =
                                                    context.getSharedPreferences(
                                                        "UserData",
                                                        Context.MODE_PRIVATE
                                                    )
                                                        .edit()
                                                editor.putString("xog_with_computer", data_from_memory)
                                                editor.apply()
                                                fla = 1
                                                cross_or_nul = "cross"
                                                break
                                            }
                                        }
                                        if (fla == 1)
                                            break
                                    }
                                } else {
                                    if_fla_is_1()
                                }
                            } else {
                                if_fla_is_1()
                            }
                        } else {
                            if_fla_is_1()
                        }

                        blocked = false
                        invalidate()
                    }, delayTime)
                }
            }


            /*if(X >= 0 && Y >= 0 && X < 7 && Y < 6) {
                if(FIELD[X][Y] == 0 && Y == 5) {
                    if (cross_or_nul == "cross") {
                        FIELD[X][Y] = 1
                        cross_or_nul = "null"
                    } else {
                        FIELD[X][Y] = 2
                        cross_or_nul = "cross"
                    }
                    //Log.w("ppppppp", FIELD[0][0].toString())
                    invalidate()
                } else {
                    if(FIELD[X][Y] == 0 && FIELD[X][Y+1]!=0) {
                        if (cross_or_nul == "cross") {
                            FIELD[X][Y] = 1
                            cross_or_nul = "null"
                        } else {
                            FIELD[X][Y] = 2
                            cross_or_nul = "cross"
                        }
                        //Log.w("ppppppp", FIELD[0][0].toString())
                        invalidate()
                    }
                }
            }*/
        }
        return true
    }
}