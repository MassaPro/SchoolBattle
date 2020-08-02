package com.example.schoolbattle.social

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.*
import com.example.schoolbattle.shop.HELPED_CONTEXT
import com.example.schoolbattle.shop.locale_context
import kotlinx.android.synthetic.main.activity_ava__dialog.*
import com.example.schoolbattle.CONTEXT
import com.example.schoolbattle.Design
import com.example.schoolbattle.R
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.ava_item_profile.view.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.design_shop_item.view.img_ava



var D : Dialog? = null
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
        locale_context = activity as AppCompatActivity
        super.onViewCreated(view, savedInstanceState)

        PICTURE_AVATAR[AVATAR]?.let { image_global_ava.setImageResource(it) }
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


        image_global_ava.setOnClickListener {
            var dialog_find_ava = Dialog(locale_context!!)
            D = dialog_find_ava
            dialog_find_ava.setContentView(R.layout.activity_ava__dialog)

            dialog_find_ava.close_find_ava.setOnClickListener {
                dialog_find_ava.dismiss()
            }
            dialog_find_ava.show()
            ProfileAvatarsetupRecyclerView(dialog_find_ava.item_profile_ava)
            gamesRecycler = dialog_find_ava.item_profile_ava
            gamesRecycler.isNestedScrollingEnabled = false;

            gamesRecycler.layoutManager = GridLayoutManager(locale_context, 3)
            dialog_find_ava.item_profile_ava.adapter?.notifyDataSetChanged()
        }
    }
}

private fun ProfileAvatarsetupRecyclerView(recyclerView: RecyclerView) {
    recyclerView.adapter = ProfileAvatarsItemRecyclerViewAdapter(ARRAY_OF_AVATAR)
}



class ProfileAvatarsItemRecyclerViewAdapter(private val DESIGN_ITEMS: MutableList<Int>):
    RecyclerView.Adapter<ProfileAvatarsItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            var item = v.tag

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ava_item_profile, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        PICTURE_AVATAR[ARRAY_OF_AVATAR[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для авы
        holder.content.text = AVATAR_TEXT[ARRAY_OF_AVATAR[position]]          //название авы

        holder.img.setOnClickListener{
            PICTURE_AVATAR[ARRAY_OF_AVATAR[position]]?.let { it1 -> locale_context?.findViewById<ImageView>(R.id.image_global_ava)?.setImageResource(it1)
                AVATAR = ARRAY_OF_AVATAR[position]
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("avatar_number", ARRAY_OF_AVATAR[position].toString())

                val prefs = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                var username = prefs?.getString("username", "")
                if (username != null) {
                    myRef.child("users").child(username).child("image").setValue(ARRAY_OF_AVATAR[position].toString())
                }

                D?.dismiss()
            }
        }
        with(holder.itemView) {
            tag = ARRAY_OF_AVATAR[position]
        }

    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_AVATAR.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_ava
        var content: TextView = view.text_ava
    }
}
