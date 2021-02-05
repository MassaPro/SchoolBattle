package com.sga.schoolbattle.social

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sga.schoolbattle.*
import com.sga.schoolbattle.shop.locale_context
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_social.view.*
import kotlinx.android.synthetic.main.activity_social.view.helped_text_social_1
import kotlinx.android.synthetic.main.activity_social.view.helped_text_social_2
import kotlinx.android.synthetic.main.activity_social.view.helped_text_social_3

class SocialActivity : Fragment() {

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
    }

    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         * Inflate tab_layout and setup Views.
         */


        val v: View = inflater.inflate(R.layout.activity_social, container, false)
        tabLayout = v.findViewById<View>(
            R.id.tabs
        ) as TabLayout
        viewPager =
            v.findViewById<View>(R.id.viewpager) as ViewPager
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

        var fon = v.findViewById<View>(R.id.social_menu)



        //v.search_social.setBackgroundResource(R.drawable.search)
        v.search_social.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }

        locale_context = activity as AppCompatActivity

        v.search_social.setBackgroundResource(R.drawable.search)
        for (i in 0 until tabLayout!!.tabCount) {
            var tv: TextView =v.findViewById(R.id.helped_text_social_1)
            if (i == 0) {
                tv = v.helped_text_social_1
            }
            if (i == 1) {
                tv =v.helped_text_social_2
            }
            if (i == 2) {
                tv =v.helped_text_social_3
            }

            when (Design) {
                "Normal" -> {
                    tv.setTextColor(Color.BLACK)   //цвет
                    tabLayout!!.getTabAt(i)?.customView = tv;

                }
                "Egypt" -> {
                    fon.setBackgroundResource(R.drawable.sign_up_egypt)
                    //tabLayout!!.setBackgroundResource(R.drawable.background_egypt)     //фон табов
                    v.toolbar_social.setBackgroundColor(rgb(255, 230, 163))// панель поиска
              //      tv.textSize = 14.3f        //так задаешь размер
                    tv.setTextColor(Color.BLACK)   //цвет
                //    tv.typeface = ResourcesCompat.getFont(locale_context!!, R.font.egypt)  //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    v.textView5.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                    v.textView5.setTextColor(Color.BLACK)
                }
                "Casino" -> {
                    fon.setBackgroundResource(R.drawable.background2_casino)
                    v.toolbar_social.setBackgroundResource(R.drawable.bottom_navigation_casino)
                  //  tv.textSize = 18f        //так задаешь размер
                    tv.setTextColor(Color.YELLOW)   //цвет
                   // tv.typeface = ResourcesCompat.getFont(locale_context!!, R.font.casino)  //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    v.search_social.setBackgroundResource(R.drawable.search_3)
                    v.textView5.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                    v.textView5.setTextColor(Color.YELLOW)
                }
                "Rome" -> {
                    fon.setBackgroundResource(R.drawable.background_rome)
                    v.toolbar_social.setBackgroundResource(R.drawable.bottom_navigation_rome)
               //     tv.textSize = 21f        //так задаешь размер
                    tv.setTextColor(rgb(193, 150, 63))   //цвет
                 //   tv.typeface = ResourcesCompat.getFont(locale_context!!, R.font.rome)  //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    v.search_social.setBackgroundResource(R.drawable.search_2)
                    v.textView5.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                    v.textView5.setTextColor(rgb(193, 150, 63))
                }
                "Gothic" -> {
                    fon.setBackgroundResource(R.drawable.background_gothic)
                    v.toolbar_social.setBackgroundColor(rgb(20,20,20))
              //      tv.textSize = 25f        //так задаешь размер
                    tv.setTextColor(Color.WHITE)   //цвет
              //      tv.typeface = ResourcesCompat.getFont(locale_context!!, R.font.gothic)  //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    v.search_social.setBackgroundResource(R.drawable.search_1)
                    v.textView5.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    v.textView5.setTextColor(Color.WHITE)
                }
                "Japan" -> {
                    fon.setBackgroundResource(R.drawable.background_japan)
                    v.toolbar_social.setBackgroundColor(Color.WHITE)
                //    tv.textSize = 14f        //так задаешь размер
                    tv.setTextColor(Color.BLACK)   //цвет
               //     tv.typeface = ResourcesCompat.getFont(locale_context!!, R.font.japan)  //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    v.textView5.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                    v.textView5.setTextColor(Color.BLACK)
                }
                "Noir" -> {
                    fon.setBackgroundResource(R.drawable.background_noir)
                    v.toolbar_social.setBackgroundColor(rgb(20,20,20))
              //      tv.textSize = 18f        //так задаешь размер
                    tv.setTextColor(Color.WHITE)   //цвет
                //    tv.typeface = ResourcesCompat.getFont(locale_context!!, R.font.noir)  //шрифт
                    tabLayout!!.getTabAt(i)?.customView = tv;
                    v.search_social.setBackgroundResource(R.drawable.search_1)
                    v.textView5.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                    v.textView5.setTextColor(Color.WHITE)
                }
            }

        }
        return v
    }

    internal inner class MyAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        /**
         * Return fragment with respect to Position .
         */
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return MyProfile()
                2 -> return FriendsList()
                1 -> return Subscriptions()
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
                    return "Профиль"
                }
                2 -> {
                    return "Подписки"
                }
                1 -> {
                    return "Подписчики"
                }

            }
            return null
        }
    }

    companion object {
        var tabLayout: TabLayout? = null
        var viewPager: ViewPager? = null
        var int_items = 3
    }
}