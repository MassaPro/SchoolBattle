package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView


class NullActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null)

        CONTEXT = this



        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")
        val inflatedView: View = layoutInflater.inflate(R.layout.activity_list_of_current_games, null)
        gamesRecycler = inflatedView.findViewById(R.id.item_list) as RecyclerView
        if (username != "" ) {
            val intent = Intent(applicationContext, NavigatorActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }
}
