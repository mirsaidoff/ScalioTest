package uz.mirsaidoff.scaliotest.model

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import uz.mirsaidoff.scaliotest.User

interface UsersRepository {
    fun searchUsers(login: String): Flow<PagingData<User>>

    companion object {
        @ExperimentalCoroutinesApi fun create(): UsersRepository {
            return UsersRepositoryImpl(UsersPagingSource.Factory())
        }
    }
}