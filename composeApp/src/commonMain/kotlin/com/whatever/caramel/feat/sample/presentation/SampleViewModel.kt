package com.whatever.caramel.feat.sample.presentation

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.sample.domain.SampleRepository
import com.whatever.caramel.feat.sample.presentation.mvi.SampleIntent
import com.whatever.caramel.feat.sample.presentation.mvi.SampleSideEffect
import com.whatever.caramel.feat.sample.presentation.mvi.SampleUiState

class SampleViewModel(
    private val sampleRepository: SampleRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<SampleUiState, SampleSideEffect, SampleIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): SampleUiState {
        return SampleUiState()
    }

    override suspend fun handleIntent(intent: SampleIntent) {
        when (intent) {
            is SampleIntent.ClickButton -> {
                postSideEffect(SampleSideEffect.ShowSnackBar)
            }
        }
    }

    fun getSampleData() {
        launch {
            val sampleData = sampleRepository.getSampleData()

            reduce {
                copy(
                    text = sampleData.toString()
                )
            }
        }
    }
}