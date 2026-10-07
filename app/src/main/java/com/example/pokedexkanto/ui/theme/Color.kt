package com.example.pokedexkanto.ui.theme

import androidx.compose.ui.graphics.Color

// Warna utama ala Pokédex
val PokeRed = Color(0xFFE3350D)
val PokeRedLight = Color(0xFFFF6B4A)
val PokeBlue = Color(0xFF30A7D7)
val PokeYellow = Color(0xFFFFCB05)

// Tema terang
val LightBackground = Color(0xFFFFF8F6)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF5E9E6)
val LightOnSurface = Color(0xFF231917)
val LightOnSurfaceVariant = Color(0xFF6B5A56)

// Tema gelap
val DarkBackground = Color(0xFF121316)
val DarkSurface = Color(0xFF1B1C20)
val DarkSurfaceVariant = Color(0xFF2A2B31)
val DarkOnSurface = Color(0xFFECE6E4)
val DarkOnSurfaceVariant = Color(0xFFB8AEAB)

// Warna untuk setiap tipe Pokémon
val TypeColors = mapOf(
    "normal" to Color(0xFFA8A77A),
    "fire" to Color(0xFFEE8130),
    "water" to Color(0xFF6390F0),
    "electric" to Color(0xFFF7D02C),
    "grass" to Color(0xFF7AC74C),
    "ice" to Color(0xFF96D9D6),
    "fighting" to Color(0xFFC22E28),
    "poison" to Color(0xFFA33EA1),
    "ground" to Color(0xFFE2BF65),
    "flying" to Color(0xFFA98FF3),
    "psychic" to Color(0xFFF95587),
    "bug" to Color(0xFFA6B91A),
    "rock" to Color(0xFFB6A136),
    "ghost" to Color(0xFF735797),
    "dragon" to Color(0xFF6F35FC),
    "dark" to Color(0xFF705746),
    "steel" to Color(0xFFB7B7CE),
    "fairy" to Color(0xFFD685AD)
)

// "fire" -> oranye. Tipe yang tidak dikenal memakai warna normal.
fun String.toTypeColor(): Color = TypeColors[this] ?: Color(0xFFA8A77A)
