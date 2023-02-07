package com.jjsh.smartshopping.data.remote.request

data class ReviewRequest(
    val productId : Long,
    val purchaseId : Long,
    val score : Int,
    val reviewText : String
)
