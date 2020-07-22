package com.example.schoolbattle

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.design_item.view.*



var fragment_activity : AppCompatActivity? = null

class SettingsFragmentActivity : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CONTEXT = requireActivity()
        return inflater.inflate(R.layout.activity_settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_activity = activity as AppCompatActivity

        (activity as AppCompatActivity?)!!.setSupportActionBar(tb1)

        if (Design == "Normal") {
            settings_menu.setBackgroundColor(Color.WHITE)
        }
        if (Design == "Casino") {
            settings_menu.setBackgroundResource(R.drawable.background2_casino)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(20f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            soundSwitch.setTextColor(Color.YELLOW)
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(20f)
        }
        else if (Design == "Egypt") {
            settings_menu.setBackgroundResource(R.drawable.background_egypt)
            tb1.setBackgroundColor(rgb(224,164,103));

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(18f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //soundSwitch.setTextColor(Color.YELLOW)
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(20f)
        }
        else if (Design == "Rome") {
            settings_menu.setBackgroundResource(R.drawable.background_rome)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            //choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(18f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            soundSwitch.setTextColor(rgb(193,150,63))
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(20f)
        }


        logOutSettings.setOnClickListener {
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            editor?.putString("username", "")
            editor?.apply()
            recyclerSet.clear()
            val intent = Intent(activity, NullActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        soundSwitch.isChecked = SOUND                //настройка свитчера звука
        soundSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            SOUND = isChecked
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            if(SOUND)
            {
                editor?.putString("sound","true")
            }
            else
            {
                editor?.putString("sound","false")
            }
            editor?.apply()
        })

        vibrationSwitch.isChecked = VIBRATION               //настройка свитчера звука
        vibrationSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            VIBRATION = isChecked
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            if(VIBRATION)
            {
                editor?.putString("vibration","true")
            }
            else
            {
                editor?.putString("vibration","false")
            }
            editor?.apply()
        })











        DesignsetupRecyclerView(item_design)
        gamesRecycler = item_design
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design.adapter?.notifyDataSetChanged()

    }



    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar)
        //setSupportActionBar(my_toolbar)




    }*/

}

private fun DesignsetupRecyclerView(recyclerView: RecyclerView) {
    recyclerView.adapter = DesignItemRecyclerViewAdapter(ARRAY_OF_DESIGN)
}



class DesignItemRecyclerViewAdapter(private val DESIGN_ITEMS: MutableList<Int>):
    RecyclerView.Adapter<DesignItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            var item = v.tag
            if(item == 0)
            {
                Design = "Normal"
                val editor = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Normal")
                editor.apply()
            }
            else if(item == 1)
            {
                Design = "Egypt"
                val editor = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Egypt")
                editor.apply()
            }
            else if(item == 2)
            {
                Design = "Casino"
                val editor = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Casino")
                editor.apply()
            }
            else if(item == 3)
            {
                Design = "Rome"
                val editor = v.context.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Rome")
                editor.apply()
            }

            val t = fragment_activity?.supportFragmentManager?.beginTransaction()
            val mFrag: Fragment= SettingsFragmentActivity()                    //ПРОСТО ПИЗДЕЦ
            t?.replace(R.id.settings_menu,mFrag)?.commitNowAllowingStateLoss()

            if(Design == "Normal")
            {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE);
            }
            else if(Design == "Egypt")
            {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(rgb(224, 164, 103));
            }
            else if(Design == "Casino")
            {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_casino)
            }
            else if(Design == "Rome")
            {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_rome)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.design_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        PICTURE_STYLES[ARRAY_OF_DESIGN[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для стиля
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == Design)
        {
            holder.contentView.setText(PICTURE_TEXT[ARRAY_OF_DESIGN[position]] + "(установлено)")          //название стиля
        }
        else
        {
            holder.contentView.setText(PICTURE_TEXT[ARRAY_OF_DESIGN[position]])          //название стиля
        }
        with(holder.itemView) {
            tag = ARRAY_OF_DESIGN[position]
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return ARRAY_OF_DESIGN.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_design
        var contentView: TextView = view.id_text_design
    }
}

