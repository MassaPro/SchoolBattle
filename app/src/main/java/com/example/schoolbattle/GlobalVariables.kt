package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import com.google.firebase.database.ChildEventListener



//______________________________________________________________________
var SOUND: Boolean = false
var VIBRATION: Boolean = false
//____________________________________________________________________________________________________________________________
var Design: String = "Normal"               //дизайн
val PICTURE_STYLES = mapOf(0 to R.drawable.avatar1, 1 to R.drawable.avatar1, 2 to R.drawable.avatar1,3 to R.drawable.avatar1,4 to R.drawable.avatar1)
val PICTURE_TEXT = mapOf(0 to "Деловой стиль", 1 to "Eгипетскй стиль", 2 to "Казино стиль",3 to "Римский стиль",4 to "Готический стиль")
val PRICE_OD_DESIGN = mapOf(0 to 10,1 to 20,2 to 30,3 to 90,4 to 1)
var ARRAY_OF_DESIGN_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3,4)             //номера  дизайнов в магазине
var ARRAY_OF_DESIGN: MutableList<Int>  = mutableListOf(0,1)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_DESIGNS = mapOf(0 to "Normal", 1 to "Egypt", 2 to "Casino",3 to "Rome", 4 to "Gothic")
//__________________________________________________________________________________________________________________________________

//____________________________________________________________________________________________________________________________
var AVATAR: Int = 0               //аватары
val PICTURE_AVATAR = mapOf(0 to R.drawable.avatar1, 1 to R.drawable.avatar1, 2 to R.drawable.avatar1,3 to R.drawable.avatar1)
val AVATAR_TEXT = mapOf(0 to "Бабай аватар", 1 to "банан", 2 to "Т - 90",3 to "AK 47")
val PRICE_OD_AVATAR = mapOf(0 to 10,1 to 20,2 to 30,3 to 90)
var ARRAY_OF_AVATAR_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3)             //номера  дизайнов в магазине
var ARRAY_OF_AVATAR: MutableList<Int>  = mutableListOf(0,1)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_AVATAR = mapOf(0 to "ava1", 1 to "ava2", 2 to "ava3",3 to "ava4")
//__________________________________________________________________________________________________________________________________

//____________________________________________________________________________________________________________________________
var EMOTION: String = "ava1"               //ЭМОЦИИ
val PICTURE_EMOTION = mapOf(0 to R.drawable.avatar1, 1 to R.drawable.avatar1, 2 to R.drawable.avatar1,3 to R.drawable.avatar1)
val EMOTION_TEXT = mapOf(0 to "Злость", 1 to "Хохот", 2 to "Ненависть",3 to "Умиление")
val PRICE_OD_EMOTION = mapOf(0 to 10,1 to 20,2 to 30,3 to 90)
var ARRAY_OF_EMOTION_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3)             //номера  дизайнов в магазине
var ARRAY_OF_EMOTION: MutableList<Int>  = mutableListOf(0,1)             //номера открытых дизайнов
var AUXILIARY_MAP_OF_EMOTION = mapOf(0 to "emotion1", 1 to "emotion2", 2 to "emotion3",3 to "emotion4")
//__________________________________________________________________________________________________________________________________


//____________________________________________________________________________________________________________________________
var SPECIALLY: String = "ava1"               //СПЕЦИАЛЬНОЕ
val PICTURE_SPECIALLY = mapOf(0 to R.drawable.avatar1, 1 to R.drawable.avatar1, 2 to R.drawable.avatar1,3 to R.drawable.avatar1)
val SPECIALLY_TEXT = mapOf(0 to "ВИДЕО С ВОЗНАГРАЖДЕНИЕМ", 1 to "ПРЕМИУМ АККАУНТ", 2 to "ШОШОШОШО",3 to "ДЛДДЛДЛДЛ")
val PRICE_OD_SPECIALLY = mapOf(0 to 10,1 to 20,2 to 30,3 to 90)
var ARRAY_OF_SPECIALLY_SHOP: MutableList<Int>  = mutableListOf(0,1,2,3)             //номера  дизайнов в магазине
var ARRAY_OF_SPECIALLY: MutableList<Int>  = mutableListOf()             //номера открытых дизайнов
var AUXILIARY_MAP_OF_SPECIALLY = mapOf(0 to "specially1", 1 to "specially2", 2 to "specially3",3 to "specially4")
//__________________________________________________________________________________________________________________________________

var INITIAL_AMOUNT: Int = 100          //начальная сумма
var MONEY: Int = 100                  //ДЕНЬГИ


var GAMES: MutableList<Game> = mutableListOf()
var FRIENDS: MutableList<String> = mutableListOf()
var CHOOSE_GAMES: MutableList<String> = mutableListOf("StupidGame", "XOGame", "DotGame", "GoGame", "SnakeGame", "BoxGame", "AngleGame","VirusGame","Reversi")
var currentContext: Context? = null
lateinit var listener: ChildEventListener
var recyclerSet: RecyclerSet = RecyclerSet()
var recyclerSetBlitz: RecyclerSetBlitz = RecyclerSetBlitz()
lateinit var CONTEXT: Activity


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