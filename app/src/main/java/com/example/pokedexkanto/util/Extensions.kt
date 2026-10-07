package com.example.pokedexkanto.util

import com.example.pokedexkanto.data.model.Pokemon
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

private val localeIndonesia: Locale = Locale.forLanguageTag("id-ID")

// "https://pokeapi.co/api/v2/pokemon/25/" -> 25
fun String.extractPokemonId(): Int =
    trimEnd('/').substringAfterLast('/').toIntOrNull() ?: 0

// 25 -> URL gambar official artwork Pikachu
fun Int.toArtworkUrl(): String = "${Constants.ARTWORK_URL}$this.png"

// 25 -> "#025"
fun Int.toPokedexNumber(): String = "#" + toString().padStart(3, '0')

// "mr-mime" -> "Mr Mime"
fun String.toDisplayName(): String =
    split("-").joinToString(" ") { word ->
        word.replaceFirstChar { it.titlecase(localeIndonesia) }
    }

// Tinggi dari API dalam desimeter: 7 -> "0,7 m"
fun Int.decimetersToMeters(): String =
    String.format(localeIndonesia, "%.1f m", this / 10.0)

// Berat dari API dalam hektogram: 69 -> "6,9 kg"
fun Int.hectogramsToKilograms(): String =
    String.format(localeIndonesia, "%.1f kg", this / 10.0)

// Nama stat dari API dibuat lebih singkat untuk label
fun String.toStatLabel(): String = when (this) {
    "hp" -> "HP"
    "attack" -> "Attack"
    "defense" -> "Defense"
    "special-attack" -> "Sp. Atk"
    "special-defense" -> "Sp. Def"
    "speed" -> "Speed"
    else -> toDisplayName()
}

// Search: cocokkan nama, atau nomor jika yang diketik angka
fun List<Pokemon>.filterByQuery(query: String): List<Pokemon> {
    val keyword = query.trim()
    if (keyword.isEmpty()) return this

    val number = keyword.removePrefix("#").toIntOrNull()
    return filter { pokemon ->
        pokemon.name.contains(keyword, ignoreCase = true) || pokemon.id == number
    }
}

// Ubah exception jadi pesan yang mudah dipahami pengguna
fun Throwable.toFriendlyMessage(): String = when (this) {
    is IOException -> "Tidak ada koneksi internet. Periksa jaringanmu lalu coba lagi."
    is HttpException -> "Server PokéAPI sedang bermasalah (kode ${code()})."
    else -> "Terjadi kesalahan: ${localizedMessage ?: "tidak diketahui"}"
}
