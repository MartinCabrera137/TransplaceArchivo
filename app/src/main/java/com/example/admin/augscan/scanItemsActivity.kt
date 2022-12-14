package com.example.admin.augscan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class scanitemsActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    var scantosearch: ImageButton? = null
    var searchbtn: Button? = null
    var adapter: Adapter? = null
    var mrecyclerview: RecyclerView? = null
    var mdatabaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_items)
        firebaseAuth = FirebaseAuth.getInstance()
        val users = firebaseAuth!!.getCurrentUser()
        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        mdatabaseReference =
            FirebaseDatabase.getInstance().getReference("items").child("items")
        resultsearcheview = findViewById(R.id.searchfield)
        scantosearch = findViewById(R.id.imageButtonsearch)
        searchbtn = findViewById(R.id.searchbtnn)
        mrecyclerview = findViewById(R.id.recyclerViews)
        val manager = LinearLayoutManager(this)
        mrecyclerview!!.setLayoutManager(manager)
        mrecyclerview!!.setHasFixedSize(true)
        mrecyclerview!!.setLayoutManager(LinearLayoutManager(this))
        scantosearch!!.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext, ScanCodeActivitysearch::class.java
                )
            )
        })
        searchbtn!!.setOnClickListener(View.OnClickListener {
            val searchtext = resultsearcheview!!.getText().toString()
            firebasesearch(searchtext)
        })
    }

    fun firebasesearch(searchtext: String) {
        val firebaseSearchQuery =
            mdatabaseReference!!.orderByChild("itemCodigoBarras").startAt(searchtext)
                .endAt(searchtext + "\uf8ff")
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<items, UsersViewHolder> =
            object : FirebaseRecyclerAdapter<items, UsersViewHolder>(
                items::class.java,
                R.layout.list_layout,
                UsersViewHolder::class.java,
                firebaseSearchQuery
            ) {
                override fun populateViewHolder(
                    viewHolder: UsersViewHolder,
                    model: items,
                    position: Int
                ) {
                    viewHolder.setDetails(
                        applicationContext,
                        model.itemCodigoBarras,
                        model.itemNombreCliente,
                        model.itemPedimento,
                        model.itemUbicacion

                    )
                }
            }
        mrecyclerview!!.adapter = firebaseRecyclerAdapter
    }

    class UsersViewHolder(var mView: View) : ViewHolder(mView) {
        fun setDetails(
            ctx: Context?,
            PitemCodigoBarras: String?,
            itemUbicacion: String?,
            itemNombreCliente: String?,
            itemPedimento: String?
        ) {
            val CodigoBarras = mView.findViewById<View>(R.id.viewPitemCodigoBarras) as TextView
            val Cliente = mView.findViewById<View>(R.id.viewitemNombreCliente) as TextView
            val Ubicacion = mView.findViewById<View>(R.id.viewitemUbicacion) as TextView
            val item = mView.findViewById<View>(R.id.viewitemPedimento) as TextView
            CodigoBarras.text = PitemCodigoBarras
            Cliente.text = itemUbicacion
            Ubicacion.text = itemNombreCliente
            item.text = itemPedimento
        }
    }

    companion object {
        var resultsearcheview: EditText? = null
    }
}