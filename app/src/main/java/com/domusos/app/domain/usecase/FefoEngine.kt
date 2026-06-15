package com.domusos.app.domain.usecase

import com.domusos.app.domain.model.InventoryItem
import kotlin.math.min

class FefoEngine {
    fun computeDischarge(items: List<InventoryItem>, demand: Double): List<Pair<InventoryItem, Double>> {
        val sorted = items.sortedWith(compareBy<InventoryItem> { it.expiryDate }.thenBy { it.purchaseDate })
        val plan = mutableListOf<Pair<InventoryItem, Double>>()
        var remaining = demand
        for (item in sorted) {
            if (remaining <= 0.0) break
            if (item.quantity <= 0.0) continue
            val take = min(item.quantity, remaining)
            plan.add(Pair(item, take))
            remaining -= take
        }
        return plan
    }
}