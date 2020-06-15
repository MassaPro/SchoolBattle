package com.example.schoolbattle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs?.getString("username", "")
        profileName.text = username

        if (Design == "Egypt"){
            my_profile.setBackgroundResource(R.drawable.background_egypt)
            profileName.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
        }
        if (Design == "Casino"){
            my_profile.setBackgroundResource(R.drawable.background2_casino)
            profileName.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
        }
    }

}
