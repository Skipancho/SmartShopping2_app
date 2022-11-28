package com.jjsh.smartshopping.domain.model

sealed class PieChartData {
    data class Body(
        val category: String,
        val price: Int,
        val percentage: Float
    ): PieChartData()

    object Head : PieChartData()
}