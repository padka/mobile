package com.example.pokedex.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.models.Pokemon
import com.example.pokedex.adapters.PokemonListAdapter
import com.example.pokedex.repositories.PokemonRepository
import com.example.pokedex.R

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonListAdapter
    private var pokemonList: List<Pokemon> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.pokemonListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PokemonListAdapter { pokemon ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("POKEMON_URL", pokemon.url)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        loadPokemons()
    }

    private fun loadPokemons() {
        PokemonRepository.getPokemonList { response ->
            if (response.isSuccessful) {
                response.body()?.results?.let { pokemons ->
                    pokemonList = pokemons
                    adapter.submitList(pokemonList)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchPokemon(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let { searchPokemon(it) }
                return true
            }
        })
        return true
    }

    private fun searchPokemon(query: String) {

        val filteredList = pokemonList.filter { it.name.contains(query, ignoreCase = true) }
        adapter.submitList(filteredList)
    }
}
