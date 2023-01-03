package com.jjsh.smartshopping.data.remote.response

import com.jjsh.smartshopping.domain.model.Review
import java.util.*

data class ReviewResponse(
    val id: Long,
    val purchaseId: Long,
    val nickName: String,
    val productName: String,
    val score: Int,
    val reviewText: String,
    val date: Date
){
    fun toReview() : Review = Review(
        id, purchaseId, nickName, productName, score, reviewText, date
    )
}
