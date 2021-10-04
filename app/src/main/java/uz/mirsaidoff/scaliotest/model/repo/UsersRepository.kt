package uz.mirsaidoff.scaliotest.model.repo

import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import uz.mirsaidoff.scaliotest.model.User
import uz.mirsaidoff.scaliotest.model.UsersPagingSource

interface UsersRepository {
    fun searchUsers(login: String): Flow<PagingData<User>>

    companion object {
        @ExperimentalCoroutinesApi fun create(): UsersRepository {
            return UsersRepositoryImpl(UsersPagingSource.Factory())
        }
    }
}