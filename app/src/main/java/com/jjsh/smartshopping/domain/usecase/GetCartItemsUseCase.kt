package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.repository.CartItemRepository
import com.jjsh.smartshopping.domain.repository.CheckItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    private val checkItemRepository: CheckItemRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    operator fun invoke(): Flow<Result<List<CartItem>>> {
        return combine(
            cartItemRepository.getCartItems(),
            checkItemRepository.getCheckItems()
        ) { cartItemResult, checkItemResult ->
            Pair(cartItemResult.getOrThrow(), checkItemResult.getOrDefault(listOf()))
        }.map { pair ->
            val cartItems = pair.first
            val checkItems = pair.second
            Result.success(
                if (checkItems.isEmpty()) cartItems.map { it.setInCheckList(false) }
                else cartItems.map { item ->
                    if (cartItems.find { it.productId == item.productId } == null)
                        item.setInCheckList(false)
                    else item
                }
            )
        }.catch {
            emit(Result.failure(it))
        }.flowOn(defaultDispatcher)
    }
}