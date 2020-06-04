package com.example.schoolbattle

import android.os.Bundle
import android.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.schoolbattle.ui.main.SectionsPagerAdapter

class SocialActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_social, container, false)
    }

    override fun onResume() {
        super.onResume()
        val sectionsPagerAdapter = activity?.let { SectionsPagerAdapter(it, requireActivity().supportFragmentManager) }
        val viewPager: ViewPager = requireActivity().findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = requireActivity().findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


    }
}