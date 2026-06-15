package com.domusos.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domusos.app.data.local.dao.DomusDao
import kotlinx.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(private val dao: DomusDao) : ViewModel() {
    private val _state = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val state: StateFlow<DashboardUiState> = _state.asStateFlow()

    fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadData -> observeLocations()
        }
    }

    private fun observeLocations() {
        viewModelScope.launch {
            dao.getAllLocations()
                .map { DashboardUiState.Success(it) as DashboardUiState }
                .catch { emit(DashboardUiState.Error(it.localizedMessage ?: "Errore Critico")) }
                .collect { _state.value = it }
        }
    }
}