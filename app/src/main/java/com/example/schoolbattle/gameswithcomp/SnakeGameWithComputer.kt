package com.example.schoolbattle.gameswithcomp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*
import kotlin.math.abs
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt


fun IntRange.random() = nextInt((endInclusive + 1) - start) +  start    //расширение функции рандома

class SnakeGameWithComputer : AppCompatActivity() {
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

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_games_template)
        signature_canvas_snake_with_computer.visibility = (View.VISIBLE)
        signature_canvas_snake_with_computer.activity = this
        signature_canvas_snake_with_computer.t1 = findViewById<TextView>(R.id.name_player1_with_computer_template)
        signature_canvas_snake_with_computer.t2 = findViewById<TextView>(R.id.name_player2_with_computer_template)

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator
        
        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("snake_with_computer_template", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_snake_with_computer.History = decode(prefs.getString("snake_with_computer_template", "").toString())
        bottom_navigation_with_computer_template.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{

                }
                R.id.page_2 ->{
                    //dialog_parametrs = Show_parametr_with_computer(this@SnakeGameWithComputer)
                    //dialog_parametrs?.showResult_with_computer(this)
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, SnakeGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                    signature_canvas_snake_with_computer.History.clear()
                    signature_canvas_snake_with_computer.Snake_1.clear()
                    signature_canvas_snake_with_computer.Snake_2.clear()
                    signature_canvas_snake_with_computer.red_or_blue = "red"
                    var data_from_memory = encode( signature_canvas_snake_with_computer.History)
                    val editor =getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("snake_with_computer_template", data_from_memory)
                    editor.apply()
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_snake_with_computer.History.size > 1) {
                        signature_canvas_snake_with_computer.History.removeLast()
                        signature_canvas_snake_with_computer.History.removeLast()
                        var data_from_memory = encode( signature_canvas_snake_with_computer.History)
                        val editor =getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("snake_with_computer_template", data_from_memory)
                        editor.apply()
                        signature_canvas_snake_with_computer.red_or_blue = "red"
                        signature_canvas_snake_with_computer.Snake_1.clear()
                        signature_canvas_snake_with_computer.Snake_2.clear()
                        if (signature_canvas_snake_with_computer.History.size > 0) {
                            for (i in 0 until signature_canvas_snake_with_computer.FIELD.size) {
                                for (j in 0 until signature_canvas_snake_with_computer.FIELD[0].size) {
                                    signature_canvas_snake_with_computer.FIELD[i][j] = 0
                                }
                            }
                            for (i in signature_canvas_snake_with_computer.History) {
                                signature_canvas_snake_with_computer.FIELD[i.first][i.second] = i.third
                                if (signature_canvas_snake_with_computer.red_or_blue == "red") {
                                    signature_canvas_snake_with_computer.Snake_1.add(Pair(i.first, i.second))
                                    signature_canvas_snake_with_computer.red_or_blue = "blue"
                                } else {
                                    signature_canvas_snake_with_computer.Snake_2.add(Pair(i.first, i.second))
                                    signature_canvas_snake_with_computer.red_or_blue = "red"
                                }

                            }
                        }
                        signature_canvas_snake_with_computer.invalidate()
                    }
                }

            }
            true
        }


        if (signature_canvas_snake_with_computer.History.size > 0) {
            signature_canvas_snake_with_computer.red_or_blue = "red"
            signature_canvas_snake_with_computer.Snake_1.clear()
            signature_canvas_snake_with_computer.Snake_2.clear()
            if (signature_canvas_snake_with_computer.History.size > 0) {
                for (i in 0 until signature_canvas_snake_with_computer.FIELD.size) {
                    for (j in 0 until signature_canvas_snake_with_computer.FIELD[0].size) {
                        signature_canvas_snake_with_computer.FIELD[i][j] = 0
                    }
                }
                for (i in signature_canvas_snake_with_computer.History) {
                    signature_canvas_snake_with_computer.FIELD[i.first][i.second] = i.third
                    if (signature_canvas_snake_with_computer.red_or_blue == "red") {
                        signature_canvas_snake_with_computer.Snake_1.add(Pair(i.first, i.second))
                        signature_canvas_snake_with_computer.red_or_blue = "blue"
                    } else {
                        signature_canvas_snake_with_computer.Snake_2.add(Pair(i.first, i.second))
                        signature_canvas_snake_with_computer.red_or_blue = "red"
                    }

                }
            }
            signature_canvas_snake_with_computer.invalidate()
        }

        when (Design) {
            "Normal" -> {
                signature_canvas_snake_with_computer.t1.setTextColor(Color.RED)
                signature_canvas_snake_with_computer.t2.setTextColor(Color.BLUE)
            }
            "Egypt" -> {

                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.player1_egypt);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.player2_egypt);
                player_1_icon_template_with_computer.setBackgroundResource(R.drawable.chip1_egypt);
                player_2_icon_template_with_computer.setBackgroundResource(R.drawable.chip2_egypt)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))

                label_with_computer.setBackgroundResource(R.drawable.background_egypt);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.rgb(255, 230, 163))
                to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
            "Casino" -> {
                name_player1_with_computer_template.setTextColor(Color.YELLOW)
                name_player2_with_computer_template.setTextColor(Color.YELLOW)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip2_casino);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip1_casino);
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_casino);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.argb(0, 224, 164, 103))
                to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
            "Rome" -> {
                name_player1_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
                name_player2_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_rome);
                button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_rome);
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_rome);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.argb(0, 224, 164, 103))
                to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
            "Gothic" -> {
                name_player1_with_computer_template.setTextColor(Color.WHITE)
                name_player2_with_computer_template.setTextColor(Color.YELLOW)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                //button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_gothic);
                //button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_gothic);
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_gothic);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
            "Japan" -> {
                name_player1_with_computer_template.setTextColor(Color.BLACK)
                name_player2_with_computer_template.setTextColor(Color.BLACK)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                //button_player_1_with_computer_template.setBackgroundResource(R.drawable.chip1_japan);
                //button_player_2_with_computer_template.setBackgroundResource(R.drawable.chip2_japan);
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_japan);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
            "Noir" -> {
                name_player1_with_computer_template.setTextColor(Color.WHITE)
                name_player2_with_computer_template.setTextColor(Color.RED)
                name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                name_player2_with_computer_template.setTextSize(20f)
                name_player1_with_computer_template.setTextSize(20f)
                //button_player_1_with_computer_template.setBackgroundResource(R.drawable.cross_gothic);
                //button_player_2_with_computer_template.setBackgroundResource(R.drawable.null_gothic);
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                label_with_computer.setBackgroundResource(R.drawable.background_noir);
                bottom_navigation_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
                to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
                toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            }
        }
    }
}

class CanvasView_SNAKE_COMPUTER(context: Context, attrs: AttributeSet?) : View(context, attrs) {
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
    fun correction_touch (x: Float,y : Float) :  Boolean // если нажали примерно туда
    {
        if( (circlex-x)*(circlex-x) + (circley - y)*(circley - y) < (step/2f)*(step/2f))
        {
            return true
        }
        return false
    }

    fun check_win() : Int
    {
        if(red_or_blue == "red" && Snake_1.size > 0)
        {
            var X : Int = Snake_1.last().first
            var Y: Int = Snake_1.last().second
            if(X == 0)
            {
                if(FIELD[10][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 0)
            {
                if(FIELD[X][10] == 0)
                {
                    return 0
                }
            }
            if(X == 10)
            {
                if(FIELD[0][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 10)
            {
                if(FIELD[X][0] == 0)
                {
                    return 0
                }
            }
            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<10)
            {
                if(FIELD[X+1][Y] == 0)
                {
                    return 0
                }
            }
            if(Y>0)
            {
                if(FIELD[X][Y-1] == 0)
                {
                    return 0
                }
            }
            if(Y<10)
            {
                if(FIELD[X][Y+1] == 0)
                {
                    return 0
                }
            }
            return 2
        }
        if(red_or_blue == "blue"  && Snake_2.size > 0)
        {
            var X : Int = Snake_2.last().first
            var Y: Int = Snake_2.last().second

            if(X == 0)
            {
                if(FIELD[10][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 0)
            {
                if(FIELD[X][10] == 0)
                {
                    return 0
                }
            }
            if(X == 10)
            {
                if(FIELD[0][Y] == 0)
                {
                    return 0
                }
            }
            if(Y == 10)
            {
                if(FIELD[X][0] == 0)
                {
                    return 0
                }
            }
            if(X>0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    return 0
                }
            }

            if(X<10)
            {
                if(FIELD[X+1][Y] == 0)
                {
                    return 0
                }
            }
            if(Y>0)
            {
                if(FIELD[X][Y-1] == 0)
                {
                    return 0
                }
            }
            if(Y<10)
            {
                if(FIELD[X][Y+1] == 0)
                {
                    return 0
                }
            }
            return 1
        }
        return -1
    }

    fun computer_algorithm(who_move : String)
    {
        var n = Random.nextInt(0, 4);

        if(Snake_2.size>0)
        {
            loop@ while(true)
            {
                when (n) {
                    0 -> {
                        if(FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] == 0)
                        {
                            FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 2;
                            Snake_2.add(Pair(abs(Snake_2.last().first-1+11)%11,Snake_2.last().second))
                            break@loop;
                        }
                        n++
                    }
                    1 -> {
                        if(FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] == 0)
                        {
                            FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 2
                            Snake_2.add(Pair(abs(Snake_2.last().first+1+11)%11,Snake_2.last().second))
                            break@loop;
                        }
                        n++
                    }
                    2 -> {
                        if(FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] == 0)
                        {
                            FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 2;
                            Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second+1+11)%11))
                            break@loop;
                        }
                        n++
                    }
                    3 -> {
                        if(FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] == 0)
                        {
                            FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 2;
                            Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second-1+11)%11))
                            break@loop;
                        }
                        n++
                        n %= 4
                    }
                }
            }

        }
        else
        {
            var x  = Random.nextInt(0, 11);
            var y = Random.nextInt(0, 11);

            loop@for(i in x until x +11)
            {
                for(j in y until y + 11)
                {
                    if(FIELD[i%11][j%11]==0)
                    {
                        FIELD[i%11][j%11] = 2;
                        Snake_2.add(Pair(i%11,j%11))
                        break@loop
                    }
                }
            }
            Toast.makeText(activity,Snake_2.size.toString(), Toast.LENGTH_SHORT).show()
        }
        History.add(Triple(Snake_2.last().first,Snake_2.last().second,2))
        var data_from_memory = encode(History)
        val editor = context.getSharedPreferences(
            "UserData",
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(
            "snake_with_computer_template",
            data_from_memory
        )
        editor.apply()
        invalidate()
        red_or_blue = if(red_or_blue == "red") {
            "blue"
        } else {
            "red"
        }
    }
    @ExperimentalStdlibApi
    fun CRAZY_COMPUTER_ALGORITHM_SNAKE(depth: Int, who_move:String, t1: Int): Triple<Int,Int,Int>
    {
        var t = t1%4;
        if(who_move == "blue")
        {
            var i = 0
            var j = 0
            if(depth == 0)
            {
                return Triple(0,0,0)
            }

            if(t==0)
            {
                if(FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 2;
                    Snake_2.add(Pair(abs(Snake_2.last().first-1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 2
                    Snake_2.add(Pair(abs(Snake_2.last().first+1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second+1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0 //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second-1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0
                    }
                }
            }
            else if(t==1)
            {
                if(FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 2
                    Snake_2.add(Pair(abs(Snake_2.last().first+1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second+1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0 //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second-1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0
                    }
                }
                if(FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 2;
                    Snake_2.add(Pair(abs(Snake_2.last().first-1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
            }
            else if(t==2)
            {
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second+1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0 //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second-1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0
                    }
                }
                if(FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 2;
                    Snake_2.add(Pair(abs(Snake_2.last().first-1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 2
                    Snake_2.add(Pair(abs(Snake_2.last().first+1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
            }
            else if(t==3)
            {
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second-1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second-1+11)%11] = 0
                    }
                }
                if(FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 2;
                    Snake_2.add(Pair(abs(Snake_2.last().first-1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first-1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] == 0)
                {
                    FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 2
                    Snake_2.add(Pair(abs(Snake_2.last().first+1+11)%11,Snake_2.last().second))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[abs(Snake_2.last().first+1+11)%11][Snake_2.last().second] = 0; //возрат к нормальному массиву
                    }
                }
                if(FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] == 0)
                {
                    FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 2;
                    Snake_2.add(Pair(Snake_2.last().first,abs(Snake_2.last().second+1+11)%11))
                    i = Snake_2.last().first
                    j = Snake_2.last().second
                    if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"red",t).first==0)
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0 //возрат к нормальному массиву
                        return Triple(1,i,j)
                    }
                    else
                    {
                        Snake_2.removeLast()
                        FIELD[Snake_2.last().first][abs(Snake_2.last().second+1+11)%11] = 0; //возрат к нормальному массиву
                    }
                }
            }

            return Triple(0,i,j)
        }
        else
        {
            var i = 0
            var j = 0
            if(depth == 0)
            {
                return Triple(0,0,0)
            }
            if(FIELD[abs(Snake_1.last().first-1+11)%11][Snake_1.last().second] == 0)
            {
                FIELD[abs(Snake_1.last().first-1+11)%11][Snake_1.last().second] = 1
                Snake_1.add(Pair(abs(Snake_1.last().first-1+11)%11,Snake_1.last().second))
                i = Snake_1.last().first
                j = Snake_1.last().second
                if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"blue",t).first==0)
                {
                    Snake_1.removeLast()
                    FIELD[abs(Snake_1.last().first-1+11)%11][Snake_1.last().second] = 0; //возрат к нормальному массиву
                    return Triple(1,i,j)
                }
                else
                {
                    Snake_1.removeLast()
                    FIELD[abs(Snake_1.last().first-1+11)%11][Snake_1.last().second] = 0; //возрат к нормальному массиву
                }
            }
            if(FIELD[abs(Snake_1.last().first+1+11)%11][Snake_1.last().second] == 0)
            {
                FIELD[abs(Snake_1.last().first+1+11)%11][Snake_1.last().second] = 1
                Snake_1.add(Pair(abs(Snake_1.last().first+1+11)%11,Snake_1.last().second))
                i = Snake_1.last().first
                j = Snake_1.last().second
                if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"blue",t).first==0)
                {
                    Snake_1.removeLast()
                    FIELD[abs(Snake_1.last().first+1+11)%11][Snake_1.last().second] = 0; //возрат к нормальному массиву
                    return Triple(1,i,j)
                }
                else
                {
                    Snake_1.removeLast()
                    FIELD[abs(Snake_1.last().first+1+11)%11][Snake_1.last().second] = 0; //возрат к нормальному массиву
                }
            }
            if(FIELD[Snake_1.last().first][abs(Snake_1.last().second+1+11)%11] == 0)
            {
                FIELD[Snake_1.last().first][abs(Snake_1.last().second+1+11)%11] = 1;
                Snake_1.add(Pair(Snake_1.last().first,abs(Snake_1.last().second+1+11)%11))
                i = Snake_1.last().first
                j = Snake_1.last().second
                if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"blue",t).first==0)
                {
                    Snake_1.removeLast()
                    FIELD[Snake_1.last().first][abs(Snake_1.last().second+1+11)%11] = 0; //возрат к нормальному массиву
                    return Triple(1,i,j)
                }
                else
                {
                    Snake_1.removeLast()
                    FIELD[Snake_1.last().first][abs(Snake_1.last().second+1+11)%11] = 0; //возрат к нормальному массиву
                }
            }
            if(FIELD[Snake_1.last().first][abs(Snake_1.last().second-1+11)%11] == 0)
            {
                FIELD[Snake_1.last().first][abs(Snake_1.last().second-1+11)%11] = 1
                Snake_1.add(Pair(Snake_1.last().first,abs(Snake_1.last().second-1+11)%11))
                i = Snake_1.last().first
                j = Snake_1.last().second
                if(CRAZY_COMPUTER_ALGORITHM_SNAKE(depth-1,"blue",t).first==0)
                {
                    Snake_1.removeLast()
                    FIELD[Snake_1.last().first][abs(Snake_1.last().second-1+11)%11] = 0 //возрат к нормальному массиву
                    return Triple(1,i,j)
                }
                else
                {
                    Snake_1.removeLast()
                    FIELD[Snake_1.last().first][abs(Snake_1.last().second-1+11)%11] = 0;
                }
            }
            return Triple(0,i,j)
        }
    }
    lateinit var activity: Activity

    lateinit var t1: TextView
    lateinit var t2: TextView

    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()
    var Snake_1: MutableList<Pair<Int,Int>>  =  mutableListOf()
    var Snake_2: MutableList<Pair<Int,Int>>  =  mutableListOf()         //векторы координат путей
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
    var border_1: Paint = Paint()
    var border_2: Paint = Paint()

    var FIELD : MutableList<MutableList<Int>> = mutableListOf()    //для фишеК
    var A: MutableList<Pair<Int,Int>> = mutableListOf()
    var TREE_OF_WAYS: MutableList<MutableList<Pair<Int,Int>>> = mutableListOf()
//    var CELLS : MutableList<MutableList<Int> = mutableListOf()         //массив клеток в которых мы будем проводить ребра


    var radius_of_point: Float = 0f
    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f





    var line_who_do_move : Paint = Paint()


    init{
        line_who_do_move.strokeWidth = 7f

        red_or_blue = "red"
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(7f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(10f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(10f)

        border_1.setColor(Color.GRAY)
        border_1.setStrokeWidth(10f)
        border_2.setColor(Color.GRAY)
        border_2.setStrokeWidth(20f)

        line_who_do_move.strokeWidth  =  7f
        when (Design) {
            "Normal" -> {
                line_who_do_move.color = Color.GREEN
            }
            "Egypt" -> {

                Line_paint.setColor(Color.rgb(100, 100, 100))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.rgb(100,100,100))
                paint_rib_1.setColor(Color.BLACK)          //цвета для ребер  и их ширина
                paint_rib_2.setColor(Color.WHITE)

                border_1.setColor(Color.rgb(100, 100, 100))
                line_who_do_move.color = Color.RED
            }
            "Casino" -> {

                Line_paint.setColor(Color.WHITE)          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.WHITE)     //цвета для точек
                paint_rib_1.setColor(Color.BLACK)          //цвета для ребер  и их ширина
                paint_rib_2.setColor(Color.RED)
                border_1.setColor(Color.WHITE)
                line_who_do_move.color = Color.YELLOW
            }
            "Rome" -> {

                Line_paint.setColor(Color.rgb(180, 180, 180))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.rgb(180, 180, 180))     //цвета для точек
                paint_rib_2.setColor(Color.BLACK)          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.rgb(193,150,63))
                border_1.setColor(Color.rgb(180, 180, 180))
                line_who_do_move.color = Color.rgb(193, 150, 63)
            }
            "Gothic" -> {

                Line_paint.setColor(Color.rgb(100, 100, 100))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.rgb(180, 180, 180))     //цвета для точек
                paint_rib_2.setColor(Color.WHITE)          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.YELLOW)
                border_1.setColor(Color.rgb(100, 100, 100))
                line_who_do_move.color = Color.RED
            }
            "Japan" -> {

                Line_paint.setColor(Color.rgb(160, 160, 160))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.rgb(160, 160, 160))     //цвета для точек
                paint_rib_2.setColor(Color.RED)          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.rgb(37,103,28))
                border_1.setColor(Color.rgb(160, 160, 160))
                line_who_do_move.color = Color.RED

            }
            "Noir" -> {

                Line_paint.setColor(Color.rgb(100, 100, 100))          //ресур для линий (ширина и цвет)
                paint_circle.setColor(Color.rgb(180, 180, 180))     //цвета для точек
                paint_rib_2.setColor(Color.RED)          //цвета для ребер  и их ширина
                paint_rib_1.setColor(Color.WHITE)
                border_1.setColor(Color.rgb(100, 100, 100))
                line_who_do_move.color = Color.RED
            }
        }

        for(i in 0 until 11)
        {
            FIELD.add(mutableListOf())
            for(j in 0 until 11)
            {
                FIELD.last().add(0)
            }
        }

    }




    var red : Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.red
    );       //картинки фишек и подсветки
    var blue: Bitmap = BitmapFactory.decodeResource(context.getResources(),
        R.drawable.blue
    );



    override fun draw(canvas: Canvas?) {
        super.draw(canvas)



        radius_of_point = 8f
        size_field_x  = 10
        size_field_y  = 10
        indent = 20f //оступ, чтобы можно было тыкнуть в границу
        width = getWidth().toFloat() - 2*indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        step = width/size_field_x
        advertising_line =  (height - 10*step)/2          //полоска для рекламы


        k = height-width*(size_field_y.toFloat()/size_field_x.toFloat())-advertising_line

        // canvas?.drawColor(Color.WHITE)


        if(red_or_blue == "red")
        {
            t1.text = "Игрок 1 думает..."
            t2.text = "Компьютер"
            canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)

        }
        else
        {
            t1.text = "Игрок 1"
            t2.text = "Компьютер думает..."
            canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
        }
        for(i in 0 until size_field_y+1)          //вырисовка горизонтальных линий
        {

            canvas?.drawLine(indent,k,width+indent,k,Line_paint)
            k = k + step
        }

        k = indent
        for(i in 0 until size_field_x+1)         //вырисовка вертикальных линий
        {

            canvas?.drawLine(k, height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())+ 5f,k,height-advertising_line-5f,Line_paint)
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
                canvas?.drawCircle(x,y,radius_of_point,paint_circle)
                y += step
            }
            x += step
            y  = height - advertising_line - width*(size_field_y.toFloat()/size_field_x.toFloat())
        }

        for(i in 0 until Snake_1.size - 1)     //вырисовка ребер змеи
        {
            var X: Float = indent + step*Snake_1[i].first
            var Y: Float =  height - advertising_line - width + step*Snake_1[i].second
            var X1: Float = indent + step*Snake_1[i+1].first
            var Y1: Float =  height - advertising_line - width + step*Snake_1[i+1].second
            if(Math.abs(Snake_1[i].first - Snake_1[i + 1].first) + Math.abs(Snake_1[i].second - Snake_1[i + 1].second) <= 2)
            {
                canvas?.drawLine(X,Y,X1,Y1,paint_rib_1)
            }

            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first < Snake_1[i+1].first )
            {
                if(Math.abs(Snake_1[i].first - Snake_1[i + 1].first) + Math.abs(Snake_1[i].second - Snake_1[i + 1].second) <= 2)
                {
                    canvas?.drawLine(X - 5, Y, X1 + 5, Y1, paint_rib_1)
                }
            }
            if(Snake_1[i].second == Snake_1[i+1].second &&Snake_1[i].first > Snake_1[i+1].first )
            {
                if(Math.abs(Snake_1[i].first - Snake_1[i + 1].first) + Math.abs(Snake_1[i].second - Snake_1[i + 1].second) <= 2)
                {
                    canvas?.drawLine(X+5,Y,X1-5,Y1,paint_rib_1)
                }
            }
        }
        for(i in 0 until Snake_2.size - 1)      //вырисовка ребер змеи
        {
            var X: Float = indent + step*Snake_2[i].first
            var Y: Float =  height - advertising_line - width + step*Snake_2[i].second
            var X1: Float = indent + step*Snake_2[i+1].first
            var Y1: Float =  height - advertising_line - width + step*Snake_2[i+1].second
            if(Math.abs(Snake_2[i].first - Snake_2[i + 1].first) + Math.abs(Snake_2[i].second - Snake_2[i + 1].second) <= 2)
            {
                canvas?.drawLine(X,Y,X1,Y1,paint_rib_2)
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first < Snake_2[i+1].first )
            {
                if(Math.abs(Snake_2[i].first - Snake_2[i + 1].first) + Math.abs(Snake_2[i].second - Snake_2[i + 1].second) <= 2)
                {
                    canvas?.drawLine(X - 5, Y, X1 + 5, Y1, paint_rib_2)
                }
            }
            if(Snake_2[i].second == Snake_2[i+1].second &&Snake_2[i].first > Snake_2[i+1].first )
            {
                if(Math.abs(Snake_2[i].first - Snake_2[i + 1].first) + Math.abs(Snake_2[i].second - Snake_2[i + 1].second) <= 2)
                {
                    canvas?.drawLine(X+5,Y,X1-5,Y1,paint_rib_2)
                }
            }
        }

        if(Snake_1.size>0)
        {
            var X: Float = indent + step * Snake_1[0].first  - step / 5f
            var X1: Float = indent + step * Snake_1[0].first  + step / 5f
            var Y: Float =
                height - advertising_line - width + step * Snake_1[0].second  - step / 5f
            var Y1: Float =
                height - advertising_line - width + step * Snake_1[0].second  + step / 5f
            canvas?.drawLine(X, Y, X1, Y1, paint_rib_1)
            canvas?.drawLine(X, Y1, X1, Y, paint_rib_1)
        }

        if(Snake_2.size>0)
        {
            var _X: Float = indent + step * Snake_2[0].first - step / 5f
            var _X1: Float = indent + step * Snake_2[0].first + step / 5f
            var _Y: Float =
                height - advertising_line - width + step * Snake_2[0].second  - step / 5f
            var _Y1: Float =
                height - advertising_line - width + step * Snake_2[0].second  + step / 5f
            canvas?.drawLine(_X, _Y, _X1, _Y1, paint_rib_2)
            canvas?.drawLine(_X, _Y1, _X1, _Y, paint_rib_2)
        }

        if(Snake_1.size > 1)
        {
            var X: Float = indent + step*Snake_1.last().first
            var Y: Float = height - width - advertising_line + step*Snake_1.last().second
            canvas?.drawCircle(X,Y,radius_of_point*2,paint_rib_1)
        }
        if(Snake_2.size > 1)
        {
            var X: Float = indent + step*Snake_2.last().first
            var Y: Float = height - width - advertising_line + step*Snake_2.last().second
            canvas?.drawCircle(X,Y,radius_of_point*2,paint_rib_2)
        }



    }

    var blocked : Boolean = false
    @ExperimentalStdlibApi
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("HISTORY",FIELD.toString())
            indent = 20f //оступ, чтобы можно было тыкнуть в границу
            advertising_line = (height - 10 * step) / 2
            if (check_win() <= 0) {
                blocked = false
            }
            if (check_win() > 0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked) {
                blocked = !blocked
            }
            if (check_win() > 0 && event!!.getAction() == MotionEvent.ACTION_UP && !blocked) {
                blocked = !blocked
                var dialog: Show_Result_with_Computer? = null
                dialog = Show_Result_with_Computer(activity)
                if (check_win() == 1) {
                    dialog?.showResult_with_Computer("Игрок 1 победил", "SnakeGame", activity)
                    return true
                }
                if (check_win() == 2) {
                    dialog?.showResult_with_Computer("Компьютер победил", "SnakeGame", activity)
                    return true
                }
            }
            circlex = event!!.x
            circley = event!!.y

            x = indent
            y =
                height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            for (i in 0..size_field_x) {
                for (j in 0..size_field_y) {
                    if (correction_touch(x, y)) {
                        if (FIELD[i][j] == 0) {
                            if (red_or_blue == "red") {
                                if (Snake_1.size == 0) {
                                    Snake_1.add(Pair(i, j))
                                    FIELD[i][j] = 1
                                    red_or_blue = "blue"
                                    History.add(Triple(i, j, FIELD[i][j]))
                                    var data_from_memory = encode(History)
                                    val editor = context.getSharedPreferences(
                                        "UserData",
                                        Context.MODE_PRIVATE
                                    ).edit()
                                    editor.putString(
                                        "snake_with_computer_template",
                                        data_from_memory
                                    )
                                    editor.apply()
                                    if (SOUND) {
                                        mSound.play(1, 1F, 1F, 1, 0, 1F)
                                    }
                                    if (VIBRATION) {
                                        vibratorService?.vibrate(70)
                                    }

                                    invalidate()
                                    val handler = android.os.Handler()
                                    handler.postDelayed({
                                        computer_algorithm("blue")
                                    },Random.nextLong(500, 700))
                                }
                                else {
                                    if ((i == Snake_1.last().first && Math.abs(j - Snake_1.last().second) % 9 == 1) || (j == Snake_1.last().second && Math.abs(
                                            i - Snake_1.last().first
                                        ) % 9 == 1)
                                    ) {
                                        Snake_1.add(Pair(i, j))
                                        FIELD[i][j] = 1
                                        red_or_blue = "blue"
                                        History.add(Triple(i, j, FIELD[i][j]))
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences(
                                            "UserData",
                                            Context.MODE_PRIVATE
                                        ).edit()
                                        editor.putString(
                                            "snake_with_computer_template",
                                            data_from_memory
                                        )
                                        editor.apply()
                                        if (SOUND) {
                                            mSound.play(1, 1F, 1F, 1, 0, 1F)
                                        }
                                        if (VIBRATION) {
                                            vibratorService?.vibrate(70)
                                        }
                                        invalidate()
                                        if (check_win() > 0) {

                                            var dialog: Show_Result_with_Computer? = null
                                            dialog = Show_Result_with_Computer(activity)
                                            if (check_win() == 1) {
                                                dialog?.showResult_with_Computer("Игрок 1 победил", "SnakeGame", activity)
                                                x = 0f
                                                y = 0f
                                                return true
                                            }
                                            if (check_win() == 2) {
                                                dialog?.showResult_with_Computer("Компьютер победил", "SnakeGame", activity)
                                                x = 0f
                                                y = 0f
                                                return true
                                            }
                                        }
                                        else
                                        {
                                            val handler = android.os.Handler()
                                            handler.postDelayed({
                                                var trip: Triple<Int,Int,Int> = CRAZY_COMPUTER_ALGORITHM_SNAKE(22,"blue",IntRange(0,10).random())
                                                Log.d("ALGOR",trip.toString())
                                                FIELD[trip.second][trip.third] = 2
                                                History.add(Triple(trip.second,trip.third,2))
                                                Snake_2.add(Pair(History.last().first,History.last().second))
                                                red_or_blue = "red"
                                                var data_from_memory = encode(History)
                                                val editor = context.getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "snake_with_computer_template",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                invalidate()

                                                if (check_win() > 0 && event!!.getAction() == MotionEvent.ACTION_UP && !blocked) {
                                                    var dialog: Show_Result_with_Computer? = null
                                                    dialog = Show_Result_with_Computer(activity)
                                                    if (check_win() == 1) {
                                                        dialog?.showResult_with_Computer("Игрок 1 победил", "SnakeGame", activity)

                                                    }
                                                    if (check_win() == 2) {
                                                        dialog?.showResult_with_Computer("Компьютер победил", "SnakeGame", activity)

                                                    }
                                                }
                                            },Random.nextLong(50, 70))
                                        }
                                    }
                                }

                            }
                        }
                    }
                    y += step
                }
                x += step
                y =
                    height - advertising_line - width * (size_field_y.toFloat() / size_field_x.toFloat())
            }
            x = 0f
            y = 0f

        return true

    }

}
