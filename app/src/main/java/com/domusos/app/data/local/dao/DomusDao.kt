package com.domusos.app.data.local.dao

import androidx.room.*
import com.domusos.app.data.local.entity.*
import kotlinx.flow.Flow

@Dao
interface DomusDao {
    @Query("SELECT * FROM storage_locations")
    fun getAllLocations(): Flow<List<StorageLocationEntity>>

    @Query("SELECT * FROM inventory_items ORDER BY expiryDate ASC")
    fun getAllInventoryItems(): Flow<List<InventoryItemEntity>>

    @Query("SELECT * FROM gdo_prices WHERE productEan IN (:eans)")
    fun getPricesForProducts(eans: List<String>): Flow<List<GdoPriceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: StorageLocationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryItem(item: InventoryItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppliance(appliance: ApplianceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTherapy(therapy: TherapyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrice(price: GdoPriceEntity)

    @Update
    suspend fun updateLocation(location: StorageLocationEntity)
}