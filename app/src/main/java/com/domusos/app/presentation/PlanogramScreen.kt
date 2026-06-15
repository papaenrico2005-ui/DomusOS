package com.domusos.app.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.domusos.app.data.local.entity.StorageLocationEntity

@Composable
fun PlanogramScreen(state: DashboardUiState, onLocationSelected: (Long) -> Unit) {
    when (state) {
        is DashboardUiState.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        is DashboardUiState.Error -> Text(text = state.reason, color = MaterialTheme.colorScheme.error)
        is DashboardUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.locations) { location ->
                    val ratio = if (location.maxVolumeLiters > 0) location.currentVolumeLiters / location.maxVolumeLiters else 0.0
                    val color by animateColorAsState(
                        targetValue = if (ratio > 0.9) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant,
                        label = "Color"
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth().height(140.dp).clickable { onLocationSelected(location.id) },
                        colors = CardDefaults.cardColors(containerColor = color)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.weight(1f))
                            LinearProgressIndicator(progress = { ratio.toFloat() }, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}