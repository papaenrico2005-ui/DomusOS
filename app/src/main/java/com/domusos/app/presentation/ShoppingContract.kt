package com.domusos.app.presentation

import com.domusos.app.domain.model.OptimizedOrder

sealed interface ShoppingUiState {
    object Idle : ShoppingUiState
    object Loading : ShoppingUiState
    data class Success(val recommendations: List<OptimizedOrder>) : ShoppingUiState
}