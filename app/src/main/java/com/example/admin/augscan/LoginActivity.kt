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
import android.view.View
import android.widget.*
import com.example.admin.augscan.ScanCodeActivitydel
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class LoginActivity : AppCompatActivity() {
    private var Email: EditText? = null
    private var Password: EditText? = null
    private var Login: Button? = null
    private var passwordreset: TextView? = null
    private var passwordresetemail: EditText? = null
    private var progressBar: ProgressBar? = null
    private var auth: FirebaseAuth? = null
    private var processDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Email = findViewById<View>(R.id.emailSignIn) as EditText
        Password = findViewById<View>(R.id.password) as EditText
        Login = findViewById<View>(R.id.Login) as Button
        passwordreset = findViewById(R.id.forgotpassword)
        passwordresetemail = findViewById(R.id.emailSignIn)
        progressBar = findViewById<View>(R.id.progressbars) as ProgressBar
        progressBar!!.visibility = View.GONE
        auth = FirebaseAuth.getInstance()
        processDialog = ProgressDialog(this)
        Login!!.setOnClickListener { validate(Email!!.text.toString(), Password!!.text.toString()) }
        passwordreset!!.setOnClickListener(View.OnClickListener { resetpasword() })
    }

    fun resetpasword() {
        val resetemail = passwordresetemail!!.text.toString()
        if (resetemail.isEmpty()) {
            passwordresetemail!!.error = "Vacio?"
            passwordresetemail!!.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        auth!!.sendPasswordResetEmail(resetemail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Revisa tu correo electronico",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "No pudimos enviarte un correo electronico",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar!!.visibility = View.GONE
            }
    }

    fun validate(userEmail: String?, userPassword: String?) {
        processDialog!!.show()
        auth!!.signInWithEmailAndPassword(userEmail!!, userPassword!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(this@LoginActivity, dashboardActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "Correo y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    processDialog!!.dismiss()
                }
            }
    }
}