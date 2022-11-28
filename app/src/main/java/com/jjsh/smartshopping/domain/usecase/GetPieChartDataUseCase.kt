package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.domain.model.PieChartData
import com.jjsh.smartshopping.domain.repository.PurchaseRepository
import javax.inject.Inject

class GetPieChartDataUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {
    suspend operator fun invoke(year: Int, month: Int): Result<List<PieChartData>> {
        return runCatching {
            val hashMap = hashMapOf<String, Int>()
            val list = purchaseRepository.getPurchaseRecord(year, month).getOrThrow()

            list.forEach {
                hashMap[it.category] = (hashMap[it.category] ?: 0) + it.price
            }

            val totalPrice = hashMap.values.sum()

            listOf(PieChartData.Head) + hashMap.map {
                val category = it.key
                val price = it.value
                val percentage = ((it.value / totalPrice.toFloat()) * 10000).toInt() / 100.0f
                PieChartData.Body(category, price, percentage)
            }.sortedByDescending { it.percentage }
        }
    }
}