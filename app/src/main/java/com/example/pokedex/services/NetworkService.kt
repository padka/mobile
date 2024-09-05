package com.example.pokedex.services

import com.example.pokedex.models.PokemonAbilityDetail
import com.example.pokedex.models.PokemonDetail
import com.example.pokedex.models.PokemonMovesDetail
import com.example.pokedex.models.PokemonResponse
import com.example.pokedex.models.PokemonStatsDetail
import com.example.pokedex.models.PokemonTypeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun listPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonResponse>

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonDetail(
        @Path("nameOrId") nameOrId: String
    ): Response<PokemonDetail>

    @GET("type/{type}")
    suspend fun getPokemonTypeDetail(
        @Path("type") type: String
    ): Response<PokemonTypeDetail>

    @GET("ability/{ability}")
    suspend fun getPokemonAbilityDetail(
        @Path("ability") ability: String
    ): Response<PokemonAbilityDetail>

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonStatsDetail(
        @Path("nameOrId") nameOrId: String
    ): Response<PokemonStatsDetail>

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonMovesDetail(
        @Path("nameOrId") nameOrId: String
    ): Response<PokemonMovesDetail>

    @GET("pokemon/{nameOrId}")
    suspend fun getPokemonHeightAndWeight(
        @Path("nameOrId") nameOrId: String
    ): Response<PokemonDetail>
}