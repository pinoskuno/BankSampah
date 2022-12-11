package com.banksampah.Ui.UI.Register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.banksampah.R
import com.banksampah.Ui.UI.login.LoginActivity
import com.banksampah.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var binding : ActivityRegisterBinding
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        databaseReference = database?.reference?.child("Profile")
        database = FirebaseDatabase.getInstance()
        register()
        playAnimation()
    }

    private fun register() {
        signupButton.setOnClickListener {
            if(TextUtils.isEmpty(ed_register_name.text.toString())){
                ed_register_name.setError("Please Enter yourname")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ed_register_email.text.toString())){
                ed_register_email.setError("Please Enter Email")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(ed_register_password.text.toString())){
                ed_register_password.setError("Please Enter password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(ed_register_email.text.toString(), ed_register_password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        showLoading(false)
                        val currentUser = auth.currentUser
                        val userDB = databaseReference?.child(currentUser?.uid!!)
                        userDB?.child("Name")?.setValue(ed_register_name.text.toString())
                        userDB?.child("Email")?.setValue(ed_register_email.text.toString())
                        Toast.makeText(this@RegisterActivity,"Registration Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    else{
                        showLoading(false)
                        Toast.makeText(this@RegisterActivity,"Registration Failed, Please Check your data ", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
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
}