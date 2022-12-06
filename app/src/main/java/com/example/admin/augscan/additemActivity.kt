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
    private var itemNombreCliente: EditText? = null
    private var itemUbicacion: EditText? = null
    private var itemPedimento: EditText? = null
    private var PitemCodigoBarras: TextView? = null
    private var firebaseAuth: FirebaseAuth? = null
    var scanbutton: Button? = null
    var additemtodatabase: Button? = null
    var databaseReference: DatabaseReference? = null
    var databaseReferencecat: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("items")
        databaseReferencecat = FirebaseDatabase.getInstance().getReference("items")
        resulttextview = findViewById(R.id.barcodeview)
        additemtodatabase = findViewById(R.id.additembuttontodatabase)
        scanbutton = findViewById(R.id.buttonscan)
        itemNombreCliente = findViewById(R.id.edititemNombreCliente)
        itemUbicacion = findViewById(R.id.editcategory)
        itemPedimento = findViewById(R.id.editprice)
        PitemCodigoBarras = findViewById(R.id.barcodeview)


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
        val itemNombreClienteValue = itemNombreCliente!!.text.toString()
        val itemUbicacionValue = itemUbicacion!!.text.toString()
        val itemPedimentoValue = itemPedimento!!.text.toString()
        val PitemCodigoBarrasValue = PitemCodigoBarras!!.text.toString()
        val users = firebaseAuth!!.currentUser
        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        if (PitemCodigoBarrasValue.isEmpty()) {
            PitemCodigoBarras!!.error = "Vacio?"
            PitemCodigoBarras!!.requestFocus()
            return
        }
        if (!TextUtils.isEmpty(itemNombreClienteValue) && !TextUtils.isEmpty(itemUbicacionValue) && !TextUtils.isEmpty(
                itemPedimentoValue
            )
        ) {
            val items = items(itemNombreClienteValue, itemUbicacionValue, itemPedimentoValue, PitemCodigoBarrasValue)
            databaseReference!!.child("items").child(PitemCodigoBarrasValue)
                .setValue(items)

            databaseReferencecat!!.child("itemsPorUbicacion")
                .child(itemUbicacionValue).child(PitemCodigoBarrasValue).setValue(items)
            //Limpiar datos
            itemNombreCliente!!.setText("")
            itemUbicacion!!.setText("")
            itemPedimento!!.setText("")
            PitemCodigoBarras!!.text = "Codigo de barras"
            //Alerta de exito
            Toast.makeText(this@additemActivity, "Cliente $itemNombreClienteValue agregado con exito", Toast.LENGTH_SHORT).show()
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