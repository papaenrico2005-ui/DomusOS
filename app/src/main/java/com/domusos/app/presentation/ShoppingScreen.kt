package com.domusos.app.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.domusos.app.domain.model.OptimizedOrder

@Composable
fun ShoppingScreen(state: ShoppingUiState) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (state is ShoppingUiState.Success) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(state.recommendations) { order ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(order.supermarketName, style = MaterialTheme.typography.titleLarge)
                            Text("Costo Effettivo Totale: ${order.totalEffectiveCost}€")
                        }
                    }
                }
            }
        }
    }
}