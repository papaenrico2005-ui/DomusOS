package com.domusos.app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.domusos.app.data.local.converter.Converters
import com.domusos.app.data.local.dao.DomusDao
import com.domusos.app.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        StorageLocationEntity::class,
        InventoryItemEntity::class,
        ApplianceEntity::class,
        TherapyEntity::class,
        GdoPriceEntity::class,
        ConsumptionHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun domusDao(): DomusDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "domus_os_db"
                )
                .addCallback(AppDatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.domusDao())
                }
            }
        }

        suspend fun populateDatabase(dao: DomusDao) {
            val rootId = dao.insertLocation(StorageLocationEntity(name = "Cucina", zoneType = "DRY_PANTRY", maxVolumeLiters = 500.0, currentVolumeLiters = 0.0))
            dao.insertLocation(StorageLocationEntity(parentId = rootId, name = "Frigo", zoneType = "COLD_FRIDGE", maxVolumeLiters = 200.0, currentVolumeLiters = 0.0))
            
            dao.insertProduct(ProductEntity(ean = "8001234567890", name = "Latte Intero", brand = "Granarolo", nutriScore = "B", ecoScore = "B", volumeLiters = 1.0, category = "FRESCHI_LATTICINI"))
            dao.insertInventoryItem(InventoryItemEntity(productEan = "8001234567890", locationId = 2, quantity = 3.0, expiryDate = Instant.now().plus(7, ChronoUnit.DAYS), purchaseDate = Instant.now()))
        }
    }
}