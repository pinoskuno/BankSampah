package com.banksampah.Ui.UI.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import com.banksampah.R
import com.banksampah.Ui.UI.Register.RegisterActivity
import com.banksampah.Ui.UI.login.LoginActivity
import com.banksampah.databinding.ActivityWelcomeBinding
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        supportActionBar?.hide()
        btnLogin()
        btnRegist()
        playAnimation()
    }

    private fun btnRegist() {
        buatAkun.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.recy, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        ObjectAnimator.ofFloat(binding.txBank, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val signup = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.buatAkun, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }

    private fun btnLogin() {
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}