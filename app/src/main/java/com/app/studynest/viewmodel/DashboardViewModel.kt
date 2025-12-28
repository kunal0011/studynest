package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.studynest.data.model.DashboardStats
import com.app.studynest.data.repository.StudyNestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val stats: DashboardStats) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: StudyNestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        fetchStats()
    }

    private fun fetchStats() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            val result = repository.getDashboardStats()
            if (result.isSuccess) {
                _uiState.value = DashboardUiState.Success(result.getOrThrow())
            } else {
                _uiState.value = DashboardUiState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
            }
        }
    }
}
