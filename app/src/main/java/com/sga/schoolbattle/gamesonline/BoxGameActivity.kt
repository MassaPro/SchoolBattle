package com.sga.schoolbattle.gamesonline

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.*
import com.sga.schoolbattle.engine.BlitzGameEngine
import com.sga.schoolbattle.engine.LongGameEngine
import com.sga.schoolbattle.engine.ShowResult
import com.sga.schoolbattle.engine.StupidGame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_box_game.signature_canvas_box
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.button_player_1_online_xog
import kotlinx.android.synthetic.main.activity_online_games_temlate.button_player_2_online_xog
import kotlinx.android.synthetic.main.activity_online_games_temlate.timer2_xog_online
import kotlinx.android.synthetic.main.activity_online_games_temlate.timer_xog_online
import java.util.*

class BoxGameActivity : AppCompatActivity() {

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
            while(i < s.length && s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            a = string_to_int(s1)
            s1 = ""
            i++
            while(i < s.length && s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            b = string_to_int(s1)
            s1 = ""
            i++
            while(i < s.length && s[i]!='a')
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

    private var isRun = false
    private var dialog: ShowResult? = null
    private var opponentsName = ""
    private  var yourName = ""
    private lateinit var gameData: DatabaseReference
    private var engine: BlitzGameEngine? = null
    private var engineLong: LongGameEngine? = null


    override fun onResume() {
        super.onResume()
        CONTEXT = this
        currentContext = this
        isRun = true
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        CONTEXT = this
        currentContext = this
        isRun = true
        super.onResume()
        setContentView(R.layout.activity_online_games_temlate)
        signature_canvas_box.visibility = View.VISIBLE

        if (StupidGame != Activity()) StupidGame.finish()
        if (NewGame != Activity()) NewGame.finish()
        yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()
        val opponentsName_: String = intent?.getStringExtra("opponent").toString()
        opponentsName = ""
        for (i in opponentsName_) {
            if (i == ' ') break
            opponentsName += i
        }
        val yu = if (opponentsName < yourName) '1' else '0'
        val op = if (opponentsName < yourName) '0' else '1'
//        players.text = yourName + " VS " + opponentsName
        //      youName.text = yourName
        //    opponentName.text = opponentsName

        val type = intent.getStringExtra("type")
        gameData = if (intent.getStringExtra("key") != null) {
            myRef.child(type).child("BoxGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName + intent.getStringExtra("key")!!  else yourName + '_' + opponentsName + intent.getStringExtra("key")!!)
            )
        } else {
            myRef.child(type).child("BoxGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName  else yourName + '_' + opponentsName)
            )
        }
        signature_canvas_box.positionData = gameData
        signature_canvas_box.blocked = true
        signature_canvas_box.username = yourName
        signature_canvas_box.isFirstMove = intent.getStringExtra("move") == "1"
        button_player_1_online_xog.text = yourName
        button_player_2_online_xog.text = opponentsName

        if (type == "blitz") {

            engine = object : BlitzGameEngine {
                override var timer = Timer(true)
                override var cntUser = 0
                override var cntOpponent = 0
                override val userT = timer2_xog_online
                override val opponentT = timer_xog_online
                override val user = yourName
                override val opponent = opponentsName
                override var move = intent.getStringExtra("move") == "1"
                override var positionData = gameData
                override var activity: Activity = this@BoxGameActivity
                override var cnt = 0
                override var type = "BoxGame"
                override var isFinished = false
                override var userRating = RATING
                override var opponentRating = intent.getStringExtra("rating")!!.toInt()
            }
            button_player_1_online_xog.text = "$yourName (${engine?.userRating})"
            button_player_2_online_xog.text = "$opponentsName (${engine?.opponentRating})"
            engine?.init()
            signature_canvas_box.engine = engine
        } else {
            engineLong = object : LongGameEngine {
                override val userT = timer2_xog_online
                override val opponentT = timer_xog_online
                override val user = yourName
                override val opponent = opponentsName
                override var move = intent.getStringExtra("move") == "1"
                override var positionData = gameData
                override var activity: Activity = this@BoxGameActivity
                override var type = "BoxGame"
                override var key = intent.getStringExtra("key")
            }
            engineLong?.init()

        }
        signature_canvas_box.username = yourName

        button_player_1_online_xog.textSize = 20f
        button_player_2_online_xog.textSize = 20f
        timer2_xog_online.textSize = 15f
        timer_xog_online.textSize = 15f
        PICTURE_AVATAR[AVATAR]?.let { your_avatar_in_game.setImageResource(it) }
        PICTURE_AVATAR[AVATAR]?.let { avatar_of_protivnic.setImageResource(it) } //TODO заменить это на значения его аватарки
        bottom_navigation_xog_online.itemIconTintList = generateColorStateList()
        bottom_navigation_xog_online.itemTextColor = generateColorStateList()
        when (Design) {
            "Egypt" -> {
                label_online.setBackgroundResource(R.drawable.background_egypt)
                button_player_1_online_xog.setTextColor(Color.BLACK)
                button_player_2_online_xog.setTextColor(Color.BLACK)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                timer_xog_online.setTextColor(Color.GREEN)
                timer2_xog_online.setTextColor(Color.GREEN)
                bottom_navigation_xog_online.setBackgroundColor(rgb(255, 230, 163))
                toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
                toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
            }
            "Casino" -> {
                label_online.setBackgroundResource(R.drawable.background2_casino)
                button_player_1_online_xog.setTextColor(Color.YELLOW)
                button_player_2_online_xog.setTextColor(Color.YELLOW)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                timer_xog_online.setTextColor(Color.GREEN)
                timer2_xog_online.setTextColor(Color.GREEN)
                bottom_navigation_xog_online.setBackgroundResource(R.drawable.bottom_navigation_casino)
                toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
                toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
            }
            "Rome" -> {
                label_online.setBackgroundResource(R.drawable.background_rome)
                button_player_1_online_xog.setTextColor(Color.rgb(224, 164, 103))
                button_player_2_online_xog.setTextColor(Color.rgb(224, 164, 103))
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                timer_xog_online.setTextColor(Color.GREEN)
                timer2_xog_online.setTextColor(Color.GREEN)
                bottom_navigation_xog_online.setBackgroundResource(R.drawable.bottom_navigation_rome)
                toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
                toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
            }
            "Japan" -> {
                label_online.setBackgroundResource(R.drawable.background_japan)
                button_player_1_online_xog.setTextColor(Color.BLACK)
                button_player_2_online_xog.setTextColor(Color.BLACK)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                timer_xog_online.setTextColor(Color.GREEN)
                timer2_xog_online.setTextColor(Color.GREEN)
                bottom_navigation_xog_online.setBackgroundColor(Color.WHITE)
                toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
                toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
            }
            "Gothic" -> {
                label_online.setBackgroundResource(R.drawable.background_gothic)
                button_player_1_online_xog.setTextColor(Color.WHITE)
                button_player_2_online_xog.setTextColor(Color.WHITE)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                timer_xog_online.setTextColor(Color.GREEN)
                timer2_xog_online.setTextColor(Color.GREEN)
                bottom_navigation_xog_online.setBackgroundColor(Color.BLACK)
                toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
                toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
                button_player_1_online_xog.textSize = 16.5f
                button_player_2_online_xog.textSize = 16.5f
            }
            "Noir" -> {
                label_online.setBackgroundResource(R.drawable.background_noir)
                button_player_1_online_xog.setTextColor(Color.WHITE)
                button_player_2_online_xog.setTextColor(Color.WHITE)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                timer_xog_online.setTextColor(Color.GREEN)
                timer2_xog_online.setTextColor(Color.GREEN)
                bottom_navigation_xog_online.setBackgroundColor(Color.BLACK)
                toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
                toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
            }
        }

        var initialMove = intent.getStringExtra("move") == "1"
        initMenuFunctions(this, bottom_navigation_xog_online, intent, yourName, opponentsName, gameData)
        gameData.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                signature_canvas_box.blocked = true
                for (i in signature_canvas_box.FIELD.indices) {
                    for (j in signature_canvas_box.FIELD[i].indices) {
                        if (p0.child("FIELD").child("$i").hasChild("$j"))
                        signature_canvas_box.FIELD[i][j] = p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
                    }
                }
                for (i in signature_canvas_box.VERTICAL_RIB.indices) {
                    for (j in signature_canvas_box.VERTICAL_RIB[i].indices) {
                        if (p0.child("VERTICAL_RIB").child("$i").hasChild("$j"))
                        {
                            signature_canvas_box.VERTICAL_RIB[i][j] = p0.child("VERTICAL_RIB").child("$i").child("$j").value.toString().toInt()
                            var flag :Boolean = true
                            val prfs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if(prfs.getString(gameData.toString()+"box_game_history", "0")!="0")
                            {
                                signature_canvas_box.History = prfs?.getString(gameData.toString()+"box_game_history", "0")?.let { decode(it) }!!
                            }
                            for(kol in 0 until signature_canvas_box.History.size)
                            {
                                if(i==signature_canvas_box.History[kol].first && j == signature_canvas_box.History[kol].second && signature_canvas_box.History[kol].third == 1)
                                {
                                    flag = false
                                }
                            }
                            if(flag)
                            {
                                signature_canvas_box.History.add(Triple(i,j, 1))
                                var data_from_memory = encode(signature_canvas_box.History)
                                val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(gameData.toString()+"box_game_history", data_from_memory)
                                editor.apply()
                            }
                        }
                    }
                }
                for (i in signature_canvas_box.HORIZONTAL_RIB.indices) {
                    for (j in signature_canvas_box.HORIZONTAL_RIB[i].indices) {
                        if (p0.child("HORIZONTAL_RIB").child("$i").hasChild("$j")) {
                            signature_canvas_box.HORIZONTAL_RIB[i][j] =
                                p0.child("HORIZONTAL_RIB").child("$i").child("$j").value.toString()
                                    .toInt()
                            var flag: Boolean = true
                            val prfs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if (prfs.getString(
                                    gameData.toString() + "box_game_history",
                                    "0"
                                ) != "0"
                            ) {
                                signature_canvas_box.History =
                                    prfs?.getString(gameData.toString() + "box_game_history", "0")
                                        ?.let { decode(it) }!!
                            }
                            for (kol in 0 until signature_canvas_box.History.size) {
                                if (i == signature_canvas_box.History[kol].first && j == signature_canvas_box.History[kol].second && signature_canvas_box.History[kol].third == 2) {
                                    flag = false
                                }
                            }
                            if (flag) {
                                signature_canvas_box.History.add(Triple(i, j, 2))
                                var data_from_memory = encode(signature_canvas_box.History)
                                val editor =
                                    getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(
                                    gameData.toString() + "box_game_history",
                                    data_from_memory
                                )
                                editor.apply()
                            }
                        }
                    }
                }

                if (p0.hasChild("red_or_blue")) {
                    if (signature_canvas_box.doMove || signature_canvas_box.red_or_blue != p0.child("red_or_blue").value.toString()) {
                        signature_canvas_box.doMove = false
                        engine?.changeMoveAndSyncTimer(p0)
                    }
                    signature_canvas_box.red_or_blue = p0.child("red_or_blue").value.toString()
                }
                if (signature_canvas_box.isFirstMove == (signature_canvas_box.red_or_blue == "red")) {
                    signature_canvas_box.blocked = false
                }
                signature_canvas_box.invalidate()
                fun check_win() : Int
                {
                    var cnt1: Int = 0
                    var cnt2: Int = 0
                    for(i in signature_canvas_box.FIELD)
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
                        return 2
                    }
                    else
                    {
                        if(cnt2>cnt1)
                        {
                            return 1
                        }
                        else
                        {
                            return 3
                        }
                    }
                }
                val ch = check_win()
                if (p0.hasChild("winner") || ch != 0) {
                    signature_canvas_box.blocked = true
                    var res = ""
                    if (!initialMove == (yourName < opponentsName_)) {
                        res = if (yu == '0') {
                            if (ch == 1) {
                                "Победа"
                            } else {
                                "Поражение"
                            }
                        } else {
                            if (ch == 2) {
                                "Победа"
                            } else {
                                "Поражение"
                            }
                        }
                    } else {
                        res = if (yu == '1') {
                            if (ch == 1) {
                                "Победа"
                            } else {
                                "Поражение"
                            }
                        } else {
                            if (ch == 2) {
                                "Победа"
                            } else {
                                "Поражение"
                            }
                        }
                    }
                    if (ch != 2 && ch != 1) res = "Ничья"
                    if (p0.child("winner").value.toString() == yourName) {
                        res = "Победа"
                    }
                    if (p0.child("winner").value.toString() == opponentsName) {
                        res = "Поражение"
                    }
                    engine?.finish(res, this@BoxGameActivity, isRun)
                    engineLong?.finish(res, this@BoxGameActivity, isRun)
                    gameData.removeEventListener(this)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        isRun = false
        currentContext = null
        engine?.finish("Поражение", this, isRun)
        finish()
    }
}

class CanvasView_Boxs_online(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var CONDITION_BOX  = 0;

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
            while(i < s.length && s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            a = string_to_int(s1)
            s1 = ""
            i++
            while(i < s.length && s[i]!='a')
            {
                s1+=s[i]
                i++
            }
            b = string_to_int(s1)
            s1 = ""
            i++
            while(i < s.length && s[i]!='a')
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

    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (width/2f/size_field_x.toFloat())*(width/2f/size_field_x.toFloat())/2f)
        {
            return true
        }
        return false
    }

    lateinit var positionData: DatabaseReference

    var username = ""
    var engine: BlitzGameEngine? = null
    var isFirstMove = false

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

    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var CLONE_FIELD = Array(7){IntArray(7)}     //для фишеК
    var CLONE_VERTICAL_RIB = Array(8){IntArray(7)}    //для ребер
    var CLONE_HORIZONTAL_RIB = Array(7){IntArray(8)}

    var radius_of_point: Float = 0f
    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f


    var blocked = true




    init{

        red_or_blue = "red"
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.BLACK)     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(5f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(5f)

        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..6) {
            for(j in 0 ..6) {
                FIELD[i][j] = 0  //не заполненный
                CLONE_FIELD[i][j] = 0;
            }
        }

        for(i in 0..7)
        {
            for(j in 0..6)
            {
                VERTICAL_RIB[i][j] = 0
                HORIZONTAL_RIB[j][i] = 0
                CLONE_VERTICAL_RIB[i][j] = 0
                CLONE_HORIZONTAL_RIB[j][i] = 0
            }
        }



    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.red
    );       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.blue
    );
    var box1_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_egypt);
    var box2_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_egypt);

    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.illumination
    );
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.green
    );


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        radius_of_point = 10f
        size_field_x  = 7
        size_field_y  = 7
        indent = (getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = 150f           //полоска для рекламы

        step = width/size_field_x
        k = height-width-advertising_line


        val right_red: Bitmap 
        val right_blue: Bitmap

        if(Design == "Egypt")
        {
            right_red = Bitmap.createScaledBitmap(box1_egypt,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            right_blue = Bitmap.createScaledBitmap(box2_egypt,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        }
        else
        {
            right_red = Bitmap.createScaledBitmap(red,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
            right_blue = Bitmap.createScaledBitmap(blue,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
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

        if(CONDITION_BOX == 0) {
            var x: Float;
            var y: Float
            x = indent
            y = height - advertising_line - width
            for (i in 0..7)                    //вырисовка точек
            {
                for (j in 0..7) {
                    canvas?.drawCircle(x, y, radius_of_point, paint_circle)
                    x += step
                }
                x = indent
                y += step
            }

            x = indent
            y = height - advertising_line - width
            for (i in 0..7)               //вырисовка вертикальных ребер
            {
                for (j in 0..6) {
                    if (VERTICAL_RIB[i][j] == 1) {
                        canvas?.drawLine(
                            x,
                            y + radius_of_point,
                            x,
                            y + step - radius_of_point,
                            paint_rib_1
                        )
                    }
                    if (VERTICAL_RIB[i][j] == 2) {
                        canvas?.drawLine(
                            x,
                            y + radius_of_point,
                            x,
                            y + step - radius_of_point,
                            paint_rib_2
                        )
                    }
                    y += step
                }
                x += step
                y = height - advertising_line - width
            }

            x = indent
            y = height - advertising_line - width
            for (i in 0..6)                 //вырисовка горизонтальных ребер
            {
                for (j in 0..7) {
                    if (HORIZONTAL_RIB[i][j] == 1) {
                        canvas?.drawLine(
                            x + radius_of_point,
                            y,
                            x + step - radius_of_point,
                            y,
                            paint_rib_1
                        )
                    }
                    if (HORIZONTAL_RIB[i][j] == 2) {
                        canvas?.drawLine(
                            x + radius_of_point,
                            y,
                            x + step - radius_of_point,
                            y,
                            paint_rib_2
                        )
                    }
                    y += step
                }
                x += step
                y = height - advertising_line - width
            }

            x = indent
            y = height - width - advertising_line
            for (i in 0..6) {
                for (j in 0..6) {
                    if (FIELD[i][j] == 1) {
                        canvas?.drawBitmap(right_red, x, y, paint_circle)
                    }
                    if (FIELD[i][j] == 2) {
                        canvas?.drawBitmap(right_blue, x, y, paint_circle)
                    }
                    y += step
                }
                x += step
                y = height - width - advertising_line
            }
        }
        else
        {
            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            History = prfs?.getString(positionData.toString()+"box_game_history", "0")?.let { decode(it) }!!
            for(i in 0 until CLONE_FIELD.size)
            {
                for(j in 0 until CLONE_FIELD[0].size)
                {
                    CLONE_FIELD[i][j] = 0;
                }
            }
            for(i in 0 until CLONE_VERTICAL_RIB.size)
            {
                for(j in 0 until CLONE_VERTICAL_RIB[0].size)
                {
                    CLONE_VERTICAL_RIB[i][j] = 0;
                }
            }
            for(i in 0 until CLONE_HORIZONTAL_RIB.size)
            {
                for(j in 0 until CLONE_HORIZONTAL_RIB[0].size)
                {
                    CLONE_HORIZONTAL_RIB[i][j] = 0;
                }
            }

            if(CONDITION_BOX>History.size)
            {
                CONDITION_BOX = History.size
            }

            for(p in 0 until History.size - CONDITION_BOX)
            {
                var i: Int  = History[p].first
                var j: Int = History[p].second
                if(History[p].third == 1)
                {
                    CLONE_VERTICAL_RIB[i][j] = VERTICAL_RIB[i][j]
                }
                else
                {
                    CLONE_HORIZONTAL_RIB[i][j] = HORIZONTAL_RIB[i][j]
                }
            }

            for(i in 0..6)
            {
                for(j in 0..6)
                {
                    if(CLONE_VERTICAL_RIB[i][j]>0 && CLONE_HORIZONTAL_RIB[i][j]>0 && CLONE_HORIZONTAL_RIB[i][j+1]>0 && CLONE_VERTICAL_RIB[i+1][j]>0 && CLONE_FIELD[i][j]==0) //если образовался квадратик
                    {
                        CLONE_FIELD[i][j] = FIELD[i][j]
                    }
                }
            }

            var x: Float;
            var y: Float
            x = indent
            y = height - advertising_line - width
            for (i in 0..7)                    //вырисовка точек
            {
                for (j in 0..7) {
                    canvas?.drawCircle(x, y, radius_of_point, paint_circle)
                    x += step
                }
                x = indent
                y += step
            }

            x = indent
            y = height - advertising_line - width
            for (i in 0..7)               //вырисовка вертикальных ребер
            {
                for (j in 0..6) {
                    if (CLONE_VERTICAL_RIB[i][j] == 1) {
                        canvas?.drawLine(
                            x,
                            y + radius_of_point,
                            x,
                            y + step - radius_of_point,
                            paint_rib_1
                        )
                    }
                    if (CLONE_VERTICAL_RIB[i][j] == 2) {
                        canvas?.drawLine(
                            x,
                            y + radius_of_point,
                            x,
                            y + step - radius_of_point,
                            paint_rib_2
                        )
                    }
                    y += step
                }
                x += step
                y = height - advertising_line - width
            }

            x = indent
            y = height - advertising_line - width
            for (i in 0..6)                 //вырисовка горизонтальных ребер
            {
                for (j in 0..7) {
                    if (CLONE_HORIZONTAL_RIB[i][j] == 1) {
                        canvas?.drawLine(
                            x + radius_of_point,
                            y,
                            x + step - radius_of_point,
                            y,
                            paint_rib_1
                        )
                    }
                    if (CLONE_HORIZONTAL_RIB[i][j] == 2) {
                        canvas?.drawLine(
                            x + radius_of_point,
                            y,
                            x + step - radius_of_point,
                            y,
                            paint_rib_2
                        )
                    }
                    y += step
                }
                x += step
                y = height - advertising_line - width
            }

            x = indent
            y = height - width - advertising_line
            for (i in 0..6) {
                for (j in 0..6) {
                    if (CLONE_FIELD[i][j] == 1) {
                        canvas?.drawBitmap(right_red, x, y, paint_circle)
                    }
                    if (CLONE_FIELD[i][j] == 2) {
                        canvas?.drawBitmap(right_blue, x, y, paint_circle)
                    }
                    y += step
                }
                x += step
                y = height - width - advertising_line
            }
        }
    }

    var doMove = false


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (blocked) {
            return true
        }

        if(CONDITION_BOX!=0)
        {
            return false
        }

        var isCorrect = false

        circlex = event!!.x
        circley = event!!.y

        x = indent               //проверка по вертикальным линиям
        y = height - advertising_line - width

        val upd = mutableMapOf<String, Any>()

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
                            upd["VERTICAL_RIB/$i/$j"] = VERTICAL_RIB[i][j]
                            //positionData.child("VERTICAL_RIB").child("$i").child("$j").setValue(VERTICAL_RIB[i][j])
                            red_or_blue = "blue"
                            isCorrect = true
                            var flag :Boolean = true
                            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if(prfs?.getString(positionData.toString()+"box_game_history", "0")!="0")
                            {
                                History = prfs?.getString(positionData.toString()+"box_game_history", "a")?.let { decode(it) }!!
                            }
                            for(kol in 0 until History.size)
                            {
                                if(i==History[kol].first && j == History[kol].second && History[kol].third == 1)
                                {
                                    flag = false
                                }
                            }
                            if(flag)
                            {
                                History.add(Triple(i,j, 1))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(positionData.toString()+"box_game_history", data_from_memory)
                                editor.apply()
                            }
                        }
                    }
                    else
                    {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 2
                            upd["VERTICAL_RIB/$i/$j"] = VERTICAL_RIB[i][j]
                            //positionData.child("VERTICAL_RIB").child("$i").child("$j").setValue(VERTICAL_RIB[i][j])
                            red_or_blue = "red"
                            isCorrect = true
                            Log.d("DOPO","ЛОЛ")
                            var flag :Boolean = true
                            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if(prfs?.getString(positionData.toString()+"box_game_history", "0")!="0")
                            {
                                History = prfs?.getString(positionData.toString()+"box_game_history", "a")?.let { decode(it) }!!
                            }
                            for(kol in 0 until History.size)
                            {
                                if(i==History[kol].first && j == History[kol].second && History[kol].third == 1)
                                {
                                    flag = false
                                }
                            }
                            if(flag)
                            {
                                History.add(Triple(i,j, 1))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(positionData.toString()+"box_game_history", data_from_memory)
                                editor.apply()
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
                            upd["HORIZONTAL_RIB/$i/$j"] = HORIZONTAL_RIB[i][j]
                            //positionData.child("HORIZONTAL_RIB").child("$i").child("$j").setValue(HORIZONTAL_RIB[i][j])
                            red_or_blue = "blue"
                            isCorrect = true
                            var flag :Boolean = true
                            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if(prfs?.getString(positionData.toString()+"box_game_history", "0")!="0")
                            {
                                History = prfs?.getString(positionData.toString()+"box_game_history", "0")?.let { decode(it) }!!
                            }
                            for(kol in 0 until History.size)
                            {
                                if(i==History[kol].first && j == History[kol].second && History[kol].third == 2)
                                {
                                    flag = false
                                }
                            }
                            if(flag)
                            {
                                History.add(Triple(i,j, 2))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(positionData.toString()+"box_game_history", data_from_memory)
                                editor.apply()
                            }
                        }
                    }
                    else
                    {
                        if (correction_touch(x+step/2f,y))
                        {
                            HORIZONTAL_RIB[i][j] = 2
                            upd["HORIZONTAL_RIB/$i/$j"] = HORIZONTAL_RIB[i][j]
                            //positionData.child("HORIZONTAL_RIB").child("$i").child("$j").setValue(HORIZONTAL_RIB[i][j])
                            red_or_blue = "red"
                            isCorrect = true
                            var flag :Boolean = true
                            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if(prfs?.getString(positionData.toString()+"box_game_history", "0")!="0")
                            {
                                History = prfs?.getString(positionData.toString()+"box_game_history", "0")?.let { decode(it) }!!
                            }
                            for(kol in 0 until History.size)
                            {
                                if(i==History[kol].first && j == History[kol].second && History[kol].third == 2)
                                {
                                    flag = false
                                }
                            }
                            if(flag)
                            {
                                History.add(Triple(i,j, 2))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(positionData.toString()+"box_game_history", data_from_memory)
                                editor.apply()
                            }
                        }
                    }
                }
                y += step
            }
            x += step
            y  = height - advertising_line - width
        }
        var flag = false
        var bur = true
        for(i in 0..6)
        {
            for(j in 0..6)
            {
                if(VERTICAL_RIB[i][j]>0 && HORIZONTAL_RIB[i][j]>0 && HORIZONTAL_RIB[i][j+1]>0 && VERTICAL_RIB[i+1][j]>0 && FIELD[i][j]==0) //если образовался квадратик
                {
                    if(!flag)
                    {
                        bur = false
                        flag = true
                        red_or_blue = if(red_or_blue == "red")            //снова ходит тот же игрок
                        {
                            "blue"
                        } else {
                            "red"
                        }
                    }
                    if(red_or_blue == "red")
                    {
                        FIELD[i][j] = 1
                        upd["FIELD/$i/$j"] = FIELD[i][j]

                    }
                    else
                    {
                        FIELD[i][j] = 2
                        upd["FIELD/$i/$j"] = FIELD[i][j]

                    }
                }
            }
        }
        x = 0f
        y = 0f
        invalidate()
        if (isCorrect) {
            if (bur) {
                doMove = true
            }
            upd["red_or_blue"] = red_or_blue
            upd["time/$username"] = engine?.cntUser.toString()
            positionData.updateChildren(upd)
        }
        return true
    }

}
