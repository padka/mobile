package com.example.pokedex.repositories

import com.example.pokedex.services.PokeApiService
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.models.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
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


    fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0,
        callback: (Response<PokemonResponse>) -> Unit
    ) {
        pokeApiService.listPokemons(limit, offset).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                callback(response)
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {

            }
        })
    }

    fun getPokemonDetail(id: String, callback: (Response<PokemonDetail>) -> Unit) {
        pokeApiService.getPokemonDetail(id).enqueue(object : Callback<PokemonDetail> {
            override fun onResponse(call: Call<PokemonDetail>, response: Response<PokemonDetail>) {
                callback(response)
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {

            }
        })
    }


}
