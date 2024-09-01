package com.example.pokedex.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.repositories.PokemonRepository
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonDetailBinding
import com.squareup.picasso.Picasso


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonUrl = intent.getStringExtra("POKEMON_URL") ?: return
        val pokemonId = extractPokemonIdFromUrl(pokemonUrl)
        PokemonRepository.getPokemonDetail(pokemonId) { response ->
            if (response.isSuccessful) {
                val pokemonDetail = response.body()
                if (pokemonDetail != null) {

                    updateUI(pokemonDetail)
                } else {
                    Log.d("DetailActivity", "Received null PokemonDetail")
                }
            } else {
                Log.d("DetailActivity", "Failed to fetch PokemonDetail")
            }
        }
    }

    private fun extractPokemonIdFromUrl(url: String): String {
        val segments = url.split("/")
        return segments[segments.size - 2]
    }

    private fun updateUI(pokemon: PokemonDetail) {
        with(binding) {
            pokemonNameTextView.text = "Name: ${pokemon.name}"
            pokemonTypeTextView.text = "Type: ${formatList(pokemon.types) { it.type.name }}"
            pokemonAbilitiesTextView.text =
                "Abilities: ${formatList(pokemon.abilities) { it.ability.name }}"
            pokemonStatsTextView.text =
                "Stats: ${formatList(pokemon.stats) { "${it.stat.name}: ${it.base_stat}" }}"
            pokemonHeightTextView.text = "Height: ${pokemon.height}"
            pokemonWeightTextView.text = "Weight: ${pokemon.weight}"
            pokemonMovesTextView.text = "Moves: ${formatList(pokemon.moves) { it.move.name }}"

            Picasso.get().load(pokemon.sprites.front_default)
                .error(R.drawable.pokemon_placeholder)
                .into(pokemonImageView)
        }
    }


    private fun <T> formatList(list: List<T>, transform: (T) -> String): String =
        list.joinToString(", ", transform = transform)


}
