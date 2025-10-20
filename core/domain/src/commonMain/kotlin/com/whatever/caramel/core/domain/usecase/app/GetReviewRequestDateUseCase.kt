package com.whatever.caramel.core.domain.usecase.app

import com.whatever.caramel.core.domain.repository.AppRepository

class GetReviewRequestDateUseCase(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke() = appRepository.getReviewRequestDate()
}