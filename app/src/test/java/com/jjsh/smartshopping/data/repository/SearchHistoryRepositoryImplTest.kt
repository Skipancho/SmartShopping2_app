package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.fake.datasource.FakeLocalDataSource
import com.jjsh.smartshopping.domain.model.SearchHistory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SearchHistoryRepositoryImplTest {

    private var _fakeLocalDataSource: FakeLocalDataSource? = null
    private val fakeLocalDataSource
        get() = _fakeLocalDataSource ?: error("fakeLocalDataSource is null")

    private var _searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl? = null
    private val searchHistoryRepositoryImpl
        get() = _searchHistoryRepositoryImpl ?: error("searchHistoryRepositoryImpl is null")

    @Before
    fun setUp() {
        _fakeLocalDataSource = FakeLocalDataSource()

        _searchHistoryRepositoryImpl = SearchHistoryRepositoryImpl(
            fakeLocalDataSource
        )
    }

    @After
    fun tearDown() {
        _fakeLocalDataSource = null

        _searchHistoryRepositoryImpl = null
    }

    @Test
    fun insertSearchHistory() {
        runTest {
            val expected = Result.success(Unit)
            val actual = searchHistoryRepositoryImpl.insertSearchHistory(
                SearchHistory("fakeSearchHistory2")
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun deleteSearchHistory() {
        runTest {
            val expected = Result.success(Unit)
            val actual = searchHistoryRepositoryImpl.deleteSearchHistory(
                SearchHistory("fakeSearchHistory2")
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun getSearchHistory() {
        runTest {
            val expected = Result.success(
                listOf(
                    SearchHistory("fakeSearchHistory1")
                )
            )
            var actual: Result<List<SearchHistory>>? = null
            searchHistoryRepositoryImpl.getSearchHistory().collect { actual = it }

            assertEquals(expected, actual)
        }
    }
}