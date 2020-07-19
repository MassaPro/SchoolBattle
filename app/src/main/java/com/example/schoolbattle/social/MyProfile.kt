package com.example.schoolbattle.social

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.schoolbattle.CONTEXT
import com.example.schoolbattle.Design
import com.example.schoolbattle.R
import kotlinx.android.synthetic.main.activity_friends_list.*
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

        if (Design == "Egypt"){
            myprofile.setBackgroundResource(R.drawable.background_egypt)
        }
        else if (Design == "Casino"){
            myprofile.setBackgroundResource(R.drawable.background2_casino)
        }
        else if (Design == "Rome"){
            myprofile.setBackgroundResource(R.drawable.background_rome)
        }

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs?.getString("username", "")
        profileMyName.text = username
        profileMyFriendsIn.setOnClickListener {
            val intent = Intent(context, FriendsAndFollowers::class.java)
            intent.putExtra("friendsType", "in")
            startActivity(intent)
        }
        profileMyFriendsOut.setOnClickListener {
            val intent = Intent(context, FriendsAndFollowers::class.java)
            intent.putExtra("friendsType", "out")
            startActivity(intent)
        }
    }
}
