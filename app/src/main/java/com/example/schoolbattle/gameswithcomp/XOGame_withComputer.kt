package com.example.schoolbattle.gameswithcomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*
import java.util.*

var XOGameMode = 0

class XOGame_withComputer : AppCompatActivity() {
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
    private var dialog_rules: Show_rules? = null
    private var dialog_parametrs: Show_parametr_with_computer? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_computer_games_template)
        signature_canvas_xog_with_computer.visibility = (View.VISIBLE)
        signature_canvas_xog_with_computer.activ = this
        CONTEXT = this

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        val prefs2 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        XOGameMode = prefs2.getInt("XOGameMode", 0)
        if (XOGameMode == 0) {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putInt("XOGameMode", 1)
            editor.apply()
            XOGameMode = 1
        }
        signature_canvas_xog_with_computer.blocked = false

        val prefs_first = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_xog_with_computer.History = decode(prefs_first.getString("xog_with_computer", "").toString())
        if (XOGameMode == 2 && signature_canvas_xog_with_computer.History.size == 0) {
            signature_canvas_xog_with_computer.blocked = true
        }

        //var h : MutableList<Triple<Int,Int,Int>> =  mutableListOf(Triple(231,231,777),Triple(231,231,777),Triple(231,231,777))
        //Log.w("momlol",decode(encode(h)).toString())

        signature_canvas_xog_with_computer.t1 = findViewById(R.id.name_player1_with_computer_template) as TextView
        signature_canvas_xog_with_computer.t2 = findViewById(R.id.name_player2_with_computer_template) as TextView
        signature_canvas_xog_with_computer.t1.text = "Игрок 1"
        signature_canvas_xog_with_computer.t2.text = "Игрок 2"
        //signature_canvas_xog_with_computer.t1.set

        if(Design == "Egypt" ) {
            name_player1_with_computer_template.setTextColor(Color.BLACK)
            name_player2_with_computer_template.setTextColor(Color.BLACK)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_template_with_computer.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_template_with_computer.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_template_with_computer.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_template_with_computer.setBackgroundResource(R.drawable.circle_egypt);
            label_with_computer.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_template_with_computer.setBackgroundColor(rgb(224,164,103))
            to_back_template_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_template_with_computer.setBackgroundColor(argb(0,0,0,0))
            toolbar2_template_with_computer.setBackgroundColor(argb(0,0,0,0))
        }
        else if(Design == "Casino" ) {
            name_player1_with_computer_template.setTextColor(Color.YELLOW)
            name_player2_with_computer_template.setTextColor(Color.YELLOW)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_template_with_computer.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_template_with_computer.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_template_with_computer.setBackgroundColor(argb(0,224, 164, 103))
            to_back_template_with_computer.setBackgroundResource(R.drawable.arrow_back)
            toolbar_template_with_computer.setBackgroundColor(argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("xog_with_computer", "")
            editor.apply()
        }

        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_xog_with_computer.History = decode(prefs.getString("xog_with_computer", "").toString())
        for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
            for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                signature_canvas_xog_with_computer.FIELD[i][j] = 0
            }
        }
        signature_canvas_xog_with_computer.cross_or_nul = "cross"
        for (i in signature_canvas_xog_with_computer.History) {
            signature_canvas_xog_with_computer.FIELD[i.first][i.second] = i.third
            if (i.third == 1) {
                signature_canvas_xog_with_computer.cross_or_nul = "null"
            } else {
                signature_canvas_xog_with_computer.cross_or_nul = "cross"
            }
        }
        signature_canvas_xog_with_computer.invalidate()

        to_back_template_with_computer.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 3)
            startActivity(intent)
        }

        if (XOGameMode == 2 && signature_canvas_xog_with_computer.History.size == 0) {      // first computer move
            signature_canvas_xog_with_computer.blocked = true

            var find_x = 0
            var find_y = 0
            var fla = 0
            var list_x: MutableList<Int> = mutableListOf(1, 1, 1, 0, -1, -1, -1, 0)
            var list_y: MutableList<Int> = mutableListOf(-1, 0, 1, 1, 1, 0, -1, -1)

            val handler = android.os.Handler()
            handler.postDelayed({

                for (j in 5 downTo 0) {
                    for (i in 0..6) {
                        if (signature_canvas_xog_with_computer.FIELD[i][j] == 0 && (j == 5 || signature_canvas_xog_with_computer.FIELD[i][j + 1] != 0)) {
                            signature_canvas_xog_with_computer.FIELD[i][j] = 2
                            if (signature_canvas_xog_with_computer.checkForWin_another_fun().size == 9) {
                                signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                find_x = i
                                find_y = j
                                fla = 1
                                break
                            }
                            signature_canvas_xog_with_computer.FIELD[i][j] = 0
                        }
                    }
                    if (fla == 1)
                        break
                }


                if (fla == 0) {
                    for (j in 5 downTo 0) {
                        for (i in 0..6) {
                            if (signature_canvas_xog_with_computer.FIELD[i][j] == 0 && (j == 5 || signature_canvas_xog_with_computer.FIELD[i][j + 1] != 0)) {
                                signature_canvas_xog_with_computer.FIELD[i][j] = 1
                                if (signature_canvas_xog_with_computer.checkForWin_another_fun().size == 9) {
                                    signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                    find_x = i
                                    find_y = j
                                    fla = 1
                                    break
                                }
                                signature_canvas_xog_with_computer.FIELD[i][j] = 0
                            }
                        }
                        if (fla == 1)
                            break
                    }
                }
                if (fla == 0) {
                    for (j in 5 downTo 0) {
                        for (i in 0..6) {
                            if (signature_canvas_xog_with_computer.FIELD[i][j] == 0 && (j == 5 || signature_canvas_xog_with_computer.FIELD[i][j + 1] != 0)) {
                                for (at in 0..7) {
                                    Log.w("TAG", "$j $i $at")
                                    var fl = 0
                                    for (k in 1..2) {
                                        if (signature_canvas_xog_with_computer.FIELD[(i + k * list_x[at] + 7) % 7][(j + k * list_y[at] + 6) % 6] != 2)
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
                }
                if (fla == 1) {
                    signature_canvas_xog_with_computer.FIELD[find_x][find_y] = 2
                    signature_canvas_xog_with_computer.History.add(Triple(find_x,find_y,2))
                    var data_from_memory = encode(signature_canvas_xog_with_computer.History)
                    val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("xog_with_computer", data_from_memory)
                    editor.apply()
                    signature_canvas_xog_with_computer.cross_or_nul = "cross"
                }

                if (fla == 0) {
                    for (j in 5 downTo 0) {
                        for (i in 0..6) {
                            if (signature_canvas_xog_with_computer.FIELD[i][j] == 0) {
                                signature_canvas_xog_with_computer.FIELD[i][j] = 2
                                signature_canvas_xog_with_computer.History.add(Triple(i,j,2))
                                var data_from_memory = encode(signature_canvas_xog_with_computer.History)
                                val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("xog_with_computer", data_from_memory)
                                editor.apply()
                                signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                fla = 1
                                break
                            }
                        }
                        if (fla == 1)
                            break
                    }
                }

                signature_canvas_xog_with_computer.blocked = false
                signature_canvas_xog_with_computer.invalidate()
            }, delayTime)
        }

        bottom_navigation_template_with_computer.setOnNavigationItemSelectedListener { item ->
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
                        Show_parametr_with_computer(
                            this@XOGame_withComputer
                        )
                    dialog_parametrs?.showResult_with_computer(this, "XOGame")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, XOGame_withComputer::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_xog_with_computer.blocked == false || signature_canvas_xog_with_computer.EXODUS != 0) {
                        if (signature_canvas_xog_with_computer.EXODUS == 0) {
                            if (signature_canvas_xog_with_computer.History.size > 1) {            //TODO дописать когда самый первый ход убираем
                                signature_canvas_xog_with_computer.History.removeLast()
                                signature_canvas_xog_with_computer.History.removeLast()
                                var data_from_memory =
                                    encode(signature_canvas_xog_with_computer.History)
                                val editor =
                                    getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString("xog_with_computer", data_from_memory)
                                editor.apply()
                                for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
                                    for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                                        signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                    }
                                }
                                signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                for (i in signature_canvas_xog_with_computer.History) {
                                    signature_canvas_xog_with_computer.FIELD[i.first][i.second] =
                                        i.third
                                    if (i.third == 1) {
                                        signature_canvas_xog_with_computer.cross_or_nul = "null"
                                    } else {
                                        signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                    }
                                }
                                signature_canvas_xog_with_computer.invalidate()
                            }
                        } else {
                            if (signature_canvas_xog_with_computer.EXODUS == 1) {
                                if (signature_canvas_xog_with_computer.History.size > 0) {
                                    signature_canvas_xog_with_computer.History.removeLast()
                                    var data_from_memory =
                                        encode(signature_canvas_xog_with_computer.History)
                                    val editor =
                                        getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("xog_with_computer", data_from_memory)
                                    editor.apply()
                                    for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
                                        for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                                            signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                        }
                                    }
                                    signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                    for (i in signature_canvas_xog_with_computer.History) {
                                        signature_canvas_xog_with_computer.FIELD[i.first][i.second] =
                                            i.third
                                        if (i.third == 1) {
                                            signature_canvas_xog_with_computer.cross_or_nul = "null"
                                        } else {
                                            signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                        }
                                    }
                                    signature_canvas_xog_with_computer.invalidate()
                                }
                            } else {
                                if (signature_canvas_xog_with_computer.History.size > 1) {
                                    signature_canvas_xog_with_computer.History.removeLast()
                                    signature_canvas_xog_with_computer.History.removeLast()
                                    var data_from_memory =
                                        encode(signature_canvas_xog_with_computer.History)
                                    val editor =
                                        getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("xog_with_computer", data_from_memory)
                                    editor.apply()
                                    for (i in 0 until signature_canvas_xog_with_computer.FIELD.size) {
                                        for (j in 0 until signature_canvas_xog_with_computer.FIELD[0].size) {
                                            signature_canvas_xog_with_computer.FIELD[i][j] = 0
                                        }
                                    }
                                    signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                    for (i in signature_canvas_xog_with_computer.History) {
                                        signature_canvas_xog_with_computer.FIELD[i.first][i.second] =
                                            i.third
                                        if (i.third == 1) {
                                            signature_canvas_xog_with_computer.cross_or_nul = "null"
                                        } else {
                                            signature_canvas_xog_with_computer.cross_or_nul = "cross"
                                        }
                                    }
                                    signature_canvas_xog_with_computer.invalidate()
                                }
                            }

                        }
                        signature_canvas_xog_with_computer.EXODUS = 0
                        signature_canvas_xog_with_computer.blocked = false
                    }
                }

            }
            true
        }


    }
}




class CanvasView_xog_with_computer(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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


    lateinit var activ : Activity

    lateinit var t1: TextView
    lateinit var t2: TextView
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

        if(cross_or_nul == "cross")
        {
            t1.text ="игрок 1 думает..."
            t2.text  = "игрок 2"
        }
        else
        {
            t1.text ="игрок 1"
            t2.text  = "игрок 2 думает..."
        }
        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)

        size_field_x  = 7
        size_field_y  = 6
        step = (width-2*indent)/size_field_x
        var advertising_line: Float = (height - step * 6) / 2
        var k: Float = height-(width-2*indent)-advertising_line


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


        var countSimb = 0
        for( i in 0..6) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..5) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    countSimb++
                    canvas?.drawBitmap(right_cross, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    countSimb++
                    canvas?.drawBitmap(right_null, translate_from_Array_to_Graphics_X(indent,i,step),
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
                canvas?.drawBitmap(right_green,a_1,a_2,paint)
                counter += 2
            }
        } else if (countSimb == 42) {
            EXODUS = 3
        }

        var dialog: Show_Result_with_Computer? = null

        if(EXODUS == 1) {
            dialog = Show_Result_with_Computer(activ)
            dialog.showResult_with_Computer("Победа","XOGame",activ)

        }
        if(EXODUS == 2) {
            dialog = Show_Result_with_Computer(activ)
            dialog.showResult_with_Computer("Поражение","XOGame",activ)
        }
        if (EXODUS == 3) {
            dialog = Show_Result_with_Computer(activ)
            dialog.showResult_with_Computer("Ничья","XOGame",activ)
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
            //blocked = false
        }
        /*var dialog: Show_Result_with_Computer? = null

        if(EXODUS == 1)
        {
            dialog =
                Show_Result_with_Computer(activ)
            dialog?.showResult_with_Computer("КРЕСТИКИ ПОБЕДИЛИ","XOGame",activ)

        }
        if(EXODUS == 2)
        {
            dialog =
                Show_Result_with_Computer(activ)
            dialog?.showResult_with_Computer("НОЛИКИ ПОБЕДИЛИ","XOGame",activ)
        }*/

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
                if(FIELD[X][Y] == 0 && (Y == 5 || FIELD[X][Y+1]!=0)) {
                    if (cross_or_nul == "cross") {
                        FIELD[X][Y] = 1
                        History.add(Triple(X,Y,1))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_with_computer", data_from_memory)
                        editor.apply()
                        cross_or_nul = "null"
                    } else {
                        FIELD[X][Y] = 2
                        History.add(Triple(X,Y,2))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("xog_with_computer", data_from_memory)
                        editor.apply()
                        cross_or_nul = "cross"
                    }
                    if(SOUND) {
                        mSound.play(1,1F,1F,1,0,1F)
                    }
                    if(VIBRATION) {
                        vibratorService?.vibrate(70)
                    }
                    Log.w("ppppppp", FIELD[0][0].toString())
                    invalidate()

                    var find_x = 0
                    var find_y = 0
                    var fla = 0
                    var list_x: MutableList<Int> = mutableListOf(1, 1, 1, 0, -1, -1, -1, 0)
                    var list_y: MutableList<Int> = mutableListOf(-1, 0, 1, 1, 1, 0, -1, -1)

                    blocked = true

                    if (checkForWin_another_fun().size==9) {
                        return true
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
                        }
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
                        }
                        if (fla == 1) {
                            FIELD[find_x][find_y] = 2
                            History.add(Triple(find_x,find_y,2))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("xog_with_computer", data_from_memory)
                            editor.apply()
                            cross_or_nul = "cross"
                        }

                        if (fla == 0) {
                            for (j in 5 downTo 0) {
                                for (i in 0..6) {
                                    if (FIELD[i][j] == 0) {
                                        FIELD[i][j] = 2
                                        History.add(Triple(i,j,2))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("xog_with_computer", data_from_memory)
                                        editor.apply()
                                        cross_or_nul = "cross"
                                        fla = 1
                                        break
                                    }
                                }
                                if (fla == 1)
                                    break
                            }
                        }

                        blocked = false
                        invalidate()
                    }, delayTime)
                }
            }
        }
        return true
    }
}