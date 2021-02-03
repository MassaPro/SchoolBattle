package com.sga.schoolbattle.shop

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.sga.schoolbattle.*
import com.sga.schoolbattle.engine.updateEconomyParams
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.reward_dialog.*
import kotlin.math.min

var Vidos : RewardedVideoAd? = null

var bp : BillingProcessor?  = null

lateinit var mRewardedVideoAd: RewardedVideoAd

class Specially : Fragment(), RewardedVideoAdListener , BillingProcessor.IBillingHandler{


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_designs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        locale_context = activity as AppCompatActivity
        mSound1.load(locale_context, R.raw.money, 1);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(locale_context)
        mRewardedVideoAd.rewardedVideoAdListener = this


        Vidos = mRewardedVideoAd


        bp = BillingProcessor.newBillingProcessor(
            locale_context,
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkher/MRJftx38FWROGozlffv10zjmTwcOKhTTXoeqo/uHogVVVJbmbR65NNCFegHvJp+GC1sGai+VQ+nc2m7b5D3rtBe6LJuayhBEuq7tvCrINzoswB1Z9O1pJNwJFie3IPHe7GY6zP4wCeCSL05ZcPkogMGH/B/+qBS0ZWvXS3rlqpBxwu36kKMkQcU27RuMaFTc6lJ5WocDt1ruGHfmAAMEvrr2HcJfnUbceJPUBuJLAMUd6WZif0qlIe3Tl9rkZD50RVRgG6TVpNnHcw3A4+A+z4m2CSq3wMtvRUsT/pl/L83eSqltKLgrPSCvA4OjksPm4G9cqfw63BkmtUc7QIDAQAB",
            this
        )
        bp?.initialize()


        choose_design_shop.text = "Разное    "





        ShopSPECIALLYsetupRecyclerView(item_design_shop)
        gamesRecycler = item_design_shop
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design_shop.adapter?.notifyDataSetChanged()







        when (Design) {
            "Egypt" -> {
                choose_design_shop.typeface = locale_context?.let {
                    ResourcesCompat.getFont(
                        it,
                        R.font.egypt
                    )
                }
                choose_design_shop.setTextColor(Color.BLACK)
                choose_design_shop.textSize = 20f
            }
            "Casino" -> {
                choose_design_shop.typeface = locale_context?.let {
                    ResourcesCompat.getFont(
                        it,
                        R.font.casino
                    )
                }
                choose_design_shop.setTextColor(Color.YELLOW)
                choose_design_shop.textSize = 20f
            }
            "Rome" -> {
                choose_design_shop.typeface = locale_context?.let {
                    ResourcesCompat.getFont(
                        it,
                        R.font.rome
                    )
                }
                choose_design_shop.setTextColor(Color.rgb(193, 150, 63))
                choose_design_shop.textSize = 25f
            }
            "Gothic" -> {
                choose_design_shop.typeface = locale_context?.let {
                    ResourcesCompat.getFont(
                        it,
                        R.font.gothic
                    )
                }
                choose_design_shop.setTextColor(Color.WHITE)
                choose_design_shop.textSize = 25f
            }
            "Japan" -> {
                choose_design_shop.typeface = locale_context?.let {
                    ResourcesCompat.getFont(
                        it,
                        R.font.japan
                    )
                }
                choose_design_shop.setTextColor(Color.BLACK)
                choose_design_shop.textSize = 20f
            }
            "Noir" -> {
                choose_design_shop.typeface = locale_context?.let {
                    ResourcesCompat.getFont(
                        it,
                        R.font.noir
                    )
                }
                choose_design_shop.setTextColor(Color.WHITE)
                choose_design_shop.textSize = 20f
            }


        }


        choose_design_shop.setOnClickListener {
            bp?.purchase(activity,"android.test.purchased")
        }


        

    }







    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(
            "ca-app-pub-3940256099942544/5224354917",          //TODO зменить на настоящий идентификатор
            AdRequest.Builder().build()
        )

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

        var dialog_reward : Dialog = Dialog(locale_context!!)
        dialog_reward.setContentView(R.layout.reward_dialog)
        dialog_reward.price_reward.text = updateEconomyParams(requireActivity(), "award").toString() //TODO ОБЯЗАТЕЛЬНО НЕ ЗАБЫТЬ ПОМЕНЯТЬ ЗДЕСЬ ЦЕНУ
        dialog_reward.close_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.ok_reward.setOnClickListener {
            dialog_reward.dismiss()
        }

        dialog_reward.show()

        if(SOUND)
        {
            mSound1.play(1, 1F, 1F, 1, 0, 1F)
        }


        Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_LONG).show()
        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text = MONEY.toString()
    }

    override fun onBillingInitialized() {
        Toast.makeText(locale_context, "гавно", Toast.LENGTH_LONG).show()
    }


    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        Log.d("FOPOR", productId)
        if(productId == "android.test.purchased") // потом заменить на настоящий идентификтор
        {
            PREMIUM = true;
            val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
            editor.putString("premium", "1")
            editor.apply()
        }
        Toast.makeText(locale_context, "гавно", Toast.LENGTH_LONG).show()
        item_design_shop.adapter?.notifyDataSetChanged()
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        Log.d("FOPOR", error?.message)
    }

    override fun onPurchaseHistoryRestored() {
        /*
    * Called when purchase history was restored and the list of all owned PRODUCT ID's
    * was loaded from Google Play
    */
    }

    override fun onDestroy() {
        bp?.release()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (bp!!.handleActivityResult(requestCode, resultCode, data)) super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
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

        PICTURE_SPECIALLY[ARRAY_OF_SPECIALLY_SHOP[position]]?.let { holder.img.setBackgroundResource(
            it
        ) }     //картинка для стиля
        holder.price.text = PRICE_OD_SPECIALLY[ARRAY_OF_SPECIALLY_SHOP[position]].toString()             //цена стиля
        holder.contentView.text = SPECIALLY_TEXT[ARRAY_OF_SPECIALLY_SHOP[position]]          //название стиля

        if(ARRAY_OF_SPECIALLY_SHOP[position] == 0)
        {
            holder.icon.setImageResource(R.drawable.money)
            val getter = locale_context?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val capital = getter?.getString("number_capital", "500").toString().toInt()
            holder.price.text  = "Посмотри видео и получи ${min(capital / 100 * 4, 5000)}"
            holder.button.text  =  "смотреть"
        }
        holder.icon.setImageResource(R.drawable.money)
        with(holder.itemView) {
            tag = ARRAY_OF_SPECIALLY_SHOP[position]
        }

        when (Design) {
            "Normal" -> {
                holder.button.setBackgroundResource(R.drawable.button)
            }
            "Egypt" -> {
                holder.background_item.setBackgroundColor(Color.rgb(255, 230, 163))

                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.BLACK)   //цвет
                holder.button.setBackgroundColor(Color.argb(0, 0, 0, 0))
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.egypt)

                holder.price.textSize = 12f        //так задаешь размер
                holder.price.setTextColor(Color.BLACK)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.egypt)

                holder.contentView.textSize = 14f        //так задаешь размер
                holder.contentView.setTextColor(Color.BLACK)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(
                    locale_context!!,
                    R.font.egypt
                )
            }
            "Casino" -> {
                holder.background_item.setBackgroundResource(R.drawable.table)
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.YELLOW)   //цвет
                holder.button.setBackgroundColor(Color.argb(0, 0, 0, 0))
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.casino)

                holder.price.textSize = 13f        //так задаешь размер
                holder.price.setTextColor(Color.YELLOW)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.casino)

                holder.contentView.textSize = 17.5f        //так задаешь размер
                holder.contentView.setTextColor(Color.YELLOW)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(
                    locale_context!!,
                    R.font.casino
                )
            }
            "Rome" -> {
                holder.background_item.setBackgroundResource(R.drawable.bottom_navigation_rome)
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setBackgroundColor(Color.argb(0, 0, 0, 0))
                holder.button.setTextColor(Color.rgb(193, 150, 63))   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)

                holder.price.textSize = 15f        //так задаешь размер
                holder.price.setTextColor(Color.rgb(193, 150, 63))   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)

                holder.contentView.textSize = 16.5f        //так задаешь размер
                holder.contentView.setTextColor(Color.rgb(193, 150, 63))   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)
            }
            "Gothic" -> {
                holder.background_item.setBackgroundColor(Color.rgb(20, 20, 20))
                holder.button.textSize = 16f        //так задаешь размер
                holder.button.setBackgroundColor(Color.rgb(30, 30, 30))
                holder.button.setTextColor(Color.WHITE)   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.gothic)

                holder.price.textSize = 15f        //так задаешь размер
                holder.price.setTextColor(Color.WHITE)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.gothic)

                holder.contentView.textSize = 19f        //так задаешь размер
                holder.contentView.setTextColor(Color.WHITE)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(
                    locale_context!!,
                    R.font.gothic
                )
            }
            "Japan" -> {
                holder.background_item.setBackgroundColor(Color.WHITE)
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.BLACK)   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.japan)

                holder.price.textSize = 13f        //так задаешь размер
                holder.price.setTextColor(Color.BLACK)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.japan)

                holder.contentView.textSize = 14f        //так задаешь размер
                holder.contentView.setTextColor(Color.BLACK)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(
                    locale_context!!,
                    R.font.japan
                )
            }
            "Noir" -> {
                holder.background_item.setBackgroundColor(Color.rgb(20, 20, 20))
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setBackgroundColor(Color.rgb(30, 30, 30))
                holder.button.setTextColor(Color.WHITE)   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)

                holder.price.textSize = 13f        //так задаешь размер
                holder.price.setTextColor(Color.WHITE)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)

                holder.contentView.textSize = 18f        //так задаешь размер
                holder.contentView.setTextColor(Color.WHITE)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)
            }
        }
        if(PREMIUM && position==1)
        {
            holder.button.background = null
            holder.button.text = "(КУПЛЕНО)"
        }
        holder.button.setOnClickListener {
            if(ARRAY_OF_SPECIALLY_SHOP[position] == 0)           // если это видос
            {
                if (Vidos?.isLoaded!!) {

                    Vidos?.show()

                }
                else
                {
                    Toast.makeText(
                        locale_context,
                        "Подождите,видео еще загружается/Проверьте подключение к интернету",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else if(ARRAY_OF_SPECIALLY_SHOP[position] == 1)           // если это премиум
            {
               // bp!!.consumePurchase("android.test.purchased")  // для повторных покупо



                bp?.purchase(locale_context, "android.test.purchased")

                Log.d("FOPOR", bp?.getPurchaseListingDetails("android.test.purchased").toString())
                
            }

        }
    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_SPECIALLY_SHOP.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_ava_shop
        var contentView: TextView = view.id_text_design_shop
        var price: TextView = view.price_design
        var icon: ImageView = view.icon_money_in_price_design
        var button: Button = view.item_button_shop_design
        var background_item: CardView = view.card_design_shop
    }
}