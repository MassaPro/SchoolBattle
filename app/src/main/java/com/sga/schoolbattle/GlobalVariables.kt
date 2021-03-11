package com.sga.schoolbattle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.rgb
import android.media.AudioManager
import android.media.SoundPool
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Vibrator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.sga.schoolbattle.engine.Game
import com.sga.schoolbattle.engine.RecyclerSet
import com.sga.schoolbattle.engine.RecyclerSetBlitz
import com.google.firebase.database.ChildEventListener
import kotlinx.android.synthetic.main.activity_game_over.*
import java.util.Collections.min

var handler_for_emotion : Handler = Handler()

//      инициализация звука
var mSound : SoundPool = SoundPool(1, AudioManager.STREAM_SYSTEM,0);
var mSound1 : SoundPool = SoundPool(1, AudioManager.STREAM_SYSTEM,0);
var mSound2 : SoundPool = SoundPool(1, AudioManager.STREAM_SYSTEM,0);

var vibratorService : Vibrator? = null
//

//______________________________________________________________________
var SOUND: Boolean = false
var VIBRATION: Boolean = false
var LANGUAGE = "Russian"
//____________________________________________________________________________________________________________________________
var Design: String = "Normal"               //дизайн
val PICTURE_STYLES = mapOf(0 to R.drawable.game_menu_normal, 1 to R.drawable.game_menu_noir, 2 to R.drawable.game_menu_rome,3 to R.drawable.game_menu_casino,4 to R.drawable.game_menu_egypt,
    5 to R.drawable.game_menu_gothic, 6 to R.drawable.game_menu_japan)
val PICTURE_TEXT = mapOf(0 to "Деловой стиль", 1 to "Нуар стиль", 2 to "Римский стиль", 3 to "Казино стиль", 4 to "Eгипетский стиль", 5 to "Готический стиль",6 to "Японский стиль")
val PRICE_OD_DESIGN = mapOf(0 to 10,1 to 250,2 to 1000,3 to 10000,4 to 70000, 5 to 800000, 6 to 2000000)
var ARRAY_OF_DESIGN_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6)             //номера  дизайнов в магазине
var ARRAY_OF_DESIGN: MutableList<Int>  = mutableListOf(0)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_DESIGNS = mapOf(0 to "Normal", 1 to "Noir", 2 to "Rome",3 to "Casino", 4 to "Egypt", 5 to "Gothic", 6 to "Japan")
//__________________________________________________________________________________________________________________________________

//____________________________________________________________________________________________________________________________
var AVATAR: Int = 0               //аватары
val PICTURE_AVATAR = mapOf(0 to R.drawable.av0, 1 to R.drawable.av1, 2 to R.drawable.av2,3 to R.drawable.av3,4 to R.drawable.av4,5 to R.drawable.av5,6 to R.drawable.av6,
    7 to R.drawable.av7, 8 to R.drawable.av8, 9 to R.drawable.av9,10 to R.drawable.av10,11 to R.drawable.av11,12 to R.drawable.av12,13 to R.drawable.av13,
    14 to R.drawable.av14, 15 to R.drawable.av15, 16 to R.drawable.av16,17 to R.drawable.av17,18 to R.drawable.av18,19 to R.drawable.av19,20 to R.drawable.av20,
    21 to R.drawable.av21, 22 to R.drawable.av22, 23 to R.drawable.av23,24 to R.drawable.av24,25 to R.drawable.av25,26 to R.drawable.av26,27 to R.drawable.av27,
    28 to R.drawable.av28, 29 to R.drawable.av29, 30 to R.drawable.av30,31 to R.drawable.av31,32 to R.drawable.av32)
val AVATAR_TEXT = mapOf(0 to "Мир",1 to "Солнце",2 to "Меркурий", 3 to "Венера", 4 to "Земля",
    5 to "Луна",6 to "Марс",7 to "Юпитер",8 to "Сатурн",9 to "Уран", 10 to "Нептун", 11 to "Плутон",
    12 to "Пентакль",13 to "Треугольник",14 to "Крест",15 to "Звезда", 16 to "Пентаграмма",
    17 to "Змея",18 to "Тигр", 19 to "Маска",20 to "Топоры",21 to "Череп",22 to "Инь-Янь",23 to "Сердце",
    24 to "Орхидея",25 to "Фиалка", 26 to "Роза",27 to "Лилия", 28 to "Солнце",
    29 to "Звезда 2", 30 to "Золото",31 to "Корона",32 to "Шут")
val PRICE_OD_AVATAR = mapOf(0 to 0, 1 to 80,2 to 100, 3 to 120, 4 to 140,
    5 to 160,6 to 180,7 to 200,8 to 220,9 to 240, 10 to 260, 11 to 400, 12 to 800,
    13 to 900,14 to 950,15 to 1000,16 to 1500, 17 to 2000, 18 to 3000, 19 to 5000,
    20 to 6000,21 to 10000,22 to 12500,23 to 15000, 24 to 20000, 25 to 25000,
    26 to 30000,27 to 40000, 28 to 50000, 29 to 70000, 30 to 100000, 31 to 500000, 32 to 1000000)
var ARRAY_OF_AVATAR_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32)             //номера  дизайнов в магазине
var ARRAY_OF_AVATAR: MutableList<Int>  = mutableListOf(0)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_AVATAR = mapOf(0 to "ava1", 1 to "ava2", 2 to "ava3",3 to "ava4")
//__________________________________________________________________________________________________________________________________

//____________________________________________________________________________________________________________________________
var EMOTION: Int =  -1               //ЭМОЦИИ
val PICTURE_EMOTION = mapOf(0 to R.drawable.e0, 1 to R.drawable.e1, 2 to R.drawable.e2,3 to R.drawable.e3,4 to R.drawable.e4,
   5 to R.drawable.e5,6 to R.drawable.e6,7 to R.drawable.e7,8 to R.drawable.e8,9 to R.drawable.e9)
val EMOTION_TEXT = mapOf(0 to "Скукота", 1 to "Хохот", 2 to "Злость",3 to "Удачка",4 to "Времечко",5 to "Грязно",6 to "ГГ ВП", 7 to "Хорош", 8 to "Не забудь", 9 to "Нуль сигма")
val PRICE_OD_EMOTION = mapOf(0 to 100,1 to 200,2 to 400,3 to 0,4 to 3000,5 to 1000,6 to 5000,7 to 7500,8 to 8000,9 to 10000)
var ARRAY_OF_EMOTION_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6,7,8,9)             //номера  дизайнов в магазине
var ARRAY_OF_EMOTION: MutableList<Int>  = mutableListOf(0,1)             //номера открытых эмоций
var AUXILIARY_MAP_OF_EMOTION = mapOf(0 to "e0", 1 to "e1", 2 to "e2",3 to "e3",4 to "e4",5 to "e5",6 to "e6",7 to "e7",8 to "e8",9 to "e9")
//__________________________________________________________________________________________________________________________________


//____________________________________________________________________________________________________________________________
var SPECIALLY: String = "ava1"               //СПЕЦИАЛЬНОЕ
val PICTURE_SPECIALLY = mapOf(0 to R.drawable.video, 1 to R.drawable.vip, 2 to R.drawable.bag1,3 to R.drawable.bag2,4 to R.drawable.bag3,5 to R.drawable.bag4,6 to  R.drawable.bag5,7 to R.drawable.bag6,8 to R.drawable.bag7)
val SPECIALLY_TEXT = mapOf(0 to "ВИДЕО С ВОЗНАГРАЖДЕНИЕМ", 1 to "ПРЕМИУМ АККАУНТ", 2 to "ГОРШОК КРИСТАЛЛОВ",3 to "СУМКА КРИСТАЛЛОВ",4 to "РЮКЗАК КРИСТАЛЛОВ",5 to "ПОРТФЕЛЬ КРИСТАЛЛОВ" , 6 to "МЕШОК КРИСТАЛЛОВ",7 to "ЧЕМОДАН КРИСТАЛЛОВ",8 to " БАНК КРИСТАЛЛОВ")
val PRICE_OD_SPECIALLY = mapOf(0 to 10,1 to 20,2 to 30,3 to 90)
var ARRAY_OF_SPECIALLY_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4,5,6,7,8)
var ARRAY_OF_SPECIALLY: MutableList<Int>  = mutableListOf()
var AUXILIARY_MAP_OF_SPECIALLY = mapOf(0 to "specially1", 1 to "specially2", 2 to "specially3",3 to "specially4")

var PREMIUM : Boolean = false

var PRODUCT_ID = "premium"
var ARRAY_OF_PRODUCT_ID = mapOf(1 to "premium",2 to "b1",3 to "b2",4 to "b3",5 to "b4",6 to "b7",7 to "b5",8 to "b6")           //идентификаторы в Google Play
//var PURCHASE_KEY =      "premium"
//__________________________________________________________________________________________________________________________________




var INITIAL_AMOUNT: Int = 250          //начальная сумма
var MONEY: Int = 200                  //ДЕНЬГИ


var GAMES: MutableList<Game> = mutableListOf()
var FRIENDS: MutableList<String> = mutableListOf()
var CHOOSE_GAMES: MutableList<String> = mutableListOf("XOGame", "DotGame", "SnakeGame", "BoxGame", "AngleGame", "VirusGame","Reversi")
var LIGTH_GAMES_ICONS = mapOf("XOGame" to R.drawable.xo_icon0,"DotGame" to R.drawable.dots_icon0, "SnakeGame" to R.drawable.sn_icon0, "BoxGame" to R.drawable.box_icon0,
    "AngleGame" to R.drawable.cor_icon0, "VirusGame" to R.drawable.vir_icon0,"Reversi" to R.drawable.rev_icon0)

var DARK_GAMES_ICONS = mapOf("XOGame" to R.drawable.xo_icon1,"DotGame" to R.drawable.dots_icon1, "SnakeGame" to R.drawable.sn_icon1, "BoxGame" to R.drawable.box_icon1,
    "AngleGame" to R.drawable.cor_icon1, "VirusGame" to R.drawable.vir_icon1,"Reversi" to R.drawable.rev_icon1)

var YELLOW_GAMES_ICONS = mapOf("XOGame" to R.drawable.xo_icon2,"DotGame" to R.drawable.dots_icon2, "SnakeGame" to R.drawable.sn_icon2,
    "BoxGame" to R.drawable.box_icon2,
    "AngleGame" to R.drawable.cor_icon2, "VirusGame" to R.drawable.vir_icon2,"Reversi" to R.drawable.rev_icon2)

var BROWN_GAMES_ICONS = mapOf("XOGame" to R.drawable.xo_icon3,"DotGame" to R.drawable.dots_icon3, "SnakeGame" to R.drawable.sn_icon3,
    "BoxGame" to R.drawable.box_icon3,
    "AngleGame" to R.drawable.cor_icon3, "VirusGame" to R.drawable.vir_icon3,"Reversi" to R.drawable.rev_icon3)

var currentContext: Context? = null
lateinit var listener: ChildEventListener
var recyclerSet: RecyclerSet = RecyclerSet()
var recyclerSetBlitz: RecyclerSetBlitz = RecyclerSetBlitz()
lateinit var CONTEXT: Activity


//Текущие игры в долгой
data class LongGame(val key: String, val type: String, val opponent: String, val move: String)
var CURRENTGAMES: MutableList<LongGame> = mutableListOf()
var USERAVAS = mutableMapOf<String, Any>()
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
            checkedColor = Color.WHITE
            uncheckedColor  = Color.WHITE
        }
        "Rome" -> {
            checkedColor = Color.GRAY
            uncheckedColor = Color.GRAY
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

var RATING = -1

lateinit var adLoader: AdLoader



lateinit var DDD: Dialog

fun populateUnifiedNativeAdView(unifiedNativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
    // Locate the view that will hold the headline, set its text, and use the
    // UnifiedNativeAdView's headlineView property to register it.
    val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
    headlineView.text = unifiedNativeAd.headline
    adView.headlineView = headlineView

    val mediaView = adView.findViewById<MediaView>(R.id.ad_app_icon)
    adView.mediaView = mediaView

    // Call the UnifiedNativeAdView's setNativeAd method to register the/
    // NativeAdObject.
    adView.setNativeAd(unifiedNativeAd)

    // Ensure that the parent view doesn't already contain an ad view.
    DDD.add_frame.removeAllViews()

    // Place the AdView into the parent.
    DDD.add_frame.addView(adView)
}

fun right_recording(s : String): String
{
    var answer :String   = ""
    if(s.toInt()>=10000)
    {
        for(i in s.indices)
        {
            answer = s[s.length - i - 1] + answer
            if( (i)%3 == 2)
            {
                answer = " $answer"
            }
        }
        return answer
    }
   return s
}

var TRANSLATE = mapOf("КУПИТЬ" to "BUY","КУПЛЕНО" to "PURCHASED","Аватары" to "Avatars","Эмоции" to "Emotions","Премиум" to "Premium",
    "Дизайны" to "Designs", "Недостаточно средств" to "Insufficient funds",
    "Купленные аватары можно применить в профиле" to "Purchased avatars can be applied to your profile",
    "Купленные эмоции вы сможете использовать во время игры" to "You can use the purchased emotions during the game",
    "Купленные дизайны можно применить в настройках" to "The purchased designs can be applied in the settings", "Имя" to "Name",
    "Пароль" to "Password","Повторите пароль" to "Repeat password","Язык" to "Language",
    "Регистрация" to "Registration","Зарегистрироваться" to "SIGN UP","Войти" to "SIGN IN","RU" to "EN",
    "блиц онлайн" to "blitz online","долгая онлайн" to "long online","Игра на одном устройстве" to "Play one device",
    "игра с компьютером" to "play with computer","Поиск Соперника" to "Search","Вибрация" to "Vibration","Выбор дизайна" to "Choose design",
    "Звук" to "Sounds","ПРИМЕНИТЬ" to "APPLY","УСТАНОВЛЕНО" to "FIXED","Выход" to "Log out",
    "Найти" to "Search","Профиль" to "Profile","Подписки" to "Following","Подписчики" to "Followers","ОТМЕНА" to "CANCEL",
    "Подождите,видео еще загружается/Проверьте подключение к интернету" to "Wait, the video is still loading/Check your internet connection",
    "в меню" to "menu","новая игра" to "new game","Победа" to "Victory","Поражение" to "Defeat",
    "ТЕКУЩИЕ ИГРЫ" to "CURRENT GAMES")
fun translate(s: String): String
{
    if(LANGUAGE == "Russian")
    {
        return s
    }
    return TRANSLATE[s].toString()
}

var TRANSLATE_MAP_AVATAR  = mapOf("Мир" to "Peace","Солнце" to "Sun","Меркурий" to "Mercury","Венера" to "Venus","Земля" to "Eath", "Луна" to "Moon",
    "Марс" to "Mars", "Юпитер" to "Jupiter","Сатурн" to "Saturn","Уран" to "Uranus","Нептун" to "Neptune","Плутон" to "Pluto","Пентакль" to "Pentacle",
    "Треугольник" to "Triangle","Крест" to "Cross","Звдезда" to "Star","Пентаграмма" to "Pentagram","Змея" to "Snake","Тигр" to "Tiger","Маска" to "Mask","Топоры" to "Axes","Череп" to "Skull",
    "Инь-Янь" to "Yin-Yang","Сердце" to "Heart","Орхидея" to "Orchid","Фиалка" to  "Violet","Роза" to "Rose","Лилия" to "Lily","Звезда 2" to "Star 2","Звезда" to "Star2<","Золото" to "Gold","Корона" to "Crown","Шут" to "Fool")

fun translate_avatar(s: String): String
{
    if(LANGUAGE == "Russian")
    {
        return s;
    }
    return TRANSLATE_MAP_AVATAR[s].toString()

}

var TRANSLATE_MAP_EMOTION  = mapOf("Скукота" to "Boredom","Хохот" to "Laughter","Злость" to  "Anger","Удачка" to "Luck","Времечко" to "Time",
    "Грязно" to "Dirty" ,"Не забудь" to "Don't forget",
    "Нуль сигма" to " Zero Sigma", "Хорош" to "Nice" , "ГГ ВП" to "GG WP")

fun translate_emotion(s: String): String
{
    if(LANGUAGE == "Russian")
    {
        return s;
    }
    return TRANSLATE_MAP_EMOTION[s].toString()

}

var TRANSLATE_MAP_DESIGN  = mapOf("Деловой стиль" to "Business style","Eгипетский стиль" to "Egyptian style","Римский стиль" to "Roman style",
    "Казино стиль" to  "Casino style","Нуар стиль" to "Noir style","Готический стиль" to "Gothic style","Японский стиль" to "Japanese style")

fun translate_design(s: String): String
{
    if(LANGUAGE == "Russian")
    {
        return s;
    }
    return TRANSLATE_MAP_DESIGN[s].toString()

}


var TRANSLATE_GAMES = mapOf("XOGame" to "Крестики-Нолики", "DotGame" to "Точки", "SnakeGame" to "Змейка", "BoxGame" to "Коробки",
    "AngleGame" to "Уголки", "VirusGame" to "Вирусы","Reversi" to "Реверси","Choose game" to "Выбери игру")
fun translate_games(s:String): String?
{
    if(LANGUAGE == "English")
    {
        return s
    }
    return TRANSLATE_GAMES[s]
}
