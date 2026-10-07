package com.example.pokedexkanto.util

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"

    // 151 = jumlah Pokémon asli region Kanto (#001 Bulbasaur sampai #151 Mew)
    const val POKEMON_LIMIT = 151

    const val ARTWORK_URL =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    // Nilai base stat tertinggi di semua Pokémon, dipakai sebagai batas progress bar
    const val MAX_BASE_STAT = 255
}
