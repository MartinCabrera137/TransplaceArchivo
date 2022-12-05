package com.example.admin.augscan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class additemActivity : AppCompatActivity() {
    private var PCliente: EditText? = null
    private var PUbicacion: EditText? = null
    private var PPedimento: EditText? = null
    private var PPCodigoBarras: TextView? = null
    private var firebaseAuth: FirebaseAuth? = null
    var scanbutton: Button? = null
    var additemtodatabase: Button? = null
    var databaseReference: DatabaseReference? = null
    var databaseReferencecat: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Items")
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("Items")
        resulttextview = findViewById(R.id.barcodeview)
        additemtodatabase = findViewById(R.id.additembuttontodatabase)
        scanbutton = findViewById(R.id.buttonscan)
        PCliente = findViewById(R.id.editPCliente)
        PUbicacion = findViewById(R.id.editcategory)
        PPedimento = findViewById(R.id.editprice)
        PPCodigoBarras = findViewById(R.id.barcodeview)


        // String result = finaluser.substring(0, finaluser.indexOf("@"));
        scanbutton!!.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext, ScanCodeActivity::class.java
                )
            )
        })
        additemtodatabase!!.setOnClickListener(View.OnClickListener { additem() })
    }

    // addding item to databse
    fun additem() {
        val PClienteValue = PCliente!!.text.toString()
        val PUbicacionValue = PUbicacion!!.text.toString()
        val PPedimentoValue = PPedimento!!.text.toString()
        val PPCodigoBarrasValue = PPCodigoBarras!!.text.toString()
        val users = firebaseAuth!!.currentUser
        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        if (PPCodigoBarrasValue.isEmpty()) {
            PPCodigoBarras!!.error = "Vacio?"
            PPCodigoBarras!!.requestFocus()
            return
        }
        if (!TextUtils.isEmpty(PClienteValue) && !TextUtils.isEmpty(PUbicacionValue) && !TextUtils.isEmpty(
                PPedimentoValue
            )
        ) {
            val items = Items(PClienteValue, PUbicacionValue, PPedimentoValue, PPCodigoBarrasValue)
            databaseReference!!.child("Items").child(PPCodigoBarrasValue)
                .setValue(items)

            databaseReferencecat!!.child("ItemPorCategoria")
                .child(PUbicacionValue).child(PPCodigoBarrasValue).setValue(items)
            //Limpiar datos
            PCliente!!.setText("")
            PUbicacion!!.setText("")
            PPedimento!!.setText("")
            PPCodigoBarras!!.text = "Codigo de barras"
            //Alerta de exito
            Toast.makeText(this@additemActivity, "Cliente $PClienteValue agregado con exito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@additemActivity, "Favor de llenar todos los campos", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // logout below
    private fun Logout() {
        firebaseAuth!!.signOut()
        finish()
        startActivity(Intent(this@additemActivity, LoginActivity::class.java))
        Toast.makeText(this@additemActivity, "Cerrando sesion", Toast.LENGTH_SHORT).show()
    }


    companion object {
        var resulttextview: TextView? = null
    }
}