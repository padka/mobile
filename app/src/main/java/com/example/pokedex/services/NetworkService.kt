package com.example.pokedex.services

import com.example.pokedex.models.PokemonAbilityDetail
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.models.PokemonMovesDetail
import com.example.pokedex.models.PokemonResponse
import com.example.pokedex.models.PokemonStatsDetail
import com.example.pokedex.models.PokemonTypeDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    fun listPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonResponse>

    @GET("pokemon/{nameOrId}")
    fun getPokemonDetail(
        @Path("nameOrId") nameOrId: String
    ): Call<PokemonDetail>

    @GET("type/{type}")
    fun getPokemonTypeDetail(
        @Path("type") type: String
    ): Call<PokemonTypeDetail>

    @GET("ability/{ability}")
    fun getPokemonAbilityDetail(
        @Path("ability") ability: String
    ): Call<PokemonAbilityDetail>

    @GET("pokemon/{nameOrId}")
    fun getPokemonStatsDetail(
        @Path("nameOrId") nameOrId: String
    ): Call<PokemonStatsDetail>

    @GET("pokemon/{nameOrId}")
    fun getPokemonMovesDetail(
        @Path("nameOrId") nameOrId: String
    ): Call<PokemonMovesDetail>

    @GET("pokemon/{nameOrId}")
    fun getPokemonHeightAndWeight(
        @Path("nameOrId") nameOrId: String
    ): Call<PokemonDetail>

}


