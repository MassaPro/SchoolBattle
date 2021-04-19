package com.sga.schoolbattle

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sga.schoolbattle.engine.colorByRating
import com.sga.schoolbattle.shop.locale_context
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.activity_social.view.*
import kotlinx.android.synthetic.main.design_item.view.*
import kotlinx.android.synthetic.main.development_dialog.*


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

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val prfs = fragment_activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")
        if(CONTEXT.toolbarNameSettings != null)
        {
            if (RATING != -1)
            {
                CONTEXT.toolbarNameSettings.text = "$username\n($RATING)"
            }
            else
            {
                CONTEXT.toolbarNameSettings.text = "$username\n"
            }
            CONTEXT.toolbarNameSettings.setTextColor(colorByRating(RATING))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_activity = activity as AppCompatActivity

        val prfs = fragment_activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")

            if (RATING != -1)
            {
                toolbarNameSettings.text = "$username\n($RATING)"
            }
            else
            {
                toolbarNameSettings.text = "$username\n"
            }
            toolbarNameSettings.setTextColor(colorByRating(RATING))

        when (Design) {
            "Normal" -> {
                settings_menu.setBackgroundColor(Color.WHITE)
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE);
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)
                //toolbarNameSettings.setTextColor(Color.BLACK)
                language_button.setTextColor(Color.BLACK)
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)
                choose_design.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)
                vibrationSwitch.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)
                soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.normal))
                vibrationSwitch.textSize = 24f
                language_button.textSize = 24f
                soundSwitch.textSize = 24f
                choose_design.textSize = 24f
            }
            "Egypt" -> {

                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(rgb(255, 230, 163))
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                //toolbarNameSettings.setTextColor(Color.BLACK)

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
                vibrationSwitch.textSize = 24f
                language_button.textSize = 24f
                language_button.setTextColor(Color.BLACK)
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
            }
            "Casino" -> {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_casino)
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                //toolbarNameSettings.setTextColor(Color.YELLOW)
                settings_menu.setBackgroundResource(R.drawable.background2_casino)
                tb1.setBackgroundResource(R.drawable.bottom_navigation_casino)

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
                language_button.setTextColor(Color.YELLOW)
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
            }
            "Rome" -> {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_rome)
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                //toolbarNameSettings.setTextColor(rgb(193,150,63))


                settings_menu.setBackgroundResource(R.drawable.background_rome)
                tb1.setBackgroundResource(R.drawable.bottom_navigation_rome)
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
                language_button.setTextColor(rgb(193,150,63))
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                language_button.setTextSize(24f)
            }
            "Gothic" -> {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.BLACK)
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
               // toolbarNameSettings.setTextColor(Color.WHITE)

                settings_menu.setBackgroundResource(R.drawable.background_gothic)
                tb1.setBackgroundColor(Color.BLACK)

                choose_design.setBackgroundColor(argb(0,0,0,0))
                choose_design.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                choose_design.setTextColor(Color.WHITE)
                choose_design.textSize = 24f

                soundSwitch.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                soundSwitch.setTextColor(Color.WHITE)
                soundSwitch.textSize = 24f

                vibrationSwitch.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                vibrationSwitch.textSize = 24f
                vibrationSwitch.setTextColor(Color.WHITE)
                language_button.setTextColor(Color.WHITE)
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                language_button1.setTextColor(Color.WHITE)
            }
            "Japan" -> {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE)
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                //toolbarNameSettings.setTextColor(Color.BLACK)
                settings_menu.setBackgroundResource(R.drawable.background_japan)

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
                //  tb1.setBackgroundColor(Color.WHITE)
                tb1.setBackgroundColor(Color.WHITE)
                language_button.setTextColor(Color.BLACK)
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
            }
            "Noir" -> {
                fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.BLACK)
                toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                //toolbarNameSettings.setTextColor(Color.WHITE)
                settings_menu.setBackgroundResource(R.drawable.background_noir)


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

                tb1.setBackgroundColor(Color.BLACK)

                language_button.setTextColor(Color.WHITE)
                language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                language_button1.setTextColor(Color.WHITE)
            }

        }

        locale_context = activity as AppCompatActivity
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb1)
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.nav_view).itemIconTintList = generateColorStateList()
        (activity as AppCompatActivity?)!!.findViewById<BottomNavigationView>(R.id.nav_view).itemTextColor = generateColorStateList()

        PICTURE_AVATAR[AVATAR]?.let { button_icon_settings.setBackgroundResource(it) }

        logOutSettings.setOnClickListener {
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            editor?.putString("username", "")
            editor?.apply()
            editor?.clear()
            editor?.apply()
            recyclerSet.clear()
            ARRAY_OF_DESIGN.clear()
            ARRAY_OF_DESIGN = mutableListOf(0,1)
            ARRAY_OF_AVATAR.clear()
            ARRAY_OF_AVATAR = mutableListOf(0,1,4,18,19)
            ARRAY_OF_EMOTION.clear()
            ARRAY_OF_EMOTION = mutableListOf(0,1)
            RATING = -1
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

        language_button.setOnClickListener {
            showDialog()
        }
        language_button1.setOnClickListener {
            showDialog()
        }

        DesignsetupRecyclerView(item_design)
        gamesRecycler = item_design
        gamesRecycler.isNestedScrollingEnabled = false;
        item_design.adapter?.notifyDataSetChanged()


        soundSwitch.text = translate("Звук")
        vibrationSwitch.text = translate("Вибрация")
        language_button.text = translate("Язык")
        choose_design.text = translate("Выбор дизайна")
        language_button1.text = translate("RU")
        logOutSettings.text = translate("Выход")
    }

    private fun showDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog

        // Initialize an array of colors
        val array = arrayOf("Русский","English")

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(locale_context!!)

        // Set a title for alert dialog


        var checkedItem = 0
        if(LANGUAGE == "Russian")
        {
            builder.setTitle("Выбор языка")
            checkedItem = 0
        }
        else
        {
            builder.setTitle("Choose a language")
            checkedItem =1
        }


        builder.setSingleChoiceItems(array,checkedItem) { _, which->
            if(which==0)
            {
                LANGUAGE = "Russian"
                val editor =  locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("language","russian")
                editor.apply()
                val navView: BottomNavigationView = locale_context!!.findViewById(R.id.nav_view)
                navView.menu.getItem(0).title = "Меню"
                navView.menu.getItem(1).title = "Игры"
                navView.menu.getItem(2).title = "Профиль"
                navView.menu.getItem(3).title = "Магазин"
                navView.menu.getItem(4).title = "Настройки"

            }
            else
            {
                LANGUAGE = "English"
                val editor =  locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                editor.putString("language","english")
                editor.apply()

                    val navView: BottomNavigationView = locale_context!!.findViewById(R.id.nav_view)
                    navView.menu.getItem(0).title = "Menu"
                    navView.menu.getItem(1).title = "Games"
                    navView.menu.getItem(2).title = "Profile"
                    navView.menu.getItem(3).title = "Shop"
                    navView.menu.getItem(4).title = "Settings"

            }
            soundSwitch.text = translate("Звук")
            vibrationSwitch.text = translate("Вибрация")
            language_button.text = translate("Язык")
            choose_design.text = translate("Выбор дизайна")
            language_button1.text = translate("RU")
            logOutSettings.text = translate("Выход")
            item_design.adapter?.notifyDataSetChanged()
        }
        builder.setPositiveButton(
           "OK"
        ) { dialog, _ ->
            dialog.dismiss()
        }

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }
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


        when {
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Normal" -> {
                holder.background_item.setBackgroundColor(Color.WHITE)
                holder.button_prem.textSize = 16f        //так задаешь размер
                holder.button_prem.setTextColor(Color.BLACK)   //цвет
                holder.button_prem.setBackgroundResource(R.drawable.button)
                holder.button_prem.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)

                holder.contentView.textSize = 20f        //так задаешь размер
                holder.contentView.setTextColor(Color.BLACK)   //цвет
                holder.contentView.setBackgroundColor(argb(0,0,0,0))
                holder.contentView.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)

            }
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Egypt" -> {
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
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Casino" -> {
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
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Rome" -> {
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
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Gothic" -> {
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
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Japan" -> {
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
            AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == "Noir" -> {
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
            //название стиля
        }

        if(AUXILIARY_MAP_OF_DESIGNS[ARRAY_OF_DESIGN[position]] == Design)
        {
            holder.button_prem.setBackgroundColor(Color.TRANSPARENT)
            holder.button_prem.text = '(' + translate("УСТАНОВЛЕНО") +  ')'
            holder.button_prem.isClickable = false
        }
        else
        {
            holder.button_prem.text = translate("ПРИМЕНИТЬ")
            holder.button_prem.isClickable = false
        }
        holder.contentView.text =
            PICTURE_TEXT[ARRAY_OF_DESIGN[position]]?.let { translate_design(it) } //название стиля
        with(holder.itemView) {
            tag = ARRAY_OF_DESIGN[position]
        }
        holder.button_prem.setOnClickListener {

            when {
                ARRAY_OF_DESIGN[position] == 0 -> {
                    Design = "Normal"
                    val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Normal")
                    editor.apply()
                }
                ARRAY_OF_DESIGN[position] == 1 -> {
                    Design = "Noir"
                    val editor =  locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Noir")
                    editor.apply()
                }
                ARRAY_OF_DESIGN[position] == 2 -> {
                    Design = "Rome"
                    val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Rome")
                    editor.apply()
                }
                ARRAY_OF_DESIGN[position] == 3 -> {
                    Design = "Casino"
                    val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Casino")
                    editor.apply()
                }
                ARRAY_OF_DESIGN[position] == 4 -> {
                    Design = "Egypt"
                    val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Egypt")
                    editor.apply()
                }
                ARRAY_OF_DESIGN[position] == 5 -> {
                    Design = "Gothic"
                    val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Gothic")
                    editor.apply()
                }
                ARRAY_OF_DESIGN[position] == 6 -> {
                    Design = "Japan"
                    val editor = locale_context!!.getSharedPreferences("UserData", Context.MODE_PRIVATE).edit()
                    editor.putString("design","Japan")
                    editor.apply()
                }
            }
            fragment_activity!!.findViewById<BottomNavigationView>(R.id.nav_view).itemIconTintList = generateColorStateList()
            fragment_activity!!.findViewById<BottomNavigationView>(R.id.nav_view).itemTextColor = generateColorStateList()
            when (Design) {
                "Normal" -> {
                    fragment_activity!!.soundSwitch.setTextColor(Color.BLACK)
                    fragment_activity!!.soundSwitch.typeface =  ResourcesCompat.getFont(CONTEXT, R.font.normal)
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.BLACK)
                    fragment_activity!!.vibrationSwitch.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)
                    fragment_activity!!.choose_design.setTextColor(Color.BLACK)
                    fragment_activity!!.choose_design.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)

                    fragment_activity!!.tb1.setBackgroundColor(rgb(245,245,245))
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.BLACK)
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)

                    fragment_activity!!.settings_menu.setBackgroundColor(Color.WHITE)
                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE);
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.BLACK)
                    fragment_activity!!.toolbarNameSettings.textSize = 25f

                    fragment_activity!!.language_button.setTextColor(Color.BLACK)
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.normal)
                    fragment_activity!!.language_button1.setTextColor(Color.BLACK)
                }
                "Egypt" -> {

                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(rgb(255, 230, 163))
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.BLACK)
                    fragment_activity!!.toolbarNameSettings.textSize = 25f

                    fragment_activity!!.settings_menu.setBackgroundResource(R.drawable.background_egypt)
                    fragment_activity!!.tb1.setBackgroundColor(rgb(255, 230, 163));

                    fragment_activity!!.choose_design.setTextColor(Color.BLACK)
                    fragment_activity!!.choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                    fragment_activity!!.choose_design.setTextSize(24f)
                    fragment_activity!!.soundSwitch.setTextColor(Color.BLACK)
                    fragment_activity!!.soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.BLACK)
                    fragment_activity!!.soundSwitch.setTextSize(24f)
                    fragment_activity!!.vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.egypt))
                    fragment_activity!!.vibrationSwitch.setTextSize(24f)
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.BLACK)

                    fragment_activity!!.language_button.setTextColor(Color.BLACK)
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                    fragment_activity!!.language_button1.setTextColor(Color.BLACK)
                }
                "Casino" -> {
                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_casino)
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.YELLOW)
                    fragment_activity!!.toolbarNameSettings.textSize = 25f
                    fragment_activity!!.settings_menu.setBackgroundResource(R.drawable.background2_casino)
                    fragment_activity!!.tb1.setBackgroundResource(R.drawable.bottom_navigation_casino)


                    fragment_activity!!.choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                    fragment_activity!!.choose_design.setTextColor(Color.YELLOW)
                    fragment_activity!!.choose_design.setTextSize(24f)

                    fragment_activity!!.soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                    fragment_activity!!.soundSwitch.setTextColor(Color.YELLOW)
                    fragment_activity!!.soundSwitch.setTextSize(24f)

                    fragment_activity!!.vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
                    fragment_activity!!.vibrationSwitch.setTextSize(24f)
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.YELLOW)
                    fragment_activity!!.language_button.setTextColor(Color.YELLOW)
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                    fragment_activity!!.language_button1.setTextColor(Color.BLACK)
                }
                "Rome" -> {
                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundResource(R.drawable.bottom_navigation_rome)
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                    //fragment_activity!!.toolbarNameSettings.setTextColor(rgb(193,150,63))
                    fragment_activity!!.toolbarNameSettings.textSize = 25f

                    fragment_activity!!.settings_menu.setBackgroundResource(R.drawable.background_rome)
                    fragment_activity!!.tb1.setBackgroundResource(R.drawable.bottom_navigation_rome)
                    fragment_activity!!.choose_design.setBackgroundColor(argb(0,0,0,0))
                    fragment_activity!!.choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                    fragment_activity!!.choose_design.setTextColor(rgb(193,150,63))
                    fragment_activity!!.choose_design.setTextSize(24f)

                    fragment_activity!!.soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                    fragment_activity!!.soundSwitch.setTextColor(rgb(193,150,63))
                    fragment_activity!!.soundSwitch.setTextSize(24f)

                    fragment_activity!!.vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.rome))
                    fragment_activity!!.vibrationSwitch.setTextSize(24f)
                    fragment_activity!!.vibrationSwitch.setTextColor(rgb(193,150,63))
                    fragment_activity!!.language_button.setTextColor(rgb(193,150,63))
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                    fragment_activity!!.language_button1.setTextColor(Color.BLACK)
                }
                "Gothic" -> {
                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.BLACK)
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.WHITE)
                    fragment_activity!!.toolbarNameSettings.textSize = 25f
                    fragment_activity!!.settings_menu.setBackgroundResource(R.drawable.background_gothic)
                    fragment_activity!!.tb1.setBackgroundColor(Color.BLACK)

                    fragment_activity!!.choose_design.setBackgroundColor(argb(0,0,0,0))
                    fragment_activity!!.choose_design.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    fragment_activity!!.choose_design.setTextColor(Color.WHITE)
                    fragment_activity!!.choose_design.textSize = 24f

                    fragment_activity!!.soundSwitch.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    fragment_activity!!.soundSwitch.setTextColor(Color.WHITE)
                    fragment_activity!!.soundSwitch.textSize = 24f

                    fragment_activity!!.vibrationSwitch.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    fragment_activity!!.vibrationSwitch.textSize = 24f
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.WHITE)
                    fragment_activity!!.language_button.setTextColor(Color.WHITE)
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                    fragment_activity!!.language_button1.setTextColor(Color.WHITE)

                }
                "Japan" -> {
                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.WHITE)
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.BLACK)
                    fragment_activity!!.toolbarNameSettings.textSize = 25f
                    fragment_activity!!.settings_menu.setBackgroundResource(R.drawable.background_japan)
                    fragment_activity!!.tb1.setBackgroundColor(argb(0,0,0,0))

                    fragment_activity!!.choose_design.setBackgroundColor(argb(0,0,0,0))
                    fragment_activity!!.choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                    fragment_activity!!.choose_design.setTextColor(Color.BLACK)
                    fragment_activity!!.choose_design.setTextSize(24f)

                    fragment_activity!!.soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                    fragment_activity!!.soundSwitch.setTextColor(Color.BLACK)
                    fragment_activity!!.soundSwitch.setTextSize(24f)

                    fragment_activity!!.vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.japan))
                    fragment_activity!!.vibrationSwitch.setTextSize(24f)
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.BLACK)
                    fragment_activity!!.tb1.setBackgroundColor(Color.WHITE)

                    fragment_activity!!.language_button.setTextColor(Color.BLACK)
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                    fragment_activity!!.language_button1.setTextColor(Color.BLACK)

                }
                "Noir" -> {
                    fragment_activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.setBackgroundColor(Color.BLACK)
                    fragment_activity!!.toolbarNameSettings.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                    //fragment_activity!!.toolbarNameSettings.setTextColor(Color.WHITE)
                    fragment_activity!!.toolbarNameSettings.textSize = 25f
                    fragment_activity!!.settings_menu.setBackgroundResource(R.drawable.background_noir)
                    fragment_activity!!.tb1.setBackgroundColor(Color.BLACK)

                    fragment_activity!!.choose_design.setBackgroundColor(argb(0,0,0,0))
                    fragment_activity!!.choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                    fragment_activity!!.choose_design.setTextColor(Color.WHITE)
                    fragment_activity!!.choose_design.setTextSize(24f)

                    fragment_activity!!.soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                    fragment_activity!!.soundSwitch.setTextColor(Color.WHITE)
                    fragment_activity!!.soundSwitch.setTextSize(24f)

                    fragment_activity!!.vibrationSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.noir))
                    fragment_activity!!.vibrationSwitch.setTextSize(24f)
                    fragment_activity!!.vibrationSwitch.setTextColor(Color.WHITE)

                    fragment_activity!!.language_button.setTextColor(Color.WHITE)
                    fragment_activity!!.language_button.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                    fragment_activity!!.language_button1.setTextColor(Color.WHITE)
                }
            }
            this.notifyDataSetChanged()
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

