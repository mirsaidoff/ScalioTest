package uz.mirsaidoff.scaliotest.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.anyInt
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uz.mirsaidoff.scaliotest.model.api.ApiService
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
class UsersPagingSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: ApiService

    lateinit var pagingSource: UsersPagingSource

    companion object {
        val mockResponse = SearchResult(
            items = listOf(
                User("", "", "")
            ),
            totalCount = 10,
            message = null,
            incompleteResults = false
        )

        val mockUnsuccessfulResponse = SearchResult(
            items = null,
            totalCount = 0,
            message = "",
            incompleteResults = true
        )
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        pagingSource = UsersPagingSource(api, "mir")
    }

    @Throws(Exception::class)
    @Test
    fun `users paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(api.searchUsers(anyString(), anyInt(), anyInt())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, User>(error)
        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = true
                )
            )
        )
    }

    @Test
    fun `users paging source load - failure - received null`() = runBlockingTest {
        given(api.searchUsers(anyString(), anyInt(), anyInt())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, User>(NullPointerException())
        assertEquals(
            expectedResult.toString(),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = true
                )
            ).toString()
        )
    }

    @Test
    fun `users paging source refresh - success`() = runBlockingTest {
        given(api.searchUsers(anyString(), anyInt(), anyInt())).willReturn(mockResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = mockResponse.items ?: emptyList(),
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult, pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = true
                )
            )
        )
    }

    @Test
    fun `users paging source refresh - unsuccessful`() = runBlockingTest {
        given(api.searchUsers(anyString(), anyInt(), anyInt())).willReturn(mockUnsuccessfulResponse)
        val expectedResult = PagingSource.LoadResult.Error<Int, User>(Throwable(mockUnsuccessfulResponse.message))
        assertEquals(
            expectedResult.toString(), pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = true
                )
            ).toString()
        )
    }
}