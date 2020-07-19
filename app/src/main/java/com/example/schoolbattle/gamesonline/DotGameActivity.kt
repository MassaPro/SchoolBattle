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
import com.example.schoolbattle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.instacart.library.truetime.TrueTime
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import java.util.*


//TODO , рисовать ребра только один раз до этого узнав, также можно не включать в цепочки вершины которые окружены 6 такими же вершинами

class DotGameActivity: AppCompatActivity() {
    private var isRun = false
    private var dialog: ShowResult? = null

    var Finish_time: Long  = 0
    var timeBegin: Long = 0

    var Finish_time_1: Long  = 0
    var timeBegin_1: Long =0

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
        val yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()
        var opponentsName_: String = intent?.getStringExtra("opponentName").toString()
        var opponentsName = ""
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
        val gameData = myRef.child(type + "DotGames").child(
            if (opponentsName < yourName)
                opponentsName + '_' + yourName else yourName + '_' + opponentsName
        )

        if (type != "") {
            initTrueTime(this)
            Finish_time = trueTime.time + 1000*60*10
            timeBegin = trueTime.time

            Finish_time_1 = trueTime.time + 1000*60*10
            timeBegin_1 = trueTime.time
            runTimer(gameData)
        }
        //signature_canvas3.blocked = true
        signature_canvas3.positionData = gameData
        signature_canvas3.blocked = true

        //button_player_1_online_dot.text = yourName
        //button_player_2_online_dot.text = opponentsName

        if(Design == "Egypt" ) {
            /*
            button_player_1_online_dot.setTextColor(Color.BLACK)
            button_player_2_online_dot.setTextColor(Color.BLACK)
            button_player_1_online_dot.setTextSize(20f)
            button_player_2_online_dot.setTextSize(20f)
            timer_dot_online.setTextSize(15f)
            timer_dot_online.setTextColor(Color.GREEN)
            timer2_dot_online.setTextSize(15f)
            timer2_dot_online.setTextColor(Color.GREEN)

            icon_player_1_dot_online.setBackgroundResource(R.drawable.player1_egypt);
            icon_player_2_dot_online.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_dot_online.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_dot_online.setBackgroundResource(R.drawable.circle_egypt);
            label_online_dot.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_dot_online.setBackgroundColor(rgb(224,164,103))
            to_back_dot_online.setBackgroundResource(R.drawable.arrow_back)
            toolbar_dot_online.setBackgroundColor(argb(0,0,0,0))
            toolbar2_dot_online.setBackgroundColor(argb(0,0,0,0))

             */
        }

        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                var cnt = 0
                signature_canvas3.blocked = true

                //signature_canvas3.isFirstMove = (p0.child("Move").value.toString() == yu.toString())
                //if (signature_canvas3.isFirstMove == (cnt % 2 == 0)) signature_canvas3.blocked = false
                //signature_canvas3.invalidate()
                signature_canvas3.isFirstMove = (p0.child("Move").value.toString() == yu.toString())
                for (i in 0..signature_canvas3.FIELD.size - 1) {
                    for (j in 0..signature_canvas3.FIELD[i].size - 1) {
                        if (p0.child("FIELD").child("$i").hasChild("$j")) {
                            cnt++
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
                signature_canvas3.red_or_blue = if (p0.hasChild("red_or_blue")) p0.child("red_or_blue").value.toString().toInt() else 1
                if ((signature_canvas3.red_or_blue == 1) == (p0.child("Move").toString().toBoolean() == (yu == '0'))) signature_canvas3.blocked = false
                signature_canvas3.invalidate()
                fun check_win() : Int
                {
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
                if (ch != 0) {
                    signature_canvas3.blocked = true
                    val res = if (ch == 2 && yu == '0' || ch == 1 && yu == '1') {
                        "Победа"
                    } else if (ch == 2 && yu == '1' || ch == 1 && yu == '0') {
                        "Поражение"
                    } else {
                        "Ничья"
                    }
                    myRef.child(type + "DotGames").child(if (opponentsName < yourName)
                        opponentsName + '_' + yourName else yourName + '_' + opponentsName
                    ).removeValue()

                    myRef.child("Users").child(yourName).child(type + "Games").child("$opponentsName DotGame").removeValue()
                    myRef.child("Users").child(opponentsName).child(type + "Games").child("$yourName DotGame").removeValue()
                    dialog =
                        ShowResult(this@DotGameActivity)
                    if (isRun) {
                        dialog?.showResult(res, "DotGame", yourName, opponentsName)
                    }
                    gameData.removeEventListener(this)
                }
            }
        })


        var timeListener: ValueEventListener? = null
        if (type == "Blitz") {
            timeListener = gameData.child("FIELD").addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    var cnt = 0
                    for (i in signature_canvas3.FIELD.indices) {
                        for (j in signature_canvas3.FIELD[i].indices) {
                            if (p0.child("$i").hasChild("$j")) {
                                cnt++
                                //signature_canvas3.FIELD[i][j] = p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
                            }
                        }
                    }
                    Toast.makeText(this@DotGameActivity, cnt.toString(), Toast.LENGTH_LONG);
                    initTrueTime(applicationContext)
                    if ((cnt%2 != 1) != signature_canvas3.isFirstMove) {//TODO predpolagaem xod protivnika tok chto nachilsa
                        //your movwe
                        Finish_time_1 += trueTime.time - timeBegin
                        timeBegin_1 = trueTime.time
                        val timeList = listOf(
                            Finish_time.toString(),
                            Finish_time_1.toString(),
                            timeBegin.toString(),
                            timeBegin_1.toString(),
                            cnt.toString()
                        )
                        gameData.child("Time").setValue(timeList)
                    } else {
                        Finish_time += trueTime.time - timeBegin_1
                        gameData.child("Time").addValueEventListener(object: ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {}
                            override fun onDataChange(p0: DataSnapshot) {
                                if (p0.exists()) {
                                    val lst = p0.value as List<String>
                                    initTrueTime(applicationContext)
                                    if (cnt == lst[4].toInt()) {
                                        var Finish_time_sync = lst[0].toLong()
                                        var Finish_time_1_sync = lst[1].toLong()
                                        var timeBegin_1_sync = lst[2].toLong()
                                        var timeBegin_sync =  lst[3].toLong()

                                        Finish_time = Finish_time_1_sync
                                        timeBegin = trueTime.time
                                    }

                                }
                            }
                        })
                    }
                    //if (cnt % 2 == )
                }
            })
        }
    }

    private fun runTimer(positionData: DatabaseReference) {
        val handler: Handler = Handler()
        handler.post(
            object: Runnable {
                override fun run() {
                    //если будут проблемы с скоротью перенести в onCreate

                    var cnt: Int = 0
                    for(i in 0 until signature_canvas3.FIELD.size)
                    {
                        for(j in 0 until  signature_canvas3.FIELD[0].size)
                        {
                            if(signature_canvas3.FIELD[i][j] != 0)
                            {
                                cnt++
                            }
                        }
                    }
                    initTrueTime(applicationContext)

                    if((cnt%2 != 1) == signature_canvas3.isFirstMove) {
                        if(trueTime.time<Finish_time)
                        {
                            var min_finish : Long = Finish_time/1000/60
                            var second_finish: Long = (Finish_time - min_finish*60*1000)/1000

                            var min_now: Long  = trueTime.time/1000/60
                            var second_now : Long  = (trueTime.time - min_now*60*1000)/1000

                            if(second_now>second_finish)
                            {
                                if (min_finish - min_now - 1 == 10.toLong() && second_finish - second_now + 60 > 0) {
                                    second_finish = second_now
                                }
                                if( (timer2_xog_online.text[0].toInt() - '0'.toInt())*60*10+(timer2_xog_online.text[1].toInt() - '0'.toInt())*60 +(timer2_xog_online.text[3].toInt() - '0'.toInt())*10 + (timer2_xog_online.text[4].toInt() - '0'.toInt()) >(min_finish - min_now - 1) *60 + second_finish - second_now + 60)
                                {
                                    timer2_xog_online.text = add_null( (min_finish - min_now - 1).toString()) + ":" + add_null( (second_finish - second_now + 60).toString())
                                }
                            }
                            else
                            {
                                if (min_finish - min_now == 10.toLong() && second_finish - second_now > 0) {
                                    second_finish = second_now
                                }
                                if( (timer2_xog_online.text[0].toInt() - '0'.toInt())*60*10+(timer2_xog_online.text[1].toInt() - '0'.toInt())*60 +(timer2_xog_online.text[3].toInt() - '0'.toInt())*10 + (timer2_xog_online.text[4].toInt() - '0'.toInt()) >(min_finish - min_now ) *60 + second_finish - second_now )
                                {
                                    timer2_xog_online.text = add_null( (min_finish - min_now).toString()) + ":" + add_null( (second_finish - second_now).toString())
                                }
                                if((timer2_xog_online.text[0].toInt() - '0'.toInt())*60*10+(timer2_xog_online.text[1].toInt() - '0'.toInt())*60 +(timer2_xog_online.text[3].toInt() - '0'.toInt())*10 + (timer2_xog_online.text[4].toInt() - '0'.toInt())<=5)
                                {
                                    timer2_xog_online.setTextColor(Color.RED)
                                }
                            }

                        }
                        else
                        {
                            timer2_xog_online.setTextColor(Color.RED)
                            timer2_xog_online.text = "time's up"
                        }


                    }
                    else
                    {

                        if(trueTime.time<Finish_time_1)
                        {
                            var min_finish : Long = Finish_time_1/1000/60
                            var second_finish: Long = (Finish_time_1 - min_finish*60*1000)/1000

                            var min_now: Long  = trueTime.time/1000/60
                            var second_now : Long  = (trueTime.time - min_now*60*1000)/1000

                            if(second_now>second_finish)
                            {
                                if( (timer_xog_online.text[0].toInt() - '0'.toInt())*60*10+(timer_xog_online.text[1].toInt() - '0'.toInt())*60 +(timer_xog_online.text[3].toInt() - '0'.toInt())*10 + (timer_xog_online.text[4].toInt() - '0'.toInt()) >(min_finish - min_now - 1) *60 + second_finish - second_now + 60)
                                {
                                    timer_xog_online.text = add_null( (min_finish - min_now - 1).toString()) + ":" + add_null( (second_finish - second_now + 60).toString())
                                }
                            }
                            else
                            {
                                if( (timer_xog_online.text[0].toInt() - '0'.toInt())*60*10+(timer_xog_online.text[1].toInt() - '0'.toInt())*60 +(timer_xog_online.text[3].toInt() - '0'.toInt())*10 + (timer_xog_online.text[4].toInt() - '0'.toInt()) >(min_finish - min_now ) *60 + second_finish - second_now )
                                {
                                    timer_xog_online.text = add_null( (min_finish - min_now).toString()) + ":" + add_null( (second_finish - second_now).toString())
                                }
                            }
                            if((timer_xog_online.text[0].toInt() - '0'.toInt())*60*10+(timer_xog_online.text[1].toInt() - '0'.toInt())*60 +(timer_xog_online.text[3].toInt() - '0'.toInt())*10 + (timer_xog_online.text[4].toInt() - '0'.toInt())<=5)
                            {
                                timer_xog_online.setTextColor(Color.RED)
                            }
                        }
                        else
                        {
                            timer_xog_online.setTextColor(Color.RED)
                            timer_xog_online.text = "time's up"
                        }

                    }
                    handler.postDelayed(this, 100)
                }
            }
        )

    }
    companion object {
        val trueTime: Date
            get() = if (TrueTime.isInitialized()) TrueTime.now() else Date()

        fun initTrueTime(ctx: Context) {
            if (isNetworkConnected(ctx)) {
                if (!TrueTime.isInitialized()) {
                    val trueTime = InitTrueTimeAsyncTask(ctx)
                    trueTime.execute()
                }
            }
        }

        @SuppressLint("ServiceCast")
        fun isNetworkConnected(ctx: Context): Boolean {
            val cm = ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            return ni != null && ni.isConnectedOrConnecting
        }

        fun add_null(s: String):String
        {
            if(s.length == 1)
            {
                return "0" + s
            }
            else
            {
                return s
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isRun = false
        currentContext = null
        if (dialog != null) {
            dialog?.delete()
            finish()
        } else {
            Log.w("HHH", "NONULL")
        }
    }
}

class CanvasViewDot(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        fun dpToPx(dp: Int): Float {
            return (dp * Resources.getSystem().displayMetrics.density)
        }

        radius_of_point = 8f
        size_field_x  = 10
        size_field_y  = 15


        indent = 0f//(getWidth().toFloat()/(size_field_x.toFloat()+1f))/2f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat() - 2 * indent //ширина и высота экрана (от ширины в основном все зависит)

        Log.w("WH", "$width  $height")
        Log.w("WH2", "${getWidth()}  ${getHeight()}")
        if (height / width < size_field_y.toFloat() / size_field_x.toFloat()) {
            width = height * size_field_x.toFloat() / size_field_y.toFloat()
        }
        indent = (width.toFloat()/(size_field_x.toFloat()+1f))/2f
        width -= 2f * indent
        height -= 2f * indent

        advertising_line = (height - width/size_field_x*size_field_y)/2 //полоска для рекламы

        step = width/size_field_x
        k = height-width*(size_field_y.toFloat()/size_field_x.toFloat())-advertising_line


        Log.d("Para",p.toString())

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

        Log.w("YYA", a.toString())
        p = find(1,a,16,11)
        for(i in 0..size_field_x)
        {
            for(j in 0..size_field_y)
            {
                if(Pair(j,i) in p)
                {
                    if(i-1>=0 && j-1>=0)
                    {
                        if(Pair(j,i-1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i - step/3*2
                            var X2: Float = indent + step*i - step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_1)
                            canvas?.drawLine(X2,Y,X,Y2,shading_1)
                        }
                    }
                    if(i+1<11 && j-1>=0)
                    {
                        if(Pair(j,i+1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i + step/6
                            var X2: Float = indent + step*i + step/3
                            var X3: Float = indent + step*i + step/2
                            var X4: Float = indent + step*i + step*2/3
                            var X5: Float = indent + step*i + step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j - step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j - step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_1)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_1)
                            canvas?.drawLine(X,Y,X3,Y3,shading_1)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_1)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_1)

                        }
                    }
                    if(i-1>=0 && j+1<16)
                    {
                        if(Pair(j,i-1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i - step/6
                            var X2: Float = indent + step*i - step/3
                            var X3: Float = indent + step*i - step/2
                            var X4: Float = indent + step*i - step*2/3
                            var X5: Float = indent + step*i - step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j + step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j + step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_1)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_1)
                            canvas?.drawLine(X,Y,X3,Y3,shading_1)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_1)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_1)

                        }
                    }
                    if(i+1<11 && j+1<16)
                    {
                        if(Pair(j,i+1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i + step/3*2
                            var X2: Float = indent + step*i + step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_1)
                            canvas?.drawLine(X2,Y,X,Y2,shading_1)

                        }
                    }
                }
            }
        }
        Log.w("YYB", a.toString())
        p = find(2,a,16,11)
        for(i in 0..size_field_x)
        {
            for(j in 0..size_field_y)
            {
                if(Pair(j,i) in p)
                {
                    if(i-1>=0 && j-1>=0)
                    {
                        if(Pair(j,i-1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i - step/3*2
                            var X2: Float = indent + step*i - step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_2)
                            canvas?.drawLine(X2,Y,X,Y2,shading_2)
                        }
                    }
                    if(i+1<11 && j-1>=0)
                    {
                        if(Pair(j,i+1) in p && Pair(j-1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i + step/6
                            var X2: Float = indent + step*i + step/3
                            var X3: Float = indent + step*i + step/2
                            var X4: Float = indent + step*i + step*2/3
                            var X5: Float = indent + step*i + step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j - step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j - step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j - step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j - step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j - step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_2)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_2)
                            canvas?.drawLine(X,Y,X3,Y3,shading_2)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_2)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_2)

                        }
                    }
                    if(i-1>=0 && j+1<16)
                    {
                        if(Pair(j,i-1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var X1: Float = indent + step*i - step/6
                            var X2: Float = indent + step*i - step/3
                            var X3: Float = indent + step*i - step/2
                            var X4: Float = indent + step*i - step*2/3
                            var X5: Float = indent + step*i - step*5/6

                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step/6
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            var Y3: Float = height - advertising_line - step*size_field_y + step*j + step/2
                            var Y4: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y5: Float = height - advertising_line - step*size_field_y + step*j + step*5/6

                            canvas?.drawLine(X,Y4,X1,Y5,shading_2)
                            canvas?.drawLine(X,Y2,X2,Y4,shading_2)
                            canvas?.drawLine(X,Y,X3,Y3,shading_2)
                            canvas?.drawLine(X2,Y,X4,Y2,shading_2)
                            canvas?.drawLine(X4,Y,X5,Y1,shading_2)

                        }
                    }
                    if(i+1<11 && j+1<16)
                    {
                        if(Pair(j,i+1) in p && Pair(j+1,i) in p)
                        {
                            var X: Float =  indent + step*i
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var X1: Float = indent + step*i + step/3*2
                            var X2: Float = indent + step*i + step/3
                            var Y1: Float = height - advertising_line - step*size_field_y + step*j + step*2/3
                            var Y2: Float = height - advertising_line - step*size_field_y + step*j + step/3
                            canvas?.drawLine(X1,Y,X,Y1,shading_2)
                            canvas?.drawLine(X2,Y,X,Y2,shading_2)

                        }
                    }
                }
            }
        }


        Log.w("YYC", a.toString())


        for(i in 0..9)    //горизонтальные ребра
        {
            for(j in 0..15)
            {
                if(j==0)
                {
                    if(a[j][i]==a[j][i+1] && (a[j][i] == a[j+1][i] || a[j][i] == a[j+1][i+1]) && a[j][i] !=0 )
                    {
                        var X: Float = indent + i*step
                        var X1: Float = X+step
                        var Y: Float = height - advertising_line - step*size_field_y + step*j
                        var Y1 :Float =  Y + step
                        if(a[j][i]  == 1)
                        {
                            canvas?.drawLine(X,Y,X1,Y,paint_rib_1)
                        }
                        else
                        {
                            canvas?.drawLine(X,Y,X1,Y,paint_rib_2)
                        }
                    }
                }
                else
                {
                    if(j==15)
                    {
                        if(a[j][i]==a[j][i+1] && (a[j][i] == a[j-1][i] || a[j][i] == a[j-1][i+1]) && a[j][i] !=0)
                        {
                            var X: Float = indent + i*step
                            var X1: Float = X+step
                            var Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1 :Float =  Y + step
                            if(a[j][i]  == 1)
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_1)
                            }
                            else
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_2)
                            }
                        }
                    }
                    else
                    {
                        var k: Int = 0
                        if(a[j][i]==a[j][i+1] && (a[j][i] == a[j+1][i] || a[j][i] == a[j+1][i+1]) && a[j][i] !=0 )
                        {
                            k++
                        }
                        if(a[j][i]==a[j][i+1] && (a[j][i] == a[j-1][i] || a[j][i] == a[j-1][i+1])  && a[j][i] !=0)
                        {
                            k++
                        }
                        if(k==1)
                        {
                            val X: Float = indent + i*step
                            val X1: Float = X+step
                            val Y: Float = height - advertising_line - step*size_field_y + step*j
                            var Y1 :Float =  Y + step
                            if(a[j][i]  == 1)
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_1)
                            }
                            else
                            {
                                canvas?.drawLine(X,Y,X1,Y,paint_rib_2)
                            }
                        }
                    }
                }
            }
        }

        for(i in 0..10)     //вертикальные ребра
        {
            for(j in 0..14)
            {
                if(i == 0)
                {
                    if(a[j][i]==a[j+1][i] && (a[j][i+1]==a[j][i] || a[j+1][i+1]==a[j][i]) && a[j][i]!= 0 )
                    {
                        var X: Float = indent + i*step
                        var X1: Float = X+step
                        var Y: Float = height - advertising_line - step*size_field_y + step*j
                        var Y1 :Float =  Y + step
                        if(a[j][i]  == 1)
                        {
                            canvas?.drawLine(X,Y,X,Y1,paint_rib_1)
                        }
                        else
                        {
                            canvas?.drawLine(X,Y,X,Y1,paint_rib_2)
                        }
                    }
                }
                else
                {
                    if(i == 10)
                    {
                        if (a[j][i] == a[j + 1][i] && (a[j][i - 1] == a[j][i] || a[j + 1][i - 1] == a[j][i]) && a[j][i] != 0) {
                            var X: Float = indent + i * step
                            var X1: Float = X + step
                            var Y: Float =
                                height - advertising_line - step * size_field_y + step * j
                            var Y1: Float = Y + step
                            if (a[j][i]  == 1) {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_1)
                            } else {
                                canvas?.drawLine(X, Y, X, Y1, paint_rib_2)
                            }
                        }
                    }
                    else
                    {
                        var k : Int = 0
                        if(a[j][i]==a[j+1][i] && (a[j][i+1]==a[j][i] || a[j+1][i+1]==a[j][i]) && a[j][i]!= 0 )
                        {
                            k++
                        }
                        if (a[j][i] == a[j + 1][i] && (a[j][i - 1] == a[j][i] || a[j + 1][i - 1] == a[j][i]) && a[j][i] != 0)
                        {
                            k++
                        }
                        if(k == 1)
                        {
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

        for(i in 0..9)
        {
            for(j in 0..14)
            {
                if(a[j][i]!=0 && a[j][i]!=a[j+1][i+1] && a[j][i] == a[j][i+1] && a[j][i]==a[j+1][i])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j][i] == 1)
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_2)
                    }
                }
                if(a[j+1][i+1]!=0 && a[j][i]!=a[j+1][i+1] && a[j+1][i+1] == a[j][i+1] && a[j+1][i+1]==a[j+1][i])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j+1][i+1] == 1)
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y1,X1,Y,paint_rib_2)
                    }
                }

                if(a[j+1][i]!=0 && a[j+1][i]!=a[j][i+1] && a[j][i] == a[j+1][i] && a[j+1][i+1] == a[j+1][i])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j+1][i] == 1)
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
                    }
                }
                if(a[j][i+1]!=0 && a[j+1][i]!=a[j][i+1] && a[j][i] == a[j][i+1] && a[j+1][i+1] == a[j][i+1])
                {
                    var X: Float = indent + i*step
                    var X1: Float = X+step
                    var Y: Float = height - advertising_line - step*size_field_y + step*j
                    var Y1 :Float =  Y + step
                    if(a[j][i+1] == 1)
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
                    }
                    else
                    {
                        canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
                    }
                }
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (blocked) {
            return true
        }

        Is_defined_TREE_OF_WAYS = true
        circlex = event!!.x
        circley = event!!.y

        if(true || event!!.action == MotionEvent.ACTION_UP) {
            var x1: Float = indent
            var y1: Float =
                height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            for (i in 0..size_field_x)                    //вырисовка точек
            {
                for (j in 0..size_field_y) {
                    if (correction_touch(x1, y1)) {
                        if (FIELD[i][j] == 0 && a[j][i] == 0) {
                            red_or_blue = 3 - red_or_blue
                            positionData.child("red_or_blue").setValue(red_or_blue)
                            blocked = true
                            if (red_or_blue == 1) {
                                FIELD[i][j] = 1
                                a[j][i] = 1
                                positionData.child("a").child("$j").child("$i").setValue(a[j][i].toString())
                                positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j].toString())
                                p = find(1, a, 16, 11)
                            } else {
                                FIELD[i][j] = 2
                                a[j][i] = 2
                                positionData.child("a").child("$j").child("$i").setValue(a[j][i].toString())
                                positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j].toString())
                                p = find(2, a, 16, 11)
                            }

                            positionData.child("red_or_blue").setValue(red_or_blue)
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
        }
        return true
    }

}








