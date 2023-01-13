package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.datasource.FakeRemoteDataSource
import com.jjsh.smartshopping.domain.model.Review
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
internal class ReviewRepositoryImplTest {

    private var _fakeRemoteDataSource: FakeRemoteDataSource? = null
    private val fakeRemoteDataSource
        get() = _fakeRemoteDataSource ?: error("fakeRemoteDataSource is null")

    private var _reviewRepositoryImpl: ReviewRepositoryImpl? = null
    private val reviewRepositoryImpl: ReviewRepositoryImpl
        get() = _reviewRepositoryImpl
            ?: error("reviewRepositoryImpl is null")

    private var _testDate : Date? = null
    private val testDate : Date get() = _testDate ?: error("testDate is null")

    @Before
    fun setUp() {
        _fakeRemoteDataSource = FakeRemoteDataSource()

        _testDate = Date()
        fakeRemoteDataSource.setTestDate(testDate)

        _reviewRepositoryImpl = ReviewRepositoryImpl(fakeRemoteDataSource)
    }

    @After
    fun tearDown() {
        _fakeRemoteDataSource = null
        _testDate = null
        _reviewRepositoryImpl = null
    }

    @Test
    fun getReview() {
        runTest {
            val expected = Result.success(
                Review(
                    1L,1L,"nickname1","name0",1,"reviewText1", testDate,"imageUrl0"
                )
            )
            val actual = reviewRepositoryImpl.getReview(1L)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun writeReview() {
        runTest {
            val expected = Result.success(Unit)
            val actual = reviewRepositoryImpl.writeReview(1L,1L,1,"text")

            assertEquals(expected, actual)
        }
    }

    @Test
    fun updateReview() {
        runTest {
            val expected = Result.success(Unit)
            val actual = reviewRepositoryImpl.updateReview(1L,1L,1,"text")

            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteReview() {
        runTest {
            val expected = Result.success(Unit)
            val actual = reviewRepositoryImpl.deleteReview(1L)

            assertEquals(expected, actual)
        }
    }

    @Test
    fun getReviews() {
        runTest {
            val expected = Result.success(
                listOf(
                    Review(
                        1L,1L,"nickname1","name0",1,"reviewText1", testDate,"imageUrl0"
                    ),
                    Review(
                        2L,2L,"nickname2","name1",2,"reviewText2", testDate,"imageUrl1"
                    )
                )
            )
            val actual = reviewRepositoryImpl.getReviews()

            assertEquals(expected, actual)
        }
    }
}