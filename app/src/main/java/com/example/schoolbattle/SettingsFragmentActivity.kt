package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
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

        chouse_design.setOnClickListener {
            val intent = Intent(context, Chouse_design_Activity::class.java)
            startActivity(intent)
        }

    }



    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar)
        //setSupportActionBar(my_toolbar)




    }*/
}
