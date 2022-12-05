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
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class deleteItemsActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    var scantodelete: Button? = null
    var deletebtn: Button? = null
    var adapter: Adapter? = null
    var mrecyclerview: RecyclerView? = null
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_items)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Items").child("Items")
        resultdeleteview = findViewById(R.id.barcodedelete)
        scantodelete = findViewById(R.id.buttonscandelete)
        deletebtn = findViewById(R.id.deleteItemToTheDatabasebtn)

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

            val searchtext = resultdeleteview!!.getText().toString()
            firebasesearch(searchtext)
        })
        deletebtn!!.setOnClickListener(View.OnClickListener { deletefrmdatabase() })
    }

    fun firebasesearch(searchtext: String) {
        val firebaseSearchQuery =
            databaseReference!!.orderByChild("PPCodigoBarras").startAt(searchtext)
                .endAt(searchtext + "\uf8ff")
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Items, scanItemsActivity.UsersViewHolder> =
            object : FirebaseRecyclerAdapter<Items, scanItemsActivity.UsersViewHolder>(
                Items::class.java,
                R.layout.list_layout,
                scanItemsActivity.UsersViewHolder::class.java,
                firebaseSearchQuery
            ) {
                override fun populateViewHolder(
                    viewHolder: scanItemsActivity.UsersViewHolder,
                    model: Items,
                    position: Int
                ) {
                    viewHolder.setDetails(
                        applicationContext,
                        model.PCodigoBarras,
                        model.PUbicacion,
                        model.PCliente,
                        model.PPedimento
                    )
                }
            }
        mrecyclerview!!.adapter = firebaseRecyclerAdapter
    }

    class UsersViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {
        fun setDetails(
            ctx: Context?,
            PPCodigoBarras: String?,
            PUbicacion: String?,
            PCliente: String?,
            PPedimento: String?
        ) {
            val item_barcode = mView.findViewById<View>(R.id.viewPPCodigoBarras) as TextView
            val item_name = mView.findViewById<View>(R.id.viewPCliente) as TextView
            val item_category = mView.findViewById<View>(R.id.viewPUbicacion) as TextView
            val item_price = mView.findViewById<View>(R.id.viewPPedimento) as TextView
            item_barcode.text = PPCodigoBarras
            item_category.text = PUbicacion
            item_name.text = PCliente
            item_price.text = PPedimento
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
            Toast.makeText(this@deleteItemsActivity, "Eliminado con exito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@deleteItemsActivity, "Escanea el codigo de barras", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        var resultdeleteview: TextView? = null


    }
}