package com.sga.schoolbattle.shop

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
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
import com.sga.schoolbattle.*
import com.sga.schoolbattle.engine.updateEconomyParams
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.design_shop_item.view.*
import kotlinx.android.synthetic.main.few_money_dialog.*
import kotlinx.android.synthetic.main.internet_dialog.*
import kotlinx.android.synthetic.main.internet_dialog.text_few_money
import kotlinx.android.synthetic.main.reward_dialog.*
import kotlinx.android.synthetic.main.shop_dialog.*

var Vidos_avatar : RewardedVideoAd? = null
lateinit var mRewardedVideoAd_avatar: RewardedVideoAd
class Avatars : Fragment() , RewardedVideoAdListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_designs, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //     vasa = activity.setContentView(R.layout.activity_shop_fragment)
        mSound1.load(locale_context, R.raw.money, 1);
        choose_design_shop.text = "Купленные аватары можно применить в профиле"
        HELPED_CONTEXT = activity

        locale_context = activity as AppCompatActivity

        mRewardedVideoAd_avatar = MobileAds.getRewardedVideoAdInstance(locale_context)
        mRewardedVideoAd_avatar.rewardedVideoAdListener = this
        Vidos_avatar = mRewardedVideoAd


        ProfileAvatarsetupRecyclerView(item_design_shop, requireActivity())
        gamesRecycler = item_design_shop
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design_shop.adapter?.notifyDataSetChanged()

        when (Design) {
            "Egypt" -> {
                choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.egypt) }
                choose_design_shop.setTextColor(Color.BLACK)
                choose_design_shop.textSize = 20f
            }
            "Casino" -> {
                choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.casino) }
                choose_design_shop.setTextColor(Color.WHITE)
                choose_design_shop.textSize = 20f
            }
            "Rome" -> {
                choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.rome) }
                choose_design_shop.setTextColor(Color.rgb(193, 150, 63))
                choose_design_shop.textSize = 25f
            }
            "Gothic" -> {
                choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.gothic) }
                choose_design_shop.setTextColor(Color.WHITE)
                choose_design_shop.textSize = 25f
            }
            "Japan" -> {
                choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.japan) }
                choose_design_shop.setTextColor(Color.BLACK)
                choose_design_shop.textSize = 20f
            }
            "Noir" -> {
                choose_design_shop.typeface =  locale_context?.let { ResourcesCompat.getFont(it, R.font.noir) }
                choose_design_shop.setTextColor(Color.WHITE)
                choose_design_shop.textSize = 20f
            }
        }



    }
    private fun loadRewardedVideoAd() {
        mRewardedVideoAd_avatar.loadAd("ca-app-pub-3940256099942544/5224354917",          //TODO зменить на настоящий идентификатор
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
        dialog_reward.price_reward.text = updateEconomyParams(requireActivity(), "award").toString() //TODO ОБЯЗАТЕЛЬНО НЕ ЗАБЫТЬ ПОМЕНЯТЬ ЗДЕСЬ ЦЕНУ
        dialog_reward.close_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.ok_reward.setOnClickListener {
            dialog_reward.dismiss()
        }
        dialog_reward.show()
        locale_context?.findViewById<TextView>(R.id.money_shop_toolbar)?.text = MONEY.toString()
    }



}




private fun ProfileAvatarsetupRecyclerView(recyclerView: RecyclerView, activity: Activity) {
    recyclerView.adapter = ProfileAvatarsItemRecyclerViewAdapter(ARRAY_OF_DESIGN_SHOP, activity)
}



class ProfileAvatarsItemRecyclerViewAdapter(private val DESIGN_ITEMS: MutableList<Int>, val activity: Activity):
    RecyclerView.Adapter<ProfileAvatarsItemRecyclerViewAdapter.ViewHolder>() {

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

        PICTURE_AVATAR[ARRAY_OF_AVATAR_SHOP[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для стиля
        holder.price.text = right_recording(PRICE_OD_AVATAR[ARRAY_OF_AVATAR_SHOP[position]].toString()  )           //цена стиля
        holder.contentView.text = AVATAR_TEXT[ARRAY_OF_AVATAR_SHOP[position]]          //название стиля


        when (Design) {
            "Normal" -> {
                holder.button.setBackgroundResource(R.drawable.button)
            }
            "Egypt" -> {
                holder.background_item.setBackgroundColor(rgb(255, 230, 163))

                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.BLACK)   //цвет
                holder.button.setBackgroundColor(argb(0,0,0,0))
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.egypt)

                holder.price.textSize = 20f        //так задаешь размер
                holder.price.setTextColor(Color.BLACK)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.egypt)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(Color.BLACK)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.egypt)
            }
            "Casino" -> {
                holder.background_item.setBackgroundResource(R.drawable.table)
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.YELLOW)   //цвет
                holder.button.setBackgroundColor(argb(0,0,0,0))
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.casino)

                holder.price.textSize = 20f        //так задаешь размер
                holder.price.setTextColor(Color.YELLOW)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.casino)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(Color.YELLOW)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.casino)
            }
            "Rome" -> {
                holder.background_item.setBackgroundResource(R.drawable.bottom_navigation_rome)
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setBackgroundColor(argb(0,0,0,0))
                holder.button.setTextColor(rgb(193, 150, 63))   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)

                holder.price.textSize = 20f        //так задаешь размер
                holder.price.setTextColor(rgb(193, 150, 63))   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(rgb(193, 150, 63))   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)
            }
            "Gothic" -> {
                holder.background_item.setBackgroundColor(rgb(20,20,20))
                holder.button.textSize = 16f        //так задаешь размер
                holder.button.setBackgroundColor(rgb(30,30,30))
                holder.button.setTextColor(Color.WHITE)   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.gothic)

                holder.price.textSize = 20f        //так задаешь размер
                holder.price.setTextColor(Color.WHITE)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.gothic)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(Color.WHITE)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.gothic)
            }
            "Japan" -> {
                holder.background_item.setBackgroundColor(Color.WHITE)
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setTextColor(Color.BLACK)   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.japan)

                holder.price.textSize = 20f        //так задаешь размер
                holder.price.setTextColor(Color.BLACK)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.japan)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(Color.BLACK)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.japan)
                holder.button.setBackgroundResource(R.drawable.button)
            }
            "Noir" -> {
                holder.background_item.setBackgroundColor(rgb(20,20,20))
                holder.button.textSize = 20f        //так задаешь размер
                holder.button.setBackgroundColor(rgb(30,30,30))
                holder.button.setTextColor(Color.WHITE)   //цвет
                holder.button.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)

                holder.price.textSize = 20f        //так задаешь размер
                holder.price.setTextColor(Color.WHITE)   //цвет
                holder.price.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(Color.WHITE)   //цвет
                holder.contentView.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)
            }
        }

        if(ARRAY_OF_AVATAR_SHOP[position] in  ARRAY_OF_AVATAR)         //если дизайн уже куплен
        {
            holder.button.text = "(КУПЛЕНО)"
            holder.button.background = null
            holder.price.text = ""
        }
        else
        {
            holder.icon.setImageResource(R.drawable.money)
        }
        //TODO MONEY передать в базу ------- сделано в строках 256 - 262
        //TODO ARRAY_OF_EMOTION передать в базу ------- сделано в строках 256 - 262
//TODO MONEY передать в файрбейс
        //TODO ARRAY_OF_AVATAR передать в файербейс
// dialog = Proof_of_purchase(HELPED_CONTEXT!!,locale_context!!,"Design",ARRAY_OF_DESIGN_SHOP[position].toString().toInt(), PRICE_OD_DESIGN[ARRAY_OF_DESIGN_SHOP[position].toString().toInt()]!!)
        //   dialog?.showResult()
//если дизайн не куплен

        //TODO MONEY = MONEY from firebase
        //TODO ARRAY_OF_AVATAR = ARRAY_OF_AVATAR from firebase


        with(holder.itemView) {
            tag = ARRAY_OF_AVATAR_SHOP[position]
        }

        holder.button.setOnClickListener{

            var dialog_internet = Dialog(locale_context!!)
            dialog_internet.setContentView(R.layout.internet_dialog)
            fun buy_avatars(): Boolean
            {
                //TODO MONEY = MONEY from firebase
                //TODO ARRAY_OF_AVATAR = ARRAY_OF_AVATAR from firebase
                if(ARRAY_OF_AVATAR_SHOP[position] !in ARRAY_OF_AVATAR && MONEY>= PRICE_OD_AVATAR[ARRAY_OF_AVATAR_SHOP[position]]!!)          //если дизайн не куплен
                {
                    // dialog = Proof_of_purchase(HELPED_CONTEXT!!,locale_context!!,"Design",ARRAY_OF_DESIGN_SHOP[position].toString().toInt(), PRICE_OD_DESIGN[ARRAY_OF_DESIGN_SHOP[position].toString().toInt()]!!)
                    //   dialog?.showResult()
                    val dialog_shop = Dialog(HELPED_CONTEXT!!)
                    dialog_shop.setContentView(R.layout.shop_dialog)

                    dialog_shop.price_shop.text = holder.price.text
                    dialog_shop.description.text = "Купить <" + AVATAR_TEXT[ARRAY_OF_AVATAR_SHOP[position]] + "> за"

                    dialog_shop.show()
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
                    dialog_shop.button_close_2_shop_dialog.setOnClickListener {
                        dialog_shop.dismiss()
                    }
                    dialog_shop.button_close_shop_dialog.setOnClickListener {
                        dialog_shop.dismiss()
                    }

                }
                if(ARRAY_OF_AVATAR_SHOP[position] !in ARRAY_OF_AVATAR && MONEY< PRICE_OD_AVATAR[ARRAY_OF_AVATAR_SHOP[position]]!!)
                {
                    val dialog_shop = Dialog(HELPED_CONTEXT!!)
                    dialog_shop.setContentView(R.layout.few_money_dialog)
                    dialog_shop.text_few_money.text = "Недостаточно средств"
                    dialog_shop.show()
                    dialog_shop.few_money_ok.setOnClickListener {
                        dialog_shop.dismiss()
                    }
                    dialog_shop.get_money.setOnClickListener {
                        if (Vidos_avatar?.isLoaded!!) {
                            Vidos_avatar?.show()
                            dialog_shop.dismiss()
                        }
                        else
                        {
                            Toast.makeText(locale_context, "Подождите,видео еще загружается/Проверьте подключение к интернету", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                return true
            }
            fun check_internet():Boolean
            {
                if(verifyAvailableNetwork(locale_context!!))
                {
                    buy_avatars()
                }
                else
                {
                    dialog_internet.show()
                    dialog_internet.button_update.setOnClickListener {
                        dialog_internet.dismiss()
                        check_internet()
                    }
                }
                return true
            }

            check_internet()

        }
    }

    override fun getItemCount(): Int {
        //Toast.makeText(currentContext,ARRAY_OF_DESIGN_SHOP.size.toString(), Toast.LENGTH_LONG).show()
        return ARRAY_OF_AVATAR_SHOP.size


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
