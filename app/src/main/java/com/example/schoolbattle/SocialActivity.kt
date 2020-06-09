package com.example.schoolbattle

import android.os.Bundle
import android.view.*
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_social.*

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