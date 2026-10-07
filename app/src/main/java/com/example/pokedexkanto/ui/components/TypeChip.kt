package com.example.pokedexkanto.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.example.pokedexkanto.ui.theme.toTypeColor
import com.example.pokedexkanto.util.toDisplayName

@Composable
fun TypeChip(
    typeName: String,
    modifier: Modifier = Modifier
) {
    val background = typeName.toTypeColor()
    // Warna terang (misal electric) pakai teks hitam supaya tetap terbaca
    val textColor = if (background.luminance() > 0.5f) Color.Black else Color.White

    Surface(
        shape = CircleShape,
        color = background,
        modifier = modifier
    ) {
        Text(
            text = typeName.toDisplayName(),
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}
