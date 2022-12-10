package com.banksampah.Ui.UI.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banksampah.R
import com.banksampah.Ui.UI.Main.MainActivity
import com.banksampah.Ui.UI.Register.RegisterActivity
import com.banksampah.Ui.UI.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnBack()
        btnSign()
        btnCreateAcc()
    }

    private fun btnCreateAcc() {
        createAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btnSign() {
        sign.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun btnBack() {
        back.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }
}