package com.sga.schoolbattle.gameswithcomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.AdRequest
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*
import kotlinx.android.synthetic.main.activity_one_device_games_template.*

var BoxMode = 0

class BoxGameWithComputer : AppCompatActivity() {
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

    private var dialog_parametrs: Show_parametr_with_computer? = null
    private var dialog_rules: Show_rules? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_computer_games_template)
        signature_canvas_box_with_computer.visibility = (View.VISIBLE)
        signature_canvas_box_with_computer.activity = this
        CONTEXT = this

        if(!PREMIUM)
        {
            mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())
        }

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        val prefs2 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        BoxMode = prefs2.getInt("BoxGameMode", 0)
        if (BoxMode == 0) {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putInt("BoxGameMode", 1)
            editor.apply()
            BoxMode = 1
        }
        signature_canvas_box_with_computer.blockedOnTouch = false

        val prefs_first = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_box_with_computer.History = decode(prefs_first.getString("box_with_computer", "").toString())
        if (BoxMode == 2 && signature_canvas_box_with_computer.History.size == 0) {
            signature_canvas_box_with_computer.blockedOnTouch = true
        }


        if(Design == "Egypt" ) {
            name_player1_with_computer_template.setTextColor(Color.BLACK)
            name_player2_with_computer_template.setTextColor(Color.BLACK)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_with_computer_template.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_with_computer_template.setBackgroundResource(R.drawable.player2_egypt);
            //player_1_icon_template_with_computer.setBackgroundResource(R.drawable.template2_egypt);
            //player_2_icon_template_with_computer.setBackgroundResource(R.drawable.template1_egypt)
            toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_with_computer_template.setBackgroundColor(Color.rgb(255, 230, 163))
            to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
            toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Casino" ) {
            name_player1_with_computer_template.setTextColor(Color.YELLOW)
            name_player2_with_computer_template.setTextColor(Color.YELLOW)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_with_computer_template.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_with_computer_template.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_with_computer_template.setBackgroundColor(argb(0,224, 164, 103))
            to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
            toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
        }

        to_back_with_computer_template.setOnClickListener {
            to_back_one_divice.setOnClickListener {
                this.finish()
                val intent = Intent(this, NewGameActivity::class.java)
                intent.putExtra("playType", 3)
                if(mInterstitialAd_in_offline_games.isLoaded && !PREMIUM)
                {
                    Intent_for_offline_games = intent
                    mInterstitialAd_in_offline_games.show()
                }
                else
                {
                    this.startActivity(intent)
                }
            }
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("box_with_computer", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_box_with_computer.History = decode(prefs.getString("box_with_computer", "").toString())
        if(signature_canvas_box_with_computer.History.size > 0)
        {
            signature_canvas_box_with_computer.red_or_blue = "red"
            for(i in 0 until signature_canvas_box_with_computer.VERTICAL_RIB.size)
            {
                for(j in 0 until signature_canvas_box_with_computer.VERTICAL_RIB[0].size)
                {
                    signature_canvas_box_with_computer.VERTICAL_RIB[i][j] = 0
                }
            }
            for(i in 0 until signature_canvas_box_with_computer.HORIZONTAL_RIB.size)
            {
                for(j in 0 until signature_canvas_box_with_computer.HORIZONTAL_RIB[0].size)
                {
                    signature_canvas_box_with_computer.HORIZONTAL_RIB[i][j] = 0
                }
            }
            for(i in 0 until signature_canvas_box_with_computer.FIELD.size)
            {
                for(j in 0 until signature_canvas_box_with_computer.FIELD[0].size)
                {
                    signature_canvas_box_with_computer.FIELD[i][j] = 0
                }
            }

            for(i in signature_canvas_box_with_computer.History)
            {
                if(i[1] == 1)
                {
                    signature_canvas_box_with_computer.VERTICAL_RIB[i[2]][i[3]] = i[0]
                }
                if(i[1] == 2)
                {
                    signature_canvas_box_with_computer.HORIZONTAL_RIB[i[2]][i[3]] = i[0]
                }

                if(signature_canvas_box_with_computer.red_or_blue == "red")
                {
                    signature_canvas_box_with_computer.red_or_blue = "blue"
                }
                else
                {
                    signature_canvas_box_with_computer.red_or_blue = "red"
                }
                var flag: Boolean = false
                for(i in 0..6)
                {
                    for(j in 0..6)
                    {
                        if(signature_canvas_box_with_computer.VERTICAL_RIB[i][j]>0 && signature_canvas_box_with_computer.HORIZONTAL_RIB[i][j]>0 && signature_canvas_box_with_computer.HORIZONTAL_RIB[i][j+1]>0 && signature_canvas_box_with_computer.VERTICAL_RIB[i+1][j]>0 && signature_canvas_box_with_computer.FIELD[i][j]==0) //если образовался квадратик
                        {
                            if(flag == false)
                            {
                                flag = true
                                if(signature_canvas_box_with_computer.red_or_blue == "red")            //снова ходит тот же игрок
                                {
                                    signature_canvas_box_with_computer.red_or_blue = "blue"
                                }
                                else
                                {
                                    signature_canvas_box_with_computer.red_or_blue = "red"
                                }
                            }
                            if(signature_canvas_box_with_computer.red_or_blue == "red")
                            {
                                signature_canvas_box_with_computer.FIELD[i][j] = 1
                            }
                            else
                            {
                                signature_canvas_box_with_computer.FIELD[i][j] = 2
                            }
                        }
                    }
                }
            }
            signature_canvas_box_with_computer.invalidate()
        }


        //TODO first computer move

        if (BoxMode == 2 && signature_canvas_box_with_computer.History.size == 0) {
            if ((signature_canvas_box_with_computer.red_or_blue == "red" && BoxMode == 2) || (signature_canvas_box_with_computer.red_or_blue == "blue" && BoxMode == 1)) {
                signature_canvas_box_with_computer.blockedOnTouch = true

                val handler = android.os.Handler()
                handler.postDelayed({


                    if (Math.random() < 0.5) {
                        if(signature_canvas_box_with_computer.red_or_blue == "red" && BoxMode == 2) {
                            signature_canvas_box_with_computer.VERTICAL_RIB[3][3] = 1
                            signature_canvas_box_with_computer.History.add(mutableListOf(1,1,3,3))
                            var data_from_memory = encode(signature_canvas_box_with_computer.History)
                            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            signature_canvas_box_with_computer.red_or_blue = "blue"
                        } else if (signature_canvas_box_with_computer.red_or_blue == "blue" && BoxMode == 1) {
                            signature_canvas_box_with_computer.VERTICAL_RIB[3][3] = 2
                            signature_canvas_box_with_computer.History.add(mutableListOf(2,1,3,3))
                            var data_from_memory = encode(signature_canvas_box_with_computer.History)
                            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            signature_canvas_box_with_computer.red_or_blue = "red"
                        }
                    } else {
                        if(signature_canvas_box_with_computer.red_or_blue == "red" && BoxMode == 2) {
                            signature_canvas_box_with_computer.HORIZONTAL_RIB[3][3] = 1
                            signature_canvas_box_with_computer.History.add(mutableListOf(1,2,3,3))
                            var data_from_memory = encode(signature_canvas_box_with_computer.History)
                            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            signature_canvas_box_with_computer.red_or_blue = "blue"
                        } else if (signature_canvas_box_with_computer.red_or_blue == "blue" && BoxMode == 1) {
                            signature_canvas_box_with_computer.HORIZONTAL_RIB[3][3] = 2
                            signature_canvas_box_with_computer.History.add(mutableListOf(2,2,3,3))
                            var data_from_memory = encode(signature_canvas_box_with_computer.History)
                            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            signature_canvas_box_with_computer.red_or_blue = "red"
                        }
                    }


                    signature_canvas_box_with_computer.blockedOnTouch = false
                    signature_canvas_box_with_computer.invalidate()

                }, delayTime)
            }
        }


        bottom_navigation_with_computer_template.itemIconTintList = generateColorStateList()
        bottom_navigation_with_computer_template.itemTextColor = generateColorStateList()
        if(LANGUAGE == "English")
        {
            bottom_navigation_with_computer_template.menu.getItem(0).title = "Rules"
            bottom_navigation_with_computer_template.menu.getItem(1).title = "Settings"
            bottom_navigation_with_computer_template.menu.getItem(2).title = "Return"
            bottom_navigation_with_computer_template.menu.getItem(3).title = "Back"
        }
        bottom_navigation_with_computer_template.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@BoxGameWithComputer
                        )
                    dialog_rules?.show("BoxGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_with_computer(
                            this@BoxGameWithComputer
                        )
                    dialog_parametrs?.showResult_with_computer(this, "BoxGame")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, BoxGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    while (signature_canvas_box_with_computer.History.size > 1 && signature_canvas_box_with_computer.History[signature_canvas_box_with_computer.History.size - 1][0] != BoxMode) {
                        signature_canvas_box_with_computer.History.removeLast()
                    }
                    if(signature_canvas_box_with_computer.History.size > 0)
                    {
                        signature_canvas_box_with_computer.History.removeLast()
                        var data_from_memory = encode(signature_canvas_box_with_computer.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("box_with_computer", data_from_memory)
                        editor.apply()
                        signature_canvas_box_with_computer.red_or_blue = "red"
                        for(i in 0 until signature_canvas_box_with_computer.VERTICAL_RIB.size)
                        {
                            for(j in 0 until signature_canvas_box_with_computer.VERTICAL_RIB[0].size)
                            {
                                signature_canvas_box_with_computer.VERTICAL_RIB[i][j] = 0
                            }
                        }
                        for(i in 0 until signature_canvas_box_with_computer.HORIZONTAL_RIB.size)
                        {
                            for(j in 0 until signature_canvas_box_with_computer.HORIZONTAL_RIB[0].size)
                            {
                                signature_canvas_box_with_computer.HORIZONTAL_RIB[i][j] = 0
                            }
                        }
                        for(i in 0 until signature_canvas_box_with_computer.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_box_with_computer.FIELD[0].size)
                            {
                                signature_canvas_box_with_computer.FIELD[i][j] = 0
                            }
                        }

                        for(i in signature_canvas_box_with_computer.History)
                        {
                            if(i[1] == 1)
                            {
                                signature_canvas_box_with_computer.VERTICAL_RIB[i[2]][i[3]] = i[0]
                            }
                            if(i[1] == 2)
                            {
                                signature_canvas_box_with_computer.HORIZONTAL_RIB[i[2]][i[3]] = i[0]
                            }

                            if(signature_canvas_box_with_computer.red_or_blue == "red")
                            {
                                signature_canvas_box_with_computer.red_or_blue = "blue"
                            }
                            else
                            {
                                signature_canvas_box_with_computer.red_or_blue = "red"
                            }
                            var flag: Boolean = false
                            for(i in 0..6)
                            {
                                for(j in 0..6)
                                {
                                    if(signature_canvas_box_with_computer.VERTICAL_RIB[i][j]>0 && signature_canvas_box_with_computer.HORIZONTAL_RIB[i][j]>0 && signature_canvas_box_with_computer.HORIZONTAL_RIB[i][j+1]>0 && signature_canvas_box_with_computer.VERTICAL_RIB[i+1][j]>0 && signature_canvas_box_with_computer.FIELD[i][j]==0) //если образовался квадратик
                                    {
                                        if(flag == false)
                                        {
                                            flag = true
                                            if(signature_canvas_box_with_computer.red_or_blue == "red")            //снова ходит тот же игрок
                                            {
                                                signature_canvas_box_with_computer.red_or_blue = "blue"
                                            }
                                            else
                                            {
                                                signature_canvas_box_with_computer.red_or_blue = "red"
                                            }
                                        }
                                        if(signature_canvas_box_with_computer.red_or_blue == "red")
                                        {
                                            signature_canvas_box_with_computer.FIELD[i][j] = 1
                                        }
                                        else
                                        {
                                            signature_canvas_box_with_computer.FIELD[i][j] = 2
                                        }
                                    }
                                }
                            }
                        }

                        signature_canvas_box_with_computer.blockedOnTouch = false
                        signature_canvas_box_with_computer.invalidate()
                    }
                }

            }
            true
        }

    }
    override fun onBackPressed()
    {
        super.onBackPressed()
        var intent = Intent(this, NewGameActivity::class.java)
        intent.putExtra("playType", 3)
        if(mInterstitialAd_in_offline_games.isLoaded && !PREMIUM)
        {
            Intent_for_offline_games = intent
            mInterstitialAd_in_offline_games.show()
        }
        else
        {
            this.startActivity(intent)
            this.finish()
        }
    }
}


class CanvasView_Boxs_with_computer(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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


    var blockedOnTouch: Boolean = false
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
            Line_paint.setColor(argb(0, 217, 217,217))          //ресур для линий (ширина и цвет)

            paint_circle.setColor(Color.WHITE)     //цвета для точек

            paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
            paint_rib_2.setColor(Color.BLACK)
        }
        if (Design == "Egypt"){
            Line_paint.setColor(argb(0, 217, 217,217))          //ресур для линий (ширина и цвет)

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


        if (check_win() == 0) {
            if ((red_or_blue == "red" && BoxMode == 2) || (red_or_blue == "blue" && BoxMode == 1)) {
                blockedOnTouch = true

                val handler = android.os.Handler()
                handler.postDelayed({
                    var fla2 = false

                    for (i in 0..6) {         // очевидно
                        for (j in 0..6) {
                            var a1 = 0
                            var a2 = 0
                            var a3 = 0
                            var a4 = 0
                            if (VERTICAL_RIB[i][j] > 0)
                                a1 = 1
                            if (HORIZONTAL_RIB[i][j] > 0)
                                a2 = 1
                            if (HORIZONTAL_RIB[i][j + 1] > 0)
                                a3 = 1
                            if (VERTICAL_RIB[i + 1][j] > 0)
                                a4 = 1
                            if (a1 + a2 + a3 + a4 == 3 && FIELD[i][j] == 0) {
                                if (VERTICAL_RIB[i][j] == 0) {
                                    if (red_or_blue == "red" && BoxMode == 2) {
                                        VERTICAL_RIB[i][j] = 1
                                        History.add(mutableListOf(1, 1, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "blue"
                                    } else if (red_or_blue == "blue" && BoxMode == 1) {
                                        VERTICAL_RIB[i][j] = 2
                                        History.add(mutableListOf(2, 1, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "red"
                                    }
                                    fla2 = true
                                } else if (HORIZONTAL_RIB[i][j] == 0) {
                                    if (red_or_blue == "red" && BoxMode == 2) {
                                        HORIZONTAL_RIB[i][j] = 1
                                        History.add(mutableListOf(1, 2, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "blue"
                                    } else if (red_or_blue == "blue" && BoxMode == 1) {
                                        HORIZONTAL_RIB[i][j] = 2
                                        History.add(mutableListOf(2, 2, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "red"
                                    }
                                    fla2 = true
                                } else if (HORIZONTAL_RIB[i][j + 1] == 0) {
                                    if (red_or_blue == "red" && BoxMode == 2) {
                                        HORIZONTAL_RIB[i][j + 1] = 1
                                        History.add(mutableListOf(1, 2, i, j + 1))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "blue"
                                    } else if (red_or_blue == "blue" && BoxMode == 1) {
                                        HORIZONTAL_RIB[i][j + 1] = 2
                                        History.add(mutableListOf(2, 2, i, j + 1))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "red"
                                    }
                                    fla2 = true
                                } else if (VERTICAL_RIB[i + 1][j] == 0) {
                                    if (red_or_blue == "red" && BoxMode == 2) {
                                        VERTICAL_RIB[i + 1][j] = 1
                                        History.add(mutableListOf(1, 1, i + 1, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "blue"
                                    } else if (red_or_blue == "blue" && BoxMode == 1) {
                                        VERTICAL_RIB[i + 1][j] = 2
                                        History.add(mutableListOf(2, 1, i + 1, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "red"
                                    }
                                    fla2 = true


                                }
                                if (fla2)
                                    break

                            }
                        }
                        if (fla2)
                            break
                    }    // очевидно

                    if (!fla2) {
                        var list_x2: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                        var list_y2: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6)
                        list_x2.shuffle()
                        list_y2.shuffle()
                        if (Math.random() < 0.5) {
                            for (i in list_x2) {
                                for (j in list_y2) {
                                    if (VERTICAL_RIB[i][j] == 0 && ((i > 0 && j <= 6 && VERTICAL_RIB[i - 1][j] > 0) || (j > 0 && i <= 7 && VERTICAL_RIB[i][j - 1] > 0) || (i < 7 && j <= 6 && VERTICAL_RIB[i + 1][j] > 0) || (j < 6 && i <= 7 && VERTICAL_RIB[i][j + 1] > 0) || (i > 0 && j <= 7 && HORIZONTAL_RIB[i - 1][j] > 0) || (j > 0 && i <= 6 && HORIZONTAL_RIB[i][j - 1] > 0) || (i < 6 && j <= 7 && HORIZONTAL_RIB[i + 1][j] > 0) || (j < 7 && i <= 6 && HORIZONTAL_RIB[i][j + 1] > 0))) {

                                        if (red_or_blue == "red" && BoxMode == 2) {

                                            VERTICAL_RIB[i][j] = 1
                                            History.add(mutableListOf(1, 1, i, j))
                                            var data_from_memory = encode(History)
                                            val editor = context.getSharedPreferences(
                                                "UserData",
                                                Context.MODE_PRIVATE
                                            ).edit()
                                            editor.putString("box_with_computer", data_from_memory)
                                            editor.apply()
                                            red_or_blue = "blue"
                                        } else if (red_or_blue == "blue" && BoxMode == 1) {

                                            VERTICAL_RIB[i][j] = 2
                                            History.add(mutableListOf(2, 1, i, j))
                                            var data_from_memory = encode(History)
                                            val editor = context.getSharedPreferences(
                                                "UserData",
                                                Context.MODE_PRIVATE
                                            ).edit()
                                            editor.putString("box_with_computer", data_from_memory)
                                            editor.apply()
                                            red_or_blue = "red"
                                        }

                                        fla2 = true
                                        break
                                    }
                                }
                                if (fla2) {
                                    break
                                }
                            }
                        } else {
                            for (i in list_y2) {
                                for (j in list_x2) {
                                    if (HORIZONTAL_RIB[i][j] == 0 && ((i > 0 && j <= 6 && VERTICAL_RIB[i - 1][j] > 0) || (j > 0 && i <= 7 && VERTICAL_RIB[i][j - 1] > 0) || (i < 7 && j <= 6 && VERTICAL_RIB[i + 1][j] > 0) || (j < 6 && i <= 7 && VERTICAL_RIB[i][j + 1] > 0) || (i > 0 && j <= 7 && HORIZONTAL_RIB[i - 1][j] > 0) || (j > 0 && i <= 6 && HORIZONTAL_RIB[i][j - 1] > 0) || (i < 6 && j <= 7 && HORIZONTAL_RIB[i + 1][j] > 0) || (j < 7 && i <= 6 && HORIZONTAL_RIB[i][j + 1] > 0))) {
                                        if (red_or_blue == "red" && BoxMode == 2) {
                                            HORIZONTAL_RIB[i][j] = 1
                                            History.add(mutableListOf(1, 2, i, j))
                                            var data_from_memory = encode(History)
                                            val editor = context.getSharedPreferences(
                                                "UserData",
                                                Context.MODE_PRIVATE
                                            ).edit()
                                            editor.putString("box_with_computer", data_from_memory)
                                            editor.apply()
                                            red_or_blue = "blue"
                                        } else if (red_or_blue == "blue" && BoxMode == 1) {
                                            HORIZONTAL_RIB[i][j] = 2
                                            History.add(mutableListOf(2, 2, i, j))
                                            var data_from_memory = encode(History)
                                            val editor = context.getSharedPreferences(
                                                "UserData",
                                                Context.MODE_PRIVATE
                                            ).edit()
                                            editor.putString("box_with_computer", data_from_memory)
                                            editor.apply()
                                            red_or_blue = "red"
                                        }

                                        fla2 = true
                                        break
                                    }
                                }
                                if (fla2) {
                                    break
                                }
                            }
                        }
                    }   // рядом


                    if (!fla2) {
                        var list_x2: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                        var list_y2: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6)
                        list_x2.shuffle()
                        list_y2.shuffle()
                        for (i in list_x2) {
                            for (j in list_y2) {
                                if (VERTICAL_RIB[i][j] == 0) {

                                    if (red_or_blue == "red" && BoxMode == 2) {

                                        VERTICAL_RIB[i][j] = 1
                                        History.add(mutableListOf(1, 1, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "blue"
                                    } else if (red_or_blue == "blue" && BoxMode == 1) {

                                        VERTICAL_RIB[i][j] = 2
                                        History.add(mutableListOf(2, 1, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "red"
                                    }

                                    fla2 = true
                                    break
                                }
                            }
                            if (fla2) {
                                break
                            }
                        }
                    }  //  оставшиеся

                    if (!fla2) {
                        var list_x2: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                        var list_y2: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6)
                        list_x2.shuffle()
                        list_y2.shuffle()
                        for (i in list_y2) {
                            for (j in list_x2) {
                                if (HORIZONTAL_RIB[i][j] == 0) {
                                    if (red_or_blue == "red" && BoxMode == 2) {
                                        HORIZONTAL_RIB[i][j] = 1
                                        History.add(mutableListOf(1, 2, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "blue"
                                    } else if (red_or_blue == "blue" && BoxMode == 1) {
                                        HORIZONTAL_RIB[i][j] = 2
                                        History.add(mutableListOf(2, 2, i, j))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString("box_with_computer", data_from_memory)
                                        editor.apply()
                                        red_or_blue = "red"
                                    }

                                    fla2 = true
                                    break
                                }
                            }
                            if (fla2) {
                                break
                            }
                        }
                    }   //   оставшиеся


                    var flag = false
                    for (i in 0..6) {
                        for (j in 0..6) {
                            if (VERTICAL_RIB[i][j] > 0 && HORIZONTAL_RIB[i][j] > 0 && HORIZONTAL_RIB[i][j + 1] > 0 && VERTICAL_RIB[i + 1][j] > 0 && FIELD[i][j] == 0) //если образовался квадратик
                            {
                                if (flag == false) {
                                    flag = true
                                    if (red_or_blue == "red")            //снова ходит тот же игрок
                                    {
                                        red_or_blue = "blue"
                                    } else {
                                        red_or_blue = "red"
                                    }
                                }
                                if (red_or_blue == "red") {
                                    FIELD[i][j] = 1
                                } else {
                                    FIELD[i][j] = 2
                                }
                            }
                        }
                    }

                    blockedOnTouch = false
                    invalidate()

                }, delayTime)
            }
        } else {
            blockedOnTouch = true

            var dialog: Show_Result_with_Computer? = null
            dialog = Show_Result_with_Computer(activity)
            if(check_win() == BoxMode)      //TODO more check
            {
                dialog?.showResult_with_Computer("Победа","BoxGame",activity)
            }
            if(check_win() == 3 - BoxMode)
            {
                dialog?.showResult_with_Computer("Поражение","BoxGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_with_Computer("Ничья","BoxGame",activity)
            }
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
            var dialog: Show_Result_with_Computer? = null
            dialog = Show_Result_with_Computer(activity)
            if(check_win() == BoxMode)      //TODO more check
            {
                dialog?.showResult_with_Computer("Победа","BoxGame",activity)
            }
            if(check_win() == 3 - BoxMode)
            {
                dialog?.showResult_with_Computer("Поражение","BoxGame",activity)
            }
            if(check_win()==3)
            {
                dialog?.showResult_with_Computer("Ничья","BoxGame",activity)
            }
        }

        if (blockedOnTouch) {
            return true
        }

        x = indent               //проверка по вертикальным линиям
        y = height - advertising_line - width
        for(i in 0..7) {
            for(j in 0..6) {
                if(VERTICAL_RIB[i][j] == 0) {

                    if(red_or_blue == "red") {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 1
                            History.add(mutableListOf(1,1,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            red_or_blue = "blue"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
                        }
                    } else {
                        if (correction_touch(x,y+step/2f)) {
                            VERTICAL_RIB[i][j] = 2
                            History.add(mutableListOf(2,1,i,j))
                            var data_from_memory = encode(History)
                            val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            red_or_blue = "red"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
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
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            red_or_blue = "blue"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
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
                            editor.putString("box_with_computer", data_from_memory)
                            editor.apply()
                            red_or_blue = "red"
                            Log.d("DOPO","ЛОЛ")
                            if(SOUND)
                            {
                                mSound.play(1,1F,1F,1,0,1F)
                            }
                            if(VIBRATION)
                            {
                                vibratorService?.vibrate(70)
                            }
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
