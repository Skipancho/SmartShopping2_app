package com.jjsh.smartshopping.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    @SerializedName("nprice") val nPrice: Int?,
    @SerializedName("sprice") val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val imagePaths: List<String>
)