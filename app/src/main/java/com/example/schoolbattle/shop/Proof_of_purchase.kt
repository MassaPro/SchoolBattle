package com.example.schoolbattle.shop

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Window
import androidx.core.content.res.ResourcesCompat
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.design_shop_item.*
import kotlinx.android.synthetic.main.parametrs_one_divice.*
import kotlinx.android.synthetic.main.parametrs_one_divice.linearLayout_parametrs_one_device
import kotlinx.android.synthetic.main.shop_dialog.*

class Proof_of_purchase(context:Context,type_of_purchase: String,number_of_purchase:Int,price : Int) {
    var Price = price
    var Type_of_purchase = type_of_purchase
    var Number_of_purchase = number_of_purchase
    var c = context
    private val dialog_shop = Dialog(context)
    fun showResult() {
        dialog_shop.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog_shop.setCancelable(false)
        dialog_shop.setCanceledOnTouchOutside(true)
        dialog_shop.setContentView(R.layout.shop_dialog)

        dialog_shop.price_shop.text = Price.toString()




        dialog_shop.buy_shop_dialog.setOnClickListener{
            if(MONEY>=Price)
            {
                MONEY -= Price
                if(Type_of_purchase == "Design")
                {
                    ARRAY_OF_DESIGN.add(Number_of_purchase)
                    dialog_shop.dismiss()
                }
            }
        }

        dialog_shop.show()
        dialog_shop.button_close_2_shop_dialog.setOnClickListener {
            dialog_shop.dismiss()
        }
        dialog_shop.button_close_shop_dialog.setOnClickListener {
            dialog_shop.dismiss()
        }
    }
    fun delete() {
        dialog_shop.dismiss()
    }
}
