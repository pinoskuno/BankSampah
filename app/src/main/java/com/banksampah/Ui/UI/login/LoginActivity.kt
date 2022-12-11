package com.banksampah.Ui.UI.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.banksampah.R
import com.banksampah.Ui.UI.Main.MainActivity
import com.banksampah.Ui.UI.welcome.WelcomeActivity
import com.banksampah.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding :ActivityLoginBinding
    private var email =""
    private val password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        playAnimation()
        checksession()
        btnSign()

    }
    private fun btnSign() {
        loginButton.setOnClickListener {
            if (TextUtils.isEmpty(ed_login_email.text.toString())){
                ed_login_email.setError(getString(R.string.email_login))
                return@setOnClickListener
            }else if (TextUtils.isEmpty(ed_login_password.text.toString())){
                ed_login_password.setError(getString(R.string.password_login))
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(ed_login_email.text.toString(),ed_login_password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        showLoading(false)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else{
                        showLoading(false)
                        Toast.makeText(this@LoginActivity,getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.apply {
            visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
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
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passowrdTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 500
        }.start()
    }
}