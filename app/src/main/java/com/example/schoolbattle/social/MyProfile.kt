package com.example.schoolbattle.social

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
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
import com.example.schoolbattle.engine.RatingGraph
import com.example.schoolbattle.engine.colorByRating
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.image_global_ava
import kotlinx.android.synthetic.main.activity_my_profile.profileMyName
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.ava_item_profile.view.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.design_shop_item.view.img_ava_shop



var D : Dialog? = null
class MyProfile : Fragment() {

    var ratingGraph: RatingGraph? = null
    val buildRating = mutableListOf<Int>()

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()

    }

    @SuppressLint("SetTextI18n")
    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_my_profile, container, false)
    }

    @ExperimentalStdlibApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locale_context = activity as AppCompatActivity
        //super.onActivityCreated(savedInstanceState)
        if (RATING != -1) {
            val prfs = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val username = prfs?.getString("username", "")
            profileMyName.text = "$username ($RATING)"
            profileMyName.setTextColor(colorByRating(RATING))
        }
        ratingGraph?.updateRating(buildRating)
        var dialog_find_ava = Dialog(locale_context!!)
        ratingGraph = RatingGraph(requireActivity())
        ratingGraph?.buildGraph()

        PICTURE_AVATAR[AVATAR]?.let { image_global_ava.setImageResource(it) }

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prefs?.getString("username", "")
        profileMyName.text = (username + if (RATING != -1) " ($RATING)" else "")
        if (buildRating.isEmpty()) {
            myRef.child("Users/$username/rating_history")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        buildRating.clear()
                        for (i in p0.children) {
                            buildRating.add(i.value.toString().toInt())
                            if (ratingGraph != null) {
                                ratingGraph?.updateRating(buildRating)
                            }
                        }
                    }
                })
        } else {
            if (ratingGraph != null) {
                ratingGraph?.updateRating(buildRating)
            }
        }

        image_global_ava.setOnClickListener {
            D = dialog_find_ava
            dialog_find_ava.setContentView(R.layout.activity_ava__dialog)

            dialog_find_ava.close_find_ava.setOnClickListener {
                dialog_find_ava.dismiss()
            }
            val display = locale_context!!.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y
            dialog_find_ava!!.window!!.setLayout(width*20/21, height*15/16);
            if(Design == "Egypt") {
                dialog_find_ava.constraintLayout_find_emotion_dialog.setBackgroundResource(R.drawable.background_egypt)
                dialog_find_ava.choose_ava_text.setBackgroundColor(Color.rgb(255, 230, 163))
                dialog_find_ava.choose_ava_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                dialog_find_ava.choose_ava_text.setTextColor(Color.BLACK)
            }
            else if(Design == "Casino")
            {
                dialog_find_ava.constraintLayout_find_emotion_dialog.setBackgroundResource(R.drawable.background_egypt)
                dialog_find_ava.choose_ava_text.setBackgroundResource(R.drawable.bottom_navigation_casino)
                dialog_find_ava.choose_ava_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                dialog_find_ava.choose_ava_text.setTextColor(Color.YELLOW)
            }
            else if(Design == "Rome")
            {
                dialog_find_ava.constraintLayout_find_emotion_dialog.setBackgroundResource(R.drawable.background_rome)
                dialog_find_ava.choose_ava_text.setBackgroundResource(R.drawable.bottom_navigation_rome)
                dialog_find_ava.choose_ava_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                dialog_find_ava.choose_ava_text.setTextColor(Color.rgb(193, 150, 63))
            }
            else if(Design == "Gothic")
            {
                dialog_find_ava.constraintLayout_find_emotion_dialog.setBackgroundResource(R.drawable.background_gothic)
                dialog_find_ava.choose_ava_text.setBackgroundColor(Color.BLACK)
                dialog_find_ava.choose_ava_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                dialog_find_ava.choose_ava_text.setTextColor(Color.WHITE)
            }
            else if(Design == "Japan")
            {
                dialog_find_ava.constraintLayout_find_emotion_dialog.setBackgroundResource(R.drawable.background_japan)
                dialog_find_ava.choose_ava_text.setBackgroundColor(Color.WHITE)
                dialog_find_ava.choose_ava_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                dialog_find_ava.choose_ava_text.setTextColor(Color.BLACK)
            }
            else if(Design == "Noir")
            {
                dialog_find_ava.constraintLayout_find_emotion_dialog.setBackgroundResource(R.drawable.background_noir)
                dialog_find_ava.choose_ava_text.setBackgroundColor(Color.BLACK)
                dialog_find_ava.choose_ava_text.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                dialog_find_ava.choose_ava_text.setTextColor(Color.WHITE)
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
                    myRef.child("Users").child(username).child("image").setValue(ARRAY_OF_AVATAR[position].toString())
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
        var img: ImageView = view.img_ava_profile
        var content: TextView = view.text_ava
    }
}
