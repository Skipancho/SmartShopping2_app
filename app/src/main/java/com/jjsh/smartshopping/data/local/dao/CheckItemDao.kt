package com.jjsh.smartshopping.data.local.dao

import androidx.room.*
import com.jjsh.smartshopping.data.local.dto.CheckItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg itemDto: CheckItemDto)

    @Delete
    fun deleteItem(vararg itemDto: CheckItemDto)

    @Query("SELECT * FROM `CHECK_ITEM` WHERE `userCode` = (:userCode) AND `productId` = (:productId)")
    suspend fun getItem(userCode: Long, productId: Long): CheckItemDto

    @Query("SELECT * FROM `CHECK_ITEM` WHERE `userCode`LIKE (:userCode)  ORDER BY `id` ASC")
    fun getItems(userCode: Long): Flow<List<CheckItemDto>>
}