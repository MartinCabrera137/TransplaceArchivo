package com.example.admin.augscan

import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.example.admin.augscan.R
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import com.example.admin.augscan.dashboardActivity
import com.example.admin.augscan.LoginActivity
import com.example.admin.augscan.RegisterActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.ProgressBar
import android.app.ProgressDialog
import com.google.android.gms.tasks.OnCompleteListener
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.admin.augscan.additemActivity
import com.example.admin.augscan.ScanCodeActivity
import android.text.TextUtils
import com.example.admin.augscan.MainActivity
import com.example.admin.augscan.SplashsActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.support.v4.content.ContextCompat
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.CardView
import com.example.admin.augscan.deleteItemsActivity
import com.example.admin.augscan.scanItemsActivity
import com.example.admin.augscan.viewInventoryActivity
import android.widget.ImageButton
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import com.example.admin.augscan.ScanCodeActivitysearch
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.example.admin.augscan.scanItemsActivity.UsersViewHolder
import android.support.v7.widget.RecyclerView.ViewHolder
import com.example.admin.augscan.ScanCodeActivitydel
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class viewInventoryActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    var mrecyclerview: RecyclerView? = null
    var mdatabaseReference: DatabaseReference? = null
    private var totalnoofitem: TextView? = null
    private var totalnoofsum: TextView? = null
    private var counttotalnoofitem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_inventory)
        totalnoofitem = findViewById(R.id.totalnoitem)
        firebaseAuth = FirebaseAuth.getInstance()
        val users = firebaseAuth!!.getCurrentUser()
        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        mdatabaseReference =
            FirebaseDatabase.getInstance().getReference("Items").child("Items")
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
                    val price = map!!["itemprice"]
                    val pValue = price.toString().toInt()
                    sum += pValue
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onStart() {
        super.onStart()
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Items, UsersViewHolder> =
            object : FirebaseRecyclerAdapter<Items, UsersViewHolder>(
                Items::class.java,
                R.layout.list_layout,
                UsersViewHolder::class.java,
                mdatabaseReference
            ) {
                override fun populateViewHolder(
                    viewHolder: UsersViewHolder,
                    model: Items,
                    position: Int
                ) {
                    viewHolder.setDetails(
                        applicationContext,
                        model.itemname,
                        model.itemcategory,
                        model.itemprice,
                        model.itembarcode
                    )
                }
            }
        mrecyclerview!!.adapter = firebaseRecyclerAdapter
    }
}