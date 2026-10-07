package com.example.pokedexkanto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexkanto.data.model.Pokemon
import com.example.pokedexkanto.data.repository.PokemonRepository
import com.example.pokedexkanto.util.filterByQuery
import com.example.pokedexkanto.util.toFriendlyMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Tiga kemungkinan kondisi layar Home
sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data class Success(val pokemonList: List<Pokemon>) : PokemonListUiState
    data class Error(val message: String) : PokemonListUiState
}

class PokemonListViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    // State mentah hasil API (belum difilter)
    private val _listState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)

    // Teks yang diketik di search bar
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // State yang dibaca UI: gabungan hasil API + kata kunci search.
    // Setiap salah satu berubah, combine menghitung ulang daftar yang tampil.
    val uiState: StateFlow<PokemonListUiState> =
        combine(_listState, _searchQuery) { state, query ->
            if (state is PokemonListUiState.Success) {
                state.copy(pokemonList = state.pokemonList.filterByQuery(query))
            } else {
                state
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokemonListUiState.Loading
        )

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _listState.value = PokemonListUiState.Loading
            _listState.value = try {
                PokemonListUiState.Success(repository.getPokemonList())
            } catch (e: Exception) {
                PokemonListUiState.Error(e.toFriendlyMessage())
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
