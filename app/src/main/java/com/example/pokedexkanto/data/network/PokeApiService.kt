package com.example.pokedexkanto.data.network

import com.example.pokedexkanto.data.model.PokemonDetail
import com.example.pokedexkanto.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    // GET https://pokeapi.co/api/v2/pokemon?limit=151&offset=0
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse

    // GET https://pokeapi.co/api/v2/pokemon/{id atau nama}
    @GET("pokemon/{idOrName}")
    suspend fun getPokemonDetail(
        @Path("idOrName") idOrName: String
    ): PokemonDetail
}
