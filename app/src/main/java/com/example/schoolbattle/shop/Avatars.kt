package com.example.schoolbattle.shop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.Design
import com.example.schoolbattle.FRIENDS
import com.example.schoolbattle.R
import com.example.schoolbattle.social.ProfileUserActivity
import kotlinx.android.synthetic.main.activity_friends_item.view.*
import kotlinx.android.synthetic.main.activity_friends_list.*

class Avatars : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_avatars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}
