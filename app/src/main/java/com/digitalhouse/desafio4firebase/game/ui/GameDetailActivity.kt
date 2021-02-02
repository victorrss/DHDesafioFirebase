package com.digitalhouse.desafio4firebase.game.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.desafio4firebase.R
import com.digitalhouse.desafio4firebase.databinding.ActivityGameDetailBinding
import com.squareup.picasso.Picasso

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val year = intent.getStringExtra("year")
        val description = intent.getStringExtra("description")
        val url = intent.getStringExtra("url")

        if (!url.toString().isNullOrEmpty())
            Picasso
                .get()
                .load(url.toString())
                .into(binding.ivPhotoDetailGame)
        else binding.ivPhotoDetailGame.setImageResource(R.drawable.sem_foto)

        binding.tvNameDetail.text = name.toString()
        binding.detail.tvNameDetailGame.text = name.toString()
        binding.detail.tvYearDetailGame.text = year.toString()
        binding.detail.tvDescriptionDetailGame.text = description.toString()
    }
}