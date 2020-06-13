package com.example.schoolbattle

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.instacart.library.truetime.TrueTime
import kotlinx.android.synthetic.main.activity_fast_game.*
import java.util.*

class FastGameActivity : AppCompatActivity() {
    var TAG = "DBG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fast_game)
        initTrueTime(this)

        checker_time.setOnClickListener {
            Toast.makeText(this,trueTime.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun onButtonClick(v: View?) {
        Log.d(TAG, trueTime.toString())
    }

    companion object {
        val trueTime: Date
            get() = if (TrueTime.isInitialized()) TrueTime.now() else Date()

        fun initTrueTime(ctx: Context) {
            if (isNetworkConnected(ctx)) {
                if (!TrueTime.isInitialized()) {
                    val trueTime = InitTrueTimeAsyncTask(ctx)
                    trueTime.execute()
                }
            }
        }

        fun isNetworkConnected(ctx: Context): Boolean {
            val cm = ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val ni = cm.activeNetworkInfo
            return ni != null && ni.isConnectedOrConnecting
        }
    }
}