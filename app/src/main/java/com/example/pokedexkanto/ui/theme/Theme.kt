package com.example.pokedexkanto.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PokeRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD2),
    onPrimaryContainer = Color(0xFF3D0600),
    secondary = PokeBlue,
    onSecondary = Color.White,
    tertiary = PokeYellow,
    onTertiary = Color(0xFF231917),
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = Color(0xFFBA1A1A)
)

private val DarkColorScheme = darkColorScheme(
    primary = PokeRedLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF8C1D00),
    onPrimaryContainer = Color(0xFFFFDAD2),
    secondary = PokeBlue,
    onSecondary = Color.White,
    tertiary = PokeYellow,
    onTertiary = Color(0xFF231917),
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = Color(0xFFFFB4AB)
)

// Dynamic color sengaja tidak dipakai supaya warna khas Pokédex tetap sama di semua HP
@Composable
fun PokeDexKantoTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}
