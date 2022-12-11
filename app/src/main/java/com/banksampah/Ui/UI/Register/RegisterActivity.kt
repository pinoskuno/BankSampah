package com.banksampah.Ui.UI.Register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.banksampah.R
import com.banksampah.Ui.UI.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        databaseReference = database?.reference!!.child("Profile")
        database = FirebaseDatabase.getInstance()
        register()
    }

    private fun register() {
        sign.setOnClickListener {
            if(TextUtils.isEmpty(ed_register_name.text.toString())){
                ed_register_name.setError("Please Enter yourname")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ed_register_email.text.toString())){
                ed_register_email.setError("Please Enter Email")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ed_register_phoneNumber.text.toString())){
                ed_register_phoneNumber.setError("Please Enter Phone number")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ed_register_password.text.toString())){
                ed_register_password.setError("Please Enter password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(ed_register_email.text.toString(), ed_register_password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val currentUser = auth.currentUser
                        val userDB = databaseReference?.child(currentUser?.uid!!)
                        userDB?.child("Name")?.setValue(ed_register_name.text.toString())
                        userDB?.child("Email")?.setValue(ed_register_email.text.toString())
                        Toast.makeText(this@RegisterActivity,"Registration Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@RegisterActivity,"Registration Failed, Please Check your data ", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun btnCreateAcc() {
        createAcc.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}