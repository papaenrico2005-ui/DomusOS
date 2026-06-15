package com.domusos.app.domain.usecase

import com.domusos.app.domain.model.*

class PurchaseOptimizer {
    fun optimize(deficit: Map<Product, Int>, availablePrices: List<GdoPrice>, globalAvailableVolume: Double): List<OptimizedOrder> {
        val pricesByMarket = availablePrices.groupBy { it.supermarketName }
        val orders = mutableListOf<OptimizedOrder>()
        for ((market, marketPrices) in pricesByMarket) {
            val lines = mutableListOf<CartLine>()
            var currentVolume = 0.0
            for ((product, qty) in deficit) {
                val spec = marketPrices.find { it.productEan == product.ean }
                if (spec != null) {
                    val target = qty.coerceAtMost(((globalAvailableVolume - currentVolume) / product.volumeLiters).toInt())
                    if (target > 0) {
                        lines.add(CartLine(product, target, spec.price))
                        currentVolume += product.volumeLiters * target
                    }
                }
            }
            if (lines.isNotEmpty()) {
                val sample = marketPrices.first()
                val subtotal = lines.sumOf { it.unitPrice * it.quantity }
                val fee = if (subtotal >= sample.freeShippingThreshold) 0.0 else sample.deliveryCost
                orders.add(OptimizedOrder(market, lines, fee))
            }
        }
        return orders.sortedBy { it.totalEffectiveCost }
    }
}