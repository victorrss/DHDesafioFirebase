package com.digitalhouse.desafio4firebase.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.desafio4firebase.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvAdd.btnNewAcc.setOnClickListener { callHome() }
    }

    private fun callHome() {
        finishAffinity()
        var intent = Intent(application, HomeActivity::class.java)
        startActivity(intent)
    }
}