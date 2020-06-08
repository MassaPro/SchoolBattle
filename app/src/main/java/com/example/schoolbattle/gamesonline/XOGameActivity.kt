package com.example.schoolbattle.gamesonline

import android.app.Activity
import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.schoolbattle.*
import com.example.schoolbattle.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_x_o_game.*

class XOGameActivity : AppCompatActivity() {
    private var isRun = false
    private var dialog: ShowResult? = null

    override fun onResume() {
        super.onResume()
        currentContext = this
        isRun = true
        CONTEXT = this
    }

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        setContentView(R.layout.activity_x_o_game)

        currentContext = this
        CONTEXT = this
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

        val gameData = myRef.child("XOGames").child(
            if (opponentsName < yourName)
                opponentsName + '_' + yourName else yourName + '_' + opponentsName
        )
        signature_canvas.blocked = true
        signature_canvas.positionData = gameData

        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                var cnt = 0
                signature_canvas.isFirstMove = (p0.child("Move").value.toString() == yu.toString())
                up_player.text = yourName + if (signature_canvas.isFirstMove) " X" else " O"
                down_player.text = opponentsName + if (!signature_canvas.isFirstMove) " X" else " O"
                for (i in 0..6) {
                    for (j in 0..5) {
                        val p = p0.child("$i").child("$j")
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



                    myRef.child("XOGames").child(if (opponentsName < yourName)
                        opponentsName + '_' + yourName else yourName + '_' + opponentsName
                    ).removeValue()

                    myRef.child("Users").child(yourName).child("Games").child("$opponentsName XOGame").removeValue()
                    myRef.child("Users").child(opponentsName).child("Games").child("$yourName XOGame").removeValue()
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





class CanvasView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

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

    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var Line_paint_1: Paint = Paint()
    var FIELD = Array(7){IntArray(6)}
    var cross_or_nul: String

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




    var icon_cross : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.cross_egypt
    )       //картинки крестиков и нулей
    var icon_null: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.circle_null
    )

    var icon_green:  Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.green_icon
    )

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        //TODO() take field from database
        canvas?.drawColor(Color.WHITE)
        val width = getWidth().toFloat()
        val height = getHeight().toFloat()
        //ширина и высота экрана (от ширины в основном все зависит)

        val size_field_x: Int = 7
        val size_field_y: Int = 6
        val step: Float = width/size_field_x
        val advertising_line: Float = (height - step * 6) / 2

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


        val right_icon_cross: Bitmap = Bitmap.createScaledBitmap(icon_cross,width.toInt()/size_field_x, width.toInt()/size_field_x, true); //подгоняем картинку под размеры экрана телефона
        val right_icon_null: Bitmap = Bitmap.createScaledBitmap(icon_null,width.toInt()/size_field_x, width.toInt()/size_field_x, true);
        val right_icon_green: Bitmap = Bitmap.createScaledBitmap(icon_green,width.toInt()/size_field_x, width.toInt()/size_field_x, true);

        var cnt = 0
        for( i in 0..6) //начальная расстановка крестиков и ноликов
        {
            for(j in 0..5) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    cnt++
                    canvas?.drawBitmap(right_icon_cross, translate_from_Array_to_Graphics_X(i,step),
                        translate_from_Array_to_Graphics_Y(j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    cnt--
                    canvas?.drawBitmap(right_icon_null, translate_from_Array_to_Graphics_X(i,step),
                        translate_from_Array_to_Graphics_Y(j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }

        cross_or_nul = if (cnt == 0) {
            "cross"
        } else {
            "null"
        }

        if (touch_refinement_Y(circley,height,size_field_y,step,advertising_line)>0)     //постановка нового обЪекта
        {
            val X: Int = touch_refinement_for_Array_X(circlex,step)
            val Y: Int = touch_refinement_for_Array_Y(circley,height,size_field_y,step,advertising_line)    //координаты нажимаего для массива

            if (moveChecker(X, Y, cnt) && FIELD[X][Y]==0)
            {
                blocked = true
                var a:Float = circlex
                var b:Float = circley
                if(cross_or_nul=="cross")
                {
                    canvas?.drawBitmap(right_icon_cross,touch_refinement_X(a,width,size_field_x),
                        touch_refinement_Y(b,height,size_field_y,step,advertising_line),paint)
                    FIELD[X][Y] = 1
                    positionData.child("$X").child("$Y").setValue(1)
                    cross_or_nul = "null"
                    Exit = 1
                }
                else
                {
                    canvas?.drawBitmap(right_icon_null,touch_refinement_X(a,width,size_field_x),
                        touch_refinement_Y(b,height,size_field_y,step,advertising_line),paint)
                    FIELD[X][Y] = 2
                    positionData.child("$X").child("$Y").setValue(2)
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
                var a_1: Float =  translate_from_Array_to_Graphics_X(checkForWin_another_fun()[counter],step)


                var a_2: Float = translate_from_Array_to_Graphics_Y(checkForWin_another_fun()[counter+1].toInt(), height,size_field_y, step, advertising_line)
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
