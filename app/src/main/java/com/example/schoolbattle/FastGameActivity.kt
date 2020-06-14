package com.example.schoolbattle

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.instacart.library.truetime.TrueTime
import kotlinx.android.synthetic.main.activity_fast_game.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class FastGameActivity : AppCompatActivity() {
    var TAG = "DBG"
    private var h: Handler = Handler()
    var Finish_minute: Int  = 0
    var Finish_second: Int  = 0
    var StopTime: Boolean = false

    fun add_null(s: String):String
    {
        if(s.length == 1)
        {
            return "0" + s
        }
        else
        {
            return s
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fast_game)

        //__________________________________________timeDeclaration
        initTrueTime(this)
        val timeFormat: DateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        var textTime : String = timeFormat.format(trueTime).toString()
        Finish_minute = (textTime.toString()[0].toInt()  - '0'.toInt())*10  + textTime.toString()[1].toInt()  - '0'.toInt() + 10
        Finish_second = (textTime.toString()[3].toInt()  - '0'.toInt())*10  + textTime.toString()[4].toInt()  - '0'.toInt()

        runTimer()
        //________________________________________________________________

    }



    //________________________________________________________________timeDeclaration
    private fun runTimer() {
        val handler: Handler = Handler()
        handler.post(
            object: Runnable {
                override fun run() {
                    initTrueTime(applicationContext)            //если будут проблемы с скоротью перенести в onCreate
                    val timeFormat: DateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
                    var textTime : String = timeFormat.format(trueTime).toString()
                    var minute: Int = ( textTime.toString()[0].toInt()  - '0'.toInt())*10  +  textTime.toString()[1].toInt()  - '0'.toInt()
                    var second: Int = ( textTime.toString()[3].toInt()  - '0'.toInt())*10  +  textTime.toString()[4].toInt()  - '0'.toInt()

                    if(Finish_minute - minute >0)
                    {
                        if(Finish_second - second>=0)
                        {
                            checker_time.text  =add_null((Finish_minute - minute).toString())+ ":"+ add_null((Finish_second - second).toString())
                        }
                        else
                        {
                            checker_time.text  =add_null((Finish_minute - minute-1).toString())+":" + add_null((Finish_second + 60- second).toString())
                        }

                    }
                    else if (minute == Finish_minute && second<Finish_second)
                    {
                        checker_time.text  = "00:"+ add_null((Finish_second - second).toString())
                    }
                    else
                    {
                        checker_time.text = "time's up"
                    }
                    handler.postDelayed(this,1000)
                }
            }
        )

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
    
    //______________________________________________________________________________________________________
}