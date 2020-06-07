package com.example.schoolbattle

import android.app.Activity
import android.os.Bundle
import android.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SocialActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_social, container, false)
    }

    override fun onResume() {
        super.onResume()
        CONTEXT = requireActivity()
        val tabs: TabLayout = requireActivity().findViewById(R.id.tabs)
    }
}