package com.banksampah.Ui.UI.Main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.banksampah.R
import com.banksampah.Ui.UI.History.HistoryActivity
import com.banksampah.Ui.UI.Post.pickUpActivity
import com.banksampah.Ui.UI.login.LoginActivity
import com.banksampah.Ui.UI.sampah.tSampahActivity
import com.banksampah.Ui.UI.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_cv_main.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    var REQ_PERMISSION = 100
    var strCurrentLatitude = 0.0
    var strCurrentLongitude = 0.0
    lateinit var strCurrentLocation: String
    lateinit var simpleLocation: SimpleLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        databaseReference = database?.reference?.child("Profile")
        database = FirebaseDatabase.getInstance()
        checksession()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                val i = Intent(this, WelcomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun setLocation() {
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }


        strCurrentLatitude = simpleLocation.latitude
        strCurrentLongitude = simpleLocation.longitude


        strCurrentLocation = "$strCurrentLatitude,$strCurrentLongitude"
    }

    private fun setInitLayout() {
        cvPickup.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, pickUpActivity::class.java)
            startActivity(intent)
        }

        cvCatagory.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, tSampahActivity::class.java)
            startActivity(intent)
        }

        cvHistory.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
        cvLocation.setOnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERMISSION && resultCode == RESULT_OK) { }
    }

    private fun setCurrentLocation() {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1)
            if (addressList != null && addressList.size > 0) {
                val strCurrentLocation = addressList[0].locality
                tv_loaction.text = strCurrentLocation
                tv_loaction.isSelected = true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun setStatusBar() {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    }

    private fun checksession(){
        val session = auth.currentUser
        if (session != null){
            setPermission()
            setStatusBar()
            setLocation()
            setInitLayout()
            setCurrentLocation()
        }
        else{
            startActivity(Intent(this,WelcomeActivity::class.java))
            finish()
        }
    }
    companion object {

    }
}