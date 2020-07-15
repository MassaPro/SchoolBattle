package com.example.schoolbattle.shop

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.ARRAY_OF_DESIGN
import com.example.schoolbattle.MONEY
import com.example.schoolbattle.R
import kotlinx.android.synthetic.main.shop_dialog.*


class Proof_of_purchase(apcompat_activity: AppCompatActivity,context:Context,type_of_purchase: String,number_of_purchase:Int,price : Int) : AppCompatActivity(){

    var Price = price
    var Type_of_purchase = type_of_purchase
    var Number_of_purchase = number_of_purchase
    var Apcompat_activity = apcompat_activity
    var c = context
    private val dialog_shop = Dialog(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_dialog)

    }
    fun showResult()
    {
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

                var t = Apcompat_activity?.supportFragmentManager?.beginTransaction()

                var mFrag: Fragment = Designs()
                //  apcompat_activity?.supportFragmentManager?.findFragmentById(R.id.shop_design_menu)?.let { it1 ->
                //        t?.remove(mFrag)
                //      }
                //       apcompat_activity?.supportFragmentManager?.findFragmentById(R.id.shop_design_menu)?.let { it1 ->
                //           t?.replace(R.id.shop_design_menu, it1)}
                //    t?.add(R.id.shop_design_menu,mFrag)
                //      t?.addToBackStack(null)
                //        t?.commit()





                val v: View = (this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.activity_designs, null)
                v.findViewById<RecyclerView>(R.id.item_design_shop).adapter!!.notifyDataSetChanged()
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
