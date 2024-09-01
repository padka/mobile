package com.example.pokedex.models

data class Pokemon(
    val name: String,
    val url: String
)

data class PokemonType(val type: TypeInfo)
data class TypeInfo(val name: String)
data class PokemonAbility(val ability: AbilityInfo)
data class AbilityInfo(val name: String)
data class PokemonStat(val stat: StatInfo, val base_stat: Int)
data class StatInfo(val name: String)
data class PokemonMove(val move: MoveInfo)
data class MoveInfo(val name: String)
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>,
    val stats: List<PokemonStat>,
    val moves: List<PokemonMove>,
    val sprites: PokemonSprites
)

data class PokemonSprites(
    val front_default: String,
    val back_default: String,
    val front_shiny: String,
    val back_shiny: String
)

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

data class PokemonTypeDetail(
    val id: Int,
    val name: String,
)

data class PokemonAbilityDetail(
    val id: Int,
    val name: String,
)

data class PokemonStatsDetail(
    val id: Int,
    val name: String,
)

data class PokemonMovesDetail(
    val id: Int,
    val name: String,
)
