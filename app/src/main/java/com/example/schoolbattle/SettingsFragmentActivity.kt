package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_settings_fragment.*


class SettingsFragmentActivity : Fragment() {
    private var dialog_parametrs: Show_language_selection? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar_settings)

        if (Design == "Normal") {
            settings_menu.setBackgroundColor(Color.WHITE)
        }
        if (Design == "Casino") {
            settings_menu.setBackgroundResource(R.drawable.background_casino)
            my_toolbar_settings.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(20f)

            languageChange.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            languageChange.setTextColor(Color.YELLOW)
            languageChange.setTextSize(20f)

            languageSelected.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            languageSelected.setTextColor(Color.YELLOW)
            languageSelected.setTextSize(20f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            soundSwitch.setTextColor(Color.YELLOW)
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(20f)
        }
        if (Design == "Egypt") {
            settings_menu.setBackgroundResource(R.drawable.background_egypt)
            my_toolbar_settings.setBackgroundColor(rgb(224,164,103));

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(18f)

            languageChange.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //languageChange.setTextColor(Color.YELLOW)
            languageChange.setTextSize(20f)

            languageSelected.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //languageSelected.setTextColor(Color.YELLOW)
            languageSelected.setTextSize(20f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //soundSwitch.setTextColor(Color.YELLOW)
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(20f)
        }


        logOutSettings.setOnClickListener {
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            editor?.putString("username", "")
            editor?.apply()
            recyclerSet.clear()
            val intent = Intent(activity, NullActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        soundSwitch.isChecked = true

        soundSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //
            } else {
                //
            }
        })

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var data_from_memory = prefs?.getString("language", "").toString()
        if (data_from_memory == "") {
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            editor?.putString("language", "EN")
            editor?.apply()
            data_from_memory = "EN"
        }

        languageSelected.text = data_from_memory

        languageChange.setOnClickListener {
            //Toast.makeText(activity,"Change text", Toast.LENGTH_LONG).show()
            dialog_parametrs = activity?.let { it1 -> Show_language_selection(it1) }
            activity?.let { it1 -> dialog_parametrs?.showResult_window(it1) }


            /*val handler = android.os.Handler()
            val delay = 1000 //milliseconds
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                    var data_from_memory = prefs?.getString("language", "").toString()
                    languageSelected.text = data_from_memory
                    handler.postDelayed(this, delay.toLong())
                }
            }, delay.toLong())*/


        }

        choose_design.setOnClickListener {
            val intent = Intent(context, Choose_design_Activity::class.java)
            startActivity(intent)
        }

    }



    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar)
        //setSupportActionBar(my_toolbar)




    }*/
}
