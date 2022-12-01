package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.data.remote.api.AuthService
import com.jjsh.smartshopping.data.remote.api.ProductService
import com.jjsh.smartshopping.data.remote.api.PurchaseService
import com.jjsh.smartshopping.data.fake.service.FakeAuthService
import com.jjsh.smartshopping.data.fake.service.FakeProductService
import com.jjsh.smartshopping.data.fake.service.FakePurchaseService
import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class RemoteDataSourceImplTest {

    private val testSigninResponse = SigninResponse(
        token = "testToken",
        refreshToken = "testRefreshToken",
        nickName = "testNickName",
        userCode = 1L,
        userName = "testUserName"
    )

    private val testProductResponses = listOf<ProductResponse>(
        ProductResponse(
            1L, "name0", "desc0", 10000, 9000, "SELLABLE", 1L, 1, listOf("imageUrl0")
        ),
        ProductResponse(
            2L, "name1", "desc1", 1000, 900, "SELLABLE", 2L, 2, listOf("imageUrl1")
        )
    )

    private val testPurchaseResponses = listOf<PurchaseResponse>(
        PurchaseResponse(
            1L,"category1",1L,"name1",10000,1
        ),
        PurchaseResponse(
            2L,"category2",2L,"name2",9000,10
        )
    )

    private lateinit var fakeAuthService: AuthService
    private lateinit var fakeProductService: ProductService
    private lateinit var fakePurchaseService: PurchaseService
    private lateinit var testDispatcher: CoroutineDispatcher

    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setUp() {
        fakeAuthService = FakeAuthService(testSigninResponse)
        fakeProductService = FakeProductService(testProductResponses)
        fakePurchaseService = FakePurchaseService(testPurchaseResponses)

        testDispatcher = UnconfinedTestDispatcher()

        remoteDataSource = RemoteDataSourceImpl(
            fakeAuthService,
            fakeProductService,
            fakePurchaseService,
            testDispatcher
        )
    }

    @Test
    fun signin() {
        runTest {
            val expected = Result.success(
                testSigninResponse
            )
            val actual = remoteDataSource.signin(
                SigninRequest("id", "password")
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun signup() {
        runTest {
            val expected = Result.success(Unit)
            val actual = remoteDataSource.signup(
                SignupRequest("id", "password", "nickName", "name")
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun validateUserId() {
        runTest {
            val expected = Result.success(Unit)
            val actual = remoteDataSource.validateUserId("id")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun validateNickName() {
        runTest {
            val expected = Result.success(Unit)
            val actual = remoteDataSource.validateNickName("nickName")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getProduct() {
        runTest {
            val expected = Result.success(testProductResponses[0])
            val actual = remoteDataSource.getProduct(0L)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getProducts() {
        runTest {
            val expected = Result.success(testProductResponses)
            val actual = remoteDataSource.getProducts(0L, null, "next", null)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun registerPurchaseRecord() {
        runTest {
            val expected = Result.success(Unit)
            val actual = remoteDataSource.registerPurchaseRecord(
                listOf(
                    PurchaseRequest(
                        productId = 3L,
                        productName = "name4",
                        price = 5000,
                        amount = 1,
                        categoryId = 3,
                    )
                )
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getPurchaseRecord() {
        runTest {
            val expected = Result.success(testPurchaseResponses)
            val actual = remoteDataSource.getPurchaseRecord(2022,11)
            assertEquals(expected, actual)
        }
    }

    @After
    fun tearDown() {

    }
}