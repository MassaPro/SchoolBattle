package com.example.schoolbattle.social

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.CheckBox
import com.example.schoolbattle.R
import com.example.schoolbattle.myRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile_user.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ProfileUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        val satView: CheckBox = profileFriendship as CheckBox
        val prfs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")

        val item = intent.getStringExtra("name")
        profileMyName.text = item
        myRef.child("Users").child(username!!).child("FriendsIn").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                satView.isChecked = p0.hasChild(item)
                satView.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        myRef.child("Users").child(username!!).child("FriendsIn").child(item).setValue(item)
                        myRef.child("Users").child(item).child("FriendsOut").child(username!!).setValue(username)
                    } else {
                        myRef.child("Users").child(username!!).child("FriendsIn").child(item).removeValue()
                        myRef.child("Users").child(item).child("FriendsOut").child(username!!).removeValue()
                    }
                }
            }
        })


    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                //navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
