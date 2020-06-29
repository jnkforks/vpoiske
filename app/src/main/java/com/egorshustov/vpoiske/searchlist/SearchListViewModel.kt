package com.egorshustov.vpoiske.searchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.egorshustov.vpoiske.data.SearchWithUsers
import com.egorshustov.vpoiske.domain.searches.DeleteSearchUseCase
import com.egorshustov.vpoiske.domain.searches.GetSearchesWithUsersUseCase
import com.egorshustov.vpoiske.util.Event
import kotlinx.coroutines.launch

class SearchListViewModel @ViewModelInject constructor(
    getSearchesWithUsersUseCase: GetSearchesWithUsersUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase
) : ViewModel() {

    private val _openSearch = MutableLiveData<Event<Long>>()
    val openSearch: LiveData<Event<Long>> = _openSearch

    val isLoading = MutableLiveData(true)

    val searchesWithUsers: LiveData<PagedList<SearchWithUsers>> = getSearchesWithUsersUseCase()
        .toLiveData(Config(pageSize = 10, enablePlaceholders = false, maxSize = 100)).map {
            isLoading.value = false
            it
        }

    fun openSearch(searchId: Long) {
        _openSearch.value = Event(searchId)
    }

    fun onSearchSwiped(id: Long) {
        viewModelScope.launch { deleteSearchUseCase(id) }
    }
}