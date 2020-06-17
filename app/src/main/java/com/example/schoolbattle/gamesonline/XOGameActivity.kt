package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.schoolbattle.*
import com.example.schoolbattle.R
import com.google.firebase.database.*
import com.instacart.library.truetime.TrueTime
import kotlinx.android.synthetic.main.activity_x_o_game.*
import kotlinx.android.synthetic.main.activity_x_o_game_one_divice.*
import java.util.*
import java.text.DateFormat
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min


class XOGameActivity : AppCompatActivity() {
    private var isRun = false
    private var dialog: ShowResult? = null

    var Finish_time: Long  = 0
    var timeBegin: Long = 0

    var Finish_time_1: Long  = 0
    var timeBegin_1: Long =0
    var fl = false


    override fun onResume() {
        super.onResume()
        currentContext = this
        isRun = true
        CONTEXT = this
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_x_o_game)




        if(Design == "Egypt" ) {
            button_player_1_online_xog.setTextColor(Color.BLACK)
            button_player_2_online_xog.setTextColor(Color.BLACK)
            button_player_1_online_xog.setTextSize(20f)
            button_player_2_online_xog.setTextSize(20f)
            timer_xog_online.setTextSize(15f)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextSize(15f)
            timer2_xog_online.setTextColor(Color.GREEN)

            icon_player_1_xog_online.setBackgroundResource(R.drawable.player1_egypt);
            icon_player_2_xog_online.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_xog_online.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_xog_online.setBackgroundResource(R.drawable.circle_egypt);
            label_online_xog.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_xog_online.setBackgroundColor(rgb(224,164,103))
            to_back_xog_online.setBackgroundResource(R.drawable.arrow_back)
            toolbar_xog_online.setBackgroundColor(argb(0,0,0,0))
            toolbar2_xog_online.setBackgroundColor(argb(0,0,0,0))
        }

        currentContext = this
        CONTEXT = this
        isRun = true

        if (StupidGame != Activity()) StupidGame.finish()
        if (NewGame != Activity()) NewGame.finish()
        val yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()

        val type = intent.getStringExtra("type")

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

        val gameData = myRef.child(type + "XOGames").child(
            if (opponentsName < yourName)
                opponentsName + '_' + yourName else yourName + '_' + opponentsName
        )

        if (type != "") {
            initTrueTime(this)
            Toast.makeText(this, trueTime.toString(), Toast.LENGTH_LONG).show()
            Finish_time = trueTime.time + 1000*60*10
            timeBegin = trueTime.time

            Finish_time_1 = trueTime.time + 1000*60*10
            timeBegin_1 = trueTime.time


            runTimer(gameData)
        }

        signature_canvas.blocked = true
        signature_canvas.positionData = gameData

        button_player_1_online_xog.text = yourName
        button_player_2_online_xog.text = opponentsName
        if(Design == "Egypt" ) {
            button_player_1_online_xog.setTextColor(Color.BLACK)
            button_player_2_online_xog.setTextColor(Color.BLACK)
            button_player_1_online_xog.setTextSize(20f)
            button_player_2_online_xog.setTextSize(20f)
            timer_xog_online.setTextSize(15f)
            timer_xog_online.setTextColor(Color.GREEN)
            timer2_xog_online.setTextSize(15f)
            timer2_xog_online.setTextColor(Color.GREEN)

            icon_player_1_xog_online.setBackgroundResource(R.drawable.player1_egypt);
            icon_player_2_xog_online.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_xog_online.setBackgroundResource(R.drawable.cross_egypt);
            player_2_icon_xog_online.setBackgroundResource(R.drawable.circle_egypt);
            label_online_xog.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_xog_online.setBackgroundColor(rgb(224, 164, 103))
            to_back_xog_online.setBackgroundResource(R.drawable.arrow_back)
            toolbar_xog_online.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_xog_online.setBackgroundColor(argb(0, 0, 0, 0))
        }


        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                var cnt = 0
                signature_canvas.isFirstMove = (p0.child("Move").value.toString() == yu.toString())

                //if (!signature_canvas.isFirstMove) " X" else " O"
                for (i in 0..6) {
                    for (j in 0..5) {
                        val p = p0.child("FIELD").child("$i").child("$j")
                        if (p.exists()) {
                            cnt++
                            signature_canvas.FIELD[i][j] = p.value.toString().toInt()
                        }
                    }
                }
                fun checkForWin(): MutableList<Int> {
                    var field = signature_canvas.FIELD
                    val list_x = mutableListOf(1, 1, 0, -1)
                    val list_y = mutableListOf(0, 1, 1, 1)

                    var ans = mutableListOf(0)
                    for (i in 0..6) {
                        for (j in 0..5) {
                            if (field[i][j] != 0) {
                                for (k in 0..3) {
                                    var fl = 0
                                    for (pos in 0..2) {
                                        Log.w("TAG", "$i ${list_x[k]} $pos")
                                        if (field[(i + list_x[k] * pos + 7) % 7][(j + list_y[k] * pos + 6) % 6] != field[(i + list_x[k] * (pos + 1) + 7) % 7][(j + list_y[k] * (pos + 1) + 6) % 6]) {
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
                if (signature_canvas.isFirstMove == (cnt % 2 == 0)) signature_canvas.blocked = false
                signature_canvas.invalidate()
                var checkList = checkForWin()
                if (checkList.size > 1 || (checkList.size == 1 && cnt == 42)) {
                    signature_canvas.blocked = true
                    Toast.makeText(applicationContext,"${signature_canvas.FIELD[checkList.get(1)][checkList.get(2)]}", Toast.LENGTH_LONG).show()
                    var whoWins = 0
                    if (checkList.size > 1) {
                        for (i2 in 0..8) {
                            if (i2 % 2 == 1) {
                                whoWins = signature_canvas.FIELD[checkList.get(i2)][checkList.get(i2 + 1)]
                                //   signature_canvas.FIELD[checkList.get(i2)][checkList.get(i2 + 1)] = 0 // add color change
                                signature_canvas.invalidate()
                            }
                            //Log.w("TAG", "${checkList.get(i2)}")
                        }
                    }
                    var res = "Тестовое состояние"
                    if (checkList.size == 1) {
                        res = "Ничья"
                    } else {
                        if (p0.child("Move").value.toString() == "0") {
                            if (yu == '0') {
                                if (whoWins == 1) {
                                    res = "Победа"
                                } else {
                                    res = "Поражение"
                                }
                            } else {
                                if (whoWins == 2) {
                                    res = "Победа"
                                } else {
                                    res = "Поражение"
                                }
                            }
                        } else {
                            if (yu == '1') {
                                if (whoWins == 1) {
                                    res = "Победа"
                                } else {
                                    res = "Поражение"
                                }
                            } else {
                                if (whoWins == 2) {
                                    res = "Победа"
                                } else {
                                    res = "Поражение"
                                }
                            }
                        }
                    }



                    myRef.child(type + "XOGames").child(if (opponentsName < yourName)
                        opponentsName + '_' + yourName else yourName + '_' + opponentsName
                    ).removeValue()

                    myRef.child("Users").child(yourName).child(type + "Games").child("$opponentsName XOGame").removeValue()
                    myRef.child("Users").child(opponentsName).child(type + "Games").child("$yourName XOGame").removeValue()
                    dialog =
                        ShowResult(this@XOGameActivity)
                    if (isRun) {
                        dialog?.showResult(res, "XOGame", yourName, opponentsName)
                    }
                    cnt = 0
                    gameData.removeEventListener(this)
                }
            }
        })

        gameData.child("FIELD").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                var cnt = 0
                for (i in 0..6) {
                    for (j in 0..5) {
                        if (p0.child("$i").hasChild("$j")) {
                            cnt++
                            //signature_canvas.FIELD[i][j] = p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
                        }
                    }
                }
                initTrueTime(applicationContext)
                if ((cnt%2 != 1) != signature_canvas.isFirstMove) {//TODO predpolagaem xod protivnika tok chto nachilsa
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
                                    Toast.makeText(this@XOGameActivity, "dlkl" + cnt.toString(), Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                    })
                }
                //if (cnt % 2 == )
            }
        })
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

    private fun runTimer(positionData: DatabaseReference) {
        val handler: Handler = Handler()
        handler.post(
            object: Runnable {
                override fun run() {
                    //если будут проблемы с скоротью перенести в onCreate

                    var cnt: Int = 0
                    for(i in 0 until signature_canvas.FIELD.size)
                    {
                        for(j in 0 until  signature_canvas.FIELD[0].size)
                        {
                            if(signature_canvas.FIELD[i][j] != 0)
                            {
                                cnt++
                            }
                        }
                    }
                    initTrueTime(applicationContext)

                    if((cnt%2 != 1) == signature_canvas.isFirstMove) {
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
                                timer2_xog_online.text = add_null( (min_finish - min_now - 1).toString()) + ":" + add_null( (second_finish - second_now + 60).toString())
                            }
                            else
                            {
                                if (min_finish - min_now == 10.toLong() && second_finish - second_now > 0) {
                                    second_finish = second_now
                                }
                                timer2_xog_online.text = add_null( (min_finish - min_now).toString()) + ":" + add_null( (second_finish - second_now).toString())
                            }
                        }
                        else
                        {
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
                                timer_xog_online.text = add_null( (min_finish - min_now - 1).toString()) + ":" + add_null( (second_finish - second_now + 60).toString())
                            }
                            else
                            {
                                timer_xog_online.text = add_null( (min_finish - min_now).toString()) + ":" + add_null( (second_finish - second_now).toString())
                            }
                        }
                        else
                        {
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
}





class CanvasView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

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

    lateinit var positionData: DatabaseReference
    var isFirstMove = false
    var blocked = true
    var Exit : Int
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f

    var indent : Float = 20f

    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var Line_paint_1: Paint = Paint()
    var FIELD = Array(7){IntArray(6)}
    var cross_or_nul: String
    var step: Float = 0f

    init {
        Exit = 0
        Line_paint.color = Color.RED          //ресур для линий (ширина и цвет)
        Line_paint.strokeWidth = 10f

        Line_paint_1.color = Color.RED          //ресур для линий (ширина и цвет)
        Line_paint_1.strokeWidth = 20f

        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..6) {
            for(j in 0 ..5) {
                FIELD[i][j] = 0 //не заполненный
            }
        }
        cross_or_nul  = "cross"
    }

    private fun moveChecker(x: Int, y: Int, cnt: Int): Boolean {
        if (cnt % 2 == 0 != isFirstMove) {
            return false
        }
        if (x > 6 || x < 0 || y > 5 || y < 0 || (y + 1 <= 5 && FIELD[x][y + 1] == 0)) {
            return false
        }
        return true
    }




    var icon_cross_egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.cross_egypt
    )       //картинки крестиков и нулей
    var icon_null_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.circle_egypt
    )

    // var BackgroundColor_Egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_egypt)
    var icon_green : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.illumination
    )
    var icon_grenn_Egypt : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.ram_egypt_xog
    )


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        //TODO() take field from database

        indent = 20f
        val width = getWidth().toFloat()
        val height = getHeight().toFloat()
        //ширина и высота экрана (от ширины в основном все зависит)

        val size_field_x: Int = 7
        val size_field_y: Int = 6
        step = (width-2*indent)/size_field_x
        val advertising_line: Float = (height - step * 6) / 2

        var k = height - (width-2*indent)*size_field_y/size_field_x - advertising_line
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


        val right_icon_cross: Bitmap  //подгоняем картинку под размеры экрана телефона
        val right_icon_null: Bitmap
        val right_icon_green: Bitmap
        if(Design == "Egypt")
        {
            right_icon_cross = Bitmap.createScaledBitmap(icon_cross_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_null = Bitmap.createScaledBitmap(icon_null_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_green = Bitmap.createScaledBitmap(
                icon_grenn_Egypt,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                true
            )
        }
        else {
            right_icon_cross = Bitmap.createScaledBitmap(icon_cross_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_null = Bitmap.createScaledBitmap(icon_null_egypt,(width.toInt()-2*indent.toInt())/size_field_x, (width.toInt()-2*indent.toInt())/size_field_x, true);
            right_icon_green = Bitmap.createScaledBitmap(
                icon_green,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                (width.toInt() - 2 * indent.toInt()) / size_field_x,
                true
            )
        }

        var cnt = 0
        for( i in 0..6) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..5) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    cnt++
                    canvas?.drawBitmap(right_icon_cross, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    cnt--
                    canvas?.drawBitmap(right_icon_null, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }

        cross_or_nul = if (cnt == 0) {
            "cross"
        } else {
            "null"
        }

        if (touch_refinement_Y(indent,circley,height,size_field_y,step,advertising_line)>0)     //постановка нового обЪекта
        {
            val X: Int = touch_refinement_for_Array_X(indent,circlex,step)
            val Y: Int = touch_refinement_for_Array_Y(indent,circley,height,size_field_y,step,advertising_line)    //координаты нажимаего для массива

            if (moveChecker(X, Y, cnt) && FIELD[X][Y]==0)
            {
                blocked = true
                var a:Float = circlex
                var b:Float = circley
                if(cross_or_nul=="cross")
                {
                    canvas?.drawBitmap(right_icon_cross,touch_refinement_X(indent,a,width,size_field_x),
                        touch_refinement_Y(indent,b,height,size_field_y,step,advertising_line),paint)
                    FIELD[X][Y] = 1
                    positionData.child("FIELD").child("$X").child("$Y").setValue(1)
                    cross_or_nul = "null"
                    Exit = 1
                }
                else
                {
                    canvas?.drawBitmap(right_icon_null,touch_refinement_X(indent,a,width,size_field_x),
                        touch_refinement_Y(indent,b,height,size_field_y,step,advertising_line),paint)
                    FIELD[X][Y] = 2
                    positionData.child("FIELD").child("$X").child("$Y").setValue(2)
                    //TODO setValue to database
                    cross_or_nul = "cross"
                }

            }
        }

        if(checkForWin_another_fun().size==9)
        {
            var counter: Int = 1
            while(counter<9)
            {
                var a_1: Float =  translate_from_Array_to_Graphics_X(indent,checkForWin_another_fun()[counter],step)


                var a_2: Float = translate_from_Array_to_Graphics_Y(indent,checkForWin_another_fun()[counter+1].toInt(), height,size_field_y, step, advertising_line)
                canvas?.drawBitmap(right_icon_green,a_1,a_2,paint)
                counter += 2
            }
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (blocked) {
            return false
        }
        super.onTouchEvent(event)
        circlex =  event!!.x
        circley =  event!!.y
        invalidate()
        return true
    }


}
