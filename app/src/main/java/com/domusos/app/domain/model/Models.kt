package com.domusos.app.domain.model

import java.time.Instant

enum class ZoneType { COLD_FRIDGE, COLD_FREEZER, DRY_PANTRY, DRY_SERVICE }
enum class ProductCategory { SURGELATI, FRESCHI_LATTICINI, FARMACI, ALTRO }

data class Product(val ean: String, val name: String, val volumeLiters: Double, val category: ProductCategory)
data class StorageLocation(val id: Long, val parentId: Long?, val name: String, val zoneType: ZoneType, val maxVolumeLiters: Double, val currentVolumeLiters: Double, val isVolumeManualOverride: Boolean)
data class InventoryItem(val id: Long, val productEan: String, val locationId: Long, val quantity: Double, val expiryDate: Instant, val purchaseDate: Instant)
data class GdoPrice(val productEan: String, val supermarketName: String, val price: Double, val deliveryCost: Double, val freeShippingThreshold: Double)
data class CartLine(val product: Product, val quantity: Int, val unitPrice: Double)
data class OptimizedOrder(val supermarketName: String, val items: List<CartLine>, val deliveryFee: Double) {
    val totalEffectiveCost: Double = items.sumOf { it.unitPrice * it.quantity } + deliveryFee
}