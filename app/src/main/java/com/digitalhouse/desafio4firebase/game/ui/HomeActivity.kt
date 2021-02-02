package com.digitalhouse.desafio4firebase.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalhouse.desafio4firebase.databinding.ActivityHomeBinding
import com.digitalhouse.desafio4firebase.game.adapter.GameAdapter

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    val viewModel by viewModels<HomeViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListGames()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.rvHomeGame
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.getListGames()

        viewModel.listGames.observe(this) { recyclerView.adapter = GameAdapter(it, this) }

        binding.fabAddGame.setOnClickListener { callCadastraJogo() }
    }

    private fun callCadastraJogo() {
        var intent = Intent(application, AddGameActivity::class.java)
        startActivity(intent)
    }
}