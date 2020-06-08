package com.example.schoolbattle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_settings_fragment.*


class SettingsFragmentActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_settings_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CONTEXT = requireActivity()
        //(activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
    }
}
