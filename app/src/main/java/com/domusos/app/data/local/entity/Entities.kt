package com.domusos.app.data.local.entity

import androidx.room.*
import java.time.Instant

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val caloricRequirementManual: Int? = null,
    val isCaloricManualOverride: Boolean = false
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val ean: String,
    val name: String,
    val brand: String,
    val nutriScore: String?,
    val ecoScore: String?,
    val volumeLiters: Double,
    val category: String,
    val isUserCreated: Boolean = false
)

@Entity(
    tableName = "storage_locations",
    foreignKeys = [ForeignKey(
        entity = StorageLocationEntity::class,
        parentColumns = ["id"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class StorageLocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val parentId: Long? = null,
    val name: String,
    val zoneType: String,
    val maxVolumeLiters: Double,
    val currentVolumeLiters: Double,
    val isVolumeManualOverride: Boolean = false,
    val isMedicineCabinet: Boolean = false
)

@Entity(
    tableName = "inventory_items",
    foreignKeys = [
        ForeignKey(entity = ProductEntity::class, parentColumns = ["ean"], childColumns = ["productEan"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = StorageLocationEntity::class, parentColumns = ["id"], childColumns = ["locationId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productEan: String,
    val locationId: Long,
    val quantity: Double,
    val expiryDate: Instant,
    val purchaseDate: Instant,
    val isManualOverride: Boolean = false
)

@Entity(tableName = "appliances")
data class ApplianceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String,
    val detergentStockGrams: Double,
    val rinseAidStockMl: Double,
    val saltStockGrams: Double,
    val gramsPerCycle: Double,
    val mlPerCycle: Double,
    val cyclesSinceMaintenance: Int,
    val maintenanceThresholdCycles: Int
)

@Entity(
    tableName = "therapies",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = ProductEntity::class, parentColumns = ["ean"], childColumns = ["productEan"], onDelete = ForeignKey.CASCADE)
    ]
)
data class TherapyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val productEan: String,
    val nextDoseTime: Instant,
    val intervalHours: Long,
    val isLifesaving: Boolean = false
)

@Entity(primaryKeys = ["productEan", "supermarketName"], tableName = "gdo_prices")
data class GdoPriceEntity(
    val productEan: String,
    val supermarketName: String,
    val price: Double,
    val deliveryCost: Double,
    val freeShippingThreshold: Double,
    val lastUpdated: Instant
)

@Entity(tableName = "consumption_history")
data class ConsumptionHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productEan: String,
    val quantityConsumed: Double,
    val timestamp: Instant
)
