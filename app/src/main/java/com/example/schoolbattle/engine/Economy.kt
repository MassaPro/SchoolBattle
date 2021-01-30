package com.example.schoolbattle.engine

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.schoolbattle.BuildConfig
import com.example.schoolbattle.MONEY
import com.example.schoolbattle.myRef
import kotlin.math.exp
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

fun initEconomyParams(activity: Activity) { //разрешено выполнять только в SignUp Activity
    val editor =
        activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
    val username = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        .getString("username", "")
    editor.putString("number_computer", "0")
    editor.putString("number_blitz", "0")
    editor.putString("number_long", "0")
    editor.putString("number_capital", "500")
    editor.apply()
    val upd = mapOf(
        "number_computer" to "0",
        "number_blitz" to "0",
        "number_long" to "0",
        "number_capital" to "500",
        "money" to "250"
    )
    myRef.child("Users").child(username!!).updateChildren(upd)
}

//эта функция тупо считает новое число денег в зависимости от дельты рейтинга и числа игр
fun calculateMoneyChange(numberOfGames: Int, ratingChange: Int, result: String): Int {//x - количество игр, // d - разница рейтинга, result - исход игры
    if (result != "winner" || //вызывается ошибка компиляции при неправильных параметрах функции
            result != "draw" ||
            result != "lose") {
        if (BuildConfig.DEBUG) {
            error("Assertion failed")
        }
    }
    val x = numberOfGames.toDouble()
    val d = ratingChange.toDouble()
    return when (result) {
        "winner" -> {
            ((53.2198 + 0.377582 * d) *
                    exp(x * (0.003829757 + (0.0042845 - 0.003829757) /
                            (1 + (d / 86.41569).pow(1.752336))))).toInt()
        }
        "draw" -> {
            calculateMoneyChange(numberOfGames, ratingChange, "winner") / 4
        }
        else -> {
            0
        }
    }
}
//type - вид того, после чего нужно добавить бабок
//res - победа, поражение или ничья (winner, draw, loser), по дефолту winner
//delta - изменение ПОЛЬЗОВАТЕЛЬСКОГО рейтинга (только для блиц-режима), по дефолту 100
fun updateEconomyParams(activity: Activity, type: String, res: String = "winner", delta: Int = 100): Int {
    Log.w("NIKITA", "RUNNED!!!")
    val getter = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    var comp = getter.getString("number_computer", "0").toString().toInt()
    var blitz = getter.getString("number_blitz", "0").toString().toInt()
    var long = getter.getString("number_long", "0").toString().toInt()
    var capital = getter.getString("number_capital", "500").toString().toInt()
    var add = 0
    when (type) {
        "blitz" -> {
            blitz += 1
            if (delta >= 0) {
                add = calculateMoneyChange(blitz, delta, "winner")
                capital += add
                MONEY += add
            }
        }
        "long" -> {
            long += 1
            add = calculateMoneyChange(long, 100, res)
            capital += add
            MONEY += add
        }
        "computer" -> {
            comp += 1
            add = min(20 + sqrt(comp.toDouble()).toInt(), 200)
            capital += add
            MONEY += add
        }
        "award" -> {
            add = min(capital / 100 * 4, 5000)
            MONEY += add
            capital += add
        }
        "increase" -> {
            MONEY += add
            capital += add
        }
        else -> {
            if (BuildConfig.DEBUG) {
                error("Wrong game type! Can't update Economy Params!")
            }
        }
    }
    val username = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        .getString("username", "")
    val editor =
        activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
    editor.putString("money", MONEY.toString())
    editor.apply()
    editor.putString("number_computer", comp.toString())
    editor.putString("number_blitz", blitz.toString())
    editor.putString("number_long", long.toString())
    editor.putString("number_capital", capital.toString())
    editor.apply()
    val upd = mapOf(
        "number_computer" to comp.toString(),
        "number_blitz" to blitz.toString(),
        "number_long" to long.toString(),
        "number_capital" to capital.toString(),
        "money" to MONEY.toString()
    )
    myRef.child("Users").child(username!!).updateChildren(upd)
    return add
}