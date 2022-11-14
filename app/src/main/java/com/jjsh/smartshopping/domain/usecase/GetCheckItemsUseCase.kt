package com.jjsh.smartshopping.domain.usecase

import com.jjsh.smartshopping.di.DefaultDispatcher
import com.jjsh.smartshopping.domain.model.CheckItem
import com.jjsh.smartshopping.domain.repository.CartItemRepository
import com.jjsh.smartshopping.domain.repository.CheckItemRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCheckItemsUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    private val checkItemRepository: CheckItemRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Result<List<CheckItem>>> {
        return combine(
            cartItemRepository.getCartItems(),
            checkItemRepository.getCheckItems()
        ) { cartItemResult, checkItemResult ->
            Pair(cartItemResult.getOrDefault(listOf()), checkItemResult.getOrThrow())
        }.map { pair ->
            val cartItems = pair.first
            val checkItems = pair.second
            Result.success(
                if (cartItems.isEmpty()) {
                    checkItems
                } else {
                    checkItems.map { item ->
                        if (cartItems.find { cartItem -> cartItem.productId == item.productId } != null)
                            item.setInCart(true)
                        else item
                    }
                }
            )
        }.catch {
            emit(Result.failure(it))
        }.flowOn(defaultDispatcher)
    }
}