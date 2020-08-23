package com.example.schoolbattle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.icu.text.Transliterator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.shop.locale_context
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_navigator.*
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

        if(Design == "Normal")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE);
        }
        else if(Design == "Egypt")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(rgb(255, 230, 163))
        }
        else if(Design == "Casino")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_casino)
        }
        else if(Design == "Rome")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_rome)
        }
        else if(Design == "Gothic")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_gothic)
        }
        else if(Design == "Japan")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE)
        }
        else if(Design == "Noir")
        {
            fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.BLACK)
        }

        locale_context = activity as AppCompatActivity
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb1)

        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.nav_view).itemIconTintList = generateColorStateList()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.nav_view).itemTextColor = generateColorStateList()
        if (Design == "Normal") {
            settings_menu.setBackgroundColor(Color.WHITE)
        }

        else if (Design == "Egypt") {

            settings_menu.setBackgroundResource(R.drawable.background_egypt)
            tb1.setBackgroundColor(rgb(255, 230, 163));

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            //choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(24f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            //soundSwitch.setSwitchColor(Color.YELLOW)
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(24f)
            vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
            vibrationSwitch.setTextSize(24f)
        }

        if (Design == "Casino") {
            settings_menu.setBackgroundResource(R.drawable.background2_casino)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(24f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            soundSwitch.setTextColor(Color.YELLOW)
            soundSwitch.setTextSize(24f)

            vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            vibrationSwitch.setTextSize(24f)
            vibrationSwitch.setTextColor(Color.YELLOW)
        }

        else if (Design == "Rome") {
            settings_menu.setBackgroundResource(R.drawable.background_rome)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            choose_design.setTextColor(rgb(193,150,63))
            choose_design.setTextSize(24f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            soundSwitch.setTextColor(rgb(193,150,63))
            soundSwitch.setTextSize(24f)

            vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
            vibrationSwitch.setTextSize(24f)
            vibrationSwitch.setTextColor(rgb(193,150,63))
        }
        else if (Design == "Gothic") {
            settings_menu.setBackgroundResource(R.drawable.background_gothic)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            choose_design.setTextColor(Color.WHITE)
            choose_design.setTextSize(24f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            soundSwitch.setTextColor(Color.WHITE)
            soundSwitch.setTextSize(24f)

            vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.gothic))
            vibrationSwitch.setTextSize(24f)
            vibrationSwitch.setTextColor(Color.WHITE)
        }
        else if (Design == "Japan") {
            settings_menu.setBackgroundResource(R.drawable.background_japan)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            choose_design.setTextColor(Color.BLACK)
            choose_design.setTextSize(24f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            soundSwitch.setTextColor(Color.BLACK)
            soundSwitch.setTextSize(24f)

            vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
            vibrationSwitch.setTextSize(24f)
            vibrationSwitch.setTextColor(Color.BLACK)
        }
        else if (Design == "Noir") {
            settings_menu.setBackgroundResource(R.drawable.background_noir)
            tb1.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            choose_design.setTextColor(Color.WHITE)
            choose_design.setTextSize(24f)

            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            soundSwitch.setTextColor(Color.WHITE)
            soundSwitch.setTextSize(24f)

            vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
            vibrationSwitch.setTextSize(24f)
            vibrationSwitch.setTextColor(Color.WHITE)
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.design_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        PICTURE_STYLES[ARRAY_OF_DESIGN[position]]?.let { holder.img.setBackgroundResource(it) }     //картинка для стиля
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == Design)
        {
            holder.button_prem.setBackgroundResource(R.drawable.nulevoe)
            holder.button_prem.text = "(УСТАНОВЛЕНО)"
            holder.button_prem.isClickable = false
        }
        //if(Design == "Egypt"){
        //    holder.background_item.setBackgroundColor(rgb(224,164,103))
        //}
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Egypt"){
            holder.background_item.setBackgroundColor(rgb(255, 230, 163))
            holder.button_prem.textSize = 15f        //так задаешь размер
            holder.button_prem.setTextColor(Color.BLACK)   //цвет
            holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
            holder.button_prem.setBackgroundColor(argb(0,0,0,0))

            holder.contentView.textSize = 20f        //так задаешь размер
            holder.contentView.setTextColor(Color.BLACK)   //цвет
            holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
            holder.contentView.setBackgroundColor(argb(0,0,0,0))

        }
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Casino"){
            holder.background_item.setBackgroundResource(R.drawable.table)
            holder.button_prem.textSize = 15f        //так задаешь размер
            holder.button_prem.setTextColor(Color.YELLOW)   //цвет
            holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
            holder.button_prem.setBackgroundColor(argb(0,0,0,0))

            holder.contentView.textSize = 20f        //так задаешь размер
            holder.contentView.setTextColor(Color.YELLOW)   //цвет
            holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
            holder.contentView.setBackgroundColor(argb(0,0,0,0))
        }
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Rome"){
            holder.background_item.setBackgroundResource(R.drawable.bottom_navigation_rome)
            holder.button_prem.textSize = 15f        //так задаешь размер
            holder.button_prem.setTextColor(rgb(193, 150, 63))   //цвет
            holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
            holder.button_prem.setBackgroundColor(argb(0,0,0,0))

            holder.contentView.textSize = 20f        //так задаешь размер
            holder.contentView.setTextColor(rgb(193, 150, 63))   //цвет
            holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
            holder.contentView.setBackgroundColor(argb(0,0,0,0))
        }
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Gothic"){
            holder.background_item.setBackgroundColor(rgb(20,20,20))
            holder.button_prem.textSize = 15f        //так задаешь размер
            holder.button_prem.setTextColor(Color.WHITE)   //цвет
            holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
            holder.button_prem.setBackgroundColor(rgb(30,30,30))

            holder.contentView.textSize = 20f        //так задаешь размер
            holder.contentView.setTextColor(Color.WHITE)   //цвет
            holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
            //holder.contentView.setBackgroundColor(rgb(30,30,30))
        }
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Japan"){
            holder.background_item.setBackgroundColor(Color.WHITE)
            holder.button_prem.textSize = 15f        //так задаешь размер
            holder.button_prem.setTextColor(Color.BLACK)   //цвет
            holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
            holder.button_prem.setBackgroundColor(argb(0,0,0,0))

            holder.contentView.textSize = 20f        //так задаешь размер
            holder.contentView.setTextColor(Color.BLACK)   //цвет
            holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
            holder.contentView.setBackgroundColor(argb(0,0,0,0))
        }
        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Noir"){
            holder.background_item.setBackgroundColor(rgb(20,20,20))
            holder.button_prem.textSize = 15f        //так задаешь размер
            holder.button_prem.setTextColor(Color.WHITE)   //цвет
            holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
            holder.button_prem.setBackgroundColor(rgb(30,30,30))

            holder.contentView.textSize = 20f        //так задаешь размер
            holder.contentView.setTextColor(Color.WHITE)   //цвет
            holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
            //holder.contentView.setBackgroundColor(rgb(30,30,30))
        }

        holder.contentView.setText(PICTURE_TEXT[ARRAY_OF_DESIGN[position]]) //название стиля
        with(holder.itemView) {
            tag = ARRAY_OF_DESIGN[position]
        }
        holder.button_prem.setOnClickListener {
            if(ARRAY_OF_DESIGN[position] == 0)
            {
                Design = "Normal"
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Normal")
                editor.apply()
            }
            if(ARRAY_OF_DESIGN[position] == 1)
            {
                Design = "Egypt"
                val editor =  locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Egypt")
                editor.apply()
            }
            if(ARRAY_OF_DESIGN[position] == 2)
            {
                Design = "Casino"
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Casino")
                editor.apply()
            }
            if(ARRAY_OF_DESIGN[position] == 3)
            {
                Design = "Rome"
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Rome")
                editor.apply()
            }
            if(ARRAY_OF_DESIGN[position] == 4)
            {
                Design = "Gothic"
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Gothic")
                editor.apply()
            }
            if(ARRAY_OF_DESIGN[position] == 5)
            {
                Design = "Japan"
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Japan")
                editor.apply()
            }
            if(ARRAY_OF_DESIGN[position] == 6)
            {
                Design = "Noir"
                val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("design","Noir")
                editor.apply()
            }
            val t = fragment_activity?.supportFragmentManager?.beginTransaction()
            val mFrag: Fragment= SettingsFragmentActivity()                    //ПРОСТО ПИЗДЕЦ
            t?.replace(R.id.settings_menu,mFrag)?.commitNowAllowingStateLoss()

        }
    }

    override fun getItemCount(): Int {
        return ARRAY_OF_DESIGN.size


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.img_design
        var contentView: TextView = view.id_text_design
        var button_prem: Button = view.primenuty
        var background_item: CardView = view.card_design
    }
}

