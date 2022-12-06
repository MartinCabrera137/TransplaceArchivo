package com.example.admin.augscan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.admin.augscan.scanitemsActivity.UsersViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class viewInventoryActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    var mrecyclerview: RecyclerView? = null
    var mdatabaseReference: DatabaseReference? = null
    private var totalnoofitem: TextView? = null

    private var counttotalnoofitem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_inventory)
        totalnoofitem = findViewById(R.id.totalnoitem)
        firebaseAuth = FirebaseAuth.getInstance()


        mdatabaseReference =
            FirebaseDatabase.getInstance().getReference("items").child("items")
        mrecyclerview = findViewById(R.id.recyclerViews)
        val manager = LinearLayoutManager(this)
        mrecyclerview!!.setLayoutManager(manager)
        mrecyclerview!!.setHasFixedSize(true)
        mrecyclerview!!.setLayoutManager(LinearLayoutManager(this))
        mdatabaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    counttotalnoofitem = dataSnapshot.childrenCount.toInt()
                    totalnoofitem!!.setText(Integer.toString(counttotalnoofitem))
                } else {
                    totalnoofitem!!.setText("0")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        mdatabaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var sum = 0
                for (ds in dataSnapshot.children) {
                    val map = ds.value as Map<String, Any>?
                    val price = map!!["itemPedimento"]
                    val pValue = price.toString().toInt()
                    sum += pValue
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onStart() {
        super.onStart()
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<items, UsersViewHolder> =
            object : FirebaseRecyclerAdapter<items, UsersViewHolder>(
                items::class.java,
                R.layout.list_layout,
                UsersViewHolder::class.java,
                mdatabaseReference
            ) {
                override fun populateViewHolder(
                    viewHolder: UsersViewHolder,
                    model: items,
                    position: Int
                ) {
                    viewHolder.setDetails(
                        applicationContext,
                        model.itemCodigoBarras,
                        model.itemNombreCliente,
                        model.itemUbicacion,
                        model.itemPedimento
                    )
                }
            }
        mrecyclerview!!.adapter = firebaseRecyclerAdapter
    }
}