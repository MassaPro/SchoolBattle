package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*


class NullActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_null)

        CONTEXT = this



        val prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs.getString("username", "")
        val inflatedView: View = layoutInflater.inflate(R.layout.activity_settings, null)
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
