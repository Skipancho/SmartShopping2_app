package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.auth.FakeAuth
import com.jjsh.smartshopping.data.fake.datasource.FakeLocalDataSource
import com.jjsh.smartshopping.domain.model.CheckItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class CheckItemRepositoryImplTest {

    private var _fakeLocalDataSource: FakeLocalDataSource? = null
    private val fakeLocalDataSource
        get() = _fakeLocalDataSource ?: error("fakeLocalDataSource is null")

    private var _fakeAuth: FakeAuth? = null
    private val fakeAuth get() = _fakeAuth ?: error("fakeAuth is null")

    private var _checkItemRepositoryImpl: CheckItemRepositoryImpl? = null
    private val checkItemRepositoryImpl
        get() = _checkItemRepositoryImpl ?: error("checkItemRepository is null")

    @Before
    fun setUp() {
        _fakeLocalDataSource = FakeLocalDataSource()
        _fakeAuth = FakeAuth()

        _checkItemRepositoryImpl = CheckItemRepositoryImpl(
            fakeLocalDataSource,
            fakeAuth
        )
    }

    @After
    fun tearDown() {
        _fakeLocalDataSource = null
        _fakeAuth = null

        _checkItemRepositoryImpl = null
    }

    @Test
    fun getCheckItems() {
        runTest {
            val expected = Result.success(
                listOf(
                    CheckItem(
                        1, 1, "fakeCheckItem1", 1111, 1111, 1, "", false
                    )
                )
            )
            var actual: Result<List<CheckItem>>? = null
            checkItemRepositoryImpl.getCheckItems().collect { actual = it }

            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertCheckItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = checkItemRepositoryImpl.insertCheckItem(
                CheckItem(
                    2, 2, "fakeCheckItem2", 2222, 2222, 2, "", false
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun updateCheckItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = checkItemRepositoryImpl.updateCheckItem(
                CheckItem(
                    2, 2, "fakeCheckItem2", 2222, 2222, 2, "", false
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteCheckItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = checkItemRepositoryImpl.deleteCheckItem(
                CheckItem(
                    2, 2, "fakeCheckItem2", 2222, 2222, 2, "", false
                )
            )

            assertEquals(expected, actual)
        }
    }
}