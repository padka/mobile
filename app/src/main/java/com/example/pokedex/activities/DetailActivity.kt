package com.example.pokedex.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityPokemonDetailBinding
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.repositories.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем URL покемона из intent
        val pokemonUrl = intent.getStringExtra("POKEMON_URL") ?: run {
            Log.e("DetailActivity", "Pokemon URL is missing")
            finish() // Завершаем активность, если URL отсутствует
            return
        }

        // Извлекаем ID покемона из URL
        val pokemonId = extractPokemonIdFromUrl(pokemonUrl)

        // Загружаем детали покемона
        loadPokemonDetails(pokemonId)
    }

    // Функция для извлечения ID покемона из его URL
    private fun extractPokemonIdFromUrl(url: String): String {
        return url.split("/").dropLast(1).last()
    }

    // Функция для загрузки деталей покемона
    private fun loadPokemonDetails(pokemonId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = PokemonRepository.getPokemonDetail(pokemonId)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            updateUI(it)
                        } ?: Log.e("DetailActivity", "Received null PokemonDetail")
                    } else {
                        Log.e("DetailActivity", "Failed to fetch PokemonDetail: ${response.errorBody()?.string()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("DetailActivity", "Error occurred while fetching Pokemon details", e)
                }
            }
        }
    }

    // Обновляем UI с деталями покемона
    private fun updateUI(pokemon: PokemonDetail) {
        with(binding) {
            // Устанавливаем значения для TextView
            pokemonNameTextView.text = "Name: ${pokemon.name.capitalize()}"
            pokemonTypeTextView.text = "Type: ${formatList(pokemon.types) { it.type.name }}"
            pokemonAbilitiesTextView.text = "Abilities: ${formatList(pokemon.abilities) { it.ability.name }}"
            pokemonStatsTextView.text = "Stats: ${formatList(pokemon.stats) { "${it.stat.name}: ${it.base_stat}" }}"
            pokemonHeightTextView.text = "Height: ${pokemon.height}"
            pokemonWeightTextView.text = "Weight: ${pokemon.weight}"
            pokemonMovesTextView.text = "Moves: ${formatList(pokemon.moves) { it.move.name }}"

            // Загрузка изображения с помощью Glide
            Glide.with(this@DetailActivity)
                .load(pokemon.sprites.front_default)
                .error(R.drawable.pokemon_placeholder) // Placeholder в случае ошибки
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("DetailActivity", "Image load failed", e)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("DetailActivity", "Image loaded successfully")
                        return false
                    }
                })
                .into(pokemonImageView) // Отображаем изображение в ImageView
        }
    }

    // Форматирование списка для вывода через TextView
    private fun <T> formatList(list: List<T>, transform: (T) -> String): String =
        list.joinToString(", ", transform = transform)
}