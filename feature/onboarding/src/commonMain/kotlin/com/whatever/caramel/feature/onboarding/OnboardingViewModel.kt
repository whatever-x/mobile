package com.whatever.caramel.feature.onboarding

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.repository.SampleRepository
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.onboarding.mvi.OnboardingIntent
import com.whatever.caramel.feature.onboarding.mvi.OnboardingSideEffect
import com.whatever.caramel.feature.onboarding.mvi.OnboardingState
import io.github.aakira.napier.Napier

class OnboardingViewModel(
    private val repository : SampleRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<OnboardingState, OnboardingSideEffect, OnboardingIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    override suspend fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.ClickNextButton -> {
                launch {
                    val remoteSample = repository.getSampleData()
                    Napier.d { remoteSample.toString()}

                    val localSample = repository.getSampleDataFromLocal()
                    val localName = repository.getSampleNameFromLocal()
                    if(localSample.isEmpty()){
                        repository.saveSampleDataToLocal(remoteSample)
                    } else {
                        Napier.d { localSample.toString() }
                    }

                    if(localName.isEmpty()) {
                        repository.saveSampleNameToLocal(remoteSample.name)
                    } else {
                        Napier.d { localName }
                    }
                }
            }
        }
    }

}