package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.datasource.FakeRemoteDataSource
import com.jjsh.smartshopping.domain.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ProductRepositoryImplTest {

    private var _fakeRemoteDataSource: FakeRemoteDataSource? = null
    private val fakeRemoteDataSource
        get() = _fakeRemoteDataSource ?: error("fakeRemoteDataSource is null")

    private var _productRepositoryImpl: ProductRepositoryImpl? = null
    private val productRepositoryImpl
        get() = _productRepositoryImpl ?: error("productRepositoryImpl is null")

    @Before
    fun setUp() {
        _fakeRemoteDataSource = FakeRemoteDataSource()

        _productRepositoryImpl = ProductRepositoryImpl(
            fakeRemoteDataSource
        )
    }

    @After
    fun tearDown() {
        _fakeRemoteDataSource = null

        _productRepositoryImpl = null
    }

    @Test
    fun getProducts() {
        runTest {
            val expected = Result.success(
                listOf(
                    Product(
                        1L,
                        "name0",
                        "desc0",
                        10000,
                        9000,
                        "SELLABLE",
                        1L,
                        1,
                        listOf("imageUrl0"),
                        "imageUrl0"
                    ),
                    Product(
                        2L,
                        "name1",
                        "desc1",
                        1000,
                        900,
                        "SELLABLE",
                        2L,
                        2,
                        listOf("imageUrl1"),
                        "imageUrl1"
                    )
                )
            )
            val actual = productRepositoryImpl.getProducts(0)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun getProductItem() {
        runTest {
            val expected = Result.success(
                Product(
                    1L,
                    "name0",
                    "desc0",
                    10000,
                    9000,
                    "SELLABLE",
                    1L,
                    1,
                    listOf("imageUrl0"),
                    "imageUrl0"
                )
            )
            val actual = productRepositoryImpl.getProductItem(1L)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun findProductByBarcode() {
        runTest {
            val expected = Result.success(
                Product(
                    1L,
                    "name0",
                    "desc0",
                    10000,
                    9000,
                    "SELLABLE",
                    1L,
                    1,
                    listOf("imageUrl0"),
                    "imageUrl0"
                )
            )
            val actual = productRepositoryImpl.findProductByBarcode(1L)

            assertEquals(expected, actual)
        }
    }
}