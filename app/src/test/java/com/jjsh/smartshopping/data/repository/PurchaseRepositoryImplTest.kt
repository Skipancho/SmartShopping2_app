package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.datasource.FakeRemoteDataSource
import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.model.Purchase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class PurchaseRepositoryImplTest {

    private var _fakeRemoteDataSource: FakeRemoteDataSource? = null
    private val fakeRemoteDataSource
        get() = _fakeRemoteDataSource ?: error("fakeRemoteDataSource is null")

    private var _purchaseRepositoryImpl: PurchaseRepositoryImpl? = null
    private val purchaseRepositoryImpl
        get() = _purchaseRepositoryImpl ?: error("purchaseRepositoryImpl is null")

    @Before
    fun setUp() {
        _fakeRemoteDataSource = FakeRemoteDataSource()

        _purchaseRepositoryImpl = PurchaseRepositoryImpl(
            fakeRemoteDataSource
        )
    }

    @After
    fun tearDown() {
        _fakeRemoteDataSource = null

        _purchaseRepositoryImpl = null
    }

    @Test
    fun registerPurchaseRecord() {
        runTest {
            val expected = Result.success(Unit)
            val actual = purchaseRepositoryImpl.registerPurchaseRecord(
                listOf(
                    CartItem(
                        3, 3, 3, "fakeCartItem3", 3333, 3333, 3, ""
                    )
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun getPurchaseRecord() {
        runTest {
            val expected = Result.success(
                listOf(
                    Purchase(
                        1L, "category1", 1L, "name1", 10000, 1, false
                    ),
                    Purchase(
                        2L, "category2", 2L, "name2", 9000, 10, false
                    )
                )
            )
            val actual = purchaseRepositoryImpl.getPurchaseRecord(2022, 12)

            assertEquals(expected, actual)
        }
    }
}