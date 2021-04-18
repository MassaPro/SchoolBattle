package com.sga.schoolbattle.shop

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.sga.schoolbattle.*
import com.sga.schoolbattle.engine.updateEconomyParams
import kotlinx.android.synthetic.main.reward_dialog.*
import kotlinx.android.synthetic.main.shop_dialog.*

class RewardDialog(activity: Activity,product_id: String)
{
    var d : Dialog = Dialog(activity)
    var a : Activity = activity
    var id: String = product_id
    fun show()
    {
        if(SOUND)
        {
            mSound1.play(1, 1F, 1F, 1, 0, 1F)
        }
        d.setContentView(R.layout.reward_dialog)
        d.price_reward.text = updateEconomyParams(
            a,
           id
        ).toString() //TODO ОБЯЗАТЕЛЬНО НЕ ЗАБЫТЬ ПОМЕНЯТЬ ЗДЕСЬ ЦЕНУ
        d.close_reward.setOnClickListener {
            d.dismiss()
        }
        d.ok_reward.setOnClickListener {
            d.dismiss()
        }

        when (Design) {
            "Normal" -> {
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.cross_normal)

                d.ok_reward.setBackgroundResource(R.drawable.button)

            }
            "Egypt" -> {
                d.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_egypt)
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.close_cross)
                d.textView4.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.textView4.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                d.textView4.setTextSize(20f)
                d.price_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.price_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                d.price_reward.setTextSize(30f)
                d.ok_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.ok_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                d.ok_reward.setTextSize(20f)
            }
            "Casino" -> {
                d.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.table)
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.close_cross4)
                d.textView4.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.textView4.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                d.textView4.setTextSize(20f)
                d.textView4.setTextColor(Color.YELLOW)
                d.price_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.price_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                d.price_reward.setTextColor(Color.YELLOW)
                d.price_reward.setTextSize(35f)
                d.ok_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.ok_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                d.ok_reward.setTextSize(25f)
                d.ok_reward.setTextColor(Color.YELLOW)

            }
            "Rome" -> {
                d.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_rome)
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.close_cross3)
                d.textView4.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.textView4.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                d.textView4.setTextSize(25f)
                d.textView4.setTextColor(Color.rgb(193, 150, 63))
                d.price_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.price_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                d.price_reward.setTextColor(Color.rgb(193, 150, 63))
                d.price_reward.setTextSize(40f)
                d.ok_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.ok_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                d.ok_reward.setTextSize(25f)
                d.ok_reward.setTextColor(Color.rgb(193, 150, 63))

            }
            "Gothic" -> {
                d.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_gothic)
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.close_cross2)
                d.textView4.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.textView4.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                d.textView4.setTextSize(25f)
                d.textView4.setTextColor(Color.WHITE)
                d.price_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.price_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                d.price_reward.setTextColor(Color.WHITE)
                d.price_reward.setTextSize(40f)
                d.ok_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.ok_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                d.ok_reward.setTextSize(25f)
                d.ok_reward.setTextColor(Color.WHITE)

            }
            "Japan" -> {
                d.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_japan)
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.close_cross)
                d.textView4.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.textView4.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                d.textView4.setTextSize(20f)
                d.price_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.price_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                d.price_reward.setTextSize(25f)
                d.ok_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.ok_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                d.ok_reward.setTextSize(20f)
                d.ok_reward.setBackgroundResource(R.drawable.button)

            }
            "Noir" -> {
                d.linearLayout_parametrs_one_device.setBackgroundResource(R.drawable.background_noir)
                d.close_reward.setTextColor(Color.argb(0, 0, 0, 0))
                d.close_reward.setBackgroundResource(R.drawable.close_cross2)
                d.textView4.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.textView4.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                d.textView4.setTextSize(20f)
                d.textView4.setTextColor(Color.WHITE)
                d.price_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.price_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                d.price_reward.setTextColor(Color.WHITE)
                d.price_reward.setTextSize(30f)
                d.ok_reward.setBackgroundColor(Color.argb(0, 0, 0, 0))
                d.ok_reward.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                d.ok_reward.setTextSize(20f)
                d.ok_reward.setTextColor(Color.WHITE)


            }
        }

        d.show()

        if (SOUND) {
            mSound1.play(1, 1F, 1F, 1, 0, 1F)
        }

        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text =
            MONEY.toString()
    }
    
    
}