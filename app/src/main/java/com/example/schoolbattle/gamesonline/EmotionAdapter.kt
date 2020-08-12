package com.example.schoolbattle.gamesonline

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.*
import com.example.schoolbattle.shop.locale_context
import com.example.schoolbattle.social.D
import kotlinx.android.synthetic.main.ava_item_profile.view.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.emotion_item.view.*

fun Emotion_in_game(recyclerView: RecyclerView) {
    recyclerView.adapter = EmotionAdapter(ARRAY_OF_EMOTION)
}



class EmotionAdapter(private val DESIGN_ITEMS: MutableList<Int>):
    RecyclerView.Adapter<EmotionAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            var item = v.tag

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.emotion_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        PICTURE_EMOTION[ARRAY_OF_EMOTION[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для авы
        holder.content.text = EMOTION_TEXT[ARRAY_OF_EMOTION[position]]          //название авы

        holder.img.setOnClickListener{
            PICTURE_EMOTION[ARRAY_OF_EMOTION[position]]?.let { it1 -> locale_context?.findViewById<ImageView>(
                R.id.image_global_ava)?.setImageResource(it1)
                EMOTION = ARRAY_OF_EMOTION[position]
                dialog_find_emotion?.dismiss()
            }
        }
        with(holder.itemView) {
            tag = ARRAY_OF_EMOTION[position]
        }

    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_EMOTION.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_emotion
        var content: TextView = view.text_emotion
    }
}