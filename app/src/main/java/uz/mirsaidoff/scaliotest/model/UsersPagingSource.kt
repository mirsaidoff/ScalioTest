package uz.mirsaidoff.scaliotest.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import uz.mirsaidoff.scaliotest.model.api.ApiService
import uz.mirsaidoff.scaliotest.model.api.Service

class UsersPagingSource(
    private val service: ApiService,
    private val login: String
) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        if (login.isEmpty())
            return (LoadResult.Page(emptyList(), prevKey = null, nextKey = null))

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        return try {
            val response = service.searchUsers(login, pageSize, page)
            if (response.isSuccessful()) {
                LoadResult.Page(
                    response.items ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page * pageSize >= response.totalCount ?: 0) null else page + 1
                )
            } else {
                LoadResult.Error(Throwable(response.message))
            }
        } catch (e: HttpException) {
            Log.e("ErrorLoading", e.message())
            LoadResult.Error(e)
        }
    }

    class Factory {
        fun create(login: String): UsersPagingSource {
            return UsersPagingSource(Service.getInstance(), login)
        }
    }
}