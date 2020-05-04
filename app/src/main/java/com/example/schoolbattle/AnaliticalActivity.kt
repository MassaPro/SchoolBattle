package com.example.schoolbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_analitical.*

class AnaliticalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analitical)
        var Answer: String = intent.getStringExtra("Answer")
        table_of_answer.text = Answer

    }
}
