package com.banksampah.Ui.UI.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banksampah.R
import com.banksampah.Ui.UI.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnCreateAcc()
        btnAlready()
    }

    private fun btnAlready() {
        sign.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun btnCreateAcc() {
        createAcc.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}