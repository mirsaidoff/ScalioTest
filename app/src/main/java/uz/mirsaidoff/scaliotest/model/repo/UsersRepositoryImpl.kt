package uz.mirsaidoff.scaliotest.model.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import uz.mirsaidoff.scaliotest.common.Constants
import uz.mirsaidoff.scaliotest.model.User
import uz.mirsaidoff.scaliotest.model.UsersPagingSource

@ExperimentalCoroutinesApi
class UsersRepositoryImpl(private val pagingSourceFactory: UsersPagingSource.Factory) : UsersRepository {

    override fun searchUsers(login: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = 3,
                initialLoadSize = Constants.PAGE_SIZE * 2
            ),
            pagingSourceFactory = { pagingSourceFactory.create(login) }
        ).flow
    }
}