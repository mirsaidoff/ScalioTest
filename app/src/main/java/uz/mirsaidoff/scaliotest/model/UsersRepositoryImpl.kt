package uz.mirsaidoff.scaliotest.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.mirsaidoff.scaliotest.Constants
import uz.mirsaidoff.scaliotest.User

@ExperimentalCoroutinesApi
class UsersRepositoryImpl(private val pagingSourceFactory: UsersPagingSource.Factory) : UsersRepository {

    override fun searchUsers(login: String): Flowable<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = 3,
                initialLoadSize = Constants.PAGE_SIZE * 2
            ),
            pagingSourceFactory = { pagingSourceFactory.create(login) }
        ).flowable
    }
}