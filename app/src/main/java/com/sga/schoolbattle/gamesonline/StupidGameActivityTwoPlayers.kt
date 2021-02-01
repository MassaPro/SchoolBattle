package com.sga.schoolbattle.gamesonline

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sga.schoolbattle.*
import com.sga.schoolbattle.engine.ShowResult
import com.sga.schoolbattle.engine.StupidGame
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_stupid_game_two_players.*

class StupidGameActivityTwoPlayers : AppCompatActivity() {

    private var isRun = false
    private var dialog: ShowResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        CONTEXT = this
        dialog = ShowResult(this)
        currentContext = this
        isRun = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stupid_game_two_players)
        if (StupidGame != Activity()) StupidGame.finish()
        if (NewGame != Activity()) NewGame.finish()
        val yourName =
            getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "")
                .toString()
        var opponentsName_: String = intent.getStringExtra("opponentName").toString()
        val type = intent.getStringExtra("type")

        if (type != "") {
            TODO()
            //ALF CODE HERE
        }

        var opponentsName = ""
        for (i in opponentsName_) {
            if (i == ' ') break
            opponentsName += i
        }
        players.text = yourName + " VS " + opponentsName
        youName.text = yourName
        opponentName.text = opponentsName

        val gameData = myRef.child(type + "StupidGames").child(
            if (opponentsName < yourName)
                opponentsName + '_' + yourName else yourName + '_' + opponentsName
        )
        val yu = if (opponentsName < yourName) '1' else '2'
        val op = if (opponentsName < yourName) '2' else '1'
        gameData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val s = p0.child("Position").value.toString()
                var mx1 = ' '
                var mx2 = ' '
                for (i in 0..s.length - 3) {
                    if (s[i] == '_' && s[i + 1] == yu && s[i + 2] == '-') {
                        if (s[i + 3] == '1') you.text = "Бумага"
                        if (s[i + 3] == '2') you.text = "Ножницы"
                        if (s[i + 3] == '3') you.text = "Камень"
                        mx1 = s[i + 3]
                    }
                    if (s[i] == '_' && s[i + 1] == op && s[i + 2] == '-') {
                        mx2 = s[i + 3]
                    }
                }
                if (mx1 != ' ' && mx2 != ' ') {
                    if (mx2 == '1') opponent.text = "Бумага"
                    if (mx2 == '2') opponent.text = "Ножницы"
                    if (mx2 == '3') opponent.text = "Камень"

                    var res = ""
                    if (mx1 == mx2) {
                        res = "Ничья:|"
                    } else if (mx1 == '2' && mx2 == '1' || mx1 == '3' && mx2 == '2' || mx1 == '1' && mx2 == '3') {
                        res = "Победа:)"
                    } else {
                        res = "Поражение:("
                    }
                    myRef.child(type + "StupidGames").child(if (opponentsName < yourName)
                        opponentsName + '_' + yourName else yourName + '_' + opponentsName
                    ).removeValue()

                    myRef.child("Users").child(yourName).child(type + "Games").child("$opponentsName StupidGame").removeValue()
                    myRef.child("Users").child(opponentsName).child(type + "Games").child("$yourName StupidGame").removeValue()



                    if (isRun) {
                        dialog?.showResult(
                            res,
                            "StupidGame",
                            yourName,
                            opponentsName,
                            -1,-1
                        )
                    }
                    gameData.removeEventListener(this)
                }
                if (!p0.value.toString().contains(
                        if (opponentsName < yourName)
                            "_1-" else "_2-"
                    )
                ) {

                    var isClicked = -1
                    if (isClicked == -1) kam.setOnClickListener {
                        isClicked = 3
                        noz.isEnabled = false
                        bum.isEnabled = false
                        gameData.child("Position").setValue(
                            p0.child("Position").value.toString() + if (opponentsName < yourName)
                                "_1-$isClicked" else "_2-$isClicked"
                        )

                    }

                    if (isClicked == -1) noz.setOnClickListener {
                        isClicked = 2

                        kam.isEnabled = false
                        bum.isEnabled = false
                        gameData.child("Position").setValue(
                            p0.child("Position").value.toString() + if (opponentsName < yourName)
                                "_1-$isClicked" else "_2-$isClicked"
                        )

                    }

                    if (isClicked == -1) bum.setOnClickListener {
                        isClicked = 1

                        noz.isEnabled = false
                        kam.isEnabled = false

                        gameData.child("Position").setValue(
                            p0.child("Position").value.toString() + if (opponentsName < yourName)
                                "_1-$isClicked" else "_2-$isClicked"
                        )

                    }
                } else {
                    noz.isEnabled = false
                    kam.isEnabled = false
                    bum.isEnabled = false
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        isRun = false
        currentContext = null
        dialog?.delete()
        finish()
    }
}
