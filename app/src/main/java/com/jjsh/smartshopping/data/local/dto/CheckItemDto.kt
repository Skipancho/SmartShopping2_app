package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jjsh.smartshopping.domain.model.CheckItem
import java.util.*

@Entity(tableName = "CHECK_ITEM")
data class CheckItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val userCode: Long,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    val amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean,
    val time: Long
) {
    fun toCheckItem(): CheckItem = CheckItem(
        id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked
    )

    fun setId(id: Long) = CheckItemDto(
        id, productId, userCode, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, time
    )
}

fun CheckItem.toDto(userCode: Long): CheckItemDto = CheckItemDto(
    id, productId, userCode, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, Date().time
)
