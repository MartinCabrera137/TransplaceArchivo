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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.admin.augscan.ScanCodeActivitydel
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class dashboardActivity : AppCompatActivity(), View.OnClickListener {
    private var firebaseAuth: FirebaseAuth? = null
    var toast: Button? = null
    private var additems: CardView? = null
    private var deleteitems: CardView? = null
    private var scanitems: CardView? = null
    private var viewInventory: CardView? = null
    private var logout: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        // this is for username to appear after login
        firebaseAuth = FirebaseAuth.getInstance()
        val users = firebaseAuth!!.getCurrentUser()
        val finaluser = users!!.email
        val result = finaluser!!.substring(0, finaluser.indexOf("@"))
        val resultemail = result.replace(".", "")

//        toast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(dashboardActivity.this, users.getEmail(), Toast.LENGTH_SHORT).show();
//            }
//        });
        additems = findViewById<View>(R.id.additems) as CardView
        deleteitems = findViewById<View>(R.id.deleteitems) as CardView
        scanitems = findViewById<View>(R.id.scanitems) as CardView
        viewInventory = findViewById<View>(R.id.viewInventory) as CardView
        logout = findViewById<View>(R.id.btnCerrarSesion) as Button
        additems!!.setOnClickListener(this)
        deleteitems!!.setOnClickListener(this)
        scanitems!!.setOnClickListener(this)
        viewInventory!!.setOnClickListener(this)
        logout!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val i: Intent
        when (view.id) {
            R.id.additems -> {
                i = Intent(this, additemActivity::class.java)
                startActivity(i)
            }
            R.id.deleteitems -> {
                i = Intent(this, deleteitemsActivity::class.java)
                startActivity(i)
            }
            R.id.scanitems -> {
                i = Intent(this, scanitemsActivity::class.java)
                startActivity(i)
            }
            R.id.viewInventory -> {
                i = Intent(this, viewInventoryActivity::class.java)
                startActivity(i)
            }
            R.id.btnCerrarSesion -> {
            Logout()
            }

            else -> {}
        }
    }

    // logout below
    private fun Logout() {
        firebaseAuth!!.signOut()
        finish()
        startActivity(Intent(this@dashboardActivity, LoginActivity::class.java))
        Toast.makeText(this@dashboardActivity, "Cerrando sesion", Toast.LENGTH_SHORT).show()
    }

}