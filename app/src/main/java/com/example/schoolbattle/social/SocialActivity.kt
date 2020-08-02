package com.example.schoolbattle.social

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.schoolbattle.CONTEXT
import com.example.schoolbattle.NewGameActivity
import com.example.schoolbattle.R
import com.example.schoolbattle.SearchActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_social.view.*

class SocialActivity : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        tabLayout!!.post { tabLayout!!.setupWithViewPager(
            viewPager
        ) }

        v.search_social.setBackgroundResource(R.drawable.search)
        v.search_social.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
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
