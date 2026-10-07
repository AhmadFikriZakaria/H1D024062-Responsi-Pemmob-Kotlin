package com.example.pokedexkanto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedexkanto.data.model.PokemonDetail
import com.example.pokedexkanto.ui.components.ErrorView
import com.example.pokedexkanto.ui.components.InfoBox
import com.example.pokedexkanto.ui.components.LoadingView
import com.example.pokedexkanto.ui.components.StatBar
import com.example.pokedexkanto.ui.components.TypeChip
import com.example.pokedexkanto.ui.theme.toTypeColor
import com.example.pokedexkanto.ui.viewmodel.PokemonDetailUiState
import com.example.pokedexkanto.ui.viewmodel.PokemonDetailViewModel
import com.example.pokedexkanto.util.decimetersToMeters
import com.example.pokedexkanto.util.hectogramsToKilograms
import com.example.pokedexkanto.util.toArtworkUrl
import com.example.pokedexkanto.util.toDisplayName
import com.example.pokedexkanto.util.toPokedexNumber
import com.example.pokedexkanto.util.toStatLabel

@Composable
fun DetailScreen(
    pokemonId: Int,
    onBackClick: () -> Unit,
    viewModel: PokemonDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Minta ViewModel mengambil detail setiap pokemonId berubah
    LaunchedEffect(pokemonId) {
        viewModel.loadDetail(pokemonId)
    }

    when (val state = uiState) {
        is PokemonDetailUiState.Loading -> DetailStateScaffold(onBackClick) {
            LoadingView(message = "Memuat detail Pokémon...")
        }

        is PokemonDetailUiState.Error -> DetailStateScaffold(onBackClick) {
            ErrorView(
                message = state.message,
                onRetry = { viewModel.loadDetail(pokemonId, forceReload = true) }
            )
        }

        is PokemonDetailUiState.Success -> DetailContent(
            detail = state.detail,
            onBackClick = onBackClick
        )
    }
}

// Kerangka sederhana dengan tombol kembali untuk state loading dan error
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailStateScaffold(
    onBackClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Pokémon") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}

@Composable
private fun DetailContent(
    detail: PokemonDetail,
    onBackClick: () -> Unit
) {
    // Tipe utama (slot 1) menentukan warna header
    val mainType = detail.types.minByOrNull { it.slot }?.type?.name ?: "normal"
    val typeColor = mainType.toTypeColor()

    // Null safety: pakai official artwork, kalau null pakai sprite biasa, kalau null juga buat dari id
    val imageUrl = detail.sprites.other?.officialArtwork?.frontDefault
        ?: detail.sprites.frontDefault
        ?: detail.id.toArtworkUrl()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // ===== Header berwarna sesuai tipe =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                .background(Brush.verticalGradient(listOf(typeColor, typeColor.copy(alpha = 0.55f))))
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = Color.White
                )
            }

            Text(
                text = detail.id.toPokedexNumber(),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = detail.name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 56.dp, bottom = 20.dp)
                    .size(220.dp)
            )
        }

        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // ===== Nama dan tipe =====
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = detail.name.toDisplayName(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    detail.types.sortedBy { it.slot }.forEach { typeSlot ->
                        TypeChip(typeName = typeSlot.type.name)
                    }
                }
            }

            // ===== Tinggi, berat, base experience =====
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoBox(
                    label = "Tinggi",
                    value = detail.height.decimetersToMeters(),
                    modifier = Modifier.weight(1f)
                )
                InfoBox(
                    label = "Berat",
                    value = detail.weight.hectogramsToKilograms(),
                    modifier = Modifier.weight(1f)
                )
                InfoBox(
                    label = "Base Exp",
                    value = detail.baseExperience?.toString() ?: "-",
                    modifier = Modifier.weight(1f)
                )
            }

            // ===== Kemampuan (nilai tambah) =====
            SectionTitle(text = "Kemampuan")
            Text(
                text = detail.abilities.joinToString(separator = " • ") { slot ->
                    val name = slot.ability.name.toDisplayName()
                    if (slot.isHidden) "$name (tersembunyi)" else name
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ===== Statistik =====
            SectionTitle(text = "Statistik Dasar")
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    detail.stats.forEach { statSlot ->
                        StatBar(
                            label = statSlot.stat.name.toStatLabel(),
                            value = statSlot.baseStat
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = detail.stats.sumOf { it.baseStat }.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = typeColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}
