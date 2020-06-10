package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import com.google.firebase.database.ChildEventListener

var GAMES: MutableList<Game> = mutableListOf()
var FRIENDS: MutableList<String> = mutableListOf("lkdsjlk", "KICH", "ksdjk", "KDJ")
var CHOOSE_GAMES: MutableList<String> = mutableListOf("StupidGame", "XOGame", "DotGame", "GoGame", "SnakeGame", "BoxGame", "AngleGame","VirusGame","Reversi")
var currentContext: Context? = null
lateinit var listener: ChildEventListener
var recyclerSet: RecyclerSet = RecyclerSet()
lateinit var CONTEXT: Activity