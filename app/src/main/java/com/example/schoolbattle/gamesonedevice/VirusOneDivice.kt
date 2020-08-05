package com.example.schoolbattle.gamesonedevice

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_one_device_games_template.*


class VirusOneDivice : AppCompatActivity() {

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


    private var dialog_parametrs: Show_parametr_one_divice_one_Device? = null
    private var dialog_rules: Show_rules? = null
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mInterstitialAd_in_offline_games.loadAd(AdRequest.Builder().build())

        Log.d("VISIT","121212121")
        CONTEXT = this

        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        setContentView(R.layout.activity_one_device_games_template)
        signature_canvas_virus_one_device.visibility = View.VISIBLE
        signature_canvas_virus_one_device.activity = this

        if(Design == "Egypt" ) {
            name_player1_one_divice.setTextColor(Color.BLACK)
            name_player2_one_divice.setTextColor(Color.BLACK)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.player1_egypt);
            button_player_2_one_divice.setBackgroundResource(R.drawable.player2_egypt);
            player_1_icon_one_divice.setBackgroundResource(R.drawable.virus1_egypt);
            player_2_icon_one_divice.setBackgroundResource(R.drawable.virus2_egypt);
            label_one_device.setBackgroundResource(R.drawable.background_egypt);
            bottom_navigation_one_divice.setBackgroundColor(rgb(224,164,103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0,0,0,0))
            toolbar2_one_divice.setBackgroundColor(argb(0,0,0,0))
        }
        else if(Design == "Casino" ) {
            name_player1_one_divice.setTextColor(Color.YELLOW)
            name_player2_one_divice.setTextColor(Color.YELLOW)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.tower1_casino);
            button_player_2_one_divice.setBackgroundResource(R.drawable.tower2_casino);
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_casino);
            bottom_navigation_one_divice.setBackgroundColor(argb(0,224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Rome" ) {
            name_player1_one_divice.setTextColor(rgb(193,150,63))
            name_player2_one_divice.setTextColor(rgb(193,150,63))
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.tower1_rome);
            button_player_2_one_divice.setBackgroundResource(R.drawable.tower2_rome);
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_rome);
            bottom_navigation_one_divice.setBackgroundColor(argb(0,224, 164, 103))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Gothic" ) {
            name_player1_one_divice.setTextColor(Color.WHITE)
            name_player2_one_divice.setTextColor(Color.WHITE)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.tower1_gothic);
            button_player_2_one_divice.setBackgroundResource(R.drawable.tower2_gothic);
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_gothic);
            bottom_navigation_one_divice.setBackgroundColor(argb(0,0,0,0))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }
        else if(Design == "Japan" ) {
            name_player1_one_divice.setTextColor(Color.BLACK)
            name_player2_one_divice.setTextColor(Color.BLACK)
            name_player1_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            name_player2_one_divice.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            name_player2_one_divice.setTextSize(20f)
            name_player1_one_divice.setTextSize(20f)
            button_player_1_one_divice.setBackgroundResource(R.drawable.tower1_japan);
            button_player_2_one_divice.setBackgroundResource(R.drawable.tower2_japan);
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            toolbar2_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
            label_one_device.setBackgroundResource(R.drawable.background_japan);
            bottom_navigation_one_divice.setBackgroundColor(argb(0,0,0,0))
            to_back_one_divice.setBackgroundResource(R.drawable.arrow_back)
            toolbar_one_divice.setBackgroundColor(argb(0, 0, 0, 0))
        }

        val usedToClear = intent.getStringExtra("usedToClear") // тип игры
        if (usedToClear == "clear") {
            val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("virus_one_divice", "")
            editor.apply()
        }
        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        signature_canvas_virus_one_device.History = decode(prefs.getString("virus_one_divice", "").toString())

        for (i in 0 until signature_canvas_virus_one_device.FIELD.size) {
            for (j in 0 until signature_canvas_virus_one_device.FIELD[0].size) {
                signature_canvas_virus_one_device.FIELD[i][j] = 0
            }
        }
        signature_canvas_virus_one_device.COUNTER_BLUE =  0
        signature_canvas_virus_one_device.COUNTER_RED = 0
        signature_canvas_virus_one_device.red_or_blue = 0
        for(i in signature_canvas_virus_one_device.History)
        {
            signature_canvas_virus_one_device.FIELD[i.first][i.second] = i.third
            if(i.third == 1)
            {
                signature_canvas_virus_one_device.COUNTER_RED++
            }
            if(i.third == 2)
            {
                signature_canvas_virus_one_device.COUNTER_BLUE++
            }
            signature_canvas_virus_one_device.red_or_blue = (signature_canvas_virus_one_device.red_or_blue+1)%6
        }




        bottom_navigation_one_divice.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{
                    dialog_rules =
                        Show_rules(
                            this@VirusOneDivice
                        )
                    dialog_rules?.show("VirusGame")
                }
                R.id.page_2 ->{
                    dialog_parametrs =
                        Show_parametr_one_divice_one_Device(
                            this@VirusOneDivice
                        )
                    dialog_parametrs?.showResult_one_device()
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, VirusOneDivice::class.java).apply {
                        putExtra("usedToClear", "clear")}
                    startActivity(intent)
                }
                R.id.page_4 ->{
                    if(signature_canvas_virus_one_device.History.size>0)
                    {
                        signature_canvas_virus_one_device.History.removeLast()
                        var data_from_memory = encode(signature_canvas_virus_one_device.History)
                        val editor = getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                        editor.putString("virus_one_divice", data_from_memory)
                        editor.apply()
                        signature_canvas_virus_one_device.red_or_blue =0
                        signature_canvas_virus_one_device.COUNTER_RED = 0
                        signature_canvas_virus_one_device.COUNTER_BLUE = 0
                        for(i in 0 until signature_canvas_virus_one_device.FIELD.size)
                        {
                            for(j in 0 until signature_canvas_virus_one_device.FIELD[0].size)
                            {
                                signature_canvas_virus_one_device.FIELD[i][j] = 0
                            }
                        }
                        for(i in signature_canvas_virus_one_device.History)
                        {
                            signature_canvas_virus_one_device.FIELD[i.first][i.second] = i.third
                            signature_canvas_virus_one_device.red_or_blue = (signature_canvas_virus_one_device.red_or_blue+1)%6
                            if(2- i.third%2 == 1)
                            {
                                signature_canvas_virus_one_device.COUNTER_RED++
                            }
                            else
                            {
                                signature_canvas_virus_one_device.COUNTER_BLUE++
                            }
                        }
                        signature_canvas_virus_one_device.invalidate()
                    }
                }

            }
            true
        }
        to_back_one_divice.setOnClickListener {
            this.finish()
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 2)
            if(mInterstitialAd_in_offline_games.isLoaded)
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
    override fun onBackPressed()
    {
        super.onBackPressed()
        var intent = Intent(this, NewGameActivity::class.java)
        intent.putExtra("playType", 2)
        if(mInterstitialAd_in_offline_games.isLoaded)
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

class CanvasView_VIRUS (context: Context, attrs: AttributeSet?) : View(context, attrs) {

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

    fun correction_touch(x: Float, y: Float): Boolean // если нажали примерно туда
    {
        if ((circlex - x) * (circlex - x) + (circley - y) * (circley - y) < (step / 2f) * (step / 2f)) {
            return true
        }
        return false
    }

    fun is_anround(x: Int,y: Int,z: Int): Boolean
    {
        var HELP = Array(10) { IntArray(10) }
        for(i in 0 until HELP.size)
        {
            for(j in 0 until HELP[0].size)
            {
                HELP[i][j] = 0
                if(FIELD[i][j]<3)
                {
                    HELP[i][j] = FIELD[i][j]
                }
            }
        }
       for(t in 0 until  50)
       {
           for(i in 0 until FIELD.size)
           {
               for(j in 0 until FIELD[0].size)
               {
                   if(HELP[i][j] == z)
                   {
                        if(i>0)
                        {
                            if(FIELD[i-1][j] == z+2)
                            {
                                HELP[i-1][j] = z
                            }
                        }
                       if(i<9)
                       {
                           if(FIELD[i+1][j] == z+2)
                           {
                               HELP[i+1][j] = z
                           }
                       }
                       if(j>0)
                       {
                           if(FIELD[i][j-1] == z+2)
                           {
                               HELP[i][j-1] = z
                           }
                       }
                       if(j<9)
                       {
                           if(FIELD[i][j+1] == z+2)
                           {
                               HELP[i][j+1] = z
                           }
                       }
                       if(i>0 && j>0)
                       {
                           if(FIELD[i-1][j-1] == z+2)
                           {
                               HELP[i-1][j-1] = z
                           }
                       }
                       if(i>0 &&  j<9)
                       {
                           if(FIELD[i-1][j+1] == z+2)
                           {
                               HELP[i-1][j+1] = z
                           }
                       }
                       if(i<9 && j>0)
                       {
                           if(FIELD[i+1][j-1] == z+2)
                           {
                               HELP[i+1][j-1] = z
                           }
                       }
                       if(i<9 && j<9)
                       {
                           if(FIELD[i+1][j+1] == z+2)
                           {
                               HELP[i+1][j+1] = z
                           }
                       }
                   }
               }
           }
       }

      if(x>0)
      {
          if(HELP[x-1][y] == z)
          {
              return true
          }
      }
     if(x<9)
      {
         if(HELP[x+1][y] == z)
         {
             return true
         }
      }
        if(y>0)
        {
            if(HELP[x][y-1] == z)
            {
                return true
            }
        }
        if(y<9)
        {
            if(HELP[x][y+1] == z)
            {
                return true
            }
        }

        if(x>0 && y>0)
        {
            if(HELP[x-1][y-1] == z)
            {
                return true
            }
        }
        if(x>0 && y<9)
        {
            if(HELP[x-1][y+1] == z)
            {
                return true
            }
        }
        if(x<9 && y>0)
        {
            if(HELP[x+1][y-1] == z)
            {
                return true
            }
        }
        if(x<9 && y<9)
        {
            if(HELP[x+1][y+1] == z)
            {
                return true
            }
        }
        if(HELP[x][y] == z )
        {
            return true
        }

        return false
    }


    fun check_win(): Int
    {
        Log.w("VISIT","121212121")
        var cnt1 : Int = 0
        var cnt2: Int = 0
        for(i in 0..9)
        {
            for(j in 0..9)
            {
                if(FIELD[i][j]==1)
                {
                    cnt1++
                }
                if(FIELD[i][j]==2)
                {
                    cnt2++
                }
            }
        }
        if(cnt2  == 0 && COUNTER_BLUE!=0 &&  COUNTER_RED!=0)
        {
            return 1
        }
        if(cnt1 == 0 && COUNTER_BLUE!=0 &&  COUNTER_RED!=0)
        {
            return 2
        }
        var VISIT = Array(10) { IntArray(10) }

        fun DFS(x: Int,y:Int,z: Int): Int{
            VISIT[x][y] = 1
            if(x>0)
            {
                if(VISIT[x-1][y]==0 && FIELD[x-1][y]!=z+2)
                {
                    DFS(x-1,y,z)
                }
            }
            if(y>0)
            {
                if(VISIT[x][y-1]==0 && FIELD[x][y-1]!=z+2)
                {
                    DFS(x,y-1,z)
                }
            }
            if(x<9)
            {
                if(VISIT[x+1][y]==0 && FIELD[x+1][y]!=z+2)
                {
                    DFS(x+1,y,z)
                }
            }
            if(y<9)
            {
                if(VISIT[x][y+1]==0 && FIELD[x][y+1]!=z+2)
                {
                    DFS(x,y+1,z)
                }
            }
            if(x>0 && y>0)
            {
                if(VISIT[x-1][y-1]==0 && FIELD[x-1][y-1]!=z+2)
                {
                    DFS(x-1,y-1,z)
                }
            }
            if(x>0 && y<9)
            {
                if(VISIT[x-1][y+1]==0 && FIELD[x-1][y+1]!=z+2)
                {
                    DFS(x-1,y+1,z)
                }
            }
            if(x<9 && y>0)
            {
                if(VISIT[x+1][y-1]==0 && FIELD[x+1][y-1]!=z+2)
                {
                    DFS(x+1,y-1,z)
                }
            }
            if(x<9 && y<9)
            {
                if(VISIT[x+1][y+1]==0 && FIELD[x+1][y+1]!=z+2)
                {
                    DFS(x+1,y+1,z)
                }
            }
            return 1
        }

        var FLAG_BLACK = false
        var FLAG_WHITE = false

        var flag = false
        var X: Int = 0
        var Y:  Int = 0
        for(i in 0 until 10)
        {
            for(j in 0 until 10)
            {
                if(FIELD[i][j]==1)
                {
                    X  =  i
                    Y = j
                    flag = true
                }
            }
        }
        for(i in 0 until  VISIT.size)
        {
            for(j in 0 until VISIT[0].size)
            {
                VISIT[i][j] = 0
            }
        }
        Log.w("VISIT",VISIT.toString())
        if(flag)
        {
            DFS(X,Y,1)
            for(i in 0..9)
            {
                for(j in 0..9)
                {
                    if(FIELD[i][j]==1 && VISIT[i][j]==0)
                    {
                        FLAG_BLACK = true
                    }
                }
            }
        }
        flag = false
        X = 0
        Y = 0
        for(i in 0 until 10)
        {
            for(j in 0 until 10)
            {
                if(FIELD[i][j]==2)
                {
                    X  =  i
                    Y = j
                    flag = true
                }
            }
        }
        for(i in 0 until  VISIT.size)
        {
            for(j in 0 until VISIT[0].size)
            {
                VISIT[i][j] = 0
            }
        }
        if(flag)
        {
            DFS(X,Y,2)
            Log.w("VISIT",VISIT.toString())
            for(i in 0..9)
            {
                for(j in 0..9)
                {
                    if(FIELD[i][j]==2 && VISIT[i][j]==0)
                    {
                        FLAG_WHITE = true
                    }
                }
            }
        }

        if(FLAG_BLACK && FLAG_WHITE)
        {
            return 3
        }

        var k : Int = 0
        if(red_or_blue < 3)
        {
            k=1
        }
        else{
            k=2
        }
        flag = true
        for(i in  0..9)
        {
            for(j in 0..9)
            {
                if(FIELD[i][j]<3 && FIELD[i][j]!=k && is_anround(i,j,k))
                {
                    flag = false
                }
            }
        }
        if(flag == true && COUNTER_RED>0 && COUNTER_BLUE>0 && k == 1)
        {
            return 2
        }
        if(flag == true && COUNTER_RED>0 && COUNTER_BLUE>0 && k == 2)
        {
            return 1
        }
        return 0
    }



    lateinit var activity: Activity

    var History: MutableList<Triple<Int,Int,Int>> = mutableListOf()

    var red_or_blue: Int            // всего 6 фаз
    var circlex: Float = 0f   //координаты нажатия
    var circley: Float = 0f
    var indent: Float = 0f      //оступ


    var paint : Paint  = Paint()
    var paint_circle: Paint = Paint()          //ресурсы для рисования
    var Line_paint: Paint = Paint()
    var paint_rib_1: Paint = Paint()
    var paint_rib_2: Paint = Paint()


    var FIELD = Array(10) { IntArray(10) }     //для фишеК

    var COUNTER_RED : Int = 0
    var COUNTER_BLUE: Int = 0

    var width: Float = 0f
    var height: Float = 0f            //ширина и высота экрана (от ширины в основном все зависит)
    var advertising_line: Float = 0f         //полоска для рекламы
    var size_field_x: Int = 0
    var size_field_y: Int = 0
    var step: Float = 0f


    init {

        red_or_blue = 0
        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(5f)

        paint_circle.setColor(Color.rgb(217, 217, 217))     //цвета для точек

        paint_rib_1.setColor(Color.RED)          //цвета для ребер  и их ширина
        paint_rib_1.setStrokeWidth(5f)
        paint_rib_2.setColor(Color.BLUE)
        paint_rib_2.setStrokeWidth(5f)

        if (Design == "Casino"){
            Line_paint.setColor(Color.WHITE)          //ресур для линий (ширина и цвет)

        }
        if (Design == "Egypt"){
            Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)

        }
        if (Design == "Rome"){
            Line_paint.setColor(rgb(193,150,63))          //ресур для линий (ширина и цвет)
        }
        if (Design == "Gothic"){
            Line_paint.setColor(rgb(100,100,100))          //ресур для линий (ширина и цвет)
        }
        if (Design == "Japan"){
            Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
        }


        for (i in 0 until FIELD.size) {
            for (j in 0 until FIELD[i].size) {
                FIELD[i][j] = 0
            }
        }

    }

    var virus1_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus1_normal);
    var virus2_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus2_normal);
    var tower1_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1_normal);
    var tower2_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2_normal);

    var virus1_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus1_egypt);
    var virus2_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus2_egypt);
    var tower1_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1_egypt);
    var tower2_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2_egypt);

    var virus1_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus1_casino);
    var virus2_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus2_casino);
    var tower1_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1_casino);
    var tower2_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2_casino);

    var virus1_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus1_rome);
    var virus2_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus2_rome);
    var tower1_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1_rome);
    var tower2_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2_rome);

    var virus1_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box1_gothic);
    var virus2_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box2_gothic);
    var tower1_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1_gothic);
    var tower2_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2_gothic);

    var virus1_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus1_japan);
    var virus2_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.virus2_japan);
    var tower1_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower1_japan);
    var tower2_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tower2_japan);


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        
        size_field_x = 10
        size_field_y = 10
        indent = 0f
        //(getWidth().toFloat() / (size_field_x.toFloat() + 1f)) / 2f //оступ, чтобы можно было тыкнуть в границу (половина клетки)
        width = getWidth().toFloat() - 2 * indent
        height = getHeight().toFloat()            //ширина и высота экрана (от ширины в основном все зависит)
        advertising_line = (height - width)/2           //полоска для рекламы
        step = width / size_field_x

        var right_virus1: Bitmap
        var right_virus2: Bitmap
        var right_tower1: Bitmap
        var right_tower2: Bitmap

        right_virus1 = Bitmap.createScaledBitmap(virus1_normal,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        right_virus2 = Bitmap.createScaledBitmap(virus2_normal,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        right_tower1 = Bitmap.createScaledBitmap(tower1_normal,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        right_tower2 = Bitmap.createScaledBitmap(tower2_normal,width.toInt()/size_field_x, width.toInt()/size_field_y, true);

        if(Design == "Egypt")
        {
            right_virus1 = Bitmap.createScaledBitmap(virus1_egypt,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_virus2 = Bitmap.createScaledBitmap(virus2_egypt,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower1 = Bitmap.createScaledBitmap(tower1_egypt,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower2 = Bitmap.createScaledBitmap(tower2_egypt,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        }
        else if (Design == "Casino")
        {
            right_virus1 = Bitmap.createScaledBitmap(virus1_casino,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_virus2 = Bitmap.createScaledBitmap(virus2_casino,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower1 = Bitmap.createScaledBitmap(tower1_casino,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower2 = Bitmap.createScaledBitmap(tower2_casino,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        }
        else if (Design == "Rome")
        {
            right_virus1 = Bitmap.createScaledBitmap(virus1_rome,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_virus2 = Bitmap.createScaledBitmap(virus2_rome,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower1 = Bitmap.createScaledBitmap(tower1_rome,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower2 = Bitmap.createScaledBitmap(tower2_rome,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        }
        else if (Design == "Gothic")
        {
            right_virus1 = Bitmap.createScaledBitmap(virus1_gothic,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_virus2 = Bitmap.createScaledBitmap(virus2_gothic,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower1 = Bitmap.createScaledBitmap(tower1_gothic,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower2 = Bitmap.createScaledBitmap(tower2_gothic,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        }
        else if (Design == "Japan")
        {
            right_virus1 = Bitmap.createScaledBitmap(virus1_japan,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_virus2 = Bitmap.createScaledBitmap(virus2_japan,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower1 = Bitmap.createScaledBitmap(tower1_japan,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
            right_tower2 = Bitmap.createScaledBitmap(tower2_japan,width.toInt()/size_field_x, width.toInt()/size_field_y, true);
        }

        var k: Float = height - width  - advertising_line

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

        var X1 : Float = indent
        var Y1: Float = height - advertising_line -  width
        for(i in 0 until size_field_x)
        {
            for(j in 0 until size_field_y)
            {
                if(FIELD[i][j] == 1)
                {
                    canvas?.drawBitmap(right_virus1,X1,Y1,paint)
                }
                if(FIELD[i][j] == 2)
                {
                    canvas?.drawBitmap(right_virus2,X1,Y1,paint)
                }
                if(FIELD[i][j] == 3)
                {
                    canvas?.drawBitmap(right_tower1,X1,Y1,paint)
                }
                if(FIELD[i][j] == 4)
                {
                    canvas?.drawBitmap(right_tower2,X1,Y1,paint)
                }

                Y1 += step
            }
            X1 += step
            Y1 = height - advertising_line -  width
        }


    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        circlex = event!!.x
        circley = event!!.y



        if(event!!.getAction()  == MotionEvent.ACTION_UP) {


            var X1: Float = indent + step / 2
            var Y1: Float = height - advertising_line - width + step / 2

            for (i in 0 until size_field_x) {
                for (j in 0 until size_field_y) {
                    if (correction_touch(X1, Y1)) {


                        if (FIELD[i][j] == 0) {
                            if (red_or_blue < 3) {
                                if (COUNTER_RED == 0) {
                                    if ((i == 0 && j == 0) || (i == 9 && j == 0) || (i == 9 && j == 9) || (i == 0 && j == 9)) {
                                        FIELD[i][j] = 1
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_RED++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("virus_one_divice", data_from_memory)
                                        editor.apply()
                                        if(SOUND)
                                        {
                                            mSound.play(1,1F,1F,1,0,1F)
                                        }
                                        if(VIBRATION)
                                        {
                                            vibratorService?.vibrate(70)
                                        }
                                        invalidate()
                                    }
                                } else {
                                    if (is_anround(i, j, 1)) {
                                        FIELD[i][j] = 1
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_RED++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("virus_one_divice", data_from_memory)
                                        editor.apply()
                                        if(SOUND)
                                        {
                                            mSound.play(1,1F,1F,1,0,1F)
                                        }
                                        if(VIBRATION)
                                        {
                                            vibratorService?.vibrate(70)
                                        }
                                        invalidate()
                                    }
                                }

                            } else {
                                if (COUNTER_BLUE == 0) {
                                    if ((i == 0 && j == 0) || (i == 9 && j == 0) || (i == 9 && j == 9) || (i == 0 && j == 9)) {
                                        FIELD[i][j] = 2
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_BLUE++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("virus_one_divice", data_from_memory)
                                        editor.apply()
                                        if(SOUND)
                                        {
                                            mSound.play(1,1F,1F,1,0,1F)
                                        }
                                        if(VIBRATION)
                                        {
                                            vibratorService?.vibrate(70)
                                        }
                                        invalidate()
                                    }
                                } else {
                                    if (is_anround(i, j, 2)) {
                                        FIELD[i][j] = 2
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_BLUE++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("virus_one_divice", data_from_memory)
                                        editor.apply()
                                        if(SOUND)
                                        {
                                            mSound.play(1,1F,1F,1,0,1F)
                                        }
                                        if(VIBRATION)
                                        {
                                            vibratorService?.vibrate(70)
                                        }
                                        invalidate()
                                    }
                                }
                            }
                        } else {
                            if (FIELD[i][j] == 1 && red_or_blue > 2) {
                                if (is_anround(i,j,2)) {
                                    FIELD[i][j] = 4
                                    History.add(Triple(i,j,FIELD[i][j]))
                                    COUNTER_BLUE++
                                    red_or_blue = (red_or_blue + 1) % 6
                                    var data_from_memory = encode(History)
                                    val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                    editor.putString("virus_one_divice", data_from_memory)
                                    editor.apply()
                                    if(SOUND)
                                    {
                                        mSound.play(1,1F,1F,1,0,1F)
                                    }
                                    if(VIBRATION)
                                    {
                                        vibratorService?.vibrate(70)
                                    }
                                    invalidate()
                                }
                            } else {
                                if (FIELD[i][j] == 2 && red_or_blue < 3) {
                                    if (is_anround(i,j,1)) {
                                        FIELD[i][j] = 3
                                        History.add(Triple(i,j,FIELD[i][j]))
                                        COUNTER_RED++
                                        red_or_blue = (red_or_blue + 1) % 6
                                        var data_from_memory = encode(History)
                                        val editor = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                                        editor.putString("virus_one_divice", data_from_memory)
                                        editor.apply()
                                        if(SOUND)
                                        {
                                            mSound.play(1,1F,1F,1,0,1F)
                                        }
                                        if(VIBRATION)
                                        {
                                            vibratorService?.vibrate(70)
                                        }
                                        invalidate()
                                    }
                                }
                            }
                        }


                    }
                    Y1 += step
                }
                X1 += step
                Y1 = height - advertising_line - width + step / 2
            }
            var ch = check_win()
            if(ch>0)
            {
                var dialog: Show_Result_one_Device? = null
                dialog = Show_Result_one_Device(activity)
                if(ch==1)
                {
                    dialog?.showResult_one_device("Игрок 1 победил","VirusGame",activity)
                    return true
                }
                if(ch==2)
                {
                    dialog?.showResult_one_device("Игрок 2 победил","VirusGame",activity)
                    return true
                }
                if(ch==3)
                {
                    dialog?.showResult_one_device("НИЧЬЯ","VirusGame",activity)
                    return true
                }
            }
        }

        return true
    }


}
