package com.domusos.app.di

import android.content.Context
import androidx.room.Room
import com.domusos.app.data.local.database.AppDatabase
import com.domusos.app.domain.usecase.*

class AppContainer(private val context: Context) {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "domus_os_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    val fefoEngine by lazy { FefoEngine() }
    val purchaseOptimizer by lazy { PurchaseOptimizer() }
    val recalculateStorageVolumesUseCase by lazy { RecalculateStorageVolumesUseCase() }
    val estimateConsumptionRateUseCase by lazy { EstimateConsumptionRateUseCase() }
}