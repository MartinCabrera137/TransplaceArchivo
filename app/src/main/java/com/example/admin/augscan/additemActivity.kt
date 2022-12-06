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
    private var itemname: EditText? = null
    private var itemcategory: EditText? = null
    private var itemprice: EditText? = null
    private var Pitembarcode: TextView? = null
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
        itemname = findViewById(R.id.edititemname)
        itemcategory = findViewById(R.id.editcategory)
        itemprice = findViewById(R.id.editprice)
        Pitembarcode = findViewById(R.id.barcodeview)


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
        val itemnameValue = itemname!!.text.toString()
        val itemcategoryValue = itemcategory!!.text.toString()
        val itempriceValue = itemprice!!.text.toString()
        val PitembarcodeValue = Pitembarcode!!.text.toString()
        val users = firebaseAuth!!.currentUser
        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        if (PitembarcodeValue.isEmpty()) {
            Pitembarcode!!.error = "Vacio?"
            Pitembarcode!!.requestFocus()
            return
        }
        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && !TextUtils.isEmpty(
                itempriceValue
            )
        ) {
            val items = Items(itemnameValue, itemcategoryValue, itempriceValue, PitembarcodeValue)
            databaseReference!!.child("Items").child(PitembarcodeValue)
                .setValue(items)

            databaseReferencecat!!.child("ItemPorCategoria")
                .child(itemcategoryValue).child(PitembarcodeValue).setValue(items)
            //Limpiar datos
            itemname!!.setText("")
            itemcategory!!.setText("")
            itemprice!!.setText("")
            Pitembarcode!!.text = "Codigo de barras"
            //Alerta de exito
            Toast.makeText(this@additemActivity, "Cliente $itemnameValue agregado con exito", Toast.LENGTH_SHORT).show()
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