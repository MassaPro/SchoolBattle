package com.example.schoolbattle

var GAMES: MutableList<Game> = mutableListOf()

class Game(val name: String = "", val type: String = "StupidGame", val text: String = "you VS") {}