package com.jjsh.smartshopping.data.fake.dao

import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.data.local.dao.CheckItemDao
import com.jjsh.smartshopping.data.local.dto.CheckItemDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCheckItemDao(
    private val fakeCheckItemDtoList : MutableList<CheckItemDto>
) : CheckItemDao {
    override fun insertItem(vararg itemDto: CheckItemDto) {
        fakeCheckItemDtoList.addAll(itemDto)
    }

    override fun deleteItem(vararg itemDto: CheckItemDto) {
        fakeCheckItemDtoList.removeAll(itemDto.toSet())
    }

    override suspend fun getItem(userCode: Long, productId: Long): CheckItemDto {
        return fakeCheckItemDtoList.find {
            it.userCode == userCode && it.productId == productId
        } ?: throw ErrorException.DefaultException("해당하는 아이템이 없습니다.")
    }

    override fun getItems(userCode: Long): Flow<List<CheckItemDto>> {
        return flow {
            emit(fakeCheckItemDtoList.filter { it.userCode == userCode })
        }
    }
}