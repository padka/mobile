package com.example.pokedex.repositories

import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.models.PokemonResponse
import com.example.pokedex.services.PokeApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {
    private val pokeApiService: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }

    // Используем suspend функции для асинхронного выполнения запросов
    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): Response<PokemonResponse> {
        return pokeApiService.listPokemons(limit, offset)
    }

    suspend fun getPokemonDetail(id: String): Response<PokemonDetail> {
        return pokeApiService.getPokemonDetail(id)
    }
}