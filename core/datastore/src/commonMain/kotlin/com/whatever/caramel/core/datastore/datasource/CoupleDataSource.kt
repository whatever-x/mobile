package com.whatever.caramel.core.datastore.datasource

interface CoupleDataSource {
    suspend fun fetchCoupleId(): Long

    suspend fun saveCoupleId(coupleId: Long)

    suspend fun deleteCoupleId()
}
