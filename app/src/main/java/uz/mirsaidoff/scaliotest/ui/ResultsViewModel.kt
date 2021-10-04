package uz.mirsaidoff.scaliotest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import uz.mirsaidoff.scaliotest.model.User
import uz.mirsaidoff.scaliotest.model.repo.UsersRepository

class ResultsViewModel(private val repo: UsersRepository) : ViewModel() {

    fun searchUsers(login: String): Flow<PagingData<User>> {
        return repo.searchUsers("$login in:login")
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
    }

    class Factory(private val repo: UsersRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ResultsViewModel(repo) as T
        }

    }
}