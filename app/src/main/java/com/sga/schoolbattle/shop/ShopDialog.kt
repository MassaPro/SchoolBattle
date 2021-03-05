package com.sga.schoolbattle.shop

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.argb
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.shop_dialog.*
import java.text.FieldPosition

class ShopDialog(activity: Activity)
{
    var activity: Activity = activity
    var dialog_shop  = Dialog(activity)

    fun do_desing(position: Int)
    {
        dialog_shop.setContentView(R.layout.shop_dialog)
        dialog_shop.button_close_2_shop_dialog.text = translate("ОТМЕНА")
        dialog_shop.buy_shop_dialog.text  = translate("КУПИТЬ")

        dialog_shop.show()
        dialog_shop.button_close_2_shop_dialog.setOnClickListener {
            dialog_shop.dismiss()
        }
        dialog_shop.button_close_shop_dialog.setOnClickListener {
            dialog_shop.dismiss()
        }
        when (Design) {
            "Normal" -> {
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.cross_normal)

                dialog_shop.buy_shop_dialog.setBackgroundResource(R.drawable.button)
                dialog_shop.button_close_2_shop_dialog.setBackgroundResource(R.drawable.button)
            }
            "Egypt" -> {
                dialog_shop.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_egypt)
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.close_cross)
                dialog_shop.description.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                dialog_shop.description.setTextSize(20f)
                dialog_shop.price_shop.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.price_shop.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                dialog_shop.price_shop.setTextSize(30f)
                dialog_shop.buy_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.buy_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                dialog_shop.buy_shop_dialog.setTextSize(20f)
                dialog_shop.button_close_2_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.button_close_2_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                dialog_shop.button_close_2_shop_dialog.setTextSize(20f)

//                dialog_shop.buy_shop_dialog.setTranslationY(50f)
              //  dialog_shop.button_close_2_shop_dialog.setTranslationY(50f)

            }
            "Casino" -> {
                dialog_shop.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.table)
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.close_cross4)
                dialog_shop.description.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                dialog_shop.description.setTextSize(20f)
                dialog_shop.description.setTextColor(Color.YELLOW)
                dialog_shop.price_shop.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.price_shop.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                dialog_shop.price_shop.setTextColor(Color.YELLOW)
                dialog_shop.price_shop.setTextSize(35f)
                dialog_shop.buy_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.buy_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                dialog_shop.buy_shop_dialog.setTextSize(25f)
                dialog_shop.buy_shop_dialog.setTextColor(Color.YELLOW)
                dialog_shop.button_close_2_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.button_close_2_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                dialog_shop.button_close_2_shop_dialog.setTextSize(25f)
                dialog_shop.button_close_2_shop_dialog.setTextColor(Color.YELLOW)

       //         dialog_shop.buy_shop_dialog.setTranslationY(80f)
         //       dialog_shop.button_close_2_shop_dialog.setTranslationY(80f)

            }
            "Rome" -> {
                dialog_shop.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_rome)
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.close_cross3)
                dialog_shop.description.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                dialog_shop.description.setTextSize(25f)
                dialog_shop.description.setTextColor(Color.rgb(193, 150, 63))
                dialog_shop.price_shop.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.price_shop.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                dialog_shop.price_shop.setTextColor(Color.rgb(193, 150, 63))
                dialog_shop.price_shop.setTextSize(40f)
                dialog_shop.buy_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.buy_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                dialog_shop.buy_shop_dialog.setTextSize(25f)
                dialog_shop.buy_shop_dialog.setTextColor(Color.rgb(193, 150, 63))
                dialog_shop.button_close_2_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.button_close_2_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                dialog_shop.button_close_2_shop_dialog.setTextSize(25f)
                dialog_shop.button_close_2_shop_dialog.setTextColor(Color.rgb(193, 150, 63))

         //       dialog_shop.buy_shop_dialog.setTranslationY(60f)
    //            dialog_shop.button_close_2_shop_dialog.setTranslationY(60f)

            }
            "Gothic" -> {
                dialog_shop.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_gothic)
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.close_cross2)
                dialog_shop.description.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                dialog_shop.description.setTextSize(25f)
                dialog_shop.description.setTextColor(Color.WHITE)
                dialog_shop.price_shop.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.price_shop.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                dialog_shop.price_shop.setTextColor(Color.WHITE)
                dialog_shop.price_shop.setTextSize(40f)
                dialog_shop.buy_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.buy_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                dialog_shop.buy_shop_dialog.setTextSize(25f)
                dialog_shop.buy_shop_dialog.setTextColor(Color.WHITE)
                dialog_shop.button_close_2_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.button_close_2_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                dialog_shop.button_close_2_shop_dialog.setTextSize(25f)
                dialog_shop.button_close_2_shop_dialog.setTextColor(Color.WHITE)

          //      dialog_shop.price_shop.setTranslationY(-80f)
        //        dialog_shop.imageView2.setTranslationY(-80f)
       //         dialog_shop.description.setTranslationY(-80f)
        //        dialog_shop.buy_shop_dialog.setTranslationY(-80f)
      //          dialog_shop.button_close_2_shop_dialog.setTranslationY(-80f)

            }
            "Japan" -> {
                dialog_shop.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_japan)
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.close_cross)
                dialog_shop.description.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                dialog_shop.description.setTextSize(20f)
                dialog_shop.price_shop.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.price_shop.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                dialog_shop.price_shop.setTextSize(25f)
                dialog_shop.buy_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.buy_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                dialog_shop.buy_shop_dialog.setTextSize(20f)
                dialog_shop.button_close_2_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.button_close_2_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                dialog_shop.button_close_2_shop_dialog.setTextSize(20f)

                dialog_shop.buy_shop_dialog.setBackgroundResource(R.drawable.button)
                dialog_shop.button_close_2_shop_dialog.setBackgroundResource(R.drawable.button)

   //             dialog_shop.buy_shop_dialog.setTranslationY(100f)
      //          dialog_shop.button_close_2_shop_dialog.setTranslationY(100f)

            }
            "Noir" -> {
                dialog_shop.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_noir)
                dialog_shop.button_close_shop_dialog.setTextColor(argb(0,0,0,0))
                dialog_shop.button_close_shop_dialog.setBackgroundResource(R.drawable.close_cross2)
                dialog_shop.description.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.description.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                dialog_shop.description.setTextSize(20f)
                dialog_shop.description.setTextColor(Color.WHITE)
                dialog_shop.price_shop.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.price_shop.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                dialog_shop.price_shop.setTextColor(Color.WHITE)
                dialog_shop.price_shop.setTextSize(30f)
                dialog_shop.buy_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.buy_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                dialog_shop.buy_shop_dialog.setTextSize(20f)
                dialog_shop.buy_shop_dialog.setTextColor(Color.WHITE)
                dialog_shop.button_close_2_shop_dialog.setBackgroundColor(argb(0,0,0,0))
                dialog_shop.button_close_2_shop_dialog.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                dialog_shop.button_close_2_shop_dialog.setTextSize(20f)
                dialog_shop.button_close_2_shop_dialog.setTextColor(Color.WHITE)

         //       dialog_shop.buy_shop_dialog.setTranslationY(100f)
      //          dialog_shop.button_close_2_shop_dialog.setTranslationY(100f)

            }
        }
    }
    fun show1(holder: ProfileAvatarsItemRecyclerViewAdapter.ViewHolder,position : Int)
    {
        do_desing(position)
        dialog_shop.description.text = AVATAR_TEXT[ARRAY_OF_AVATAR_SHOP[position]]?.let {
            translate_avatar(
                it
            )
        }
        dialog_shop.price_shop.text = holder.price.text
        dialog_shop.buy_shop_dialog.setOnClickListener {
            if(SOUND)
            {
                mSound1.play(1, 1F, 1F, 1, 0, 1F)
            }
            dialog_shop.buy_shop_dialog.isClickable = false
            MONEY -= holder.price.text.toString().replace(" ","").toInt()
            val ARRAY_OF_AVATAR_COPY = ARRAY_OF_AVATAR.toMutableList()
            ARRAY_OF_AVATAR_COPY.add(ARRAY_OF_AVATAR_SHOP[position])
            //TODO MONEY передать в файрбейс
            //TODO ARRAY_OF_AVATAR передать в файербейс
            val username = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "").toString()
            val pushMap = mapOf(
                "Users/$username/money" to MONEY,
                "Users/$username/array_of_avatars" to CODE(ARRAY_OF_AVATAR_COPY)
            )
            myRef.updateChildren(pushMap).addOnSuccessListener {
                ARRAY_OF_AVATAR.add(ARRAY_OF_AVATAR_SHOP[position])
                Toast.makeText(activity, "Success to do operation", Toast.LENGTH_LONG)
                    .show()
                //TODO MONEY передать в базу ------- сделано в строках 256 - 262
                //TODO ARRAY_OF_EMOTION передать в базу ------- сделано в строках 256 - 262
                holder.price.text = ""
                holder.icon.setImageResource(R.drawable.nulevoe)
                holder.button.setBackgroundColor(Color.argb(0, 0, 0, 0))
                holder.button.text = "(КУПЛЕНО)"
                locale_context!!.findViewById<TextView>(R.id.money_shop_toolbar).text =
                    MONEY.toString()

                val editor =
                    locale_context!!.getSharedPreferences(
                        "UserData",
                        Context.MODE_PRIVATE
                    )
                        .edit()
                editor.putString("money", MONEY.toString())
                editor.putString("open_avatars", CODE(ARRAY_OF_AVATAR))
                editor.apply()

                dialog_shop.dismiss()
            }
        }
        PICTURE_AVATAR[position]?.let { dialog_shop.imageView8.setImageResource(it)}

    }
    fun show2(holder: ShopDesignItemRecyclerViewAdapter.ViewHolder,position: Int)
    {
        do_desing(position)
        dialog_shop.price_shop.text = holder.price.text
        dialog_shop.description.text = PICTURE_TEXT[ARRAY_OF_DESIGN_SHOP[position]]
        dialog_shop.buy_shop_dialog.setOnClickListener {
            if(SOUND)
            {
                mSound1.play(1, 1F, 1F, 1, 0, 1F)
            }
            MONEY -= holder.price.text.toString().replace(" ","").toInt()

            val ARRAY_OF_DESIGN_COPY = ARRAY_OF_DESIGN.toMutableList()
            ARRAY_OF_DESIGN_COPY.add(ARRAY_OF_DESIGN_SHOP[position])
            dialog_shop.buy_shop_dialog.isClickable = false
            val username = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "").toString()
            //TODO MONEY передать в базу
            //TODO ARRAY_OF_DESIGN передать в базу
            val pushMap = mapOf(
                "Users/$username/money" to MONEY,
                "Users/$username/array_of_designs" to CODE(ARRAY_OF_DESIGN_COPY)
            )
            myRef.updateChildren(pushMap).addOnSuccessListener {
                ARRAY_OF_DESIGN.add(ARRAY_OF_DESIGN_SHOP[position])
                holder.price.text = ""
                holder.icon.setImageResource(R.drawable.nulevoe)
                holder.button.setBackgroundColor(argb(0, 0, 0, 0))
                holder.button.text = "(" + translate("КУПЛЕНО") +")"
                locale_context!!.findViewById<TextView>(R.id.money_shop_toolbar).text =
                    MONEY.toString()

                val editor =
                    locale_context!!.getSharedPreferences(
                        "UserData",
                        Context.MODE_PRIVATE
                    )
                        .edit()
                editor.putString("money", MONEY.toString())
                editor.putString("open_design", CODE(ARRAY_OF_DESIGN))
                editor.apply()

                dialog_shop.dismiss()
            }
        }
        PICTURE_STYLES[position]?.let { dialog_shop.imageView8.setImageResource(it) }
    }
    fun show3(holder: ShopEMOTIONsItemRecyclerViewAdapter.ViewHolder,position: Int)
    {
        do_desing(position)
        dialog_shop.price_shop.text = holder.price.text
        dialog_shop.description.text = EMOTION_TEXT[ARRAY_OF_EMOTION_SHOP[position]]
        dialog_shop.buy_shop_dialog.setOnClickListener {
            if(SOUND)
            {
                mSound1.play(1, 1F, 1F, 1, 0, 1F)
            }
            dialog_shop.buy_shop_dialog.isClickable = false
            val username = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("username", "").toString()
            MONEY -= holder.price.text.toString().replace(" ","").toInt()
            val ARRAY_OF_EMOTION_COPY = ARRAY_OF_EMOTION.toMutableList()
            ARRAY_OF_EMOTION_COPY.add(ARRAY_OF_EMOTION_SHOP[position])
            val pushMap = mapOf(
                "Users/$username/money" to MONEY,
                "Users/$username/array_of_emotions" to CODE(ARRAY_OF_EMOTION_COPY)
            )
            myRef.updateChildren(pushMap).addOnSuccessListener {
                ARRAY_OF_EMOTION.add(ARRAY_OF_EMOTION_SHOP[position])
                Toast.makeText(activity, "Success to do operation", Toast.LENGTH_LONG).show()
                //TODO MONEY передать в базу ------- сделано
                //TODO ARRAY_OF_EMOTION передать в базу ------- сделано
                holder.price.text = ""
                holder.icon.setImageResource(R.drawable.nulevoe)
                holder.button.setBackgroundColor(argb(0, 0, 0, 0))
                holder.button.text = "(" + translate("КУПЛЕНО")+")"
                locale_context!!.findViewById<TextView>(R.id.money_shop_toolbar).text =
                    MONEY.toString()

                val editor =
                    locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                        .edit()
                editor.putString("money", MONEY.toString())
                editor.putString("open_emotions", CODE(ARRAY_OF_EMOTION))
                editor.apply()

                dialog_shop.dismiss()
            }
        }
        PICTURE_EMOTION[position]?.let { dialog_shop.imageView8.setImageResource(it) }
    }



}