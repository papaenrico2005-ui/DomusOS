package com.domusos.app.domain.repository

import com.domusos.app.domain.model.*
import kotlinx.flow.Flow

interface StorageRepository {
    fun getAllLocations(): Flow<List<StorageLocation>>
}
interface GdoRepository {
    fun getPricesForProducts(eans: List<String>): Flow<List<GdoPrice>>
}