package com.example.pokedexkanto.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.pokedexkanto.util.Constants

@Composable
fun StatBar(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    // Mulai dari 0, lalu naik ke nilai sebenarnya saat pertama tampil
    var startAnimation by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (startAnimation) value / Constants.MAX_BASE_STAT.toFloat() else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "statProgress"
    )
    LaunchedEffect(Unit) { startAnimation = true }

    val barColor = when {
        value >= 100 -> Color(0xFF4CAF50)
        value >= 60 -> Color(0xFFFFB300)
        else -> Color(0xFFF4511E)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(72.dp)
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.width(40.dp)
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp),
            color = barColor,
            trackColor = barColor.copy(alpha = 0.18f),
            strokeCap = StrokeCap.Round
        )
    }
}
