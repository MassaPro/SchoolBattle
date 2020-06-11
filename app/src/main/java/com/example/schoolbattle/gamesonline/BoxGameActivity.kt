package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.schoolbattle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_box_game.*

class BoxGameActivity : AppCompatActivity() {

    private var isRun = false
    private var dialog: ShowResult? = null

    override fun onResume() {
        super.onResume()
        CONTEXT = this
        currentContext = this
        isRun = true
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        CONTEXT = this
        currentContext = this
        isRun = true
        super.onResume()
        setContentView(R.layout.activity_box_game)
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
        if (type != "") {
            TODO()
            //ALF CODE HERE
        }
        val gameData = myRef.child(type + "BoxGames").child(
            if (opponentsName < yourName)
                opponentsName + '_' + yourName else yourName + '_' + opponentsName
        )
        signature_canvas_box.positionData = gameData
        signature_canvas_box.blocked = true
        gameData.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                signature_canvas_box.blocked = true

                signature_canvas_box.red_or_blue = if (p0.hasChild("red_or_blue")) p0.child("red_or_blue").value.toString() else "red"
                if ((signature_canvas_box.red_or_blue == "red") == (p0.child("Move").toString().toBoolean() == (yu == '0'))) signature_canvas_box.blocked = false
                for (i in signature_canvas_box.FIELD.indices) {
                    for (j in signature_canvas_box.FIELD[i].indices) {
                        if (p0.child("FIELD").child("$i").hasChild("$j"))
                        signature_canvas_box.FIELD[i][j] = p0.child("FIELD").child("$i").child("$j").value.toString().toInt()
                    }
                }
                for (i in signature_canvas_box.VERTICAL_RIB.indices) {
                    for (j in signature_canvas_box.VERTICAL_RIB[i].indices) {
                        if (p0.child("VERTICAL_RIB").child("$i").hasChild("$j"))
                            signature_canvas_box.VERTICAL_RIB[i][j] = p0.child("VERTICAL_RIB").child("$i").child("$j").value.toString().toInt()
                    }
                }
                for (i in signature_canvas_box.HORIZONTAL_RIB.indices) {
                    for (j in signature_canvas_box.HORIZONTAL_RIB[i].indices) {
                        if (p0.child("HORIZONTAL_RIB").child("$i").hasChild("$j"))
                            signature_canvas_box.HORIZONTAL_RIB[i][j] = p0.child("HORIZONTAL_RIB").child("$i").child("$j").value.toString().toInt()
                    }
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
                val ch = check_win()
                if (ch != 0) {
                    signature_canvas_box.blocked = true
                    var res = if (ch == 2 && yu == '0' || ch == 1 && yu == '1') {
                            "Победа"
                        } else if (ch == 2 && yu == '1' || ch == 1 && yu == '0') {
                            "Поражение"
                        } else {
                            "Ничья"
                        }
                    myRef.child(type + "BoxGames").child(if (opponentsName < yourName)
                        opponentsName + '_' + yourName else yourName + '_' + opponentsName
                    ).removeValue()

                    myRef.child("Users").child(yourName).child(type + "Games").child("$opponentsName BoxGame").removeValue()
                    myRef.child("Users").child(opponentsName).child(type + "Games").child("$yourName BoxGame").removeValue()
                    dialog =
                        ShowResult(this@BoxGameActivity)
                    if (isRun) {
                        dialog?.showResult(res, "BoxGame", yourName, opponentsName)
                    }
                    gameData.removeEventListener(this)
                }
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
}

class CanvasView_Boxs_online(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (width/2f/size_field_x.toFloat())*(width/2f/size_field_x.toFloat())/2f)
        {
            return true
        }
        return false
    }

    lateinit var positionData: DatabaseReference

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




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.red
    );       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.blue
    );
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

        canvas?.drawColor(Color.WHITE)
        val right_red: Bitmap = Bitmap.createScaledBitmap(red,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        val right_blue: Bitmap = Bitmap.createScaledBitmap(blue,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
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


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (blocked) {
            return true
        }

        var isCorrect = false

        circlex = event!!.x
        circley = event!!.y

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
                            red_or_blue = "blue"
                            isCorrect = true
                            Log.d("DOPO","ЛОЛ")
                        }
                    }
                    else
                    {
                        if (correction_touch(x,y+step/2f))
                        {
                            VERTICAL_RIB[i][j] = 2
                            red_or_blue = "red"
                            isCorrect = true
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
                            red_or_blue = "blue"
                            isCorrect = true
                            Log.d("DOPO","ЛОЛ")
                        }
                    }
                    else
                    {
                        if (correction_touch(x+step/2f,y))
                        {
                            HORIZONTAL_RIB[i][j] = 2
                            red_or_blue = "red"
                            isCorrect = true
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
        if (isCorrect) {
            positionData.child("red_or_blue").setValue(red_or_blue)
            for (i in FIELD.indices) {
                for (j in FIELD[i].indices) {
                    if (FIELD[i][j] != 0)
                    positionData.child("FIELD").child("$i").child("$j").setValue(FIELD[i][j])
                }
            }
            for (i in VERTICAL_RIB.indices) {
                for (j in VERTICAL_RIB[i].indices) {
                    if (VERTICAL_RIB[i][j] != 0)
                    positionData.child("VERTICAL_RIB").child("$i").child("$j").setValue(VERTICAL_RIB[i][j])
                }
            }
            for (i in HORIZONTAL_RIB.indices) {
                for (j in HORIZONTAL_RIB[i].indices) {
                    if (HORIZONTAL_RIB[i][j] != 0)
                    positionData.child("HORIZONTAL_RIB").child("$i").child("$j").setValue(HORIZONTAL_RIB[i][j])
                }
            }
        }
        return true
    }

}