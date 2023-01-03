package com.jjsh.smartshopping.domain.model

import java.util.*

data class Review(
    val id: Long,
    val purchaseId: Long,
    val nickName: String,
    val productName: String,
    val score: Int,
    val reviewText: String,
    val date: Date
)
