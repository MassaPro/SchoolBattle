package com.example.schoolbattle.shop

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.reward_dialog.*
import kotlinx.android.synthetic.main.shop_dialog.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

var Vidos : RewardedVideoAd? = null
lateinit var mRewardedVideoAd: RewardedVideoAd
class Specially : Fragment(), RewardedVideoAdListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_designs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(locale_context)
        mRewardedVideoAd.rewardedVideoAdListener = this

        Vidos = mRewardedVideoAd
        loadRewardedVideoAd()
            // mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(locale_context)
     //   mRewardedVideoAd.rewardedVideoAdListener = this

        Vidos = mRewardedVideoAd
       // loadRewardedVideoAd()
        //     vasa = activity.setContentView(R.layout.activity_shop_fragment)

        choose_design_shop.text = "Купленные эмоции можно вы сможете использовать во время игры"
        HELPED_CONTEXT = activity

        locale_context = activity as AppCompatActivity

        //  vasa = (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.activity_shop_fragment, null)
        //   (activity as AppCompatActivity?)!!.setSupportActionBar(tb1_shop_design)
        ShopSPECIALLYsetupRecyclerView(item_design_shop)
        gamesRecycler = item_design_shop
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design_shop.adapter?.notifyDataSetChanged()




    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",          //TODO зменить на настоящий идентификатор
            AdRequest.Builder().build())
    }
    override fun onRewarded(reward: RewardItem) {

    }

    override fun onRewardedVideoAdLeftApplication() {

    }

    override fun onRewardedVideoAdClosed() {
        loadRewardedVideoAd()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {

    }

    override fun onRewardedVideoAdLoaded() {

    }

    override fun onRewardedVideoAdOpened() {

    }

    override fun onRewardedVideoStarted() {

    }

    override fun onRewardedVideoCompleted() {
        loadRewardedVideoAd()
        var dialog_reward : Dialog = locale_context!!.let { Dialog(it) }
        dialog_reward.setContentView(R.layout.reward_dialog)
        dialog_reward.price_reward.text = "10" //TODO ОБЯЗАТЕЛЬНО НЕ ЗАБЫТЬ ПОМЕНЯТЬ ЗДЕСЬ ЦЕНУ
        dialog_reward.close_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.ok_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.show()
        MONEY += 10
        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text = MONEY.toString()
    }


}








private fun ShopSPECIALLYsetupRecyclerView(recyclerView: RecyclerView) {
    recyclerView.adapter = ShopSPECIALLYsItemRecyclerViewAdapter(ARRAY_OF_SPECIALLY_SHOP)
}



class ShopSPECIALLYsItemRecyclerViewAdapter(private val DESIGN_ITEMS: MutableList<Int>):
    RecyclerView.Adapter<ShopSPECIALLYsItemRecyclerViewAdapter.ViewHolder>() {

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

        PICTURE_SPECIALLY[ARRAY_OF_SPECIALLY_SHOP[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для стиля
        holder.price.text = PRICE_OD_SPECIALLY[ARRAY_OF_SPECIALLY_SHOP[position]].toString()             //цена стиля
        holder.contentView.text = SPECIALLY_TEXT[ARRAY_OF_SPECIALLY_SHOP[position]]          //название стиля

        if(ARRAY_OF_SPECIALLY_SHOP[position] == 0)
        {
            holder.icon.setImageResource(R.drawable.money)
            holder.price.text  = "Посмотри видео и получи " + PRICE_OD_SPECIALLY[ARRAY_OF_SPECIALLY_SHOP[position]].toString()
            holder.button.text  =  "смотреть"
        }

        with(holder.itemView) {
            tag = ARRAY_OF_SPECIALLY_SHOP[position]
        }

        holder.button.setOnClickListener {
            if(ARRAY_OF_SPECIALLY_SHOP[position] == 0)           // если это видос
            {
                if (Vidos?.isLoaded!!) {
                    Vidos?.show()
                }
                else
                {
                    Toast.makeText(locale_context, "Подождите,видео еще загружается/Проверьте подключение к интернету", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_SPECIALLY_SHOP.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_ava
        var contentView: TextView = view.id_text_design_shop
        var price: TextView = view.price_design
        var icon: ImageView = view.icon_money_in_price_design
        var button: Button = view.item_button_shop_design
    }
}
