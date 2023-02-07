package com.jjsh.smartshopping.data.fake.datasource

import com.jjsh.smartshopping.data.local.datasource.LocalDataSource
import com.jjsh.smartshopping.data.local.dto.CartItemDto
import com.jjsh.smartshopping.data.local.dto.CheckItemDto
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDataSource : LocalDataSource {

    private val searchHistoryDtoList = mutableListOf<SearchHistoryDto>(
        SearchHistoryDto("fakeSearchHistory1", 1L)
    )
    private val cartItemDtoList = mutableListOf<CartItemDto>(
        CartItemDto(
            1, 1, 1, 1, "fakeCartItem1", 1111, 1111, 1, "", false
        )
    )
    private val checkItemDtoList = mutableListOf<CheckItemDto>(
        CheckItemDto(
            1, 1, 1, "fakeCheckItem1", 1111, 1111, 1, "", false
        )
    )

    override fun getSearchHistory(): Flow<Result<List<SearchHistoryDto>>> {
        return flow {
            emit(Result.success(searchHistoryDtoList))
        }
    }

    override suspend fun insertSearchHistory(historyDto: SearchHistoryDto): Result<Unit> {
        return runCatching { searchHistoryDtoList.add(historyDto) }
    }

    override suspend fun deleteSearchHistory(vararg historyDto: SearchHistoryDto): Result<Unit> {
        return runCatching { searchHistoryDtoList.removeAll(historyDto.toSet()) }
    }

    override fun getCartItems(userCode: Long): Flow<Result<List<CartItemDto>>> {
        return flow {
            emit(Result.success(cartItemDtoList))
        }
    }

    override suspend fun getCartItem(userCode: Long, itemId: Long): Result<CartItemDto> {
        return runCatching { cartItemDtoList[0] }
    }

    override suspend fun insertCartItem(vararg cartItemDto: CartItemDto): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deleteCartItem(vararg cartItemDto: CartItemDto): Result<Unit> {
        return Result.success(Unit)
    }

    override fun getCheckItems(userCode: Long): Flow<Result<List<CheckItemDto>>> {
        return flow {
            emit(Result.success(checkItemDtoList))
        }
    }

    override suspend fun getCheckItem(userCode: Long, itemId: Long): Result<CheckItemDto> {
        return runCatching { checkItemDtoList[0] }
    }

    override suspend fun insertCheckItem(vararg checkItemDto: CheckItemDto): Result<Unit> {
        return runCatching { checkItemDtoList.addAll(checkItemDto) }
    }

    override suspend fun deleteCheckItem(vararg checkItemDto: CheckItemDto): Result<Unit> {
        return runCatching { checkItemDtoList.removeAll(checkItemDto.toSet()) }
    }
}
