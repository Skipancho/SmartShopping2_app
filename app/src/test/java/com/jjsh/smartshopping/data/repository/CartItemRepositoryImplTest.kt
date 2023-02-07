package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.auth.FakeAuth
import com.jjsh.smartshopping.data.fake.datasource.FakeLocalDataSource
import com.jjsh.smartshopping.domain.model.CartItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class CartItemRepositoryImplTest {

    private var _fakeLocalDataSource: FakeLocalDataSource? = null
    private val fakeLocalDataSource
        get() = _fakeLocalDataSource ?: error("fakeLocalDataSource is null")

    private var _fakeAuth: FakeAuth? = null
    private val fakeAuth
        get() = _fakeAuth ?: error("fakeAuth is null")

    private var _cartItemRepositoryImpl: CartItemRepositoryImpl? = null
    private val cartItemRepositoryImpl
        get() = _cartItemRepositoryImpl ?: error("cartItemRepositoryImpl is null")

    @Before
    fun setUp() {
        _fakeLocalDataSource = FakeLocalDataSource()
        _fakeAuth = FakeAuth()

        _cartItemRepositoryImpl = CartItemRepositoryImpl(
            fakeLocalDataSource,
            fakeAuth
        )
    }

    @After
    fun tearDown() {
        _fakeLocalDataSource = null
        _fakeAuth = null

        _cartItemRepositoryImpl = null
    }

    @Test
    fun getCartItems() {
        runTest {
            val expected = Result.success(
                listOf(
                    CartItem(
                        1, 1, 1, "fakeCartItem1", 1111, 1111, 1, "", false
                    )
                )
            )
            var actual: Result<List<CartItem>>? = null
            cartItemRepositoryImpl.getCartItems().collect { actual = it }

            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertCartItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = cartItemRepositoryImpl.insertCartItem(
                CartItem(
                    2,
                    2,
                    2,
                    "fakeCartItem2",
                    2222,
                    2222,
                    2,
                    "",
                    false
                )
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun updateCartItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = cartItemRepositoryImpl.updateCartItem(
                CartItem(
                    2,
                    2,
                    2,
                    "fakeCartItem2",
                    2222,
                    2222,
                    2,
                    "",
                    false
                )
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteCartItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = cartItemRepositoryImpl.deleteCartItem(
                CartItem(
                    2,
                    2,
                    2,
                    "fakeCartItem2",
                    2222,
                    2222,
                    2,
                    "",
                    false
                )
            )
            assertEquals(expected, actual)
        }
    }
}