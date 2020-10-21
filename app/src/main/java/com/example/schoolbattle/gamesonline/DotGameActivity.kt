package com.example.schoolbattle.gamesonline

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import com.example.schoolbattle.engine.BlitzGameEngine
import com.example.schoolbattle.engine.LongGameEngine
import com.example.schoolbattle.engine.ShowResult
import com.example.schoolbattle.engine.StupidGame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.instacart.library.truetime.TrueTime
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.bottom_navigation_xog_online
import kotlinx.android.synthetic.main.activity_online_games_temlate.button_player_1_online_xog
import kotlinx.android.synthetic.main.activity_online_games_temlate.button_player_2_online_xog
import kotlinx.android.synthetic.main.activity_online_games_temlate.timer2_xog_online
import kotlinx.android.synthetic.main.activity_online_games_temlate.timer_xog_online
import kotlinx.android.synthetic.main.activity_online_games_temlate.toolbar2_xog_online
import kotlinx.android.synthetic.main.activity_online_games_temlate.toolbar_xog_online
import kotlinx.android.synthetic.main.activity_x_o_game.*
import java.util.*


//TODO , рисовать ребра только один раз до этого узнав, также можно не включать в цепочки вершины которые окружены 6 такими же вершинами

class DotGameActivity: AppCompatActivity() {

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


    private var isRun = false
    private var engine: BlitzGameEngine? = null
    private var engineLong: LongGameEngine? = null

    var yourName = ""
    var opponentsName = ""
    var type = ""
    lateinit var gameData: DatabaseReference


    override fun onResume() {
        super.onResume()
        currentContext = this
        CONTEXT = this
        isRun = true
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_online_games_temlate)
        signature_canvas3.visibility = (View.VISIBLE)
        CONTEXT = this
        currentContext = this
        isRun = true
       
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


        if (intent.getStringExtra("type") != null) type = intent.getStringExtra("type")!!
        gameData = if (intent.getStringExtra("key") != null) {
            myRef.child(type).child("DotGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName + intent.getStringExtra("key")!!  else yourName + '_' + opponentsName + intent.getStringExtra("key")!!)
            )
        } else {
            myRef.child(type).child("DotGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName  else yourName + '_' + opponentsName)
            )
        }

        //signature_canvas3.blocked = true
        signature_canvas3.positionData = gameData
        signature_canvas3.blocked = true
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
                override var activity: Activity = this@DotGameActivity
                override var cnt = 0
                override var type = "DotGame"
                override var isFinished = false
                override var userRating = RATING
                override var opponentRating = intent.getStringExtra("rating")!!.toInt()
            }
            button_player_1_online_xog.text = "$yourName (${engine?.userRating})"
            button_player_2_online_xog.text = "$opponentsName (${engine?.opponentRating})"
            engine?.init()
            signature_canvas3.engine = engine
            signature_canvas3.username = yourName
        } else {
            engineLong = object : LongGameEngine {
                override val userT = timer2_xog_online
                override val opponentT = timer_xog_online
                override val user = yourName
                override val opponent = opponentsName
                override var move = intent.getStringExtra("move") == "1"
                override var positionData = gameData
                override var activity: Activity = this@DotGameActivity
                override var type = "DotGame"
                override var key = intent.getStringExtra("key")
            }
            //Toast.makeText(this, engineLong?.key.toString(), Toast.LENGTH_LONG).show()
            engineLong?.init()
        }
        signature_canvas3.username = yourName
        signature_canvas3.isFirstMove = intent.getStringExtra("move") == "1"
        if (!signature_canvas3.isFirstMove) {
            signature_canvas3.red_or_blue = 2
        }

        button_player_1_online_xog.textSize = 20f
        button_player_2_online_xog.textSize = 20f
        timer2_xog_online.textSize = 15f
        timer_xog_online.textSize = 15f
        PICTURE_AVATAR[AVATAR]?.let { your_avatar_in_game.setImageResource(it) }
        PICTURE_AVATAR[AVATAR]?.let { avatar_of_protivnic.setImageResource(it) } //TODO заменить это на значения его аватарки
        bottom_navigation_xog_online.itemIconTintList = generateColorStateList()
        bottom_navigation_xog_online.itemTextColor = generateColorStateList()
        if(Design == "Egypt" ) {
            label_online.setBackgroundResource(R.drawable.background_egypt)
            button_player_1_online_xog.setTextColor(Color.BLACK)
            button_player_2_online_xog.setTextColor(Color.BLACK)
            button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
            button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextColor(Color.GREEN)
            bottom_navigation_xog_online.setBackgroundColor(Color.rgb(255, 230, 163))
            toolbar_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Casino" ) {
            label_online.setBackgroundResource(R.drawable.background2_casino)
            button_player_1_online_xog.setTextColor(Color.YELLOW)
            button_player_2_online_xog.setTextColor(Color.YELLOW)
            button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
            button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextColor(Color.GREEN)
            bottom_navigation_xog_online.setBackgroundResource(R.drawable.bottom_navigation_casino)
            toolbar_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Rome" ) {
            label_online.setBackgroundResource(R.drawable.background_rome)
            button_player_1_online_xog.setTextColor(Color.rgb(224, 164, 103))
            button_player_2_online_xog.setTextColor(Color.rgb(224, 164, 103))
            button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
            button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextColor(Color.GREEN)
            bottom_navigation_xog_online.setBackgroundResource(R.drawable.bottom_navigation_rome)
            toolbar_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Japan" ) {
            label_online.setBackgroundResource(R.drawable.background_japan)
            button_player_1_online_xog.setTextColor(Color.BLACK)
            button_player_2_online_xog.setTextColor(Color.BLACK)
            button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
            button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextColor(Color.GREEN)
            bottom_navigation_xog_online.setBackgroundColor(Color.WHITE)
            toolbar_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }
        else if(Design == "Gothic" ) {
            label_online.setBackgroundResource(R.drawable.background_gothic)
            button_player_1_online_xog.setTextColor(Color.WHITE)
            button_player_2_online_xog.setTextColor(Color.WHITE)
            button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
            button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextColor(Color.GREEN)
            bottom_navigation_xog_online.setBackgroundColor(Color.BLACK)
            toolbar_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            button_player_1_online_xog.textSize = 16.5f
            button_player_2_online_xog.textSize = 16.5f
        }
        else if(Design == "Noir") {
            label_online.setBackgroundResource(R.drawable.background_noir)
            button_player_1_online_xog.setTextColor(Color.WHITE)
            button_player_2_online_xog.setTextColor(Color.WHITE)
            button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
            button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextColor(Color.GREEN)
            bottom_navigation_xog_online.setBackgroundColor(Color.BLACK)
            toolbar_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(Color.argb(0, 0, 0, 0))
        }

        initMenuFunctions(this, bottom_navigation_xog_online, intent, yourName, opponentsName, gameData)
        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.childrenCount >= 2) {
                    engine?.changeMoveAndSyncTimer(p0)
                    Log.w("HHHHHH", "HI")
                }
                var cnt = 0
                signature_canvas3.blocked = true
                for (i in 0..signature_canvas3.FIELD.size - 1) {
                    for (j in 0..signature_canvas3.FIELD[i].size - 1) {
                        if (p0.child("FIELD").child("$i").hasChild("$j")) {
                            cnt++
                            if(signature_canvas3.FIELD[i][j]==0)
                            {
                                val prfs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                                if(prfs?.getString(gameData.toString()+"dot_game_history", "0")!="0")
                                {
                                    signature_canvas3.History = prfs?.getString(gameData.toString()+"dot_game_history", "0")?.let { decode(it) }!!
                                }
                                var flag :Boolean = true
                                for(kol in 0 until signature_canvas3.History.size)
                                {
                                    if(i==signature_canvas3.History[kol].first && j ==signature_canvas3.History[kol].second)
                                    {
                                        flag = false
                                    }
                                }
                                if(flag)
                                {
                                    signature_canvas3.History.add(Triple(i,j, p0.child("FIELD").child("$i").child("$j").value.toString().toInt()))
                                    var data_from_memory = encode(signature_canvas3.History)
                                    val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString(gameData.toString()+"dot_game_history", data_from_memory)
                                    editor.apply()
                                }

                            }
                            signature_canvas3.FIELD[i][j] =
                                p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
                        }
                    }
                }

                for (i in 0..signature_canvas3.a.size - 1) {
                    for (j in 0..signature_canvas3.a[i].size - 1) {
                        if (p0.child("a").child("$i").hasChild("$j")) {
                            signature_canvas3.a[i][j] =
                                p0.child("a").child("$i").child("$j").value.toString().toInt()
                        }
                    }
                }

                if (p0.hasChild("red_or_blue")) {
                    signature_canvas3.red_or_blue = p0.child("red_or_blue").value.toString().toInt()
                }
                if (signature_canvas3.isFirstMove == (cnt % 2 == 0)) {
                    signature_canvas3.blocked = false
                }
                Log.w("db_red_or_blue", signature_canvas3.red_or_blue.toString())
                Log.w("db_isFirst", signature_canvas3.isFirstMove.toString())
                Log.w("db_blocked", signature_canvas3.blocked.toString())
                signature_canvas3.invalidate()

                fun check_win() : Int {
                    var cnt1 : Int = 0
                    var cnt2 : Int = 0
                    for(i in 0 until signature_canvas3.FIELD.size)
                    {
                        for(j in 0 until signature_canvas3.FIELD[0].size)
                        {
                            if(signature_canvas3.a[j][i]==0)
                            {
                                return 0
                            }
                            if(signature_canvas3.a[j][i]!= signature_canvas3.FIELD[i][j])
                            {
                                if(signature_canvas3.a[j][i]==1)
                                {
                                    cnt1++
                                }
                                else
                                {
                                    cnt2++
                                }
                            }
                        }
                    }
                    if(cnt1>cnt2)
                    {
                        return 1
                    }
                    else
                    {
                        if(cnt1 == cnt2 )
                        {
                            return 3
                        }
                        else
                        {
                            return 2
                        }
                    }
                }
                val ch = check_win()
                Log.w("RES", "$ch")
                if (p0.hasChild("winner") || ch != 0) {
                    Log.w("RES2", "$ch")
                    engine?.stopTimer()
                    signature_canvas3.blocked = true
                    var res = if (ch == 2 && yu == '0' || ch == 1 && yu == '1') {
                        "Победа"
                    } else if (ch == 2 && yu == '1' || ch == 1 && yu == '0') {
                        "Поражение"
                    } else {
                        "Ничья"
                    }
                    if (p0.child("winner").value == yourName) {
                        res = "Победа"
                    }
                    if (p0.child("winner").value == opponentsName) {
                        res = "Поражение"
                    }
                    engine?.finish(res, this@DotGameActivity, isRun)
                    engineLong?.finish(res, this@DotGameActivity, isRun)
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

class CanvasViewDot(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var CONDITION_DOT : Int = 0;

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

    var username: String = ""
    var engine: BlitzGameEngine? = null

    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (step/2f)*(step/2f))
        {
            return true
        }
        return false
    }


    fun find(playerId: Int, g: MutableList<MutableList<Int>>,N: Int, M: Int): MutableList<Pair<Int, Int>> {
        val used: MutableList<MutableList<Boolean>> = mutableListOf()
        val res: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in 0 until N ){
            used.add(mutableListOf())
            for (j in 0 until M) {
                used[i].add(false)
            }
        }

        fun ch(i : Int, j: Int): Boolean {
            return (i in 0 until N) && (j in 0 until M) && (g[i][j] != playerId)
        }

        fun dfs(i : Int, j : Int) {
            used[i][j] = true
            for (x in -1 .. 1) {
                for (y in -1..1) {
                    if (ch(i + x, j + y) && !used[i + x][j + y]) {
                        if (x * y == 0 || g[i][j + y] != playerId || g[i + x][j] != playerId) {
                            dfs(i + x, j + y)
                        }
                    }
                }
            }
        }
        for (i in 0 until N) {
            if (ch(i, 0)) {
                dfs(i, 0)
            }
            if (ch(i, M - 1)) {
                dfs(i, M - 1)
            }
        }
        for (j in 0 until M) {
            if (ch(0, j)) {
                dfs(0, j)
            }
            if (ch(N - 1, j)) {
                dfs(N - 1, j)
            }
        }
        for (i in 0 until N) {
            for (j in 0 until M) {
                if (!used[i][j]) {
                    g[i][j] = playerId
                    res.add(Pair(i, j))
                }
            }
        }
        return res
    }


    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var blocked = true
    var isFirstMove = false

    var lastx: Int = -1
    var lasty: Int = -1
    var red_or_blue = 1
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var indent: Float = 0f      //оступ

    var paint_circle : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var paint_rib_1: Paint = Paint()
    var paint_rib_2: Paint = Paint()

    var shading_1 : Paint = Paint()
    var shading_2 : Paint = Paint()

    var FIELD = Array(11){IntArray(16)}     //для фишеК
    val a: MutableList<MutableList<Int>> = mutableListOf()     // для псевдо фишек
    var p: MutableList<Pair<Int,Int>> = mutableListOf()

    var FIELD_CLONE = Array(11){IntArray(16)}     //для фишеК
    val a_CLONE: MutableList<MutableList<Int>> = mutableListOf()     // для псевдо фишек
    var p_CLONE: MutableList<Pair<Int,Int>> = mutableListOf()
    var red_or_blue_CLONE = 1




    var radius_of_point: Float = 0f
    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f

    lateinit var positionData: DatabaseReference

    var Is_defined_TREE_OF_WAYS : Boolean = false
    init{

        for (i in 0 until 16) {
            a.add(mutableListOf())
        }
        for (i in a.indices) {
            for (j in 0 until 11) {
                a[i].add(0)
            }
        }
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        if(Design == "Egypt") {
            paint_rib_1.setColor(Color.BLACK) //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(5f)
            paint_rib_2.setColor(Color.WHITE)
            paint_rib_2.setStrokeWidth(5f)

            shading_1.setColor(Color.BLACK)
            shading_2.setColor(Color.WHITE)
            shading_1.setStrokeWidth(2f)
            shading_2.setStrokeWidth(2f)
        }
        else {
            paint_rib_1.setColor(Color.RED) //цвета для ребер  и их ширина
            paint_rib_1.setStrokeWidth(5f)
            paint_rib_2.setColor(Color.BLUE)
            paint_rib_2.setStrokeWidth(5f)

            shading_1.setColor(Color.RED)
            shading_2.setColor(Color.BLUE)
            shading_1.setStrokeWidth(2f)
            shading_2.setStrokeWidth(2f)
        }

        for(i in 0 until FIELD.size)
        {
            for(j in 0 until FIELD[i].size)
            {
                FIELD[i][j] = 0
            }
        }

    }




    //var red : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red)       //картинки фишек и подсветки
    //var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blue)


    @ExperimentalStdlibApi
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if(CONDITION_DOT==0) {
            fun dpToPx(dp: Int): Float {
                return (dp * Resources.getSystem().displayMetrics.density)
            }

            radius_of_point = 8f
            size_field_x = 10
            size_field_y = 15


            indent =
                0f//(getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
            width = getWidth().toFloat() - 2 * indent
            height =
                getHeight().toFloat() - 2 * indent //ширина и высота экрана (от ширины в основном все зависит)

            Log.w("WH", "$width  $height")
            Log.w("WH2", "${getWidth()}  ${getHeight()}")
            if (height / width < size_field_y.toFloat() / size_field_x.toFloat()) {
                width = height * size_field_x.toFloat() / size_field_y.toFloat()
            }
            indent = (width.toFloat() / (size_field_x.toFloat() + 1f)) / 2f
            width -= 2f * indent
            height -= 2f * indent

            advertising_line =
                (height - width / size_field_x * size_field_y) / 2 //полоска для рекламы

            step = width / size_field_x
            k =
                height - width * (size_field_y.toFloat() / size_field_x.toFloat()) - advertising_line


            Log.d("Para", p.toString())

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

            var x: Float;
            var y: Float
            x = indent
            y =
                height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            for (i in 0..size_field_x)                    //вырисовка точек
            {
                for (j in 0..size_field_y) {
                    if (FIELD[i][j] == 1) {
                        canvas?.drawCircle(x, y, radius_of_point * 1.5f, paint_rib_1)
                    } else {
                        if (FIELD[i][j] == 2) {
                            canvas?.drawCircle(x, y, radius_of_point * 1.5f, paint_rib_2)
                        } else {
                            canvas?.drawCircle(x, y, radius_of_point, paint_circle)
                        }
                    }

                    y += step
                }
                x += step
                y =
                    height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            }

            Log.w("YYA", a.toString())
            p = find(1, a, 16, 11)
            for (i in 0..size_field_x) {
                for (j in 0..size_field_y) {
                    if (Pair(j, i) in p) {
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            if (Pair(j, i - 1) in p && Pair(j - 1, i) in p) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i - step / 3 * 2
                                var X2: Float = indent + step * i - step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_1)
                                canvas?.drawLine(X2, Y, X, Y2, shading_1)
                            }
                        }
                        if (i + 1 < 11 && j - 1 >= 0) {
                            if (Pair(j, i + 1) in p && Pair(j - 1, i) in p) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i + step / 6
                                var X2: Float = indent + step * i + step / 3
                                var X3: Float = indent + step * i + step / 2
                                var X4: Float = indent + step * i + step * 2 / 3
                                var X5: Float = indent + step * i + step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_1)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_1)
                                canvas?.drawLine(X, Y, X3, Y3, shading_1)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_1)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_1)

                            }
                        }
                        if (i - 1 >= 0 && j + 1 < 16) {
                            if (Pair(j, i - 1) in p && Pair(j + 1, i) in p) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i - step / 6
                                var X2: Float = indent + step * i - step / 3
                                var X3: Float = indent + step * i - step / 2
                                var X4: Float = indent + step * i - step * 2 / 3
                                var X5: Float = indent + step * i - step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_1)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_1)
                                canvas?.drawLine(X, Y, X3, Y3, shading_1)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_1)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_1)

                            }
                        }
                        if (i + 1 < 11 && j + 1 < 16) {
                            if (Pair(j, i + 1) in p && Pair(j + 1, i) in p) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i + step / 3 * 2
                                var X2: Float = indent + step * i + step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_1)
                                canvas?.drawLine(X2, Y, X, Y2, shading_1)

                            }
                        }
                    }
                }
            }
            Log.w("YYB", a.toString())
            p = find(2, a, 16, 11)
            for (i in 0..size_field_x) {
                for (j in 0..size_field_y) {
                    if (Pair(j, i) in p) {
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            if (Pair(j, i - 1) in p && Pair(j - 1, i) in p) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i - step / 3 * 2
                                var X2: Float = indent + step * i - step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_2)
                                canvas?.drawLine(X2, Y, X, Y2, shading_2)
                            }
                        }
                        if (i + 1 < 11 && j - 1 >= 0) {
                            if (Pair(j, i + 1) in p && Pair(j - 1, i) in p) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i + step / 6
                                var X2: Float = indent + step * i + step / 3
                                var X3: Float = indent + step * i + step / 2
                                var X4: Float = indent + step * i + step * 2 / 3
                                var X5: Float = indent + step * i + step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_2)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_2)
                                canvas?.drawLine(X, Y, X3, Y3, shading_2)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_2)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_2)

                            }
                        }
                        if (i - 1 >= 0 && j + 1 < 16) {
                            if (Pair(j, i - 1) in p && Pair(j + 1, i) in p) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i - step / 6
                                var X2: Float = indent + step * i - step / 3
                                var X3: Float = indent + step * i - step / 2
                                var X4: Float = indent + step * i - step * 2 / 3
                                var X5: Float = indent + step * i - step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_2)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_2)
                                canvas?.drawLine(X, Y, X3, Y3, shading_2)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_2)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_2)

                            }
                        }
                        if (i + 1 < 11 && j + 1 < 16) {
                            if (Pair(j, i + 1) in p && Pair(j + 1, i) in p) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i + step / 3 * 2
                                var X2: Float = indent + step * i + step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_2)
                                canvas?.drawLine(X2, Y, X, Y2, shading_2)

                            }
                        }
                    }
                }
            }


            Log.w("YYC", a.toString())


            for (i in 0..9)    //горизонтальные ребра
            {
                for (j in 0..15) {
                    if (j == 0) {
                        if (a[j][i] == a[j][i + 1] && (a[j][i] == a[j + 1][i] || a[j][i] == a[j + 1][i + 1]) && a[j][i] != 0) {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a[j][i] == 1) {
                                canvas?.drawLine(X, Y, X1, Y, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X1, Y, paint_rib_2)
                            }
                        }
                    } else {
                        if (j == 15) {
                            if (a[j][i] == a[j][i + 1] && (a[j][i] == a[j - 1][i] || a[j][i] == a[j - 1][i + 1]) && a[j][i] != 0) {
                                var X: Float = indent + i * step
                                var X1: Float = X + step
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_2)
                                }
                            }
                        } else {
                            var k: Int = 0
                            if (a[j][i] == a[j][i + 1] && (a[j][i] == a[j + 1][i] || a[j][i] == a[j + 1][i + 1]) && a[j][i] != 0) {
                                k++
                            }
                            if (a[j][i] == a[j][i + 1] && (a[j][i] == a[j - 1][i] || a[j][i] == a[j - 1][i + 1]) && a[j][i] != 0) {
                                k++
                            }
                            if (k == 1) {
                                val X: Float = indent + i * step
                                val X1: Float = X + step
                                val Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_2)
                                }
                            }
                        }
                    }
                }
            }

            for (i in 0..10)     //вертикальные ребра
            {
                for (j in 0..14) {
                    if (i == 0) {
                        if (a[j][i] == a[j + 1][i] && (a[j][i + 1] == a[j][i] || a[j + 1][i + 1] == a[j][i]) && a[j][i] != 0) {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a[j][i] == 1) {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                            }
                        }
                    } else {
                        if (i == 10) {
                            if (a[j][i] == a[j + 1][i] && (a[j][i - 1] == a[j][i] || a[j + 1][i - 1] == a[j][i]) && a[j][i] != 0) {
                                var X: Float = indent + i * step
                                var X1: Float = X + step
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                                }
                            }
                        } else {
                            var k: Int = 0
                            if (a[j][i] == a[j + 1][i] && (a[j][i + 1] == a[j][i] || a[j + 1][i + 1] == a[j][i]) && a[j][i] != 0) {
                                k++
                            }
                            if (a[j][i] == a[j + 1][i] && (a[j][i - 1] == a[j][i] || a[j + 1][i - 1] == a[j][i]) && a[j][i] != 0) {
                                k++
                            }
                            if (k == 1) {
                                var X: Float = indent + i * step
                                var X1: Float = X + step
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                                }
                            }
                        }
                    }
                }
            }

            for (i in 0..9) {
                for (j in 0..14) {
                    if (a[j][i] != 0 && a[j][i] != a[j + 1][i + 1] && a[j][i] == a[j][i + 1] && a[j][i] == a[j + 1][i]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a[j][i] == 1) {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_2)
                        }
                    }
                    if (a[j + 1][i + 1] != 0 && a[j][i] != a[j + 1][i + 1] && a[j + 1][i + 1] == a[j][i + 1] && a[j + 1][i + 1] == a[j + 1][i]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a[j + 1][i + 1] == 1) {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_2)
                        }
                    }

                    if (a[j + 1][i] != 0 && a[j + 1][i] != a[j][i + 1] && a[j][i] == a[j + 1][i] && a[j + 1][i + 1] == a[j + 1][i]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a[j + 1][i] == 1) {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_2)
                        }
                    }
                    if (a[j][i + 1] != 0 && a[j + 1][i] != a[j][i + 1] && a[j][i] == a[j][i + 1] && a[j + 1][i + 1] == a[j][i + 1]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a[j][i + 1] == 1) {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_2)
                        }
                    }
                }
            }
        }
        else
        {
            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            History = prfs?.getString(positionData.toString()+"dot_game_history", "0")?.let { decode(it) }!!
            for(i in 0 until FIELD_CLONE.size)
            {
                for(j in 0 until FIELD_CLONE[0].size)
                {
                    FIELD_CLONE[i][j] = 0;
                }
            }
            a_CLONE.clear()
            for (i in 0 until 16) {
                a_CLONE.add(mutableListOf())
            }
            for (i in a_CLONE.indices) {
                for (j in 0 until 11) {
                    a_CLONE[i].add(0)
                }
            }

            if(CONDITION_DOT>History.size)
            {
                CONDITION_DOT = History.size
            }


            if(isFirstMove)
            {
                red_or_blue_CLONE = 2
            }
            else
            {
                red_or_blue_CLONE = 1
            }
            for(q in 0 until History.size - CONDITION_DOT)
            {
                var i = History[q].first
                var j = History[q].second
                FIELD_CLONE[i][j] = FIELD[i][j]
                if (FIELD[i][j]==1) {
                    FIELD_CLONE[i][j] = 1
                    a_CLONE[j][i] = 1
                    p_CLONE = find(1, a_CLONE, 16, 11)
                }
                else
                {
                    FIELD_CLONE[i][j] = 2
                    a_CLONE[j][i] = 2
                    p_CLONE = find(2, a_CLONE, 16, 11)
                }
                red_or_blue_CLONE = 3 - red_or_blue_CLONE
            }


            radius_of_point = 8f
            size_field_x = 10
            size_field_y = 15


            indent =
                0f//(getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
            width = getWidth().toFloat() - 2 * indent
            height =
                getHeight().toFloat() - 2 * indent //ширина и высота экрана (от ширины в основном все зависит)


            if (height / width < size_field_y.toFloat() / size_field_x.toFloat()) {
                width = height * size_field_x.toFloat() / size_field_y.toFloat()
            }
            indent = (width.toFloat() / (size_field_x.toFloat() + 1f)) / 2f
            width -= 2f * indent
            height -= 2f * indent

            advertising_line =
                (height - width / size_field_x * size_field_y) / 2 //полоска для рекламы

            step = width / size_field_x
            k =
                height - width * (size_field_y.toFloat() / size_field_x.toFloat()) - advertising_line



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

            var x: Float;
            var y: Float
            x = indent
            y =
                height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            for (i in 0..size_field_x)                    //вырисовка точек
            {
                for (j in 0..size_field_y) {
                    if (FIELD_CLONE[i][j] == 1) {
                        canvas?.drawCircle(x, y, radius_of_point * 1.5f, paint_rib_1)
                    } else {
                        if (FIELD_CLONE[i][j] == 2) {
                            canvas?.drawCircle(x, y, radius_of_point * 1.5f, paint_rib_2)
                        } else {
                            canvas?.drawCircle(x, y, radius_of_point, paint_circle)
                        }
                    }

                    y += step
                }
                x += step
                y =
                    height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            }


            p_CLONE = find(1, a_CLONE, 16, 11)
            for (i in 0..size_field_x) {
                for (j in 0..size_field_y) {
                    if (Pair(j, i) in p_CLONE) {
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            if (Pair(j, i - 1) in p_CLONE && Pair(j - 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i - step / 3 * 2
                                var X2: Float = indent + step * i - step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_1)
                                canvas?.drawLine(X2, Y, X, Y2, shading_1)
                            }
                        }
                        if (i + 1 < 11 && j - 1 >= 0) {
                            if (Pair(j, i + 1) in p_CLONE && Pair(j - 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i + step / 6
                                var X2: Float = indent + step * i + step / 3
                                var X3: Float = indent + step * i + step / 2
                                var X4: Float = indent + step * i + step * 2 / 3
                                var X5: Float = indent + step * i + step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_1)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_1)
                                canvas?.drawLine(X, Y, X3, Y3, shading_1)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_1)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_1)

                            }
                        }
                        if (i - 1 >= 0 && j + 1 < 16) {
                            if (Pair(j, i - 1) in p_CLONE && Pair(j + 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i - step / 6
                                var X2: Float = indent + step * i - step / 3
                                var X3: Float = indent + step * i - step / 2
                                var X4: Float = indent + step * i - step * 2 / 3
                                var X5: Float = indent + step * i - step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_1)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_1)
                                canvas?.drawLine(X, Y, X3, Y3, shading_1)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_1)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_1)

                            }
                        }
                        if (i + 1 < 11 && j + 1 < 16) {
                            if (Pair(j, i + 1) in p_CLONE && Pair(j + 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i + step / 3 * 2
                                var X2: Float = indent + step * i + step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_1)
                                canvas?.drawLine(X2, Y, X, Y2, shading_1)

                            }
                        }
                    }
                }
            }

            p_CLONE = find(2, a_CLONE, 16, 11)
            for (i in 0..size_field_x) {
                for (j in 0..size_field_y) {
                    if (Pair(j, i) in p_CLONE) {
                        if (i - 1 >= 0 && j - 1 >= 0) {
                            if (Pair(j, i - 1) in p_CLONE && Pair(j - 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i - step / 3 * 2
                                var X2: Float = indent + step * i - step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_2)
                                canvas?.drawLine(X2, Y, X, Y2, shading_2)
                            }
                        }
                        if (i + 1 < 11 && j - 1 >= 0) {
                            if (Pair(j, i + 1) in p_CLONE && Pair(j - 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i + step / 6
                                var X2: Float = indent + step * i + step / 3
                                var X3: Float = indent + step * i + step / 2
                                var X4: Float = indent + step * i + step * 2 / 3
                                var X5: Float = indent + step * i + step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j - step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j - step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_2)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_2)
                                canvas?.drawLine(X, Y, X3, Y3, shading_2)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_2)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_2)

                            }
                        }
                        if (i - 1 >= 0 && j + 1 < 16) {
                            if (Pair(j, i - 1) in p_CLONE && Pair(j + 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var X1: Float = indent + step * i - step / 6
                                var X2: Float = indent + step * i - step / 3
                                var X3: Float = indent + step * i - step / 2
                                var X4: Float = indent + step * i - step * 2 / 3
                                var X5: Float = indent + step * i - step * 5 / 6

                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 6
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                var Y3: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 2
                                var Y4: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y5: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 5 / 6

                                canvas?.drawLine(X, Y4, X1, Y5, shading_2)
                                canvas?.drawLine(X, Y2, X2, Y4, shading_2)
                                canvas?.drawLine(X, Y, X3, Y3, shading_2)
                                canvas?.drawLine(X2, Y, X4, Y2, shading_2)
                                canvas?.drawLine(X4, Y, X5, Y1, shading_2)

                            }
                        }
                        if (i + 1 < 11 && j + 1 < 16) {
                            if (Pair(j, i + 1) in p_CLONE && Pair(j + 1, i) in p_CLONE) {
                                var X: Float = indent + step * i
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var X1: Float = indent + step * i + step / 3 * 2
                                var X2: Float = indent + step * i + step / 3
                                var Y1: Float =
                                    height - advertising_line - step * size_field_y + step * j + step * 2 / 3
                                var Y2: Float =
                                    height - advertising_line - step * size_field_y + step * j + step / 3
                                canvas?.drawLine(X1, Y, X, Y1, shading_2)
                                canvas?.drawLine(X2, Y, X, Y2, shading_2)

                            }
                        }
                    }
                }
            }





            for (i in 0..9)    //горизонтальные ребра
            {
                for (j in 0..15) {
                    if (j == 0) {
                        if (a_CLONE[j][i] == a_CLONE[j][i + 1] && (a_CLONE[j][i] == a_CLONE[j + 1][i] || a_CLONE[j][i] == a_CLONE[j + 1][i + 1]) && a_CLONE[j][i] != 0) {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a_CLONE[j][i] == 1) {
                                canvas?.drawLine(X, Y, X1, Y, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X1, Y, paint_rib_2)
                            }
                        }
                    } else {
                        if (j == 15) {
                            if (a_CLONE[j][i] == a_CLONE[j][i + 1] && (a_CLONE[j][i] == a_CLONE[j - 1][i] || a_CLONE[j][i] == a_CLONE[j - 1][i + 1]) && a_CLONE[j][i] != 0) {
                                var X: Float = indent + i * step
                                var X1: Float = X + step
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a_CLONE[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_2)
                                }
                            }
                        } else {
                            var k: Int = 0
                            if (a_CLONE[j][i] == a_CLONE[j][i + 1] && (a_CLONE[j][i] == a_CLONE[j + 1][i] || a_CLONE[j][i] == a_CLONE[j + 1][i + 1]) && a_CLONE[j][i] != 0) {
                                k++
                            }
                            if (a_CLONE[j][i] == a_CLONE[j][i + 1] && (a_CLONE[j][i] == a_CLONE[j - 1][i] || a_CLONE[j][i] == a_CLONE[j - 1][i + 1]) && a_CLONE[j][i] != 0) {
                                k++
                            }
                            if (k == 1) {
                                val X: Float = indent + i * step
                                val X1: Float = X + step
                                val Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a_CLONE[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X1, Y, paint_rib_2)
                                }
                            }
                        }
                    }
                }
            }

            for (i in 0..10)     //вертикальные ребра
            {
                for (j in 0..14) {
                    if (i == 0) {
                        if (a_CLONE[j][i] == a_CLONE[j + 1][i] && (a_CLONE[j][i + 1] == a_CLONE[j][i] || a_CLONE[j + 1][i + 1] == a_CLONE[j][i]) && a_CLONE[j][i] != 0) {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a_CLONE[j][i] == 1) {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                            }
                        }
                    } else {
                        if (i == 10) {
                            if (a_CLONE[j][i] == a_CLONE[j + 1][i] && (a_CLONE[j][i - 1] == a_CLONE[j][i] || a_CLONE[j + 1][i - 1] == a_CLONE[j][i]) && a_CLONE[j][i] != 0) {
                                var X: Float = indent + i * step
                                var X1: Float = X + step
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a_CLONE[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                                }
                            }
                        } else {
                            var k: Int = 0
                            if (a_CLONE[j][i] == a_CLONE[j + 1][i] && (a_CLONE[j][i + 1] == a_CLONE[j][i] || a_CLONE[j + 1][i + 1] == a_CLONE[j][i]) && a_CLONE[j][i] != 0) {
                                k++
                            }
                            if (a_CLONE[j][i] == a_CLONE[j + 1][i] && (a_CLONE[j][i - 1] == a_CLONE[j][i] || a_CLONE[j + 1][i - 1] == a_CLONE[j][i]) && a_CLONE[j][i] != 0) {
                                k++
                            }
                            if (k == 1) {
                                var X: Float = indent + i * step
                                var X1: Float = X + step
                                var Y: Float =
                                    height - advertising_line - step * size_field_y + step * j
                                var Y1: Float = Y + step
                                if (a_CLONE[j][i] == 1) {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                                } else {
                                    canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                                }
                            }
                        }
                    }
                }
            }

            for (i in 0..9) {
                for (j in 0..14) {
                    if (a_CLONE[j][i] != 0 && a_CLONE[j][i] != a_CLONE[j + 1][i + 1] && a_CLONE[j][i] == a_CLONE[j][i + 1] && a_CLONE[j][i] == a_CLONE[j + 1][i]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a_CLONE[j][i] == 1) {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_2)
                        }
                    }
                    if (a_CLONE[j + 1][i + 1] != 0 && a_CLONE[j][i] != a_CLONE[j + 1][i + 1] && a_CLONE[j + 1][i + 1] == a_CLONE[j][i + 1] && a_CLONE[j + 1][i + 1] == a_CLONE[j + 1][i]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a_CLONE[j + 1][i + 1] == 1) {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y1, X1, Y, paint_rib_2)
                        }
                    }

                    if (a_CLONE[j + 1][i] != 0 && a_CLONE[j + 1][i] != a_CLONE[j][i + 1] && a_CLONE[j][i] == a_CLONE[j + 1][i] && a_CLONE[j + 1][i + 1] == a_CLONE[j + 1][i]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a_CLONE[j + 1][i] == 1) {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_2)
                        }
                    }
                    if (a_CLONE[j][i + 1] != 0 && a_CLONE[j + 1][i] != a_CLONE[j][i + 1] && a_CLONE[j][i] == a_CLONE[j][i + 1] && a_CLONE[j + 1][i + 1] == a_CLONE[j][i + 1]) {
                        var X: Float = indent + i * step
                        var X1: Float = X + step
                        var Y: Float = height - advertising_line - step * size_field_y + step * j
                        var Y1: Float = Y + step
                        if (a_CLONE[j][i + 1] == 1) {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
                        } else {
                            canvas?.drawLine(X, Y, X1, Y1, paint_rib_2)
                        }
                    }
                }
            }
        }
        Log.d("FIELD_REAL",FIELD.toString())

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {


        if (blocked) {
            return true
        }
        if(CONDITION_DOT!=0)
        {
            return true
        }

        Is_defined_TREE_OF_WAYS = true
        circlex = event!!.x
        circley = event.y

        if(true || event!!.action == MotionEvent.ACTION_UP) {
            Log.d("History",History.toString())
            var fl = false
            var x1: Float = indent
            var y1: Float =
                height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            val upd: MutableMap<String, Any> = mutableMapOf()
            for (i in 0..size_field_x)                    //вырисовка точек
            {
                for (j in 0..size_field_y) {
                    if (correction_touch(x1, y1)) {
                        if (FIELD[i][j] == 0 && a[j][i] == 0) {
                            red_or_blue = 3 - red_or_blue
                            blocked = true
                            if (red_or_blue == 1) {
                                FIELD[i][j] = 1
                                a[j][i] = 1
                                upd["a/$j/$i"] = a[j][i]
                                upd["FIELD/$i/$j"] = FIELD[i][j]
                                p = find(1, a, 16, 11)
                            //    History.add()
                            } else {
                                FIELD[i][j] = 2
                                a[j][i] = 2
                                upd["a/$j/$i"] = a[j][i]
                                upd["FIELD/$i/$j"] = FIELD[i][j]
                                p = find(2, a, 16, 11)
                            }
                            fl = true
                            upd["time/$username/"] = engine?.cntUser.toString()
                            var flag :Boolean = true
                            val prfs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            if(prfs?.getString(positionData.toString()+"dot_game_history", "0")!="0")
                            {
                                History = prfs?.getString(positionData.toString()+"dot_game_history", "a")?.let { decode(it) }!!
                            }
                            for(kol in 0 until History.size)
                            {
                                if(i==History[kol].first && j == History[kol].second)
                                {
                                    flag = false
                                }
                            }
                            if(flag)
                            {
                                History.add(Triple(i,j, FIELD[i][j]))
                                var data_from_memory = encode(History)
                                val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                editor.putString(positionData.toString()+"dot_game_history", data_from_memory)
                                editor.apply()
                            }
                            invalidate()
                            break
                        }
                    }
                    y1 += step
                }

                x1 += step
                y1 =
                    height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())

            }
            upd["red_or_blue"] = red_or_blue
            if (fl) {
                positionData.updateChildren(upd)
            }
        }
        return true
    }

}








