package com.example.schoolbattle.shop

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.argb
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
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.shop_dialog.*

var locale_context : AppCompatActivity? = null
var HELPED_CONTEXT : Context? = null

var vasa : View? = null



class Designs  : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_designs, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
   //     vasa = activity.setContentView(R.layout.activity_shop_fragment)
        HELPED_CONTEXT = activity

        locale_context = activity as AppCompatActivity

      //  vasa = (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.activity_shop_fragment, null)
     //   (activity as AppCompatActivity?)!!.setSupportActionBar(tb1_shop_design)
        ShopDesignsetupRecyclerView(item_design_shop)
        gamesRecycler = item_design_shop
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design_shop.adapter?.notifyDataSetChanged()

        //item_design_shop.setBackgroundResource(R.drawable.new_game_item_casino)

        if(Design == "Egypt")
        {
            choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.egypt) }
            choose_design_shop.setTextColor(Color.BLACK)
            choose_design_shop.textSize = 20f
        }

    }



}




private fun ShopDesignsetupRecyclerView(recyclerView: RecyclerView) {
    recyclerView.adapter = ShopDesignItemRecyclerViewAdapter(ARRAY_OF_DESIGN_SHOP)
}



class ShopDesignItemRecyclerViewAdapter(private val DESIGN_ITEMS: MutableList<Int>):
    RecyclerView.Adapter<ShopDesignItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            var item = v.tag

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.design_shop_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        PICTURE_STYLES[ARRAY_OF_DESIGN_SHOP[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для стиля
        holder.price.text = PRICE_OD_DESIGN[ARRAY_OF_DESIGN_SHOP[position]].toString()             //цена стиля
        holder.contentView.text = PICTURE_TEXT[ARRAY_OF_DESIGN_SHOP[position]]          //название стиля

        if(ARRAY_OF_DESIGN_SHOP[position] in  ARRAY_OF_DESIGN)         //если дизайн уже куплен
        {
            holder.button.text = "(КУПЛЕНО)"
            holder.button.background = null
            holder.price.text = ""
        }
        else
        {
            holder.icon.setImageResource(R.drawable.money)
        }

        with(holder.itemView) {
            tag = ARRAY_OF_DESIGN_SHOP[position]
        }

        holder.button.setOnClickListener{
            if(ARRAY_OF_DESIGN_SHOP[position] !in ARRAY_OF_DESIGN)          //если дизайн не куплен
            {

                var dialog_shop = Dialog(HELPED_CONTEXT!!)
                dialog_shop.setContentView(R.layout.shop_dialog)

                dialog_shop.price_shop.text = holder.price.text
                dialog_shop.description.text = "Купить <" + PICTURE_TEXT[ARRAY_OF_DESIGN_SHOP[position]] + "> за"

                dialog_shop.show()
                dialog_shop.buy_shop_dialog.setOnClickListener {
                    MONEY -= holder.price.text.toString().toInt()
                    ARRAY_OF_DESIGN.add(ARRAY_OF_DESIGN_SHOP[position])
                    holder.price.text = ""
                    holder.icon.setImageResource(R.drawable.nulevoe)
                    holder.button.setBackgroundColor(argb(0, 0, 0, 0))
                    holder.button.text = "(КУПЛЕНО)"
                    locale_context!!.findViewById<TextView>(R.id.money_shop_toolbar).text =
                        MONEY.toString()

                    val editor =
                        locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                            .edit()
                    editor.putString("money", MONEY.toString())
                    editor.putString("open_design", CODE(ARRAY_OF_DESIGN))
                    editor.apply()

                    dialog_shop.dismiss()
                }
                dialog_shop.button_close_2_shop_dialog.setOnClickListener {
                    dialog_shop.dismiss()
                }
                dialog_shop.button_close_shop_dialog.setOnClickListener {
                    dialog_shop.dismiss()
                }

            }
        }
    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_DESIGN_SHOP.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_ava
        var contentView: TextView = view.id_text_design_shop
        var price: TextView = view.price_design
        var icon: ImageView = view.icon_money_in_price_design
        var button: Button = view.item_button_shop_design
    }
}



