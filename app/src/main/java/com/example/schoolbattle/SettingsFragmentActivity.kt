package com.example.schoolbattle

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.schoolbattle.gamesonline.*
import kotlinx.android.synthetic.main.activity_game_item.view.*
import kotlinx.android.synthetic.main.activity_list_of_current_games.*
import kotlinx.android.synthetic.main.activity_settings_fragment.*
import kotlinx.android.synthetic.main.design_item.view.*
import kotlinx.android.synthetic.main.profile_dialog.view.*


class SettingsFragmentActivity : Fragment() {
    private var dialog_parametrs: Show_language_selection? = null

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

        (activity as AppCompatActivity?)!!.setSupportActionBar(my_toolbar_settings)

        if (Design == "Normal") {
            settings_menu.setBackgroundColor(Color.WHITE)
        }
        if (Design == "Casino") {
            settings_menu.setBackgroundResource(R.drawable.background_casino)
            my_toolbar_settings.setBackgroundColor(argb(0,0,0,0))

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(20f)


            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.casino))
            soundSwitch.setTextColor(Color.YELLOW)
            //soundSwitch.setBackgroundColor(Color.YELLOW)
            soundSwitch.setTextSize(20f)
        }
        if (Design == "Egypt") {
            settings_menu.setBackgroundResource(R.drawable.background_egypt)
            my_toolbar_settings.setBackgroundColor(rgb(224,164,103));

            choose_design.setBackgroundColor(argb(0,0,0,0))
            choose_design.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //choose_design.setTextColor(Color.YELLOW)
            choose_design.setTextSize(18f)



            soundSwitch.setTypeface(ResourcesCompat.getFont(CONTEXT, R.font.s))
            //soundSwitch.setTextColor(Color.YELLOW)
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
        soundSwitch.isChecked = true

        soundSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //
            } else {
                //
            }
        })

        val prefs = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        var data_from_memory = prefs?.getString("language", "").toString()
        if (data_from_memory == "") {
            val editor = activity?.getSharedPreferences("UserData", Context.MODE_PRIVATE)?.edit()
            editor?.putString("language", "EN")
            editor?.apply()
            data_from_memory = "EN"
        }









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
            val intent = Intent(v.context,NavigatorActivity::class.java)
            v.context.startActivity(intent)
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

