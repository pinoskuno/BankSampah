package com.banksampah.Ui.UI.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.banksampah.R
import com.banksampah.Ui.UI.Main.MainActivity
import com.banksampah.Ui.UI.Register.RegisterActivity
import com.banksampah.Ui.UI.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.createAcc
import kotlinx.android.synthetic.main.activity_login.sign
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        checksession()
        btnBack()
        btnCreateAcc()
        btnSign()

    }

    private fun btnCreateAcc() {
        createAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btnSign() {
        sign.setOnClickListener {
            if (TextUtils.isEmpty(ed_login_email.text.toString())){
                ed_login_email.setError("Please enter your Email ")
                return@setOnClickListener
            }else if (TextUtils.isEmpty(ed_login_password.text.toString())){
                ed_login_password.setError("Please enter your Password ")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(ed_login_email.text.toString(),ed_login_password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                     }
                    else{
                        Toast.makeText(this@LoginActivity,"Login Failed, Please Check your data ", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun checksession(){
        val session = auth.currentUser
        if (session != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun btnBack() {
        back.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
}