package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrderSummary(
    val planName: String,
    val planPrice: String,
    val seatLabel: String,
    val tax: String,
    val total: String
)

sealed class CheckoutUiState {
    object Loading : CheckoutUiState()
    data class Success(val orderSummary: OrderSummary) : CheckoutUiState()
    data class Error(val message: String) : CheckoutUiState()
}

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Loading)
    val uiState: StateFlow<CheckoutUiState> = _uiState

    init {
        loadOrderSummary()
    }

    private fun loadOrderSummary() {
        viewModelScope.launch {
            _uiState.value = CheckoutUiState.Loading
            // Mocking data as if fetched from a shared state or backend
            val summary = OrderSummary(
                planName = "Monthly Plan",
                planPrice = "₹1,999",
                seatLabel = "B2",
                tax = "₹360",
                total = "₹2,359"
            )
            _uiState.value = CheckoutUiState.Success(summary)
        }
    }
}

