package com.domusos.app.presentation

import com.domusos.app.data.local.entity.StorageLocationEntity

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(val locations: List<StorageLocationEntity>) : DashboardUiState
    data class Error(val reason: String) : DashboardUiState
}

sealed interface DashboardIntent {
    object LoadData : DashboardIntent
}