package uz.mirsaidoff.scaliotest.model

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import uz.mirsaidoff.scaliotest.ApiService
import uz.mirsaidoff.scaliotest.Service
import uz.mirsaidoff.scaliotest.User

class UsersPagingSource(
    private val service: ApiService,
    private val login: String
) : RxPagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        val anchorPos = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPos) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, User>> {
        if (login.isEmpty())
            return Single.just(LoadResult.Page(emptyList(), prevKey = null, nextKey = null))

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize

        return service.searchUsers(login, pageSize, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    it.items ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page * pageSize >= it.totalCount ?: 0) null else page + 1
                )
            }
        //            .onErrorReturn { LoadResult.Error(it) }

    }

    class Factory {
        fun create(login: String): UsersPagingSource {
            return UsersPagingSource(Service.getInstance(), login)
        }
    }
}