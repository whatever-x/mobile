package com.whatever.caramel.core.datastore.datasource

interface UserDataSource {
    suspend fun getOnBoardingCompletion() : Boolean
    suspend fun setOnBoardingCompletion(completed: Boolean)
    suspend fun getUserStatus() : String
    suspend fun setUserStatus(state: String)
}