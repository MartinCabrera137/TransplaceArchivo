package com.example.admin.augscan

import android.Manifest
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
import com.google.zxing.Result

class ScanCodeActivitydel : AppCompatActivity(), ZXingScannerView.ResultHandler {
    var MY_PERMISSIONS_REQUEST_CAMERA = 0
    var scannerView: ZXingScannerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerView = ZXingScannerView(this)
        setContentView(scannerView)
    }

    override fun handleResult(result: Result) {

        deleteitemsActivity.Companion.resultdeleteview!!.setText(result.text)
        onBackPressed()

    }

    override fun onPause() {
        super.onPause()
        scannerView!!.stopCamera()
    }

    //    @Override
    //    protected void onResume() {
    //        super.onResume();
    //        scannerView.setResultHandler(this);
    //        scannerView.startCamera();
    //    }
    override fun onPostResume() {
        super.onPostResume()
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                MY_PERMISSIONS_REQUEST_CAMERA
            )
        }
        scannerView!!.setResultHandler(this)
        scannerView!!.startCamera()
    }
}