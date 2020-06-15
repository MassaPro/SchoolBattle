package com.example.schoolbattle.gamesonedevice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_box_game_one_divice.*

class BoxGameOneDivice : AppCompatActivity() {
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

    private var dialog_parametrs: Show_parametr_one_divice_one_Device? = null
    private var dialog_rules: Show_rules? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_box_game_one_divice)
        signature_canvas_box_one_device.activity = this

        CONTEXT = this

        if(Design == "Egypt" ) {
            name_player1_one_divice_box.setTextColor(Color.BLACK)
            name_player2_one_divice_box.setTextColor(Color.BLACK)
            name_player1_one_divice_box.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_one_divice_box.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_one_divice_box.setTextSize(20f)
            name_player1_one_divice_box.setTextSize(20f)
            button_player_1_box_one_divice.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_box_one_divice.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_box_one_divice.setBackgroundResource(R.drawable.box2_egypt);
            player_2_icon_box_one_divice.setBackgroundResource(R.drawable.box1_egypt)
            toolbar_box_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_box_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device_box.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_box_one_divice.setBackgroundColor(Color.rgb(224, 164, 103))
            to_back_box_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_box_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Casino" ) {
            name_player1_one_divice_box.setTextColor(Color.YELLOW)
            name_player2_one_divice_box.setTextColor(Color.YELLOW)
            name_player1_one_divice_box.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice_box.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice_box.setTextSize(20f)
            name_player1_one_divice_box.setTextSize(20f)
            button_player_1_box_one_divice.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_box_one_divice.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_box_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_box_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device_box.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_box_one_divice.setBackgroundColor(argb(0,224, 164, 103))
            to_back_box_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_box_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }

        to_back_box_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
            startActivity(intent)
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("box_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_box_one_device.History = decode(prefs.getString("box_one_divice", "").toString())
        if(signature_canvas_box_one_device.History.size > 0)
        {
            signature_canvas_box_one_device.red_or_blue = "red"
            for(i in 0 until signature_canvas_box_one_device.VERTICAL_RIB.size)
            {
                for(j in 0 until signature_canvas_box_one_device.VERTICAL_RIB[0].size)
                {
                    signature_canvas_box_one_device.VERTICAL_RIB[i][j] = 0
                }
            }
            for(i in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB.size)
            {
                for(j in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB[0].size)
                {
                    signature_canvas_box_one_device.HORIZONTAL_RIB[i][j] = 0
                }
            }
            for(i in 0 until signature_canvas_box_one_device.FIELD.size)
            {
                for(j in 0 until signature_canvas_box_one_device.FIELD[0].size)
                {
                    signature_canvas_box_one_device.FIELD[i][j] = 0
                }
            }

            for(i in signature_canvas_box_one_device.History)
            {
                if(i[1] == 1)
                {
                    signature_canvas_box_one_device.VERTICAL_RIB[i[2]][i[3]] = i[0]
                }
                if(i[1] == 2)
                {
                    signature_canvas_box_one_device.HORIZONTAL_RIB[i[2]][i[3]] = i[0]
                }

                if(signature_canvas_box_one_device.red_or_blue == "red")
                {
                    signature_canvas_box_one_device.red_or_blue = "blue"
                }
                else
                {
                    signature_canvas_box_one_device.red_or_blue = "red"
                }
                var flag: Boolean = false
                for(i in 0..6)
                {
                    for(j in 0..6)
                    {
                        if(signature_canvas_box_one_device.VERTICAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j+1]>0 && signature_canvas_box_one_device.VERTICAL_RIB[i+1][j]>0 && signature_canvas_box_one_device.FIELD[i][j]==0) //если образовался квадратик
                        {
                            if(flag == false)
                            {
                                flag = true
                                if(signature_canvas_box_one_device.red_or_blue == "red")            //снова ходит тот же игрок
                                {
                                    signature_canvas_box_one_device.red_or_blue = "blue"
                                }
                                else
                                {
                                    signature_canvas_box_one_device.red_or_blue = "red"
                                }
                            }
                            if(signature_canvas_box_one_device.red_or_blue == "red")
                            {
                                signature_canvas_box_one_device.FIELD[i][j] = 1
                            }
                            else
                            {
                                signature_canvas_box_one_device.FIELD[i][j] = 2
                            }
                        }
                    }
                }
            }
            signature_canvas_box_one_device.invalidate()
        }


        bottom_navigation_box_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@BoxGameOneDivice
                        )
                    dialog_rules?.show("BoxGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_one_divice_one_Device(
                            this@BoxGameOneDivice
                        )
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, BoxGameOneDivice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if(signature_canvas_box_one_device.History.size > 0)
                    {
                        signature_canvas_box_one_device.History.removeLast()
                        var data_from_memory = encode(signature_canvas_box_one_device.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("box_one_divice", data_from_memory)
                        editor.apply()
                        signature_canvas_box_one_device.red_or_blue = "red"
                        for(i in 0 until signature_canvas_box_one_device.VERTICAL_RIB.size)
                        {
                            for(j in 0 until signature_canvas_box_one_device.VERTICAL_RIB[0].size)
                            {
                                signature_canvas_box_one_device.VERTICAL_RIB[i][j] = 0
                            }
                        }
                        for(i in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB.size)
                        {
                            for(j in 0 until signature_canvas_box_one_device.HORIZONTAL_RIB[0].size)
                            {
                                signature_canvas_box_one_device.HORIZONTAL_RIB[i][j] = 0
                            }
                        }
                        for(i in 0 until signature_canvas_box_one_device.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_box_one_device.FIELD[0].size)
                            {
                                signature_canvas_box_one_device.FIELD[i][j] = 0
                            }
                        }

                        for(i in signature_canvas_box_one_device.History)
                        {
                            if(i[1] == 1)
                            {
                                signature_canvas_box_one_device.VERTICAL_RIB[i[2]][i[3]] = i[0]
                            }
                            if(i[1] == 2)
                            {
                                signature_canvas_box_one_device.HORIZONTAL_RIB[i[2]][i[3]] = i[0]
                            }

                            if(signature_canvas_box_one_device.red_or_blue == "red")
                            {
                                signature_canvas_box_one_device.red_or_blue = "blue"
                            }
                            else
                            {
                                signature_canvas_box_one_device.red_or_blue = "red"
                            }
                            var flag: Boolean = false
                            for(i in 0..6)
                            {
                                for(j in 0..6)
                                {
                                    if(signature_canvas_box_one_device.VERTICAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j]>0 && signature_canvas_box_one_device.HORIZONTAL_RIB[i][j+1]>0 && signature_canvas_box_one_device.VERTICAL_RIB[i+1][j]>0 && signature_canvas_box_one_device.FIELD[i][j]==0) //если образовался квадратик
                                    {
                                        if(flag == false)
                                        {
                                            flag = true
                                            if(signature_canvas_box_one_device.red_or_blue == "red")            //снова ходит тот же игрок
                                            {
                                                signature_canvas_box_one_device.red_or_blue = "blue"
                                            }
                                            else
                                            {
                                                signature_canvas_box_one_device.red_or_blue = "red"
                                            }
                                        }
                                        if(signature_canvas_box_one_device.red_or_blue == "red")
                                        {
                                            signature_canvas_box_one_device.FIELD[i][j] = 1
                                        }
                                        else
                                        {
                                            signature_canvas_box_one_device.FIELD[i][j] = 2
                                        }
                                    }
                                }
                            }
                        }
                        signature_canvas_box_one_device.invalidate()
                    }
                }

            }
            true
        }
    }
}


class CanvasView_Boxs(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (width/2f/size_field_x.toFloat())*(width/2f/size_field_x.toFloat())/2f)
        {
            return true
        }
        return false
    }

    fun check_win() : Int
    {
        var cnt1: Int = 0
        var cnt2: Int = 0
        for(i in FIELD)
        {
            for(j in i)
            {
                if(j == 0)
                {
                    return 0
                }
                if(j == 1)
                {
                    cnt1++
                }
                else
                {
                    cnt2++
                }
            }
        }
        if(cnt1>cnt2)
        {
            return 1
        }
        else
        {
            if(cnt2>cnt1)
            {
                return 2
            }
            else
            {
                return 3
            }
        }
    }

    lateinit var activity: Activity

    var History: MutableList<MutableList<Int>> = mutableListOf()

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
    var FIELD = Array(7){IntArray(7)}     //для фишеК
    var VERTICAL_RIB = Array(8){IntArray(7)}    //для ребер
    var HORIZONTAL_RIB = Array(7){IntArray(8)}

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

        paint_circle.setColor(Color.BLACK)     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(5f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(5f)

        if (Design == "Casino"){
            Line_paint.setColor(Color.argb(0, 217, 217,217))          //ресур для линий (ширина и цвет)

            paint_circle.setColor(Color.WHITE)     //цвета для точек

            paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
            paint_rib_2.setColor(Color.BLACK)
        }
        if (Design == "Egypt"){
            Line_paint.setColor(Color.argb(0, 217, 217,217))          //ресур для линий (ширина и цвет)

        }

        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..6) {
            for(j in 0 ..6) {
                FIELD[i][j] = 0  //не заполненный
            }
        }

        for(i in 0..7)
        {
            for(j in 0..6)
            {
                VERTICAL_RIB[i][j] = 0
                HORIZONTAL_RIB[j][i] = 0
            }
        }



    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red);       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue);

    var box1_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_egypt);
    var box2_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_egypt);

    var box1_casino : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_casino);
    var box2_casino : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_casino);



    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        radius_of_point = 10f
        size_field_x  = 7
        size_field_y  = 7
        indent = (getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)


        step = width/size_field_x
        advertising_line = ( height - 7*step)/2         //полоска для рекламы
        k = height-width-advertising_line


        var right_red: Bitmap
        var right_blue: Bitmap


        right_red = Bitmap.createScaledBitmap(red,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        right_blue = Bitmap.createScaledBitmap(blue,width.toInt()/size_field_x, width.toInt()/size_field_x, true);

        if(Design == "Egypt")
        {
            right_red = Bitmap.createScaledBitmap(box1_egypt,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            right_blue = Bitmap.createScaledBitmap(box2_egypt,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        }
        else if (Design == "Casino")
        {
            right_red = Bitmap.createScaledBitmap(box1_casino,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            right_blue = Bitmap.createScaledBitmap(box2_casino,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        }

        for(i in 0 until size_field_x+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            k = k + step
        }
        k = indent
        for(i in 0 until size_field_y+2)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k,height-advertising_line-width,k,height-advertising_line,Line_paint)
            k = k + step
        }

        var x: Float;
        var y: Float
        x = indent
        y = height - advertising_line - width
        for(i in 0..7)                    //вырисовка точек
        {
            for(j in 0..7)
            {
                canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                x += step
            }
            x = indent
            y += step
        }

        x = indent
        y = height - advertising_line - width
        for(i in 0..7)               //вырисовка вертикальных ребер
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j] == 1)
                {
                    canvas?.drawLine(x,y+radius_of_point,x,y+step-radius_of_point,paint_rib_1)
                }
                if(VERTICAL_RIB[i][j] == 2)
                {
                    canvas?.drawLine(x,y+radius_of_point,x,y+step-radius_of_point,paint_rib_2)
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }

        x = indent
        y = height - advertising_line - width
        for(i in 0..6)                 //вырисовка горизонтальных ребер
        {
            for(j in 0..7)
            {
                if(HORIZONTAL_RIB[i][j] == 1)
                {
                    canvas?.drawLine(x + radius_of_point,y,x+step - radius_of_point,y,paint_rib_1)
                }
                if(HORIZONTAL_RIB[i][j] == 2)
                {
                    canvas?.drawLine(x + radius_of_point,y,x+step - radius_of_point,y,paint_rib_2)
                }
                y += step
            }
            x += step
            y =  height - advertising_line - width
        }

        x = indent
        y = height - width - advertising_line
        for(i in 0..6)
        {
            for(j in 0..6)
            {
                if(FIELD[i][j] == 1)
                {
                    canvas?.drawBitmap(right_red, x ,y,paint_circle)
                }
                if(FIELD[i][j] == 2)
                {
                    canvas?.drawBitmap(right_blue, x ,y,paint_circle)
                }
                y += step
            }
            x += step
            y = height - width - advertising_line
        }




    }

    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        circlex = event!!.x
        circley = event!!.y
        if(check_win()<=0)
        {
            blocked = false
        }
        if(check_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
        }
        if(check_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked)
        {
            blocked = !blocked
            var dialog: Show_Result_one_Device? = null
            dialog = Show_Result_one_Device(activity)
            if(check_win()==1)
            {
                dialog?.showResult_one_device("КРАСНЫЕ ПОБЕДИЛИ","BoxGame",activity)
            }
            if(check_win()==2)
            {
                dialog?.showResult_one_device("СИНИЕ ПОБЕДИЛИ","BoxGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_one_device("НИЧЬЯ","BoxGame",activity)
            }
        }
        x = indent               //проверка по вертикальным линиям
        y = height - advertising_line - width
        for(i in 0..7)
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j] == 0)
                {
                    if(red_or_blue == "red")
                    {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 1
                            History.add(mutableListOf(1,1,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "blue"
                            Log.d("DOPO","ЛОЛ")
                        }
                    }
                    else
                    {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 2
                            History.add(mutableListOf(2,1,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "red"
                            Log.d("DOPO","ЛОЛ")
                        }
                    }
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }
        x = 0f
        y = 0f

        x = indent               //проверка по горизонтальным линиям
        y = height - advertising_line - width
        for(i in 0..6)
        {
            for(j in 0..7)
            {
                if(HORIZONTAL_RIB[i][j] == 0)
                {
                    if(red_or_blue == "red")
                    {
                        if (correction_touch(x+step/2f,y))
                        {
                            HORIZONTAL_RIB[i][j] = 1
                            History.add(mutableListOf(1,2,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "blue"
                            Log.d("DOPO","ЛОЛ")
                        }
                    }
                    else
                    {
                        if (correction_touch(x+step/2f,y))
                        {
                            HORIZONTAL_RIB[i][j] = 2
                            History.add(mutableListOf(2,2,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_one_divice", data_from_memory)
                            editor.apply()
                            red_or_blue = "red"
                            Log.d("DOPO","ЛОЛ")
                        }
                    }
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }
        var flag: Boolean = false
        for(i in 0..6)
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j]>0 && HORIZONTAL_RIB[i][j]>0 && HORIZONTAL_RIB[i][j+1]>0 && VERTICAL_RIB[i+1][j]>0 && FIELD[i][j]==0) //если образовался квадратик
                {
                    if(flag == false)
                    {
                        flag = true
                        if(red_or_blue == "red")            //снова ходит тот же игрок
                        {
                            red_or_blue = "blue"
                        }
                        else
                        {
                            red_or_blue = "red"
                        }
                    }
                    if(red_or_blue == "red")
                    {
                        FIELD[i][j] = 1
                    }
                    else
                    {
                        FIELD[i][j] = 2
                    }
                }
            }
        }
        x = 0f
        y = 0f
        invalidate()
        return true
    }

}