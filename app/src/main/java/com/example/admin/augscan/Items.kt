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
import android.widget.EditText
import android.widget.TextView
import android.widget.ProgressBar
import android.app.ProgressDialog
import com.google.android.gms.tasks.OnCompleteListener
import android.widget.Toast
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
import android.widget.ImageButton
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import com.example.admin.augscan.ScanCodeActivitysearch
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.example.admin.augscan.scanitemsActivity.UsersViewHolder
import android.support.v7.widget.RecyclerView.ViewHolder
import com.example.admin.augscan.ScanCodeActivitydel
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class items {
    var itemNombreCliente: String? = null
        private set
    var itemUbicacion: String? = null
        private set
    var itemPedimento: String? = null
        private set
    var itemCodigoBarras: String? = null
        private set

    constructor() {}
    constructor(
        itemNombreCliente: String?,
        itemUbicacion: String?,
        itemPedimento: String?,
        itemCodigoBarras: String?
    ) {
        this.itemNombreCliente = itemNombreCliente
        this.itemUbicacion = itemUbicacion
        this.itemPedimento = itemPedimento
        this.itemCodigoBarras = itemCodigoBarras
    }
}