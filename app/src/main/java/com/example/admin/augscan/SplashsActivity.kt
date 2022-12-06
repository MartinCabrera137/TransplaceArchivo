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
import android.app.ProgressDialog
import com.google.android.gms.tasks.OnCompleteListener
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
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.CardView
import com.example.admin.augscan.deleteitemsActivity
import com.example.admin.augscan.scanitemsActivity
import com.example.admin.augscan.viewInventoryActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import com.example.admin.augscan.ScanCodeActivitysearch
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.example.admin.augscan.scanitemsActivity.UsersViewHolder
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.*
import com.example.admin.augscan.ScanCodeActivitydel
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class SplashsActivity : AppCompatActivity() {
    private var logo: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashs)
        logo = findViewById<View>(R.id.logo) as ImageView
        Handler().postDelayed({
            val i = Intent(this@SplashsActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, splashTimeOut.toLong())
        val myanim = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation)
        logo!!.startAnimation(myanim)
    }

    companion object {
        private const val splashTimeOut = 2000
    }
}