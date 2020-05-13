package com.example.schoolbattle

var GAMES: MutableList<Game> = mutableListOf()
var CHOOSE_GAMES: MutableList<String> = mutableListOf("StupidGame", "KNB", "BUR", "sl", "xdkj", "STSTSTSTST", "OPAOPAOPA", "???", "XOROSHO")

class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {
    override fun toString(): String {
        return "$name $type"
    }
}