package com.example.pokedexkanto.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexkanto.data.model.PokemonDetail
import com.example.pokedexkanto.data.repository.PokemonRepository
import com.example.pokedexkanto.util.toFriendlyMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PokemonDetailUiState {
    data object Loading : PokemonDetailUiState
    data class Success(val detail: PokemonDetail) : PokemonDetailUiState
    data class Error(val message: String) : PokemonDetailUiState
}

class PokemonDetailViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    fun loadDetail(id: Int, forceReload: Boolean = false) {
        // Jangan panggil API lagi kalau data Pokémon ini sudah ada (misal saat layar diputar)
        val current = _uiState.value
        if (!forceReload && current is PokemonDetailUiState.Success && current.detail.id == id) return

        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading
            _uiState.value = try {
                PokemonDetailUiState.Success(repository.getPokemonDetail(id))
            } catch (e: Exception) {
                PokemonDetailUiState.Error(e.toFriendlyMessage())
            }
        }
    }
}
