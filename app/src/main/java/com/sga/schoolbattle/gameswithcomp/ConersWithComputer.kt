package com.sga.schoolbattle.gameswithcomp


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_computer_games_template.*

var AngleGameMode = 0

class ConersWithComputer : AppCompatActivity() {
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
        signature_canvas_corners_with_computer.visibility = (View.VISIBLE)

        signature_canvas_corners_with_computer.activity = this
        CONTEXT = this

      //  mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator


        val prefs2 = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        AngleGameMode = prefs2.getInt("AngleGameMode", 0)
        if (AngleGameMode == 0) {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putInt("AngleGameMode", 1)
            editor.apply()
            AngleGameMode = 1
        }
        signature_canvas_corners_with_computer.blockedOnTouch = false

        val prefs_first = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_corners_with_computer.History = decode(prefs_first.getString("corner_with_computer", "").toString())
        if (AngleGameMode == 2 && signature_canvas_corners_with_computer.History.size == 0) {
            signature_canvas_corners_with_computer.blockedOnTouch = true        // TODO check
        }




        signature_canvas_corners_with_computer.t1 = findViewById(R.id.name_player1_with_computer_template) as TextView
        signature_canvas_corners_with_computer.t2 = findViewById(R.id.name_player2_with_computer_template) as TextView

        if(Design == "Egypt" ) {
            name_player1_with_computer_template.setTextColor(Color.BLACK)
            name_player2_with_computer_template.setTextColor(Color.BLACK)
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_with_computer_template.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_with_computer_template.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_template_with_computer.setBackgroundResource(R.drawable.black_chip_egypt);
            player_2_icon_template_with_computer.setBackgroundResource(R.drawable.white_chip_egypt)
            toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
            toolbar2_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))

            label_with_computer.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_with_computer_template.setBackgroundColor(Color.rgb(255, 230, 163))
            to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
            toolbar_with_computer_template.setBackgroundColor(Color.argb(0, 0, 0, 0))
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
        else if(Design == "Rome" ) {
            name_player1_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
            name_player2_with_computer_template.setTextColor(Color.rgb(193, 150, 63))
            name_player1_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_with_computer_template.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_with_computer_template.setTextSize(20f)
            name_player1_with_computer_template.setTextSize(20f)
            button_player_1_with_computer_template.setBackgroundResource(R.drawable.white_chip_rome);
            button_player_2_with_computer_template.setBackgroundResource(R.drawable.black_chip_rome);
            toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
            label_with_computer.setBackgroundResource(R.drawable.background_rome);
            bottom_navigation_with_computer_template.setBackgroundColor(argb(0,224, 164, 103))
            to_back_with_computer_template.setBackgroundResource(R.drawable.arrow_back)
            toolbar_with_computer_template.setBackgroundColor(argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("corner_with_computer", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_corners_with_computer.History = decode(prefs.getString("corner_with_computer", "").toString())
        if (signature_canvas_corners_with_computer.History.size > 0)
        {

            for(i in 0 until signature_canvas_corners_with_computer.FIELD.size)
            {
                for(j in 0 until signature_canvas_corners_with_computer.FIELD[0].size)
                {
                    signature_canvas_corners_with_computer.FIELD[i][j] = 0
                }
            }
            signature_canvas_corners_with_computer.FIELD[0][5] = 1;signature_canvas_corners_with_computer.FIELD[1][5] = 1;signature_canvas_corners_with_computer.FIELD[2][5] = 1;
            signature_canvas_corners_with_computer.FIELD[0][6] = 1;signature_canvas_corners_with_computer.FIELD[1][6] = 1;signature_canvas_corners_with_computer.FIELD[2][6] = 1;
            signature_canvas_corners_with_computer.FIELD[0][7] = 1;signature_canvas_corners_with_computer.FIELD[1][7] = 1;signature_canvas_corners_with_computer.FIELD[2][7] = 1;


            signature_canvas_corners_with_computer.FIELD[5][0] = 2;signature_canvas_corners_with_computer.FIELD[5][1] = 2;signature_canvas_corners_with_computer.FIELD[5][2] = 2;
            signature_canvas_corners_with_computer.FIELD[6][0] = 2;signature_canvas_corners_with_computer.FIELD[6][1] = 2;signature_canvas_corners_with_computer.FIELD[6][2] = 2;
            signature_canvas_corners_with_computer.FIELD[7][0] = 2;signature_canvas_corners_with_computer.FIELD[7][1] = 2;signature_canvas_corners_with_computer.FIELD[7][2] = 2;

            signature_canvas_corners_with_computer.Black_or_grey_chip = "black"
            for (i in signature_canvas_corners_with_computer.History) {
                signature_canvas_corners_with_computer.FIELD[i[2]][i[3]] = signature_canvas_corners_with_computer.FIELD[i[0]][i[1]]
                signature_canvas_corners_with_computer.FIELD[i[0]][i[1]] = 0
                if(signature_canvas_corners_with_computer.Black_or_grey_chip == "black")
                {
                    signature_canvas_corners_with_computer.Black_or_grey_chip =  "grey"
                }
                else
                {
                    signature_canvas_corners_with_computer.Black_or_grey_chip =  "black"
                }
            }
            signature_canvas_corners_with_computer.PHASE = true
            for(i in  0 until signature_canvas_corners_with_computer.Array_of_illumination.size)
            {
                for(j in 0 until signature_canvas_corners_with_computer.Array_of_illumination[0].size)
                {
                    signature_canvas_corners_with_computer.Array_of_illumination[i][j] =0
                }
            }
            signature_canvas_corners_with_computer.invalidate()
        }



        if (AngleGameMode == 2 && signature_canvas_corners_with_computer.History.size == 0) {
            signature_canvas_corners_with_computer.blockedOnTouch = true

            val handler = android.os.Handler()
            handler.postDelayed({
                var flag = 0

                var list_x: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                var list_y: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                list_x.shuffle()
                list_y.shuffle()

                for (i in list_x) {
                    for (j in list_y) {
                        if (signature_canvas_corners_with_computer.FIELD[i][j] == 3 - AngleGameMode) {
                            signature_canvas_corners_with_computer.MakeLight(i, j, 1)

                            if (AngleGameMode == 1) {
                                for (i2 in 0..i) {
                                    for (j2 in j..7) {

                                        if (i2 != i || j2 != j) {

                                            if (signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] == 1 || signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] == 2)     //если подсвечена область
                                            {
                                                signature_canvas_corners_with_computer.FIELD[i2][j2] =
                                                    signature_canvas_corners_with_computer.FIELD[i][j]         //перемещение фишки
                                                signature_canvas_corners_with_computer.History.add(mutableListOf(i, j, i2, j2))
                                                var data_from_memory = encode(signature_canvas_corners_with_computer.History)
                                                val editor = getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "corner_with_computer",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                signature_canvas_corners_with_computer.FIELD[i][j] = 0
                                                if (signature_canvas_corners_with_computer.Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                                                {
                                                    signature_canvas_corners_with_computer.Black_or_grey_chip = "grey"
                                                } else {
                                                    signature_canvas_corners_with_computer.Black_or_grey_chip = "black"
                                                }

                                                flag = 1
                                                break
                                            }

                                        }
                                    }
                                    if (flag == 1) {
                                        break
                                    }
                                }
                            }


                            if (AngleGameMode == 2 && flag == 0) {
                                for (i2 in i..7) {
                                    for (j2 in j..7) {

                                        if (i2 != i || j2 != j) {

                                            if (signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] == 1 || signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] == 2)     //если подсвечена область
                                            {
                                                signature_canvas_corners_with_computer.FIELD[i2][j2] =
                                                    signature_canvas_corners_with_computer.FIELD[i][j]         //перемещение фишки

                                                signature_canvas_corners_with_computer.History.add(mutableListOf(i, j, i2, j2))
                                                var data_from_memory = encode(signature_canvas_corners_with_computer.History)
                                                val editor = getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "corner_with_computer",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                signature_canvas_corners_with_computer.FIELD[i][j] = 0
                                                if (signature_canvas_corners_with_computer.Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                                                {
                                                    signature_canvas_corners_with_computer.Black_or_grey_chip = "grey"
                                                } else {
                                                    signature_canvas_corners_with_computer.Black_or_grey_chip = "black"
                                                }

                                                flag = 1
                                                break
                                            }

                                        }
                                    }
                                    if (flag == 1) {
                                        break
                                    }
                                }
                            }




                            if (flag == 0) {
                                for (i2 in 0..7) {
                                    for (j2 in 0..7) {

                                        if (i2 != i || j2 != j) {

                                            if (signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] == 1 || signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] == 2)     //если подсвечена область
                                            {
                                                signature_canvas_corners_with_computer.FIELD[i2][j2] =
                                                    signature_canvas_corners_with_computer.FIELD[i][j]         //перемещение фишки

                                                signature_canvas_corners_with_computer.History.add(mutableListOf(i, j, i2, j2))
                                                var data_from_memory = encode(signature_canvas_corners_with_computer.History)
                                                val editor = getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "corner_with_computer",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                signature_canvas_corners_with_computer.FIELD[i][j] = 0
                                                if (signature_canvas_corners_with_computer.Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                                                {
                                                    signature_canvas_corners_with_computer.Black_or_grey_chip = "grey"
                                                } else {
                                                    signature_canvas_corners_with_computer.Black_or_grey_chip = "black"
                                                }

                                                flag = 1
                                                break
                                            }

                                        }
                                    }
                                    if (flag == 1) {
                                        break
                                    }
                                }
                            }

                            for (i2 in 0..7) {
                                for (j2 in 0..7) {
                                    signature_canvas_corners_with_computer.Array_of_illumination[i2][j2] = 0   //обнуляем массива подсветки, чтобы он не оображался
                                }
                            }

                            if (flag == 1) {
                                break
                            }
                        }
                    }
                    if (flag == 1) {
                        break
                    }
                }

                signature_canvas_corners_with_computer.blockedOnTouch = false

                signature_canvas_corners_with_computer.invalidate()


            }, delayTime)
        }


        bottom_navigation_with_computer_template.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@ConersWithComputer
                        )
                    dialog_rules?.show("AngleGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_with_computer(
                            this@ConersWithComputer
                        )
                    dialog_parametrs?.showResult_with_computer(this, "AngleGame")
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, ConersWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if (signature_canvas_corners_with_computer.History.size > 0)
                    {
                        signature_canvas_corners_with_computer.History.removeLast()
                        var data_from_memory = encode(signature_canvas_corners_with_computer.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("corner_with_computer", data_from_memory)
                        editor.apply()
                        for(i in 0 until signature_canvas_corners_with_computer.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_corners_with_computer.FIELD[0].size)
                            {
                                signature_canvas_corners_with_computer.FIELD[i][j] = 0
                            }
                        }
                        signature_canvas_corners_with_computer.FIELD[0][5] = 1;signature_canvas_corners_with_computer.FIELD[1][5] = 1;signature_canvas_corners_with_computer.FIELD[2][5] = 1;
                        signature_canvas_corners_with_computer.FIELD[0][6] = 1;signature_canvas_corners_with_computer.FIELD[1][6] = 1;signature_canvas_corners_with_computer.FIELD[2][6] = 1;
                        signature_canvas_corners_with_computer.FIELD[0][7] = 1;signature_canvas_corners_with_computer.FIELD[1][7] = 1;signature_canvas_corners_with_computer.FIELD[2][7] = 1;


                        signature_canvas_corners_with_computer.FIELD[5][0] = 2;signature_canvas_corners_with_computer.FIELD[5][1] = 2;signature_canvas_corners_with_computer.FIELD[5][2] = 2;
                        signature_canvas_corners_with_computer.FIELD[6][0] = 2;signature_canvas_corners_with_computer.FIELD[6][1] = 2;signature_canvas_corners_with_computer.FIELD[6][2] = 2;
                        signature_canvas_corners_with_computer.FIELD[7][0] = 2;signature_canvas_corners_with_computer.FIELD[7][1] = 2;signature_canvas_corners_with_computer.FIELD[7][2] = 2;

                        signature_canvas_corners_with_computer.Black_or_grey_chip = "black"
                        for (i in signature_canvas_corners_with_computer.History) {
                            signature_canvas_corners_with_computer.FIELD[i[2]][i[3]] = signature_canvas_corners_with_computer.FIELD[i[0]][i[1]]
                            signature_canvas_corners_with_computer.FIELD[i[0]][i[1]] = 0
                            if(signature_canvas_corners_with_computer.Black_or_grey_chip == "black")
                            {
                                signature_canvas_corners_with_computer.Black_or_grey_chip =  "grey"
                            }
                            else
                            {
                                signature_canvas_corners_with_computer.Black_or_grey_chip =  "black"
                            }
                        }
                        signature_canvas_corners_with_computer.PHASE = true
                        for(i in  0 until signature_canvas_corners_with_computer.Array_of_illumination.size)
                        {
                            for(j in 0 until signature_canvas_corners_with_computer.Array_of_illumination[0].size)
                            {
                                signature_canvas_corners_with_computer.Array_of_illumination[i][j] =0
                            }
                        }
                        signature_canvas_corners_with_computer.invalidate()
                    }
                }

            }
            true
        }

        to_back_with_computer_template.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 3)
            startActivity(intent)
        }
    }
}

class CanvasView_corners_with_computer (context: Context, attrs: AttributeSet?) : View(context, attrs) {

    fun check_block_corners(X: Int,Y: Int) : Boolean
    {
        if(Black_or_grey_chip == "black")
        {
            if(FIELD[X][Y]==1)
            {                                //ПРОВЕРЯЕМ ПРОТИВОПОЛОЖНЫЙ УГОЛ, ЕСЛИ ОН ПУСТ а твой нет, ТО ЗАПРЕЩАЕМ ДВИГАТЬ ФИШКУ НЕ ИЗ СВОЕГО УГЛА
                if(FIELD[5][0] !=2 && FIELD[5][1] !=2 && FIELD[5][2] !=2 &&
                    FIELD[6][0] !=2&& FIELD[6][1] !=2&& FIELD[6][2] !=2 &&
                    FIELD[7][0] !=2 && FIELD[7][1]!=2  && FIELD[7][2] !=2 && (FIELD[0][5] ==1 || FIELD[1][5] ==1 || FIELD[2][5] ==1 ||
                            FIELD[0][6] ==1 || FIELD[1][6] ==1 || FIELD[2][6] ==1 ||
                            FIELD[0][7] ==1 || FIELD[1][7]==1 || FIELD[2][7] ==1)){
                    if(X>2)
                    {
                        return false
                    }
                    if(Y<5)
                    {
                        return false
                    }
                }
                return true
            }
            else
            {
                return false
            }
        }
        else
        {
            if(FIELD[X][Y]==2)
            {
                if(FIELD[0][5] !=1 && FIELD[1][5] !=1 && FIELD[2][5] !=1 &&
                    FIELD[0][6] !=1 && FIELD[1][6] !=1 && FIELD[2][6] !=1 &&
                    FIELD[0][7] !=1 && FIELD[1][7]!=1 && FIELD[2][7] !=1 &&(FIELD[5][0] ==2 || FIELD[5][1] ==2 || FIELD[5][2] ==2 ||
                            FIELD[6][0] ==2 || FIELD[6][1] ==2 || FIELD[6][2] ==2 ||
                            FIELD[7][0] ==2 || FIELD[7][1]==2  || FIELD[7][2] ==2)){
                    if(X<5)
                    {
                        return false
                    }
                    if(Y>2)
                    {
                        return false
                    }
                }
                return true
            }
            else
            {
                return false
            }
        }
    }
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

    fun chek_win(): Int{
        if(FIELD[0][5] == 2 && FIELD[1][5] == 2 && FIELD[2][5] == 2 &&
            FIELD[0][6] == 2 && FIELD[1][6] == 2 && FIELD[2][6] == 2 &&
            FIELD[0][7] == 2 && FIELD[1][7] == 2 && FIELD[2][7] == 2)
        {
            return 2
        }
        if(FIELD[5][0] == 1 && FIELD[5][1] ==1 && FIELD[5][2] ==1 &&
            FIELD[6][0] ==1 && FIELD[6][1] ==1 && FIELD[6][2] ==1 &&
            FIELD[7][0] ==1 && FIELD[7][1] ==1  && FIELD[7][2] ==1)
        {
            return 1
        }
        return 0

    }

    lateinit var activity: Activity

    lateinit var t1: TextView
    lateinit var t2: TextView


    var blockedOnTouch = false

    var History: MutableList<MutableList<Int>> = mutableListOf()
    var EXODUS : Int = 0
    var indent : Float = 0f
    var circlex : Float = 0f   //координаты нажатия
    var circley : Float = 0f
    var Black_or_grey_chip: String = "black"
    var paint : Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var FIELD = Array(8){IntArray(8)}     //для фишек
    var Array_of_illumination = Array(8) { IntArray(8) }  //для подсветки

    var PHASE: Boolean     //определяет выбрана ли клетка
    var motion_chip: Boolean      //надо ли делать перемещение

    var width : Float = 0f
    var height : Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line : Float = 0f         //полоска для рекламы
    var size_field_x : Int = 0
    var size_field_y  : Int = 0
    var step : Float = 0f
    var k : Float = 0f

    var lastX = -1
    var lastY = -1

    var exception: Boolean = false

    init{


        PHASE = true
        motion_chip = false

        Line_paint.setColor(Color.RED)          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        if(Design == "Egypt")
        {
            Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)
        }
        else if(Design == "Casino")
        {
            Line_paint.setColor(Color.WHITE)          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)
        }
        else if(Design == "Rome")
        {
            Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
            Line_paint.setStrokeWidth(5f)
        }


        // TODO нужно взять из DataBase (статистика ходов)
        for( i in 0..7) {
            for(j in 0 ..7) {
                FIELD[i][j] = 0  //не заполненный
            }
        }

        for (i in 0..7) {
            for (j in 0..7) {
                Array_of_illumination[i][j] = 0
            }
        }
        FIELD[0][5] = 1;FIELD[1][5] = 1;FIELD[2][5] = 1;
        FIELD[0][6] = 1;FIELD[1][6] = 1;FIELD[2][6] = 1;
        FIELD[0][7] = 1;FIELD[1][7] = 1;FIELD[2][7] = 1;


        FIELD[5][0] = 2;FIELD[5][1] = 2;FIELD[5][2] = 2;
        FIELD[6][0] = 2;FIELD[6][1] = 2;FIELD[6][2] = 2;
        FIELD[7][0] = 2;FIELD[7][1] = 2;FIELD[7][2] = 2;

    }




    var black_chip_normal : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_chip_normal);       //картинки фишек и подсветки
    var grey_chip_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_chip_normal);

    var black_chip_egypt:Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_chip_egypt);
    var grey_chip_egypt:Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_chip_egypt);

    var black_chip_casino:Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.red_chip_casino);
    var grey_chip_casino:Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_chip_casino);

    var black_chip_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.white_chip_rome);
    var grey_chip_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.black_chip_rome);

    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green
    );


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        indent = 20f
        width = getWidth().toFloat()
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        size_field_x  = 8
        size_field_y  = 8
        step = (width-2*indent)/size_field_x
        advertising_line =  (height - 8*step)/2         //полоска для рекламы
        k = height-(width-2*indent)-advertising_line



        if(Black_or_grey_chip == "black")
        {
            t1.text ="игрок 1 думает..."
            t2.text  = "игрок 2"
        }
        else
        {
            t1.text ="игрок 1"
            t2.text  = "игрок 2 думает..."
        }
        //TODO() take field from database



        for(i in 0 until size_field_x+1)          //вырисовка горизонтальных линий
        {
            canvas?.drawLine(indent,k,width-indent,k,Line_paint)
            k = k + step
        }
        k = indent
        for(i in 0 until size_field_y+2)         //вырисовка вертикальных линий
        {
            canvas?.drawLine(k,height-advertising_line-width+2*indent,k,height-advertising_line,Line_paint)
            k = k + step
        }


        var right_black_chip: Bitmap
        var right_grey_chip: Bitmap
        var right_illumination:Bitmap
        var right_green:Bitmap

        right_black_chip  = Bitmap.createScaledBitmap(black_chip_normal,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
        right_grey_chip  = Bitmap.createScaledBitmap(grey_chip_normal,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        right_green  = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);

        if(Design == "Egypt")
        {
            right_black_chip  = Bitmap.createScaledBitmap(black_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
            right_grey_chip  = Bitmap.createScaledBitmap(grey_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_green = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        }
        else if(Design == "Casino")
        {
            right_black_chip  = Bitmap.createScaledBitmap(black_chip_casino,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
            right_grey_chip  = Bitmap.createScaledBitmap(grey_chip_casino,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            right_green  = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        }
        else if (Design == "Rome")
        {
            right_black_chip = Bitmap.createScaledBitmap(black_chip_rome,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
            right_grey_chip = Bitmap.createScaledBitmap(grey_chip_rome,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        }


        for( i in 0..7) // расстановка фишек
        {
            for(j in 0..7) {
                if (FIELD[i][j] == 1)  //крестик
                {
                    canvas?.drawBitmap(right_black_chip, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
                if (FIELD[i][j] == 2)  //нолик
                {
                    canvas?.drawBitmap(right_grey_chip, translate_from_Array_to_Graphics_X(indent,i,step),
                        translate_from_Array_to_Graphics_Y(indent,j,height,size_field_y,step,advertising_line),paint)
                }
            }
        }
        for (i in 0..7) {             //расстановка подсветки
            for (j in 0..7) {
                if (Array_of_illumination[i][j] == 1 || Array_of_illumination[i][j] == 2) {
                    canvas?.drawBitmap(
                        right_green, translate_from_Array_to_Graphics_X(indent,i, step),
                        translate_from_Array_to_Graphics_Y(indent,
                            j,
                            height,
                            size_field_y,
                            step,
                            advertising_line
                        ), paint
                    )
                }
            }
        } //расстановка подсветки

        if(PHASE == false)
        {
            if(circley> height - advertising_line - width+2*indent && y < height - advertising_line){
                if( touch_refinement_X (indent,circlex,width,size_field_x)>0 && touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line)>=0f)
                {
                    canvas?.drawBitmap( right_illumination, touch_refinement_X(indent,circlex, width, size_field_x), touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line), paint)
                }
            }

        }

    }


    fun MakeLight(X: Int, Y:Int, comp: Int) {

        var s: Int = 0
        //   Log.d("DOPO",X.toString()+" "+ Y.toString() + " " + lastX.toString() + " " + lastY.toString())
        if ( ((FIELD[X][Y] == 1 && Black_or_grey_chip == "black") || (FIELD[X][Y] == 2 && Black_or_grey_chip == "grey"))  &&   check_block_corners(X,Y))       //если подсвечивается фишка
        {

            Array_of_illumination[X][Y] = -1
            if(X-1>=0)
            {
                if(FIELD[X-1][Y] == 0)
                {
                    Array_of_illumination[X-1][Y] = 2
                    s++
                }
            }
            if(X+1<8)
            {
                if(FIELD[X+1][Y] == 0)
                {
                    Array_of_illumination[X+1][Y] = 2
                    s++
                }
            }
            if(Y-1>=0)
            {
                if(FIELD[X][Y-1] == 0)
                {
                    Array_of_illumination[X][Y-1] = 2
                    s++
                }
            }
            if(Y+1<8)
            {
                if(FIELD[X][Y+1] == 0)
                {
                    Array_of_illumination[X][Y+1] = 2
                    s++
                }
            }
            for (t in 0..8) {
                for (i in 0..7) {
                    for (j in 0..7) {
                        if (Array_of_illumination[i][j] == -1 || Array_of_illumination[i][j] == 1)
                        {
                            if (i > 1) {
                                if (FIELD[i - 2][j] == 0 && FIELD[i - 1][j] != 0) {
                                    Array_of_illumination[i - 2][j] = 1
                                    s++

                                }
                            }
                            if (i < 6) {
                                if (FIELD[i + 2][j] == 0 && FIELD[i + 1][j] != 0) {
                                    Array_of_illumination[i + 2][j] = 1
                                    s++
                                }
                            }
                            if (j > 1) {
                                if (FIELD[i][j - 2] == 0 && FIELD[i][j - 1] != 0) {
                                    Array_of_illumination[i][j - 2] = 1
                                    s ++
                                }
                            }
                            if (j < 6) {
                                if (FIELD[i][j + 2] == 0 && FIELD[i][j + 1] != 0) {
                                    Array_of_illumination[i][j + 2] = 1
                                    s ++
                                }
                            }
                        }
                    }
                }
            }     //создание массива подсветки

        }
        else
        {
            for(i in 0..7)
            {
                for(j in 0..7)
                {
                    Array_of_illumination[i][j] = 0
                }
            }
        }

        if (comp == 0) {
            if (s == 0) {
                PHASE = true
            }
        }

    }



    var blocked : Boolean = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        advertising_line = (height - 8*step)/2
        if(chek_win()<=0)
        {
            blocked = false
        }
        if(chek_win() >0 && event!!.getAction() == MotionEvent.ACTION_UP && blocked)
        {
            blocked=!blocked
            return true
        }

        if (blockedOnTouch) {
            return true
        }
        var dialog: Show_Result_with_Computer? = null

        if(chek_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked) {
            if (chek_win() == AngleGameMode) {         //TODO more check
                dialog = Show_Result_with_Computer(activity)
                dialog?.showResult_with_Computer("Победа", "AngleGame", activity)
                return true
            }
            if (chek_win() != AngleGameMode) {
                dialog = Show_Result_with_Computer(activity)
                dialog?.showResult_with_Computer("Поражение", "AngleGame", activity)
                return true
            }
        }
        circlex = event!!.x
        circley = event!!.y
        var X: Int = touch_refinement_for_Array_X(indent,circlex, step)
        var Y: Int = touch_refinement_for_Array_Y(indent,circley, height, size_field_y, step, advertising_line)      //перевод последнего нажатия в координаты массива
        if (X in 0..7 && Y in 0..7 && touch_refinement_Y(indent,circley, height, size_field_y, step, advertising_line) > 0 && touch_refinement_X(indent,circlex,width,size_field_x)>0)     //постановка нового обЪекта, проверка что на поле
        {
            if ((X != lastX || Y != lastY ) || (exception == true) )   //если касание в новую область
            {
                if(exception == true)
                {
                    exception = false
                }
                PHASE = !PHASE   //смена фазы TODO добавить условие проверки
                if (PHASE == true)
                {
                    if (Array_of_illumination[X][Y] == 1 || Array_of_illumination[X][Y] == 2)     //если подсвечена область
                    {
                        FIELD[X][Y] = FIELD[lastX][lastY]         //перемещение фишки
                        if(SOUND)
                        {
                            mSound.play(1,1F,1F,1,0,1F)
                        }
                        if(VIBRATION)
                        {
                            vibratorService?.vibrate(70)
                        }
                        History.add(mutableListOf(lastX,lastY,X,Y))
                        var data_from_memory = encode(History)
                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("corner_with_computer", data_from_memory)
                        editor.apply()
                        FIELD[lastX][lastY] = 0
                        if(Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                        {
                            Black_or_grey_chip = "grey"
                        }
                        else
                        {
                            Black_or_grey_chip = "black"
                        }
                    }
                    else
                    {
                        exception = true     //описывает тот случай, когда мы не поставили на зеленую область а тыкнули в другую фишку, а потом решили походить фишкой в которую мы тыкнули
                    }
                    for (i in 0..7) {
                        for (j in 0..7) {
                            Array_of_illumination[i][j] = 0   //обнуляем массива подсветки, чтобы он не оображался
                        }
                    }

                }
                else
                {
                    MakeLight(X, Y, 0)
                }

                invalidate()
            }
            lastX = X
            lastY = Y
        }
        Log.w("DOPO",Black_or_grey_chip)


        if(chek_win()>0) {
            if (chek_win() == AngleGameMode) {         //TODO more check
                dialog = Show_Result_with_Computer(activity)
                dialog?.showResult_with_Computer("Победа", "AngleGame", activity)
                return true
            }
            if (chek_win() != AngleGameMode) {
                dialog = Show_Result_with_Computer(activity)
                dialog?.showResult_with_Computer("Поражение", "AngleGame", activity)
                return true
            }
        }


        if ((Black_or_grey_chip == "black" && AngleGameMode == 2) || (Black_or_grey_chip == "grey" && AngleGameMode == 1)) {
            blockedOnTouch = true

            val handler = android.os.Handler()
            handler.postDelayed({
                var flag = 0

                var list_x: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                var list_y: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7)
                list_x.shuffle()
                list_y.shuffle()

                for (i in list_x) {
                    for (j in list_y) {
                        if (FIELD[i][j] == 3 - AngleGameMode) {
                            MakeLight(i, j, 1)

                            if (AngleGameMode == 1) {
                                for (i2 in 0..i) {
                                    for (j2 in j..7) {

                                        if (i2 != i || j2 != j) {

                                            if (Array_of_illumination[i2][j2] == 1 || Array_of_illumination[i2][j2] == 2)     //если подсвечена область
                                            {
                                                FIELD[i2][j2] =
                                                    FIELD[i][j]         //перемещение фишки
                                                History.add(mutableListOf(i, j, i2, j2))
                                                var data_from_memory = encode(History)
                                                val editor = context.getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "corner_with_computer",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                FIELD[i][j] = 0
                                                if (Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                                                {
                                                    Black_or_grey_chip = "grey"
                                                } else {
                                                    Black_or_grey_chip = "black"
                                                }

                                                flag = 1
                                                break
                                            }

                                        }
                                    }
                                    if (flag == 1) {
                                        break
                                    }
                                }
                            }

                            if (AngleGameMode == 2 && flag == 0) {
                                for (i2 in i..7) {
                                    for (j2 in 0..j) {

                                        if (i2 != i || j2 != j) {

                                            if (Array_of_illumination[i2][j2] == 1 || Array_of_illumination[i2][j2] == 2)     //если подсвечена область
                                            {
                                                FIELD[i2][j2] =
                                                    FIELD[i][j]         //перемещение фишки

                                                History.add(mutableListOf(i, j, i2, j2))
                                                var data_from_memory = encode(History)
                                                val editor = context.getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "corner_with_computer",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                FIELD[i][j] = 0
                                                if (Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                                                {
                                                    Black_or_grey_chip = "grey"
                                                } else {
                                                    Black_or_grey_chip = "black"
                                                }

                                                flag = 1
                                                break
                                            }

                                        }
                                    }
                                    if (flag == 1) {
                                        break
                                    }
                                }
                            }


                            if (flag == 0) {
                                for (i2 in 0..7) {
                                    for (j2 in 0..7) {

                                        if (i2 != i || j2 != j) {

                                            if (Array_of_illumination[i2][j2] == 1 || Array_of_illumination[i2][j2] == 2)     //если подсвечена область
                                            {
                                                FIELD[i2][j2] =
                                                    FIELD[i][j]         //перемещение фишки

                                                History.add(mutableListOf(i, j, i2, j2))
                                                var data_from_memory = encode(History)
                                                val editor = context.getSharedPreferences(
                                                    "UserData",
                                                    Context.MODE_PRIVATE
                                                ).edit()
                                                editor.putString(
                                                    "corner_with_computer",
                                                    data_from_memory
                                                )
                                                editor.apply()
                                                FIELD[i][j] = 0
                                                if (Black_or_grey_chip == "black")          //смена игроков, чтобы нельзя было сделать ходы подряд одному игроку
                                                {
                                                    Black_or_grey_chip = "grey"
                                                } else {
                                                    Black_or_grey_chip = "black"
                                                }

                                                flag = 1
                                                break
                                            }

                                        }
                                    }
                                    if (flag == 1) {
                                        break
                                    }
                                }
                            }

                            for (i2 in 0..7) {
                                for (j2 in 0..7) {
                                    Array_of_illumination[i2][j2] = 0   //обнуляем массива подсветки, чтобы он не оображался
                                }
                            }

                            if (flag == 1) {
                                break
                            }
                        }
                    }
                    if (flag == 1) {
                        break
                    }
                }

                blockedOnTouch = false

                invalidate()


            }, delayTime)
        }


        if(chek_win()>0) {
            if (chek_win() == AngleGameMode) {         //TODO more check
                dialog = Show_Result_with_Computer(activity)
                dialog?.showResult_with_Computer("Победа", "AngleGame", activity)
                return true
            }
            if (chek_win() != AngleGameMode) {
                dialog = Show_Result_with_Computer(activity)
                dialog?.showResult_with_Computer("Поражение", "AngleGame", activity)
                return true
            }
        }

        return true
    }

}