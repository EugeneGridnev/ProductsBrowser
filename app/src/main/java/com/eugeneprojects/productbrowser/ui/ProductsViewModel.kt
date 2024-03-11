package com.eugeneprojects.productbrowser.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.eugeneprojects.productbrowser.network.ConnectivityRepository
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.repository.paging.ProductPagingSource
import com.eugeneprojects.productbrowser.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    private val connectivityRepository: ConnectivityRepository
) :
    ViewModel() {

    private var searchQuery: MutableLiveData<String> = MutableLiveData("")

    val isOnline = connectivityRepository.isConnected.asLiveData()


    val products = searchQuery
        .asFlow()
        .debounce(500)
        .flatMapLatest {
        Pager(
            config = Constants.PAGING_CONFIG,
            pagingSourceFactory = { ProductPagingSource(productsRepository, it) }
        ).flow
    }.flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {

        if (searchQuery.value != query) {
            searchQuery.value = query
        }
    }
}