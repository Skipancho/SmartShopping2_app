package com.jjsh.smartshopping.data.fake.dao

import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.data.local.dao.CartItemDao
import com.jjsh.smartshopping.data.local.dto.CartItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCartItemDao(
    private val fakeCartItemDtoList: MutableList<CartItemDto>
) : CartItemDao {
    override suspend fun insertItem(vararg item: CartItemDto) {
        fakeCartItemDtoList.addAll(item)
    }

    override suspend fun deleteItem(vararg item: CartItemDto) {
        fakeCartItemDtoList.removeAll(item.toList())
    }

    override suspend fun getItem(userCode: Long, productId: Long): CartItemDto {
        return fakeCartItemDtoList.find {
            it.userCode == userCode && it.productId == productId
        } ?: throw ErrorException.DefaultException("해당하는 아이템이 없습니다.")
    }

    override fun getItems(userCode: Long): Flow<List<CartItemDto>> {
        return flow {
            emit(fakeCartItemDtoList.filter { it.userCode == userCode })
        }
    }
}