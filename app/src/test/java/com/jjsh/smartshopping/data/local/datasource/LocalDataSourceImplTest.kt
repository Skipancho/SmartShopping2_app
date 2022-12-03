package com.jjsh.smartshopping.data.local.datasource

import com.jjsh.smartshopping.data.fake.dao.FakeCartItemDao
import com.jjsh.smartshopping.data.fake.dao.FakeCheckItemDao
import com.jjsh.smartshopping.data.fake.dao.FakeSearchHistoryDao
import com.jjsh.smartshopping.data.local.dao.CartItemDao
import com.jjsh.smartshopping.data.local.dao.CheckItemDao
import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
import com.jjsh.smartshopping.data.local.dto.CartItemDto
import com.jjsh.smartshopping.data.local.dto.CheckItemDto
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class LocalDataSourceImplTest {

    private val testSearchHistoryDtoList = mutableListOf<SearchHistoryDto>(
        SearchHistoryDto("test1", 1L),
        SearchHistoryDto("test2", 2L),
        SearchHistoryDto("test3", 3L)
    )

    private val testCheckItemDtoList = mutableListOf<CheckItemDto>(
        CheckItemDto(1, 1, 1, "test1", 1111, 1111, 1, "", false),
        CheckItemDto(2, 2, 2, "test2", 2222, 2222, 2, "", false),
        CheckItemDto(3, 3, 3, "test3", 3333, 3333, 3, "", false)
    )

    private val testCartItemDtoList = mutableListOf<CartItemDto>(
        CartItemDto(1, 1, 1, 1, "test1", 1111, 1111, 1, "", false),
        CartItemDto(2, 2, 2, 2, "test2", 2222, 2222, 2, "", false),
        CartItemDto(3, 3, 3, 3, "test3", 3333, 3333, 3, "", false),
    )


    private var _fakeSearchHistoryDao: SearchHistoryDao? = null
    private val fakeSearchHistoryDao get() = _fakeSearchHistoryDao ?: error("fakeDao is null")

    private var _fakeCartItemDao: CartItemDao? = null
    private val fakeCartItemDao get() = _fakeCartItemDao ?: error("fakeDao is null")

    private var _fakeCheckItemDao: CheckItemDao? = null
    private val fakeCheckItemDao get() = _fakeCheckItemDao ?: error("fakeDao is null")

    private var _testDispatcher: CoroutineDispatcher? = null
    private val testDispatcher get() = _testDispatcher ?: error("Dispatcher is null")

    private var _localDataSource: LocalDataSourceImpl? = null
    private val localDataSource get() = _localDataSource ?: error("localDataSource is null")

    @Before
    fun setUp() {
        _fakeSearchHistoryDao = FakeSearchHistoryDao(testSearchHistoryDtoList)
        _fakeCartItemDao = FakeCartItemDao(testCartItemDtoList)
        _fakeCheckItemDao = FakeCheckItemDao(testCheckItemDtoList)

        _testDispatcher = UnconfinedTestDispatcher()

        _localDataSource = LocalDataSourceImpl(
            fakeSearchHistoryDao,
            fakeCartItemDao,
            fakeCheckItemDao,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        _fakeSearchHistoryDao = null
        _fakeCartItemDao = null
        _fakeCheckItemDao = null

        _testDispatcher = null

        _localDataSource = null
    }

    @Test
    fun getSearchHistory() {
        runTest {
            val expected = Result.success(testSearchHistoryDtoList)
            var actual: Result<List<SearchHistoryDto>>? = null
            localDataSource.getSearchHistory().collect { actual = it }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertSearchHistory() {
        runTest {
            val expected = Result.success(Unit)
            val actual = localDataSource.insertSearchHistory(
                SearchHistoryDto("test4", 4L)
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteSearchHistory() {
        runTest {
            val expected = Result.success(Unit)
            val actual = localDataSource.deleteSearchHistory(
                SearchHistoryDto("test4", 4L)
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getCartItems() {
        runTest {
            val expected = Result.success(testCartItemDtoList.filter { it.userCode == 1L })
            var actual: Result<List<CartItemDto>>? = null
            localDataSource.getCartItems(1L).collect { actual = it }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getCartItem() {
        runTest {
            val expected = Result.success(
                CartItemDto(1, 1, 1, 1, "test1", 1111, 1111, 1, "", false)
            )
            val actual = localDataSource.getCartItem(userCode = 1L, itemId = 1L)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertCartItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = localDataSource.insertCartItem(
                CartItemDto(4, 4, 4, 4, "test4", 4444, 4444, 4, "", false)
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteCartItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = localDataSource.deleteCartItem(
                CartItemDto(4, 4, 4, 4, "test4", 4444, 4444, 4, "", false)
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getCheckItems() {
        runTest {
            val expected = Result.success(testCheckItemDtoList.filter { it.userCode == 1L })
            var actual: Result<List<CheckItemDto>>? = null
            localDataSource.getCheckItems(1L).collect { actual = it }
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getCheckItem() {
        runTest {
            val expected = Result.success(
                CheckItemDto(1, 1, 1, "test1", 1111, 1111, 1, "", false)
            )
            val actual = localDataSource.getCheckItem(userCode = 1L, itemId = 1L)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun insertCheckItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = localDataSource.insertCheckItem(
                CheckItemDto(4, 4, 4, "test4", 4444, 4444, 4, "", false)
            )
            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteCheckItem() {
        runTest {
            val expected = Result.success(Unit)
            val actual = localDataSource.deleteCheckItem(
                CheckItemDto(4, 4, 4, "test4", 4444, 4444, 4, "", false)
            )
            assertEquals(expected, actual)
        }
    }
}