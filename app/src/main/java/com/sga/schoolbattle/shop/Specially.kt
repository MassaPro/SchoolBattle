package com.sga.schoolbattle.shop

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.argb
import android.os.Bundle
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
import com.android.billingclient.api.*

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import java.io.IOException
import java.util.ArrayList
import kotlin.math.min

var Vidos : RewardedVideoAd? = null
var billingClient: BillingClient? = null
var p1 : PurchasesUpdatedListener? = null

lateinit var mRewardedVideoAd: RewardedVideoAd

var reward : RewardDialog? = null
class Specially : Fragment(), RewardedVideoAdListener, PurchasesUpdatedListener {


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
        p1 = this as PurchasesUpdatedListener
        mSound1.load(locale_context, R.raw.money, 1);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(locale_context)
        mRewardedVideoAd.rewardedVideoAdListener = this
        Vidos = mRewardedVideoAd


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


        billingClient = BillingClient.newBuilder(requireActivity())
            .enablePendingPurchases().setListener(this).build()
        billingClient!!.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val queryPurchase = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
                    val queryPurchases = queryPurchase.purchasesList
                    if (queryPurchases != null && queryPurchases.size > 0) {
                        handlePurchases(queryPurchases)
                    }
                }
            }

            override fun onBillingServiceDisconnected() {}
        })

    }

    //__________________видосы
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
    override fun onRewardedVideoStarted() {}
    override fun onRewardedVideoCompleted() {

        loadRewardedVideoAd()

        var reward  = locale_context?.let {
            RewardDialog(
                it,
                PRODUCT_ID
            )
        }
        reward?.show()



        Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_LONG).show()
        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text = MONEY.toString()
    }
    //________________



    private val preferenceObject: SharedPreferences
        private get() = locale_context!!.getSharedPreferences(PREF_FILE, 0)
    private val preferenceEditObject: SharedPreferences.Editor
        private get() {
            val pref = locale_context!!.getSharedPreferences(PREF_FILE, 0)
            return pref.edit()
        }
    private val purchaseCountValueFromPref: Int
        private get() = preferenceObject.getInt(PURCHASE_KEY, 0)

    private fun savePurchaseCountValueToPref(value: Int) {
        preferenceEditObject.putInt(PURCHASE_KEY, value).commit()
    }

    //initiate purchase on consume button click




    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        //if item newly purchased
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases)
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchasesResult = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
            val alreadyPurchases = queryAlreadyPurchasesResult.purchasesList
            alreadyPurchases?.let { handlePurchases(it) }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(locale_context, "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(locale_context, "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            //if item is purchased
            if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(locale_context, "Error : Invalid Purchase", Toast.LENGTH_SHORT).show()
                    return
                }
                else {
                    if (PRODUCT_ID == "premium") {
                        PREMIUM = true
                        val editor =
                            locale_context?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
                                ?.edit()
                        editor?.putString("premium", "1")
                        editor?.apply()
                        locale_context?.item_design_shop?.adapter?.notifyItemChanged(1)
                    } else {
                        var reward  = locale_context?.let {
                            RewardDialog(
                                it,
                                PRODUCT_ID
                            )
                        }
                        reward?.show()
                    }
                }

                // else purchase is valid
                //if item is purchased and not consumed
                if (!purchase.isAcknowledged) {
                    val consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    billingClient!!.consumeAsync(consumeParams, consumeListener)
                }
            } else if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                Toast.makeText(
                    locale_context,
                    "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show()
            } else if (PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                Toast.makeText(locale_context, "Purchase Status Unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var consumeListener = ConsumeResponseListener { billingResult, purchaseToken ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            val consumeCountValue = purchaseCountValueFromPref + 1
            savePurchaseCountValueToPref(consumeCountValue)
            Toast.makeText(locale_context,"Item Consumed", Toast.LENGTH_SHORT).show()
        //    consumeCount!!.text = "Item Consumed $purchaseCountValueFromPref Time(s)"
        }
    }


    /**
     * Verifies that the purchase was signed correctly for this developer's public key.
     *
     * Note: It's strongly recommended to perform such check on your backend since hackers can
     * replace this method with "constant true" if they decompile/rebuild your app.
     *
     */
    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            //for old playconsole
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //for new play console
            //To get key go to Developer Console > Select your app > Monetize > Monetization setup
            val base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkher/MRJftx38FWROGozlffv10zjmTwcOKhTTXoeqo/uHogVVVJbmbR65NNCFegHvJp+GC1sGai+VQ+nc2m7b5D3rtBe6LJuayhBEuq7tvCrINzoswB1Z9O1pJNwJFie3IPHe7GY6zP4wCeCSL05ZcPkogMGH/B/+qBS0ZWvXS3rlqpBxwu36kKMkQcU27RuMaFTc6lJ5WocDt1ruGHfmAAMEvrr2HcJfnUbceJPUBuJLAMUd6WZif0qlIe3Tl9rkZD50RVRgG6TVpNnHcw3A4+A+z4m2CSq3wMtvRUsT/pl/L83eSqltKLgrPSCvA4OjksPm4G9cqfw63BkmtUc7QIDAQAB";
            Security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (billingClient != null) {
            billingClient!!.endConnection()
        }
    }

    companion object {
        const val PREF_FILE = "MyPref"
        const val PURCHASE_KEY = "premium"

    }

}

private fun initiatePurchase() {
    val skuList: MutableList<String> = ArrayList()
    skuList.add(PRODUCT_ID)
    val params = SkuDetailsParams.newBuilder()
    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)

    billingClient!!.querySkuDetailsAsync(params.build()
    )
    {
            billingResult, skuDetailsList ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            if (skuDetailsList != null && skuDetailsList.size > 0) {
                val flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetailsList[0])
                    .build()
                billingClient!!.launchBillingFlow(locale_context!!, flowParams)
            }
            else {
                //try to add item/product id "consumable" inside managed product in google play console
                Toast.makeText(locale_context, "Purchase Item not Found", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(
                locale_context,
                " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
        }
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
            holder.price.text  = "ПОЛУЧИ " +  right_recording(min(capital / 100 * 4, 5000).toString())
            holder.button.text  =  "смотреть"
        }
        holder.icon.setImageResource(R.drawable.money)
        with(holder.itemView) {
            tag = ARRAY_OF_SPECIALLY_SHOP[position]
        }

        when (Design) {
            "Normal" -> {
                holder.button.setBackgroundResource(R.drawable.button)
                holder.price.setTextColor(Color.BLACK)

            }
            "Egypt" -> {
                holder.background_item.setBackgroundColor(Color.rgb(255, 230, 163))

                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.BLACK)   //цвет
                holder.button.setBackgroundColor(argb(0, 0, 0, 0))
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
                holder.button.setBackgroundColor(argb(0, 0, 0, 0))
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
                holder.button.setBackgroundColor(argb(0, 0, 0, 0))
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
                holder.button.setBackgroundResource(R.drawable.button)
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
            else          // если это премиум
            {
                PRODUCT_ID = ARRAY_OF_PRODUCT_ID[position].toString()
                if (billingClient!!.isReady) {
                    initiatePurchase()
                } else {
                    if( !(PRODUCT_ID == "premium" && PREMIUM))
                    {
                        billingClient = BillingClient.newBuilder(locale_context!!).enablePendingPurchases().setListener(p1!!).build()
                        billingClient!!.startConnection(object : BillingClientStateListener {
                            override fun onBillingSetupFinished(billingResult: BillingResult) {
                                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                    initiatePurchase()
                                } else {
                                    Toast.makeText(locale_context, "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onBillingServiceDisconnected() {}
                        })
                    }

                }
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

