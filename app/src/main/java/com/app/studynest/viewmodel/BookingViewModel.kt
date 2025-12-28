package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.studynest.data.model.Booking
import com.app.studynest.data.model.Seat
import com.app.studynest.data.repository.StudyNestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class BookingUiState {
    object Idle : BookingUiState()
    object Loading : BookingUiState()
    object Success : BookingUiState()
    data class Error(val message: String) : BookingUiState()
}

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val repository: StudyNestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookingUiState>(BookingUiState.Idle)
    val uiState: StateFlow<BookingUiState> = _uiState

    private val _seats = MutableStateFlow<List<Seat>>(emptyList())
    val seats: StateFlow<List<Seat>> = _seats

    private val _selectedSeat = MutableStateFlow<Seat?>(null)
    val selectedSeat: StateFlow<Seat?> = _selectedSeat

    private val _selectedPlan = MutableStateFlow<String?>(null)
    val selectedPlan: StateFlow<String?> = _selectedPlan

    fun fetchSeats(date: String, hallId: String) {
        viewModelScope.launch {
            _uiState.value = BookingUiState.Loading
            val result = repository.getSeats(date, hallId)
            if (result.isSuccess) {
                _seats.value = result.getOrThrow()
                _uiState.value = BookingUiState.Idle
            } else {
                _uiState.value = BookingUiState.Error(result.exceptionOrNull()?.message ?: "Error fetching seats")
            }
        }
    }

    fun selectSeat(seat: Seat) {
        _selectedSeat.value = seat
    }

    fun selectPlan(planId: String) {
        _selectedPlan.value = planId
    }

    fun confirmBooking() {
        val seat = _selectedSeat.value ?: return
        // val plan = _selectedPlan.value ?: return

        viewModelScope.launch {
            _uiState.value = BookingUiState.Loading
            val booking = Booking(
                id = UUID.randomUUID().toString(),
                seatId = seat.id,
                userId = "current_user_id", // Should come from User session
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 3600000, // 1 hour mock
                status = "CONFIRMED"
            )
            val result = repository.createBooking(booking)
            if (result.isSuccess) {
                _uiState.value = BookingUiState.Success
            } else {
                _uiState.value = BookingUiState.Error(result.exceptionOrNull()?.message ?: "Booking failed")
            }
        }
    }
}
