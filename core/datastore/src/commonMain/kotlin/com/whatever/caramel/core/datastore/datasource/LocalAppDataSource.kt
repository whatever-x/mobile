package com.whatever.caramel.core.datastore.datasource

interface LocalAppDataSource {
    suspend fun saveReviewRequestDate(date: String)
    suspend fun fetchReviewRequestDate(): String
    suspend fun saveAppLaunchCount(count: Int)
    suspend fun fetchAppLaunchCount(): Int
}