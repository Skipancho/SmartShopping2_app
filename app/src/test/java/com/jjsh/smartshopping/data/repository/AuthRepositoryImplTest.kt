package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.auth.FakeAuth
import com.jjsh.smartshopping.data.fake.datasource.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class AuthRepositoryImplTest {

    private var _fakeRemoteDataSource: FakeRemoteDataSource? = null
    private val fakeRemoteDataSource get() = _fakeRemoteDataSource ?: error("fakeRemoteDataSource is null")

    private var _fakeAuth: FakeAuth? = null
    private val fakeAuth get() = _fakeAuth ?: error("fakeAuth is null")

    private var _authRepositoryImpl : AuthRepositoryImpl? = null
    private val authRepositoryImpl get() = _authRepositoryImpl ?: error("authRepositoryImpl is null")

    @Before
    fun setUp() {
        _fakeRemoteDataSource = FakeRemoteDataSource()
        _fakeAuth = FakeAuth()

        _authRepositoryImpl = AuthRepositoryImpl(
            fakeRemoteDataSource,
            fakeAuth
        )
    }

    @After
    fun tearDown() {
        _fakeRemoteDataSource = null
        _fakeAuth = null
        _authRepositoryImpl = null
    }

    @Test
    fun signin() {
        runTest {
            val expected = Result.success(Unit)
            val actual = authRepositoryImpl.signin("testID", "testPW")

            assertEquals(expected, actual)
            assertEquals("testID",fakeAuth.userId)
        }
    }

    @Test
    fun signup() {
        runTest {
            val expected = Result.success(Unit)
            val actual = authRepositoryImpl.signup(
                "testID",
                "testPW",
                "testNickName",
                "testName"
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun validateUserId() {
        runTest {
            val expected = Result.success(Unit)
            val actual = authRepositoryImpl.validateUserId("testID")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun validateNickName() {
        runTest {
            val expected = Result.success(Unit)
            val actual = authRepositoryImpl.validateNickName("testNickName")
            assertEquals(expected, actual)
        }
    }
}