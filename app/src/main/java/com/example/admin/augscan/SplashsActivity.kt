package com.example.admin.augscan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

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