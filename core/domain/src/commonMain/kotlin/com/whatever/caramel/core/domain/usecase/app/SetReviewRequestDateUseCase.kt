package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository
import kotlinx.datetime.LocalDateTime

class SetReviewRequestDateUseCase(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(date: LocalDateTime) {
        repository.setReviewRequestDate(date)
    }
}