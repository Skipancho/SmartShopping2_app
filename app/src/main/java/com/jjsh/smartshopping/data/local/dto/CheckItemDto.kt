package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CHECK_ITEM")
data class CheckItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val productId: Long,
    val userCode: Long,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    val amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean,
    val time: Long
)
