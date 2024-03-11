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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    connectivityRepository: ConnectivityRepository
) :
    ViewModel() {

    private var searchQuery: MutableLiveData<String> = MutableLiveData("")

    val isOnline = connectivityRepository.isConnected.asLiveData()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val products = searchQuery
        .asFlow()
        .debounce(500)
        .flatMapLatest {
        Pager(
            config = Constants.PAGING_CONFIG,
            pagingSourceFactory = { ProductPagingSource(productsRepository, it) }
        ).flow
    }.cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        if (searchQuery.value != query) {
            searchQuery.value = query
        }
    }
}