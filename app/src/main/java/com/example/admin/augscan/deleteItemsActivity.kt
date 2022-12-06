package com.example.admin.augscan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class deleteitemsActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    var scantodelete: ImageButton? = null
    var deletebtn: Button? = null
    var searchbtn: Button? = null
    var adapter: Adapter? = null
    var mrecyclerview: RecyclerView? = null
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_items)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("items").child("items")
        resultdeleteview = findViewById(R.id.barcodedelete)
        scantodelete = findViewById(R.id.buttonscandelete)
        deletebtn = findViewById(R.id.deleteItemToTheDatabasebtn)
        searchbtn = findViewById(R.id.searchbtnn)
        mrecyclerview = findViewById(R.id.recyclerViews)
        val manager = LinearLayoutManager(this)
        mrecyclerview!!.setLayoutManager(manager)
        mrecyclerview!!.setHasFixedSize(true)
        mrecyclerview!!.setLayoutManager(LinearLayoutManager(this))

        scantodelete!!.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext, ScanCodeActivitydel::class.java
                )
            )

        })
        searchbtn!!.setOnClickListener(View.OnClickListener {
            val searchtext = resultdeleteview!!.getText().toString()
            firebasesearch(searchtext)
        })
        deletebtn!!.setOnClickListener(View.OnClickListener { deletefrmdatabase() })
    }

    fun firebasesearch(searchtext: String) {
        val firebaseSearchQuery =
            databaseReference!!.orderByChild("itemCodigoBarras").startAt(searchtext)
                .endAt(searchtext + "\uf8ff")
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<items, scanitemsActivity.UsersViewHolder> =
            object : FirebaseRecyclerAdapter<items, scanitemsActivity.UsersViewHolder>(
                items::class.java,
                R.layout.list_layout,
                scanitemsActivity.UsersViewHolder::class.java,
                firebaseSearchQuery
            ) {
                override fun populateViewHolder(
                    viewHolder: scanitemsActivity.UsersViewHolder,
                    model: items,
                    position: Int
                ) {
                    viewHolder.setDetails(
                        applicationContext,
                        model.itemCodigoBarras,
                        model.itemNombreCliente,
                        model.itemUbicacion,
                        model.itemPedimento

                    )
                }
            }
        mrecyclerview!!.adapter = firebaseRecyclerAdapter
    }

    class UsersViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {
        fun setDetails(
            ctx: Context?,
            PitemCodigoBarras: String?,
            itemUbicacion: String?,
            itemNombreCliente: String?,
            itemPedimento: String?
        ) {
            val item_barcode = mView.findViewById<View>(R.id.viewPitemCodigoBarras) as TextView
            val item_name = mView.findViewById<View>(R.id.viewitemNombreCliente) as TextView
            val item_category = mView.findViewById<View>(R.id.viewitemUbicacion) as TextView
            val item_price = mView.findViewById<View>(R.id.viewitemPedimento) as TextView
            item_barcode.text = PitemCodigoBarras
            item_category.text = itemUbicacion
            item_name.text = itemNombreCliente
            item_price.text = itemPedimento
        }
    }
    fun deletefrmdatabase() {
        val deletebarcodevalue = resultdeleteview!!.text.toString()
        val users = firebaseAuth!!.currentUser
        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        if (!TextUtils.isEmpty(deletebarcodevalue)) {
            databaseReference!!.child(deletebarcodevalue)
                .removeValue()
            Toast.makeText(this@deleteitemsActivity, "Eliminado con exito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@deleteitemsActivity, "Escanea el codigo de barras", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        var resultdeleteview: TextView? = null


    }
}