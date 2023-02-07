package com.jjsh.smartshopping.domain.model

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val nPrice: Int,
    val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val categoryId: Int,
    val imagePaths: List<String>,
    val thumbnailPath: String
) {
    val sale get() = if (nPrice > 0) 100 * (nPrice - sPrice) / nPrice else 0

    companion object {
        val nullProduct = Product(-1, "상품 없음", "상품 없음", 0, 0, "err", -1L,-1, listOf(), "")
    }
}
