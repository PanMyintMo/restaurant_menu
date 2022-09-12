package com.pan.foodbox.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.pan.foodbox.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Login Activity"
        firebaseAuth = FirebaseAuth.getInstance()

        updateUI(false)
        checkConnectionType()

        binding.textLogin.setOnClickListener {
            checkLoginDetail()
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun updateUI(login: Boolean) {
        binding.textProgress.visibility = if (login) View.VISIBLE else View.GONE
        binding.textProgress.isIndeterminate = false

    }
    private fun checkLoginDetail() {
        val email = binding.textLoginMail.text.toString()
        val pass = binding.textPass.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.textLoginMail.error = "Email is required"
            binding.textPass.requestFocus()
        }
        if (TextUtils.isEmpty(pass)) {
            binding.textPass.error = "Password is required"
            binding.textLoginMail.requestFocus()
        } else {
            loginUser(email, pass)
        }
    }
    private fun checkConnectionType() {

        val connectionManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi_connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile_data_connection =
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi_connection?.isConnectedOrConnecting == true) {
            Toast.makeText(this, "Wifi Connection is On", Toast.LENGTH_SHORT).show()
        } else {
            if (mobile_data_connection?.isConnectedOrConnecting == true) {
                Toast.makeText(this, "Mobile Data Connection is On", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, pass: String) {
        updateUI(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUI(false)
                    checkConnectionType()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
            .addOnFailureListener {
                Snackbar.make(
                    binding.root, it.message.toString(), Snackbar.LENGTH_SHORT
                ).show()
            }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}