package com.whatever.caramel.feat.sample.presentation

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.CaramelException
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.sample.domain.SampleModel
import com.whatever.caramel.feat.sample.domain.SampleRepository
import com.whatever.caramel.feat.sample.presentation.mvi.SampleIntent
import com.whatever.caramel.feat.sample.presentation.mvi.SampleSideEffect
import com.whatever.caramel.feat.sample.presentation.mvi.SampleUiState
import io.github.aakira.napier.Napier

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

            is SampleIntent.GetLocal -> {
                getSampleDataInLocal()
            }

            is SampleIntent.SaveLocal -> {
                saveSampleDataInLocal(intent.sampleModel)
            }
        }
    }

    override fun handleClientException(throwable: CaramelException) {
        postSideEffect(SampleSideEffect.ShowError(throwable.message))
    }

    fun getSampleData() {
        launch {
            val sampleData = sampleRepository.getSampleData()

            reduce {
                copy(
                    networkResultData = sampleData,
                    networkResult = sampleData.toString()
                )
            }
        }
    }

    private fun saveSampleDataInLocal(data: SampleModel) {
        Napier.d { "data = $data" }
        launch {
            sampleRepository.saveSampleDataToLocal(data)
            sampleRepository.saveSampleNameToLocal(data.name)

            reduce {
                copy(
                    localResult = "success",
                    saveNameResult = "success"
                )
            }
        }
    }

    private fun getSampleDataInLocal() {
        launch {
            val sampleData = sampleRepository.getSampleDataFromLocal()
            val sampleName = sampleRepository.getSampleNameFromLocal()

            reduce {
                copy(
                    localResult = if (sampleData.isNotEmpty()) {
                        sampleData.toString()
                    } else {
                        "no save"
                    },
                    saveNameResult = sampleName
                )
            }
        }
    }

}