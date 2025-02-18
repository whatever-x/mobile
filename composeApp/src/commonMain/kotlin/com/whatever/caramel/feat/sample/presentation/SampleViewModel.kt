package com.whatever.caramel.feat.sample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whatever.caramel.core.domain.CaramelException
import com.whatever.caramel.core.domain.ErrorUiType
import com.whatever.caramel.feat.sample.domain.SampleRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SampleUiState(
    val text: String = ""
)

class SampleViewModel(
    private val sampleRepository: SampleRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SampleUiState> = MutableStateFlow(SampleUiState())
    val state: StateFlow<SampleUiState> = _state.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val exception = throwable as CaramelException
        handleClientException(exception = exception)
    }

    init {
        getSampleData()
    }

    private fun getSampleData() {
        viewModelScope.launch(context = coroutineExceptionHandler) {
            val sampleData = sampleRepository.getSampleData()

            _state.update { uiState ->
                uiState.copy(text = sampleData.toString())
            }
        }
    }

    private fun handleClientException(exception: CaramelException) {
        when (exception.errorUiType) {
            ErrorUiType.SNACK_BAR -> { }
            ErrorUiType.EMPTY_UI -> { }
        }
    }
}