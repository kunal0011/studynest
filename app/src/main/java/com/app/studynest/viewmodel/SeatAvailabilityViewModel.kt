package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.app.studynest.data.model.Seat
import com.app.studynest.data.repository.StudyNestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SeatAvailabilityUiState {
    object Loading : SeatAvailabilityUiState()
    data class Success(val seats: List<Seat>) : SeatAvailabilityUiState()
    data class Error(val message: String) : SeatAvailabilityUiState()
}

@HiltViewModel
class SeatAvailabilityViewModel @Inject constructor(
    private val repository: StudyNestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SeatAvailabilityUiState>(SeatAvailabilityUiState.Loading)
    val uiState: StateFlow<SeatAvailabilityUiState> = _uiState

    init {
        loadSeats()
    }

    fun loadSeats() {
        viewModelScope.launch {
            _uiState.value = SeatAvailabilityUiState.Loading
            // Hardcoded date/hall for now as per repository mock
            val result = repository.getSeats("2023-10-27", "Hall-1")
            if (result.isSuccess) {
                _uiState.value = SeatAvailabilityUiState.Success(result.getOrThrow())
            } else {
                _uiState.value = SeatAvailabilityUiState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
            }
        }
    }
}

