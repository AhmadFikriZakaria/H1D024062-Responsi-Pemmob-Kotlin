package com.example.pokedexkanto.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedexkanto.data.model.Pokemon
import com.example.pokedexkanto.ui.components.EmptyView
import com.example.pokedexkanto.ui.components.ErrorView
import com.example.pokedexkanto.ui.components.LoadingView
import com.example.pokedexkanto.ui.components.PokemonCard
import com.example.pokedexkanto.ui.components.PokemonSearchBar
import com.example.pokedexkanto.ui.viewmodel.PokemonListUiState
import com.example.pokedexkanto.ui.viewmodel.PokemonListViewModel
import com.example.pokedexkanto.util.Constants

@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onPokemonClick: (Int) -> Unit,
    viewModel: PokemonListViewModel = viewModel()
) {
    // Setiap StateFlow berubah, HomeScreen di-recompose dengan nilai terbaru
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            HomeHeader(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )

            PokemonSearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            // UI berubah sesuai state dari ViewModel
            when (val state = uiState) {
                is PokemonListUiState.Loading -> LoadingView(message = "Memuat Pokémon...")

                is PokemonListUiState.Error -> ErrorView(
                    message = state.message,
                    onRetry = viewModel::loadPokemon
                )

                is PokemonListUiState.Success -> {
                    if (state.pokemonList.isEmpty()) {
                        EmptyView(query = searchQuery)
                    } else {
                        PokemonGrid(
                            pokemonList = state.pokemonList,
                            onPokemonClick = onPokemonClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "PokéDex Kanto",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${Constants.POKEMON_LIMIT} Pokémon asli dari region Kanto",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FilledTonalIconButton(onClick = onToggleTheme) {
            Icon(
                imageVector = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                contentDescription = if (isDarkTheme) "Ganti ke tema terang" else "Ganti ke tema gelap"
            )
        }
    }
}

@Composable
private fun PokemonGrid(
    pokemonList: List<Pokemon>,
    onPokemonClick: (Int) -> Unit
) {
    Text(
        text = "Menampilkan ${pokemonList.size} Pokémon",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // key = id supaya Compose tahu item mana yang sama saat daftar difilter
        items(items = pokemonList, key = { it.id }) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                onClick = { onPokemonClick(pokemon.id) }
            )
        }
    }
}
