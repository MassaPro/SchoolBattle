package com.sga.schoolbattle.shop

import android.app.Activity
import android.app.Dialog
import android.widget.TextView
import com.sga.schoolbattle.MONEY
import com.sga.schoolbattle.R
import com.sga.schoolbattle.SOUND
import com.sga.schoolbattle.engine.updateEconomyParams
import com.sga.schoolbattle.mSound1
import kotlinx.android.synthetic.main.reward_dialog.*

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

        d.show()

        if (SOUND) {
            mSound1.play(1, 1F, 1F, 1, 0, 1F)
        }

        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text =
            MONEY.toString()
    }
    
    
}