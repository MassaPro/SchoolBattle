package com.sga.schoolbattle.social

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sga.schoolbattle.*
import kotlinx.android.synthetic.main.activity_friends_item.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.activity_profile_user.profileMyName
import kotlinx.android.synthetic.main.activity_profile_user.profileMyStatus
import kotlinx.android.synthetic.main.activity_social.view.*

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ProfileUserActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        CONTEXT = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        val satView: CheckBox = profileFriendship as CheckBox
        val prfs = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = prfs?.getString("username", "")!!

        val item = intent.getStringExtra("name")!!
        profileMyName.text = item
        profileSendCall.setOnClickListener {
            val intent = Intent(this, NewGameActivity::class.java)
            intent.putExtra("playType", 5)
            intent.putExtra("opponent", item)
            startActivity(intent)
        }
        when (Design) {
            "Normal" -> {
                profileSendCall.width = 360
                profileSendCall.setBackgroundResource(R.drawable.button)

            }
            "Egypt" -> {
                userprofile.setBackgroundResource(R.drawable.background_egypt)
                toolbar.setBackgroundColor(rgb(255, 230, 163))
                profileMyName.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                profileMyName.setTextColor(Color.BLACK)
                profileMyName.setTextSize(20f)
                profileMyStatus.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                profileMyStatus.setTextColor(Color.BLACK)
                profileMyStatus.setTextSize(20f)
                profileFriendship.setTextColor(Color.BLACK)
                profileFriendship.setTextSize(16f)
                profileMyFriendsIn.setTextColor(Color.BLACK)
                profileMyFriendsIn.setTextSize(16f)
                profileMyFriendsOut.setTextColor(Color.BLACK)
                profileMyFriendsOut.setTextSize(16f)
                profileSendCall.setBackgroundColor(argb(0,0,0,0))
                profileSendCall.typeface = ResourcesCompat.getFont(CONTEXT, R.font.egypt)
                profileSendCall.setTextColor(Color.BLACK)
                profileSendCall.setTextSize(20f)
            }
            "Casino" -> {
                userprofile.setBackgroundResource(R.drawable.background2_casino)
                toolbar.setBackgroundResource(R.drawable.bottom_navigation_casino)
                profileMyName.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                profileMyName.setTextColor(Color.YELLOW)
                profileMyName.setTextSize(20f)
                profileMyStatus.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                profileMyStatus.setTextColor(Color.YELLOW)
                profileMyStatus.setTextSize(20f)
                profileFriendship.setTextColor(Color.YELLOW)
                profileFriendship.setTextSize(16f)
                profileMyFriendsIn.setTextColor(Color.YELLOW)
                profileMyFriendsIn.setTextSize(16f)
                profileMyFriendsOut.setTextColor(Color.YELLOW)
                profileMyFriendsOut.setTextSize(16f)
                profileSendCall.setBackgroundColor(argb(0,0,0,0))
                profileSendCall.typeface = ResourcesCompat.getFont(CONTEXT, R.font.casino)
                profileSendCall.setTextColor(Color.YELLOW)
                profileSendCall.setTextSize(20f)

            }
            "Rome" -> {
                userprofile.setBackgroundResource(R.drawable.background_rome)
                toolbar.setBackgroundResource(R.drawable.bottom_navigation_rome)
                profileMyName.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                profileMyName.setTextColor(rgb(193, 150, 63))
                profileMyName.setTextSize(20f)
                profileMyStatus.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                profileMyStatus.setTextColor(rgb(193, 150, 63))
                profileMyStatus.setTextSize(20f)
                profileFriendship.setTextColor(rgb(193, 150, 63))
                profileFriendship.setTextSize(16f)
                profileMyFriendsIn.setTextColor(rgb(193, 150, 63))
                profileMyFriendsIn.setTextSize(16f)
                profileMyFriendsOut.setTextColor(rgb(193, 150, 63))
                profileMyFriendsOut.setTextSize(16f)
                profileSendCall.setBackgroundColor(argb(0,0,0,0))
                profileSendCall.typeface = ResourcesCompat.getFont(CONTEXT, R.font.rome)
                profileSendCall.setTextColor(rgb(193, 150, 63))
                profileSendCall.setTextSize(20f)
            }
            "Gothic" -> {
                userprofile.setBackgroundResource(R.drawable.background_gothic)
                toolbar.setBackgroundResource(R.drawable.bottom_navigation_gothic)
                profileMyName.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                profileMyName.setTextColor(Color.WHITE)
                profileMyName.setTextSize(20f)
                profileMyStatus.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                profileMyStatus.setTextColor(Color.WHITE)
                profileMyStatus.setTextSize(20f)
                profileFriendship.setTextColor(Color.WHITE)
                profileFriendship.setTextSize(16f)
                profileMyFriendsIn.setTextColor(Color.WHITE)
                profileMyFriendsIn.setTextSize(16f)
                profileMyFriendsOut.setTextColor(Color.WHITE)
                profileMyFriendsOut.setTextSize(16f)
                profileSendCall.setBackgroundColor(argb(0,0,0,0))
                profileSendCall.typeface = ResourcesCompat.getFont(CONTEXT, R.font.gothic)
                profileSendCall.setTextColor(Color.WHITE)
                profileSendCall.setTextSize(20f)
            }
            "Japan" -> {
                userprofile.setBackgroundResource(R.drawable.background_japan)
                toolbar.setBackgroundColor(Color.WHITE)
                profileMyName.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                profileMyName.setTextColor(Color.BLACK)
                profileMyName.setTextSize(20f)
                profileMyStatus.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                profileMyStatus.setTextColor(Color.BLACK)
                profileMyStatus.setTextSize(20f)
                profileFriendship.setTextColor(Color.BLACK)
                profileFriendship.setTextSize(16f)
                profileMyFriendsIn.setTextColor(Color.BLACK)
                profileMyFriendsIn.setTextSize(16f)
                profileMyFriendsOut.setTextColor(Color.BLACK)
                profileMyFriendsOut.setTextSize(16f)
                profileSendCall.setBackgroundColor(argb(0,0,0,0))
                profileSendCall.typeface = ResourcesCompat.getFont(CONTEXT, R.font.japan)
                profileSendCall.setTextColor(Color.BLACK)
                profileSendCall.setTextSize(20f)
            }
            "Noir" -> {
                userprofile.setBackgroundResource(R.drawable.background_noir)
                toolbar.setBackgroundColor(Color.BLACK)
                profileMyName.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                profileMyName.setTextColor(Color.WHITE)
                profileMyName.setTextSize(20f)
                profileMyStatus.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                profileMyStatus.setTextColor(Color.WHITE)
                profileMyStatus.setTextSize(20f)
                profileFriendship.setTextColor(Color.WHITE)
                profileFriendship.setTextSize(16f)
                profileMyFriendsIn.setTextColor(Color.WHITE)
                profileMyFriendsIn.setTextSize(16f)
                profileMyFriendsOut.setTextColor(Color.WHITE)
                profileMyFriendsOut.setTextSize(16f)
                profileSendCall.setBackgroundColor(argb(0,0,0,0))
                profileSendCall.typeface = ResourcesCompat.getFont(CONTEXT, R.font.noir)
                profileSendCall.setTextColor(Color.WHITE)
                profileSendCall.setTextSize(20f)

            }
        }

        myRef.child("Users").child(username).child("FriendsIn").addListenerForSingleValueEvent(
            object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    satView.isChecked = p0.hasChild(item)
                    satView.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            myRef.child("Users").child(username).child("FriendsIn").child(item)
                                .setValue(
                                    item
                                )
                            myRef.child("Users").child(item).child("FriendsOut").child(username)
                                .setValue(
                                    username
                                )
                        } else {
                            myRef.child("Users").child(username).child("FriendsIn").child(item)
                                .removeValue()
                            myRef.child("Users").child(item).child("FriendsOut").child(username)
                                .removeValue()
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
