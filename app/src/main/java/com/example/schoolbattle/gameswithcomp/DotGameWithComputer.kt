package com.example.schoolbattle.gameswithcomp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.schoolbattle.R
import kotlinx.android.synthetic.main.activity_computer_games_template.*

class DotGameWithComputer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_games_template)



        bottom_navigation_template_with_computer.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 ->{

                }
                R.id.page_2 ->{
                    //dialog_parametrs = Show_parametr_with_computer(this@SnakeGameWithComputer)
                    //dialog_parametrs?.showResult_with_computer(this)
                }
                R.id.page_3 ->{
                    this.finish()
                    val intent = Intent(this, DotGameWithComputer::class.java).apply {
                        putExtra("usedToClear", "clear")
                    }
                    startActivity(intent)
                }
                R.id.page_4 ->{

                }

            }
            true
        }
    }
}
