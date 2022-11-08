package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.data.remote.api.AuthService
import com.jjsh.smartshopping.data.remote.api.ProductService
import com.jjsh.smartshopping.data.remote.datasource.fakeService.FakeAuthService
import com.jjsh.smartshopping.data.remote.datasource.fakeService.FakeProductService
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
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
            0L, "name0", "desc0", 10000, 9000, "SELLABLE", 0L, listOf("imageUrl0")
        ),
        ProductResponse(
            1L, "name1", "desc1", 1000, 900, "SELLABLE", 1L, listOf("imageUrl1")
        )
    )

    private lateinit var fakeAuthService: AuthService
    private lateinit var fakeProductService: ProductService
    private lateinit var testDispatcher: CoroutineDispatcher

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        fakeAuthService = FakeAuthService(testSigninResponse)
        fakeProductService = FakeProductService(testProductResponses)
        testDispatcher = UnconfinedTestDispatcher()

        remoteDataSource = RemoteDataSourceImpl(
            fakeAuthService,
            fakeProductService,
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
                SignupRequest("id","password","nickName","name")
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
            val actual = remoteDataSource.getProducts(0L,null,"next",null)
            assertEquals(expected, actual)
        }
    }

    @After
    fun tearDown() {

    }
}