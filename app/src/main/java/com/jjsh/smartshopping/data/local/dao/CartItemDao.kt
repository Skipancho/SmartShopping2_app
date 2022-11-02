package com.jjsh.smartshopping.data.local.dao

import androidx.room.*
import com.jjsh.smartshopping.data.local.dto.CartItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(vararg item: CartItemDto)

    @Delete
    suspend fun deleteItem(vararg item: CartItemDto)

    @Query("SELECT * FROM `CART_ITEM` WHERE `userCode` = (:userCode) AND `id` = (:itemId)")
    suspend fun getItem(userCode: Long, itemId: Long): CartItemDto

    @Query("SELECT * FROM `CART_ITEM` WHERE `userCode`LIKE (:userCode)  ORDER BY `time` DESC")
    fun getItems(userCode: Long): Flow<List<CartItemDto>>
}