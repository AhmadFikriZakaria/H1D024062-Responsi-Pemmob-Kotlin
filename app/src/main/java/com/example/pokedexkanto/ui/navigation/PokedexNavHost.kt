package com.example.pokedexkanto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedexkanto.ui.screens.DetailScreen
import com.example.pokedexkanto.ui.screens.HomeScreen

object Routes {
    const val HOME = "home"
    const val ARG_POKEMON_ID = "pokemonId"
    const val DETAIL = "detail/{$ARG_POKEMON_ID}"

    fun detail(pokemonId: Int) = "detail/$pokemonId"
}

@Composable
fun PokedexNavHost(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                onPokemonClick = { id -> navController.navigate(Routes.detail(id)) }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument(Routes.ARG_POKEMON_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt(Routes.ARG_POKEMON_ID) ?: 1
            DetailScreen(
                pokemonId = pokemonId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
