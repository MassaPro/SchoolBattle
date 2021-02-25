package com.sga.schoolbattle.shop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sga.schoolbattle.*
import com.google.android.material.tabs.TabLayout
import com.sga.schoolbattle.social.SocialActivity
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.activity_shop_fragment.*



class ShopFragmentActivity : Fragment (){


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb1)

        locale_context = activity as AppCompatActivity?

        var fon = locale_context!!.findViewById<View>(R.id.shop_menu)
        var t_shop = locale_context!!.findViewById<View>(R.id.toolbar_shop)



        helped_text1.text = translate("Аватары")
        helped_text2.text = translate("Эмоции")
        helped_text3.text = translate("Дизайны")
        helped_text4.text = translate("Премиум")

        for (i in 0 until tabLayout!!.tabCount) {
            var tv : TextView? = null
            if(i ==0)
            {tv  = helped_text1}
            if(i ==1 )
            {tv  = helped_text2}
            if(i == 2)
            {tv  = helped_text3}
            if(i == 3)
            {tv = helped_text4}

            when (Design) {
                "Normal" -> {
                    tv?.setTextColor(Color.BLACK)
                    tabLayout!!.getTabAt(i)?.customView = tv;
                }
                "Egypt" -> {
        //            tv?.textSize = 14f        //так задаешь размер
                    tv?.setTextColor(Color.BLACK)   //цвет
  //                  tv?.typeface = locale_context?.let { ResourcesCompat.getFont(it, R.font.egypt) } //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    t_shop.setBackgroundColor(rgb(255, 230, 163))
                    fon.setBackgroundResource(R.drawable.sign_in_egypt)
             //       button_shop_name.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                    button_shop_name.textSize = 25f
                   // money_shop_toolbar.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                    tabLayout!!.setSelectedTabIndicatorColor (Color.BLACK)

                }
                "Casino" -> {
       //             tv?.textSize = 18f        //так задаешь размер
                    tv?.setTextColor(Color.YELLOW)   //цвет
       //             tv?.typeface = locale_context?.let { ResourcesCompat.getFont(it, R.font.casino) } //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    t_shop.setBackgroundResource(R.drawable.bottom_navigation_casino)
                    fon.setBackgroundResource(R.drawable.background2_casino)
                    button_shop_name.setTextColor(Color.YELLOW)
             //       button_shop_name.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                    button_shop_name.textSize = 25f
                    money_shop_toolbar.setTextColor(Color.YELLOW)
                //    money_shop_toolbar.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                    tabLayout!!.setSelectedTabIndicatorColor (Color.YELLOW)
                }
                "Rome" -> {
      //              tv?.textSize = 20f       //так задаешь размер
                    tv?.setTextColor(rgb(193, 150, 63))   //цвет
             //       tv?.typeface = locale_context?.let { ResourcesCompat.getFont(it, R.font.rome) } //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    t_shop.setBackgroundResource(R.drawable.bottom_navigation_rome)
                    fon.setBackgroundResource(R.drawable.background_rome)
                    button_shop_name.setTextColor(rgb(193, 150, 63))
             //       button_shop_name.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                    button_shop_name.textSize = 25f
                    money_shop_toolbar.setTextColor(rgb(193, 150, 63))
            //        money_shop_toolbar.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                    money_shop_toolbar.textSize = 25f
                    tabLayout!!.setSelectedTabIndicatorColor (rgb(193, 150, 63))
                }
                "Gothic" -> {
    //                tv?.textSize = 21f        //так задаешь размер
                    tv?.setTextColor(Color.WHITE)   //цвет
            //        tv?.typeface = locale_context?.let { ResourcesCompat.getFont(it, R.font.gothic) } //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    t_shop.setBackgroundColor(rgb(20,20,20))
                    fon.setBackgroundResource(R.drawable.background_gothic)
                    button_shop_name.setTextColor(Color.WHITE)
         //           button_shop_name.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                    button_shop_name.textSize = 25f
                    money_shop_toolbar.setTextColor(Color.WHITE)
         //           money_shop_toolbar.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
                    tabLayout!!.setSelectedTabIndicatorColor (Color.WHITE)
                }
                "Japan" -> {
           //         tv?.textSize = 21f        //так задаешь размер
                    tv?.setTextColor(Color.BLACK)   //цвет
                  //  tv?.typeface = locale_context?.let { ResourcesCompat.getFont(it, R.font.gothic) } //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    t_shop.setBackgroundColor(Color.WHITE)
                    fon.setBackgroundResource(R.drawable.background_japan)
          //          button_shop_name.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                    button_shop_name.textSize = 25f
                //    money_shop_toolbar.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                    tabLayout!!.setSelectedTabIndicatorColor (Color.BLACK)
                }
                "Noir" -> {
      //              tv?.textSize = 18f        //так задаешь размер
                    tv?.setTextColor(Color.WHITE)   //цвет
                  //  tv?.typeface = locale_context?.let { ResourcesCompat.getFont(it, R.font.noir) } //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    t_shop.setBackgroundColor(rgb(20,20,20))
                    fon.setBackgroundResource(R.drawable.background_noir)
                    button_shop_name.setTextColor(Color.WHITE)
        //            button_shop_name.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                    button_shop_name.textSize = 25f
                    money_shop_toolbar.setTextColor(Color.WHITE)
         //           money_shop_toolbar.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                    tabLayout!!.setSelectedTabIndicatorColor (Color.WHITE)
                }
            }
        }




    }

    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        CONTEXT = requireActivity()
        /**
         * Inflate tab_layout and setup Views.
         */

        var v: View = inflater.inflate(R.layout.activity_shop_fragment, container, false)



        tabLayout = v.findViewById<View>(R.id.tabs_shop) as TabLayout
        viewPager = v.findViewById<View>(R.id.viewpager_shop) as ViewPager


        var name  = v.findViewById<View>(R.id.button_shop_name) as Button
        var money = v.findViewById<View>(R.id.money_shop_toolbar) as TextView
        var fon = v.findViewById<View>(R.id.shop_menu)
        var t_shop = v.findViewById<View>(R.id.toolbar_shop)
        //var text1 = v.findViewById<TextView>(R.id.choose_design_shop)

        if(Design == "Egypt")
        {
            fon.setBackgroundResource(R.drawable.sign_in_egypt)
            t_shop.setBackgroundColor(rgb(255, 230, 163))
        }
        else if(Design == "Casino")
        {
            fon.setBackgroundResource(R.drawable.background2_casino)
            t_shop.setBackgroundResource(R.drawable.bottom_navigation_casino)
        }
        else if(Design == "Rome")
        {
            fon.setBackgroundResource(R.drawable.background_rome)
            t_shop.setBackgroundResource(R.drawable.bottom_navigation_rome)
        }
        else if(Design == "Gothic")
        {
            fon.setBackgroundResource(R.drawable.background_gothic)
            t_shop.setBackgroundColor(rgb(20,20,20))
        }
        else if(Design == "Japan")
        {
            fon.setBackgroundResource(R.drawable.background_japan)
            t_shop.setBackgroundColor(Color.WHITE)
        }
        else if(Design == "Noir")
        {
            fon.setBackgroundResource(R.drawable.background_noir)
            t_shop.setBackgroundColor(rgb(20,20,20))
        }

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val globalName = prefs?.getString("username", "").toString()
        name.text = globalName
        money.text  = MONEY.toString()
        /**
         * Set an Apater for the View Pager
         */
        viewPager!!.adapter = MyAdapter(childFragmentManager)
        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */
        tabLayout!!.setupWithViewPager(viewPager)



        return v
    }

    internal inner class MyAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        /**
         * Return fragment with respect to Position .
         */
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return Avatars()
                1 -> return Emotions()
                2 -> return Designs()
                3 -> return Specially()
            }
            return Fragment()
        }

        override fun getCount(): Int {
            return int_items
        }

        /**
         * This method returns the title of the tab according to the position.
         */
        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> {
                    return translate("Аватары")
                }
                1 -> {
                    return translate("Эмоции")
                }
                2 -> {
                    return translate("Дизайн")
                }
                3 -> {
                    return translate("Премиум")
                }
            }
            return null
        }
    }

    companion object {
        var tabLayout: TabLayout? = null
        var viewPager: ViewPager? = null
        var int_items = 4
    }
}