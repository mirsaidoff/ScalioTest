package uz.mirsaidoff.scaliotest.model

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import uz.mirsaidoff.scaliotest.User

interface UsersRepository {
    fun searchUsers(login: String): Flowable<PagingData<User>>

    companion object {
        fun create(): UsersRepository {
            return UsersRepositoryImpl(UsersPagingSource.Factory())
        }
    }
}