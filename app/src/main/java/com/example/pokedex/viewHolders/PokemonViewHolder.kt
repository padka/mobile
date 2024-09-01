package com.example.pokedex.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.Pokemon
import com.example.pokedex.databinding.PokemonListItemBinding

class PokemonViewHolder(
    private val binding: PokemonListItemBinding,
    val onClick: (Pokemon) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemon: Pokemon) {
        binding.nameTextView.text = pokemon.name
        itemView.setOnClickListener { onClick(pokemon) }
    }
}
