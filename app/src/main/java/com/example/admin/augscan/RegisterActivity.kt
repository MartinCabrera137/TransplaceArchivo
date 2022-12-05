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
import com.example.admin.augscan.deleteItemsActivity
import com.example.admin.augscan.scanItemsActivity
import com.example.admin.augscan.viewInventoryActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import com.example.admin.augscan.ScanCodeActivitysearch
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.example.admin.augscan.scanItemsActivity.UsersViewHolder
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.admin.augscan.ScanCodeActivitydel
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class RegisterActivity : AppCompatActivity() {
    private var editTextName: EditText? = null
    private var editTextEmail: EditText? = null
    private var editTextPassword: EditText? = null
    private var editTextcPassword: EditText? = null
    var UserRegisterBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editTextName = findViewById(R.id.PersonName)
        editTextEmail = findViewById(R.id.emailRegister)
        editTextPassword = findViewById(R.id.passwordRegister)
        editTextcPassword = findViewById(R.id.confirmPassword)
        UserRegisterBtn = findViewById(R.id.button_register)
        //        editTextPhone = findViewById(R.id.edit_text_phone);
        progressBar = findViewById(R.id.progressbar)
        progressBar!!.setVisibility(View.GONE)
        mAuth = FirebaseAuth.getInstance()

        //  findViewById(R.id.button_register).setOnClickListener(this);
        UserRegisterBtn!!.setOnClickListener(View.OnClickListener { registerUser() })
    }

    override fun onStart() {
        super.onStart()
        if (mAuth!!.currentUser != null) {
            //handle the already login user
        }
    }

    private fun registerUser() {
        val name = editTextName!!.text.toString().trim { it <= ' ' }
        val email = editTextEmail!!.text.toString()
        val password = editTextPassword!!.text.toString().trim { it <= ' ' }
        val cpassword = editTextcPassword!!.text.toString().trim { it <= ' ' }
        // final String phone = editTextPhone.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail!!.error = "No puede estar vacio"
            editTextEmail!!.requestFocus()
            return
        }
        if (name.isEmpty()) {
            editTextName!!.error = "No puede estar vacio"
            editTextName!!.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail!!.error = "Correo invalido"
            editTextEmail!!.requestFocus()
            return
        }
        if (password.isEmpty()) {
            editTextPassword!!.error = "No puede estar vacio"
            editTextPassword!!.requestFocus()
            return
        }
        if (password.length < 6) {
            editTextPassword!!.error = "Debe contener mas de 6 caracteres"
            editTextPassword!!.requestFocus()
            return
        }
        if (password != cpassword) {
            editTextcPassword!!.error = "Las contraseñas no coinciden"
            editTextcPassword!!.requestFocus()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = User(
                        name,
                        email
                    )
                    //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    //important to retrive data and send data based on user email
                    val usernameinfirebase = mAuth!!.currentUser
                    val UserID = usernameinfirebase!!.email
                    // String result = UserID.substring(0, UserID.indexOf("@"));
                    val resultemail = UserID!!.replace(".", "")
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(resultemail).child("DatosDeUsuario")
                        .setValue(user).addOnCompleteListener { task ->
                            progressBar!!.visibility = View.GONE
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Registro exitoso",
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(
                                    Intent(
                                        this@RegisterActivity,
                                        dashboardActivity::class.java
                                    )
                                )
                            } else {

                            }
                        }
                } else {
                    progressBar!!.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, "Error al registrar el usuario", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}