package uz.mirsaidoff.scaliotest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModel() : ViewModel() {

    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel() as T
        }

    }
}