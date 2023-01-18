package com.jjsh.smartshopping.data.remote.request

import com.jjsh.smartshopping.domain.model.ProductRegistrationInfo

data class ProductRegistrationRequest(
    val name : String,
    val description : String,
    val nPrice : Int?,
    val sPrice : Int,
    val categoryId : Int,
    val imageIds : List<Long>,
    val barcode : Long
)

fun ProductRegistrationInfo.toRequest(imageIds: List<Long>) =
    ProductRegistrationRequest(productName,description, nPrice, sPrice, categoryId, imageIds, barcode)