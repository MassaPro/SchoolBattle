/*
package com.sga.schoolbattle.gamesonline

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.sga.schoolbattle.*
import com.sga.schoolbattle.engine.BlitzGameEngine
import com.sga.schoolbattle.engine.LongGameEngine
import com.sga.schoolbattle.engine.StupidGame
import kotlinx.android.synthetic.main.activity_one_device_games_template.*
import kotlinx.android.synthetic.main.activity_online_games_temlate.*
import kotlinx.android.synthetic.main.activity_x_o_game.*
import kotlinx.android.synthetic.main.activity_x_o_game.bottom_navigation_xog_online
import kotlinx.android.synthetic.main.activity_x_o_game.button_player_1_online_xog
import kotlinx.android.synthetic.main.activity_x_o_game.button_player_2_online_xog
import kotlinx.android.synthetic.main.activity_x_o_game.signature_canvas
import kotlinx.android.synthetic.main.activity_x_o_game.toolbar2_xog_online
import kotlinx.android.synthetic.main.activity_x_o_game.toolbar_xog_online
import kotlinx.android.synthetic.main.activity_x_o_game.timer2_xog_online
import kotlinx.android.synthetic.main.activity_x_o_game.timer_xog_online
import java.util.*

class ConersGameActivity : AppCompatActivity() {
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
        isRun = true
        CONTEXT = this
    }


    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        var helpend_var = 0
        mSound.load(this, R.raw.xlup, 1);
        vibratorService = getSystemService(VIBRATOR_SERVICE) as Vibrator

        setContentView(R.layout.activity_online_games_temlate)
        signature_canvas.visibility = View.VISIBLE
        PICTURE_AVATAR[AVATAR]?.let { your_avatar_in_game.setImageResource(it) }
        PICTURE_AVATAR[AVATAR]?.let { avatar_of_protivnic.setImageResource(it) } //TODO заменить это на значения его аватарки

        bottom_navigation_xog_online.itemIconTintList = generateColorStateList()
        bottom_navigation_xog_online.itemTextColor = generateColorStateList()

        button_player_1_online_xog.textSize = 20f
        button_player_2_online_xog.textSize = 20f
        //                    Toast.makeText(applicationContext,"${signature_canvas.FIELD[checkList[1]][checkList[2]]}", Toast.LENGTH_LONG).show()

        when (Design) {
            "Normal" -> {

            }
            "Egypt" -> {
                label_online.setBackgroundResource(R.drawable.background_egypt)
                toolbar_xog_online.setBackgroundColor(Color.TRANSPARENT)
                toolbar2_xog_online.setBackgroundColor(Color.TRANSPARENT)
                button_player_1_online_xog.setTextColor(Color.BLACK)
                button_player_2_online_xog.setTextColor(Color.BLACK)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                bottom_navigation_xog_online.setBackgroundColor(Color.rgb(255, 230, 163))
            }
            "Casino" -> {
                label_online.setBackgroundResource(R.drawable.background2_casino)
                toolbar_xog_online.setBackgroundColor(Color.TRANSPARENT)
                toolbar2_xog_online.setBackgroundColor(Color.TRANSPARENT)
                button_player_1_online_xog.setTextColor(Color.YELLOW)
                button_player_2_online_xog.setTextColor(Color.YELLOW)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                bottom_navigation_xog_online.setBackgroundResource(R.drawable.bottom_navigation_casino)
            }
            "Rome" -> {
                label_online.setBackgroundResource(R.drawable.background_rome)
                toolbar_xog_online.setBackgroundColor(Color.TRANSPARENT)
                toolbar2_xog_online.setBackgroundColor(Color.TRANSPARENT)
                button_player_1_online_xog.setTextColor(Color.rgb(224, 164, 103))
                button_player_2_online_xog.setTextColor(Color.rgb(224, 164, 103))
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                bottom_navigation_xog_online.setBackgroundResource(R.drawable.bottom_navigation_rome)
            }
            "Gothic" -> {
                label_online.setBackgroundResource(R.drawable.background_gothic)
                toolbar_xog_online.setBackgroundColor(Color.TRANSPARENT)
                toolbar2_xog_online.setBackgroundColor(Color.TRANSPARENT)
                button_player_1_online_xog.setTextColor(Color.WHITE)
                button_player_2_online_xog.setTextColor(Color.WHITE)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                bottom_navigation_xog_online.setBackgroundColor(Color.BLACK)
                button_player_1_online_xog.textSize = 16.5f
                button_player_2_online_xog.textSize = 16.5f
            }
            "Japan" -> {
                label_online.setBackgroundResource(R.drawable.background_japan)
                toolbar_xog_online.setBackgroundColor(Color.TRANSPARENT)
                toolbar2_xog_online.setBackgroundColor(Color.TRANSPARENT)
                button_player_1_online_xog.setTextColor(Color.BLACK)
                button_player_2_online_xog.setTextColor(Color.BLACK)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                bottom_navigation_xog_online.setBackgroundColor(Color.WHITE)
            }
            "Noir" -> {
                label_online.setBackgroundResource(R.drawable.background_noir)
                toolbar_xog_online.setBackgroundColor(Color.TRANSPARENT)
                toolbar2_xog_online.setBackgroundColor(Color.TRANSPARENT)
                button_player_1_online_xog.setTextColor(Color.WHITE)
                button_player_2_online_xog.setTextColor(Color.WHITE)
                button_player_1_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                button_player_2_online_xog.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
            }
            //Emotions начало--------------------------------------------------------------------------------------------
            //Emotion конец-----------------------------------------------------------------------------------------------


            // gameData.child("FIELDD").child("result").onDisconnect().setValue(yourName)
            // gameData.onDisconnect().removeValue()
        }
        currentContext = this
        CONTEXT = this
        isRun = true

        if (StupidGame != Activity()) StupidGame.finish()
        if (NewGame != Activity()) NewGame.finish()
        yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()

        var type = intent.getStringExtra("type")
        if (type == null) type = ""
        val opponentsName_: String = intent?.getStringExtra("opponent").toString()
        for (i in opponentsName_) {
            if (i == ' ') break
            opponentsName += i
        }
        val yu = if (opponentsName < yourName) '1' else '0'
        val op = if (opponentsName < yourName) '0' else '1'
        gameData = if (intent.getStringExtra("key") != null) {
            myRef.child(type).child("AngleGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName + intent.getStringExtra("key")!!  else yourName + '_' + opponentsName + intent.getStringExtra("key")!!)
            )
        } else {
            myRef.child(type).child("AngleGame").child(
                (if (opponentsName < yourName)
                    opponentsName + '_' + yourName  else yourName + '_' + opponentsName)
            )
        }

        initMenuFunctions(this, bottom_navigation_xog_online, intent, yourName, opponentsName, gameData)

        signature_canvas.blocked = true
        signature_canvas.positionData = gameData
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
                override var activity: Activity = this@ConersGameActivity
                override var cnt = 0
                override var type = "AngleGame"
                override var isFinished = false
                override var userRating = RATING
                override var opponentRating = intent.getStringExtra("rating")!!.toInt()
            }
            Toast.makeText(this, engine?.opponentRating.toString(), Toast.LENGTH_LONG).show()
            button_player_1_online_xog.text = "$yourName (${engine?.userRating})"
            button_player_2_online_xog.text = "$opponentsName (${engine?.opponentRating})"
            engine?.init()
            signature_canvas.engine = engine
            signature_canvas.username = yourName
        } else {
            engineLong = object : LongGameEngine {
                override val userT = timer2_xog_online
                override val opponentT = timer_xog_online
                override val user = yourName
                override val opponent = opponentsName
                override var move = intent.getStringExtra("move") == "1"
                override var positionData = gameData
                override var activity: Activity = this@ConersGameActivity
                override var type = "AngleGame"
                override var key = intent.getStringExtra("key")
            }
            Toast.makeText(this, engineLong?.key.toString(), Toast.LENGTH_LONG).show()
            engineLong?.init()
        }
        var initialMove = intent.getStringExtra("move") == "1"
        signature_canvas.username = yourName
        signature_canvas.isFirstMove = intent.getStringExtra("move") == "1"
        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                var cnt = 0
                if (p0.childrenCount >= 2) {
                    engine?.changeMoveAndSyncTimer(p0)
                }

                fun checkForWin(): MutableList<Int> {
                    val field = signature_canvas.FIELD
                    val list_x = mutableListOf(1, 1, 0, -1)
                    val list_y = mutableListOf(0, 1, 1, 1)

                    val ans = mutableListOf(0)
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
                val checkList = checkForWin()
                Toast.makeText(this@XOGameActivity, engine?.move.toString(), Toast.LENGTH_LONG).show()
                if (p0.hasChild("winner") || checkList.size > 1 || (checkList.size == 1 && cnt == 42)) {
                    gameData.child("FIELD").child("result").onDisconnect().cancel()
                    engine?.stopTimer()
                    signature_canvas.blocked = true
                    var whoWins = 0
                    if (!p0.child("FIELD").hasChild("result")) {
//                    Toast.makeText(applicationContext,"${signature_canvas.FIELD[checkList[1]][checkList[2]]}", Toast.LENGTH_LONG).show()
                        if (checkList.size > 1) {
                            for (i2 in 0..8) {
                                if (i2 % 2 == 1) {
                                    whoWins =
                                        signature_canvas.FIELD[checkList.get(i2)][checkList.get(i2 + 1)]
                                    signature_canvas.invalidate()
                                }
                            }
                        }
                    }
                    var res: String
                    if (checkList.size == 1) {
                        res = "Ничья"
                    } else {
                        if (initialMove == (yourName < opponentsName_)) {
                            res = if (yu == '0') {
                                if (whoWins == 1) {
                                    "Победа"
                                } else {
                                    "Поражение"
                                }
                            } else {
                                if (whoWins == 2) {
                                    "Победа"
                                } else {
                                    "Поражение"
                                }
                            }
                        } else {
                            res = if (yu == '1') {
                                if (whoWins == 1) {
                                    "Победа"
                                } else {
                                    "Поражение"
                                }
                            } else {
                                if (whoWins == 2) {
                                    "Победа"
                                } else {
                                    "Поражение"
                                }
                            }
                        }
                    }
                    if (p0.hasChild("winner") && p0.child("winner").value.toString() == yourName) {
                        res = "Победа"
                    }
                    if (p0.hasChild("winner") && p0.child("winner").value.toString() == opponentsName) {
                        res = "Поражение"
                    }
                    engine?.finish(res, this@CornersGameActivity, isRun)
                    engineLong?.finish(res, this@CornersGameActivity, isRun)
                    gameData.removeEventListener(this)
                }
            }
        })
        if(Design == "Noir" ) {
            label_online.setBackgroundResource(R.drawable.background_noir);
        }


        DDD = Dialog(this)
        DDD.setContentView(R.layout.activity_game_over)
        adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forUnifiedNativeAd { unifiedNativeAd : UnifiedNativeAd ->
                // Show the ad.

                val adView = this.layoutInflater
                    .inflate(R.layout.natative_ads, null) as UnifiedNativeAdView
                populateUnifiedNativeAdView(unifiedNativeAd, adView)
                if (this.isDestroyed) {
                    unifiedNativeAd.destroy()
                    return@forUnifiedNativeAd
                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(errorCode: Int) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    // Methods in the NativeAdOptions.Builder class can be
                    // used here to specify individual options settings.
                    .build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

    }
}

class CanvasView_corners_one_device (context: Context, attrs: AttributeSet?) : View(context, attrs) {

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

    var line_who_do_move : Paint = Paint()

    init{


        PHASE = true
        motion_chip = false

        Line_paint.setColor(Color.rgb(217, 217, 217))          //ресур для линий (ширина и цвет)
        Line_paint.setStrokeWidth(7f)
        line_who_do_move.strokeWidth = 14f

        when (Design) {
            "Normal" ->{
                line_who_do_move.color = Color.GREEN
            }
            "Egypt" -> {
                Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED
            }
            "Casino" -> {
                Line_paint.setColor(Color.WHITE)          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.YELLOW
            }
            "Rome" -> {
                Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.BLACK
            }
            "Gothic" -> {
                Line_paint.setColor(Color.rgb(100,100,100))          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.WHITE
            }
            "Japan" -> {
                Line_paint.setColor(Color.BLACK)          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED
            }
            "Noir" -> {
                Line_paint.setColor(Color.rgb(100,100,100))          //ресур для линий (ширина и цвет)
                line_who_do_move.color = Color.RED
            }
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




    var black_chip_normal : Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_normal);       //картинки фишек и подсветки
    var grey_chip_normal: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_normal);

    var black_chip_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_egypt);
    var grey_chip_egypt: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_egypt);

    var black_chip_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_casino);
    var grey_chip_casino: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_casino);

    var black_chip_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_rome);
    var grey_chip_rome: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_rome);

    var black_chip_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_gothic);
    var grey_chip_gothic: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_gothic);

    var black_chip_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_japan);
    var grey_chip_japan: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_japan);

    var black_chip_noir: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip1_noir);
    var grey_chip_noir: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chip2_noir);

    var illumination: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.illumination);
    var green: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);
    var romb1: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.romb);
    var romb2: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.romb2);
    var romb3: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.romb3);


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
            t1.text ="Игрок 1 думает..."
            t2.text  = "Игрок 2"
            canvas?.drawLine(getWidth().toFloat(),getHeight().toFloat()/2,getWidth().toFloat(),getHeight().toFloat(),line_who_do_move)
        }
        else
        {
            t1.text ="Игрок 1"
            t2.text  = "Игрок 2 думает..."
            canvas?.drawLine(getWidth().toFloat(),0f,getWidth().toFloat(),getHeight().toFloat()/2,line_who_do_move)
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
        var right_illumination: Bitmap
        var right_green: Bitmap

        right_black_chip  = Bitmap.createScaledBitmap(black_chip_normal,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
        right_grey_chip  = Bitmap.createScaledBitmap(grey_chip_normal,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
        right_green  = Bitmap.createScaledBitmap(green,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);


        when (Design) {
            "Normal" ->{

            }
            "Egypt" -> {
                right_black_chip  = Bitmap.createScaledBitmap(black_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip  = Bitmap.createScaledBitmap(grey_chip_egypt,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb3,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Casino" -> {
                right_black_chip  = Bitmap.createScaledBitmap(black_chip_casino,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip  = Bitmap.createScaledBitmap(grey_chip_casino,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_illumination = Bitmap.createScaledBitmap(illumination,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb1,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Rome" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_rome,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_rome,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb2,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Gothic" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_gothic,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_gothic,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb1,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Japan" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_japan,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_japan,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb3,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
            "Noir" -> {
                right_black_chip = Bitmap.createScaledBitmap(black_chip_noir,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true); //подгоняем картинки под размеры экрана телефона
                right_grey_chip = Bitmap.createScaledBitmap(grey_chip_noir,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
                right_green = Bitmap.createScaledBitmap(romb1,(width-2*indent).toInt()/size_field_x, (width-2*indent).toInt()/size_field_x, true);
            }
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
        var dialog: Show_Result_one_Device? = null

        if(chek_win()>0 && event!!.getAction()  == MotionEvent.ACTION_UP && !blocked) {
            if(SOUND)
            {
                mSound2.play(1, 1F, 1F, 1, 0, 1F)
            }
            if (chek_win() == 2) {3
                dialog =
                    Show_Result_one_Device(activity)
                dialog?.showResult_one_device("Игрок 1 победил", "AngleGame", activity)
                return true
            }
            if (chek_win() == 1) {
                dialog =
                    Show_Result_one_Device(activity)
                dialog?.showResult_one_device("Игрок 2 победил", "AngleGame", activity)
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
                        editor.putString("corner_one_divice", data_from_memory)
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

                    if (s == 0)
                    {
                        PHASE = true
                    }
                }

                invalidate()
            }
            lastX = X
            lastY = Y
        }
        Log.w("DOPO",Black_or_grey_chip)


        return true
    }

}
*/