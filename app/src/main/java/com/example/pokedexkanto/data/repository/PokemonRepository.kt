package com.example.pokedexkanto.data.repository

import com.example.pokedexkanto.data.model.Pokemon
import com.example.pokedexkanto.data.model.PokemonDetail
import com.example.pokedexkanto.data.network.PokeApiService
import com.example.pokedexkanto.data.network.RetrofitClient
import com.example.pokedexkanto.util.Constants
import com.example.pokedexkanto.util.extractPokemonId
import com.example.pokedexkanto.util.toArtworkUrl

// Repository = satu-satunya pintu ViewModel untuk mengambil data
class PokemonRepository(
    private val apiService: PokeApiService = RetrofitClient.apiService
) {

    suspend fun getPokemonList(): List<Pokemon> {
        val response = apiService.getPokemonList(limit = Constants.POKEMON_LIMIT)

        // Lengkapi data daftar: id diambil dari url, gambar dibuat dari id
        return response.results.map { result ->
            val id = result.url.extractPokemonId()
            Pokemon(
                id = id,
                name = result.name,
                imageUrl = id.toArtworkUrl()
            )
        }
    }

    suspend fun getPokemonDetail(id: Int): PokemonDetail =
        apiService.getPokemonDetail(id.toString())
}
