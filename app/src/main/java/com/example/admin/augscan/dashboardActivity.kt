package com.example.admin.augscan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class dashboardActivity : AppCompatActivity(), View.OnClickListener {
    private var firebaseAuth: FirebaseAuth? = null
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