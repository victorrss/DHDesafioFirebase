package com.digitalhouse.desafio4firebase.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.desafio4firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvLogin.btnLogin
            .setOnClickListener { callHome() }

        binding.cvLogin.tvNewAcc
            .setOnClickListener { callRegister() }
    }

    private fun callRegister() {
        var intent = Intent(application, AddActivity::class.java)
        startActivity(intent)
    }

    private fun callHome() {
        finishAffinity()
        var intent = Intent(application, HomeActivity::class.java)
        startActivity(intent)
    }
}