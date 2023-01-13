package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.data.remote.api.AuthService
import com.jjsh.smartshopping.data.remote.api.ProductService
import com.jjsh.smartshopping.data.remote.api.PurchaseService
import com.jjsh.smartshopping.data.fake.service.FakeAuthService
import com.jjsh.smartshopping.data.fake.service.FakeProductService
import com.jjsh.smartshopping.data.fake.service.FakePurchaseService
import com.jjsh.smartshopping.data.fake.service.FakeReviewService
import com.jjsh.smartshopping.data.remote.api.ReviewService
import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import com.jjsh.smartshopping.data.remote.response.ReviewResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

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
            1L, "category1", 1L, "name1", 10000, 1, false
        ),
        PurchaseResponse(
            2L, "category2", 2L, "name2", 9000, 10,false
        )
    )

    private val testDate = Date()

    private val testReviewResponses = listOf<ReviewResponse>(
        ReviewResponse(
            1L,1L,"nickname1",1L,1,"reviewText1", testDate
        ),
        ReviewResponse(
            2L,2L,"nickname2",2L,2,"reviewText2", testDate
        )
    )

    private var _fakeAuthService: AuthService? = null
    private val fakeAuthService get() = _fakeAuthService ?: error("AuthService is null")

    private var _fakeProductService: ProductService? = null
    private val fakeProductService get() = _fakeProductService ?: error("ProductService is null")

    private var _fakePurchaseService: PurchaseService? = null
    private val fakePurchaseService get() = _fakePurchaseService ?: error("PurchaseService is null")

    private var _fakeReviewService: ReviewService? = null
    private val fakeReviewService get() = _fakeReviewService ?: error("ReviewService is null")

    private var _testDispatcher: CoroutineDispatcher? = null
    private val testDispatcher get() = _testDispatcher ?: error("Dispatcher is null")

    private var _remoteDataSource: RemoteDataSourceImpl? = null
    private val remoteDataSource get() = _remoteDataSource ?: error("remoteDataSource is null")

    @Before
    fun setUp() {
        _fakeAuthService = FakeAuthService(testSigninResponse)
        _fakeProductService = FakeProductService(testProductResponses)
        _fakePurchaseService = FakePurchaseService(testPurchaseResponses)
        _fakeReviewService = FakeReviewService(testReviewResponses)

        _testDispatcher = UnconfinedTestDispatcher()

        _remoteDataSource = RemoteDataSourceImpl(
            fakeAuthService,
            fakeProductService,
            fakePurchaseService,
            fakeReviewService,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        _fakeAuthService = null
        _fakeProductService = null
        _fakePurchaseService = null
        _fakeReviewService = null

        _testDispatcher = null

        _remoteDataSource = null
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
            val actual = remoteDataSource.getProduct(1L)
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
            val actual = remoteDataSource.getPurchaseRecord(2022, 11)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getReview() {
        runTest {
            val expected = Result.success(
                ReviewResponse(
                    1L,1L,"nickname1",1L,1,"reviewText1", testDate
                )
            )
            val actual = remoteDataSource.getReview(1L)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getReviews() {
        runTest {
            val expected = Result.success(testReviewResponses)
            val actual = remoteDataSource.getReviews(0)
            assertEquals(expected, actual)
        }
    }
}