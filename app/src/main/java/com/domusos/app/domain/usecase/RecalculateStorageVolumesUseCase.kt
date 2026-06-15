package com.domusos.app.domain.usecase

import com.domusos.app.domain.model.*

class RecalculateStorageVolumesUseCase {
    fun execute(locations: List<StorageLocation>, items: List<InventoryItem>, products: Map<String, Product>): List<Pair<Long, Double>> {
        val volumes = locations.associate { loc ->
            loc.id to items.filter { it.locationId == loc.id }.sumOf { (products[it.productEan]?.volumeLiters ?: 0.0) * it.quantity }
        }.toMutableMap()
        return volumes.toList()
    }
}