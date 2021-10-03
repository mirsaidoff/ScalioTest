package uz.mirsaidoff.scaliotest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.mirsaidoff.scaliotest.User
import uz.mirsaidoff.scaliotest.model.UsersRepository

class ResultsViewModel(private val repo: UsersRepository) : ViewModel() {

    private val _liveResults = MutableLiveData<List<User>>()
    val liveResults: LiveData<List<User>>
        get() = _liveResults

    fun searchUsers(login: String?) {
        login?.run {
            repo.searchUsers("$this in:login")
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                    { _liveResults.postValue(it.items!!) },
//                    { Log.e("USERS", it.message.toString()) }
//                )
        }
    }

    class Factory(private val repo: UsersRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ResultsViewModel(repo) as T
        }

    }
}