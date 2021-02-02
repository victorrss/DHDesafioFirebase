package com.digitalhouse.desafio4firebase.game.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.desafio4firebase.R
import com.digitalhouse.desafio4firebase.game.entities.Game
import com.digitalhouse.desafio4firebase.game.ui.GameDetailActivity
import com.squareup.picasso.Picasso

class GameAdapter(val listGames: ArrayList<Game>, val context: Context) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentItem = listGames[position]
        holder.name.text = currentItem.name
        holder.year.text = currentItem.year

        if (!currentItem.url.isNullOrEmpty())
            Picasso
                .get()
                .load(currentItem.url)
                .into(holder.imageView)
        else holder.imageView.setImageResource(R.drawable.sem_foto)

        holder.imageView.setOnClickListener {
            context.startActivity(
                Intent(it.context, GameDetailActivity::class.java)
                    .putExtra("name", currentItem.name)
                    .putExtra("year", currentItem.year)
                    .putExtra("description", currentItem.description)
                    .putExtra("url", currentItem.url)
            )
        }
    }

    override fun getItemCount(): Int {
        return listGames.size
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tvName)
        var year: TextView = itemView.findViewById(R.id.tvYear)
        var imageView: ImageView = itemView.findViewById(R.id.ivImage)
    }
}

