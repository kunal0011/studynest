package com.app.studynest.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.app.studynest.data.model.Plan
import com.app.studynest.data.repository.StudyNestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SelectPlanUiState {
    object Loading : SelectPlanUiState()
    data class Success(val plans: List<Plan>) : SelectPlanUiState()
    data class Error(val message: String) : SelectPlanUiState()
}

@HiltViewModel
class SelectPlanViewModel @Inject constructor(
    private val repository: StudyNestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SelectPlanUiState>(SelectPlanUiState.Loading)
    val uiState: StateFlow<SelectPlanUiState> = _uiState

    init {
        loadPlans()
    }

    fun loadPlans() {
        viewModelScope.launch {
            _uiState.value = SelectPlanUiState.Loading
            val result = repository.getPlans()
            if (result.isSuccess) {
                _uiState.value = SelectPlanUiState.Success(result.getOrThrow())
            } else {
                _uiState.value = SelectPlanUiState.Error(result.exceptionOrNull()?.message ?: "Unknown Error")
            }
        }
    }
}

