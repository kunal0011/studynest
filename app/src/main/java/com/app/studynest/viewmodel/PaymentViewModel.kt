package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.app.studynest.data.model.Booking
import com.app.studynest.data.repository.StudyNestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

sealed class PaymentUiState {
    object Idle : PaymentUiState()
    object Processing : PaymentUiState()
    object Success : PaymentUiState()
    data class Error(val message: String) : PaymentUiState()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: StudyNestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val uiState: StateFlow<PaymentUiState> = _uiState

    fun processPayment(paymentMethod: String) {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Processing
            delay(2000) // Simulate network delay

            // Mock Booking Creation
            val booking = Booking(
                id = UUID.randomUUID().toString(),
                seatId = "B2", // Mocked seat
                userId = "1", // Mocked user
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 3600000,
                status = "CONFIRMED"
            )

            val result = repository.createBooking(booking)
            if (result.isSuccess) {
                _uiState.value = PaymentUiState.Success
            } else {
                _uiState.value = PaymentUiState.Error(result.exceptionOrNull()?.message ?: "Payment Failed")
            }
        }
    }
}

