package com.example.schoolbattle.shop

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
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.schoolbattle.*
import com.example.schoolbattle.social.FriendsList
import com.example.schoolbattle.social.MyProfile
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_designs.*
import kotlinx.android.synthetic.main.activity_game_menu.*
import kotlinx.android.synthetic.main.activity_null.view.*
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.activity_shop_fragment.*
import org.w3c.dom.Text


class ShopFragmentActivity : Fragment (){


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb1)



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

        var fon = v.findViewById<View>(R.id.shop_menu)
        var t_shop = v.findViewById<View>(R.id.toolbar_shop)
        var name  = v.findViewById<View>(R.id.button_shop_name) as Button
        var money = v.findViewById<View>(R.id.money_shop_toolbar) as TextView
        //var text1 = v.findViewById<TextView>(R.id.choose_design_shop)
        if(Design == "Normal")
        {
            t_shop.setBackgroundColor(rgb(214,214,214))

        }
        else if(Design == "Egypt")
        {
            t_shop.setBackgroundColor(rgb(224,164,103))
            fon.setBackgroundResource(R.drawable.background_egypt)

        }
        else if(Design == "Casino")
        {
            t_shop.setBackgroundColor(rgb(0,0,0))
            fon.setBackgroundResource(R.drawable.background2_casino)
        }
        else if(Design == "Rome")
        {
            t_shop.setBackgroundColor(rgb(0,0,0))
            fon.setBackgroundResource(R.drawable.background_rome)
        }
        else if(Design == "Gothic")
        {
            t_shop.setBackgroundColor(rgb(100,100,100))
            fon.setBackgroundResource(R.drawable.background_gothic)
        }
        else if(Design == "Japan")
        {
            t_shop.setBackgroundColor(Color.WHITE)
            fon.setBackgroundResource(R.drawable.background_japan)
            //text1.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
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
        tabLayout!!.post { tabLayout!!.setupWithViewPager(
            viewPager
        ) }
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
                    return "Аватар"
                }
                1 -> {
                    return "Эмоции"
                }
                2 -> {
                    return "Дизайн"
                }
                3 -> {
                    return "ПРЕМИУМ"
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
