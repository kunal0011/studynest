package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.studynest.data.model.Booking
import com.app.studynest.data.repository.StudyNestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class BookingHistoryUiState {
    object Loading : BookingHistoryUiState()
    data class Success(val bookings: List<Booking>) : BookingHistoryUiState()
    data class Error(val message: String) : BookingHistoryUiState()
}

@HiltViewModel
class BookingHistoryViewModel @Inject constructor(
    private val repository: StudyNestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookingHistoryUiState>(BookingHistoryUiState.Loading)
    val uiState: StateFlow<BookingHistoryUiState> = _uiState.asStateFlow()

    init {
        fetchBookings()
    }

    fun fetchBookings() {
        viewModelScope.launch {
            _uiState.value = BookingHistoryUiState.Loading
            try {
                // Trigger a sync to get latest data from API
                repository.syncBookings("1")
                
                // Collect from local database
                repository.bookings.collect { bookings ->
                    _uiState.value = BookingHistoryUiState.Success(bookings)
                }
            } catch (e: Exception) {
                _uiState.value = BookingHistoryUiState.Error(e.message ?: "Failed to fetch bookings")
            }
        }
    }
}
