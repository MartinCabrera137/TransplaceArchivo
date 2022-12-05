package com.example.admin.augscan

import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val user = auth!!.getCurrentUser()
        if (user != null) {
            finish()
            startActivity(Intent(this, dashboardActivity::class.java))
        }
    }

    fun login(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun register(view: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}