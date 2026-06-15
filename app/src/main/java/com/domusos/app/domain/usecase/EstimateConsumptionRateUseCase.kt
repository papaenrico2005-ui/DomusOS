package com.domusos.app.domain.usecase

import java.time.Instant
import java.time.temporal.ChronoUnit

class EstimateConsumptionRateUseCase {
    fun execute(productEan: String, history: List<Pair<String, Double>>, timestamps: List<Instant>, windowDays: Long = 30): Double {
        val cutoff = Instant.now().minus(windowDays, ChronoUnit.DAYS)
        var total = 0.0
        for (i in history.indices) {
            if (history[i].first == productEan && timestamps[i].isAfter(cutoff)) {
                total += history[i].second
            }
        }
        return total / windowDays.toDouble()
    }
}