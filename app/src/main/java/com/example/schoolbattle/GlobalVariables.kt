package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.rgb
import android.media.AudioManager
import android.media.SoundPool
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.engine.Game
import com.example.schoolbattle.engine.RecyclerSet
import com.example.schoolbattle.engine.RecyclerSetBlitz
import com.google.firebase.database.ChildEventListener

var handler_for_emotion : Handler = Handler()

//      инициализация звука
var mSound : SoundPool = SoundPool(1, AudioManager.STREAM_SYSTEM,0);
var vibratorService : Vibrator? = null
//

//______________________________________________________________________
var SOUND: Boolean = false
var VIBRATION: Boolean = false
//____________________________________________________________________________________________________________________________
var Design: String = "Normal"               //дизайн
val PICTURE_STYLES = mapOf(0 to R.drawable.avatar1, 1 to R.drawable.game_menu_egypt, 2 to R.drawable.game_menu_casino,3 to R.drawable.game_menu_rome,4 to R.drawable.game_menu_gothic,
    5 to R.drawable.game_menu_japan, 6 to R.drawable.game_menu_noir)
val PICTURE_TEXT = mapOf(0 to "Деловой стиль", 1 to "Eгипетскй стиль", 2 to "Казино стиль",3 to "Римский стиль",4 to "Готический стиль",5 to "Японский стиль", 6 to "Нуар стиль")
val PRICE_OD_DESIGN = mapOf(0 to 10,1 to 20,2 to 30,3 to 90,4 to 1, 5 to 3, 6 to 10)
var ARRAY_OF_DESIGN_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6)             //номера  дизайнов в магазине
var ARRAY_OF_DESIGN: MutableList<Int>  = mutableListOf(0,1)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_DESIGNS = mapOf(0 to "Normal", 1 to "Egypt", 2 to "Casino",3 to "Rome", 4 to "Gothic", 5 to "Japan", 6 to "Noir")
//__________________________________________________________________________________________________________________________________

//____________________________________________________________________________________________________________________________
var AVATAR: Int = 0               //аватары
val PICTURE_AVATAR = mapOf(0 to R.drawable.av0, 1 to R.drawable.av1, 2 to R.drawable.av2,3 to R.drawable.av3,4 to R.drawable.av4,5 to R.drawable.av5,6 to R.drawable.av6,
    7 to R.drawable.av7, 8 to R.drawable.av8, 9 to R.drawable.av9,10 to R.drawable.av10,11 to R.drawable.av11,12 to R.drawable.av12,13 to R.drawable.av13,
    14 to R.drawable.av14, 15 to R.drawable.av15, 16 to R.drawable.av16,17 to R.drawable.av17,18 to R.drawable.av18,19 to R.drawable.av19,20 to R.drawable.av20,
    21 to R.drawable.av21, 22 to R.drawable.av22, 23 to R.drawable.av23,24 to R.drawable.av24,25 to R.drawable.av25,26 to R.drawable.av26,27 to R.drawable.av27,
    28 to R.drawable.av28, 29 to R.drawable.av29, 30 to R.drawable.av30)
val AVATAR_TEXT = mapOf(0 to "Мир", 1 to "Звезда 1", 2 to "Золото",3 to "Корона",4 to "Инь-Янь",5 to "Солнце",6 to "Меркурий",
    7 to "Венера", 8 to "Земля", 9 to "Луна",10 to "Марс",11 to "Юпитер",12 to "Сатурн",13 to "Уран",
    14 to "Нептун", 15 to "Плутон", 16 to "Пентакль",17 to "Треугольник",18 to "Крест",19 to "Звезда 2",20 to "Тигр",
    21 to "Пентаграмма", 22 to "Змея", 23 to "Маска",24 to "Топоры",25 to "Череп",26 to "Лилия",27 to "Роза",
    28 to "Сердце", 29 to "Солнце", 30 to "Шут")
val PRICE_OD_AVATAR = mapOf(0 to 0, 1 to 0, 2 to 20,3 to 100,4 to 0,5 to 10,6 to 10,
    7 to 10, 8 to 10, 9 to 10,10 to 10,11 to 20,12 to 20,13 to 20,
    14 to 20, 15 to 20, 16 to 30,17 to 30,18 to 0,19 to 10,20 to 50,
    21 to 50, 22 to 20, 23 to 30,24 to 60,25 to 60,26 to 100,27 to 50,
    28 to 50, 29 to 100, 30 to 1)
var ARRAY_OF_AVATAR_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30)             //номера  дизайнов в магазине
var ARRAY_OF_AVATAR: MutableList<Int>  = mutableListOf(0,1,4,18,19)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_AVATAR = mapOf(0 to "ava1", 1 to "ava2", 2 to "ava3",3 to "ava4")
//__________________________________________________________________________________________________________________________________

//____________________________________________________________________________________________________________________________
var EMOTION: Int =  -1               //ЭМОЦИИ
val PICTURE_EMOTION = mapOf(0 to R.drawable.e0, 1 to R.drawable.e1, 2 to R.drawable.e2,3 to R.drawable.e3,4 to R.drawable.e4,
   5 to R.drawable.e5,6 to R.drawable.e6,7 to R.drawable.e7,8 to R.drawable.e8,9 to R.drawable.e9)
val EMOTION_TEXT = mapOf(0 to "Злость", 1 to "Хохот", 2 to "Ненависть",3 to "Умиление")
val PRICE_OD_EMOTION = mapOf(0 to 10,1 to 20,2 to 30,3 to 90,4 to 1,5 to 1,6 to 1,7 to 1,8 to 1,9 to 1)
var ARRAY_OF_EMOTION_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6,7,8,9)             //номера  дизайнов в магазине
var ARRAY_OF_EMOTION: MutableList<Int>  = mutableListOf(0,1)             //номера открытых эмоций
var AUXILIARY_MAP_OF_EMOTION = mapOf(0 to "e0", 1 to "e1", 2 to "e2",3 to "e3",4 to "e4",5 to "e5",6 to "e6",7 to "e7",8 to "e8",9 to "e9")
//__________________________________________________________________________________________________________________________________


//____________________________________________________________________________________________________________________________
var SPECIALLY: String = "ava1"               //СПЕЦИАЛЬНОЕ
val PICTURE_SPECIALLY = mapOf(0 to R.drawable.video, 1 to R.drawable.vip, 2 to R.drawable.avatar1,3 to R.drawable.avatar1)
val SPECIALLY_TEXT = mapOf(0 to "ВИДЕО С ВОЗНАГРАЖДЕНИЕМ", 1 to "ПРЕМИУМ АККАУНТ", 2 to "ШОШОШОШО",3 to "ДЛДДЛДЛДЛ")
val PRICE_OD_SPECIALLY = mapOf(0 to 10,1 to 20,2 to 30,3 to 90)
var ARRAY_OF_SPECIALLY_SHOP: MutableList<Int>  = mutableListOf(0,1)
var ARRAY_OF_SPECIALLY: MutableList<Int>  = mutableListOf()
var AUXILIARY_MAP_OF_SPECIALLY = mapOf(0 to "specially1", 1 to "specially2", 2 to "specially3",3 to "specially4")
//__________________________________________________________________________________________________________________________________

var INITIAL_AMOUNT: Int = 100          //начальная сумма
var MONEY: Int = 100                  //ДЕНЬГИ


var GAMES: MutableList<Game> = mutableListOf()
var FRIENDS: MutableList<String> = mutableListOf()
var CHOOSE_GAMES: MutableList<String> = mutableListOf("XOGame", "DotGame", "SnakeGame", "BoxGame", "AngleGame", "VirusGame","Reversi")
var currentContext: Context? = null
lateinit var listener: ChildEventListener
var recyclerSet: RecyclerSet = RecyclerSet()
var recyclerSetBlitz: RecyclerSetBlitz = RecyclerSetBlitz()
lateinit var CONTEXT: Activity


//Текущие игры в долгой
data class LongGame(val key: String, val type: String, val opponent: String, val move: String)
var CURRENTGAMES: MutableList<LongGame> = mutableListOf()
var currentGamesRecycler: RecyclerView? = null

  //__________________________________________________________________для пихания в память телефона
fun CODE(m : MutableList<Int>): String
{
    var s: String = ""
    for(i in m)
    {
        s+=i.toString()
        s+='a'
    }
    return s
}

fun DECODE(s : String): MutableList<Int>
{
    var m: MutableList<Int> = mutableListOf()
    var k: Int = 0
    for(i in s.indices)
    {
        if(s[i]!='a')
        {
            k *= 10
            k += s[i].toInt() - '0'.toInt()
        }
        else
        {
            m.add(k)
            k = 0
        }
    }
    return(m)
}
fun generateColorStateList() :ColorStateList
{

    var checkedColor:Int = rgb(255,255,255)
    var uncheckedColor:Int = rgb(255,148,148)

    when (Design) {
        "Normal" -> {
            checkedColor = rgb(0,0,0)
            uncheckedColor = rgb(0,0,0)
        }
        "Egypt" -> {
            checkedColor = rgb(0,0,0)
            uncheckedColor = rgb(0,0,0)
        }
        "Casino" -> {
            checkedColor = Color.YELLOW
            uncheckedColor  = Color.YELLOW
        }
        "Rome" -> {
            checkedColor = Color.rgb(193, 150, 63)
            uncheckedColor = Color.rgb(193, 150, 63)
        }
        "Gothic" -> {
            checkedColor = rgb(255,255,255)
            uncheckedColor = rgb(255,255,255)
        }
        "Noir" -> {
            checkedColor = rgb(255,255,255)
            uncheckedColor = rgb(255,255,255)
        }
        "Japan" -> {
            checkedColor = rgb(0,0,0)
            uncheckedColor = rgb(0,0,0)
        }
    }
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked))
    val colors = intArrayOf(
        checkedColor, // checked
        uncheckedColor // unchecked
        )
    return ColorStateList(states, colors)
}
 //функция проверки соединения с интернетом
fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
    val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo=connectivityManager.activeNetworkInfo
    return  networkInfo!=null && networkInfo.isConnected
}
