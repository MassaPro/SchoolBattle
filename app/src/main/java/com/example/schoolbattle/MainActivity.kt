package com.example.schoolbattle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


//var database = FirebaseDatabase.getInstance()
//var myRef: DatabaseReference = database.getReference("message")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*
        myRef.child("bura").setValue("buuuuuur");

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

            }

        })

*/
    }

}
