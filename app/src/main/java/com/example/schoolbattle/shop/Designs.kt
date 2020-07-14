package com.example.schoolbattle.shop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.*
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.design_item.view.*
import kotlinx.android.synthetic.main.design_shop_item.view.*

class Designs  : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_designs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
     //   (activity as AppCompatActivity?)!!.setSupportActionBar(tb1_shop_design)
        ShopDesignsetupRecyclerView(item_design_shop)
        gamesRecycler = item_design_shop
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design_shop.adapter?.notifyDataSetChanged()
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        PICTURE_STYLES[ARRAY_OF_DESIGN_SHOP[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для стиля
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN_SHOP[position]] == Design)
        {
            holder.contentView.setText(PICTURE_TEXT[ARRAY_OF_DESIGN_SHOP[position]] + "(установлено)")          //название стиля
        }
        else
        {
            holder.contentView.setText(PICTURE_TEXT[ARRAY_OF_DESIGN_SHOP[position]])          //название стиля
        }
        with(holder.itemView) {
            tag = ARRAY_OF_DESIGN_SHOP[position]
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_DESIGN_SHOP.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_design_shop
        var contentView: TextView = view.id_text_design_shop
    }
}