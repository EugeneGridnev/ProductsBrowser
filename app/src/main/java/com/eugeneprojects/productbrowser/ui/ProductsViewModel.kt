package com.eugeneprojects.productbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eugeneprojects.productbrowser.models.Product
import com.eugeneprojects.productbrowser.network.ConnectivityRepository
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.repository.paging.ProductPagingSource
import com.eugeneprojects.productbrowser.util.Constants
import kotlinx.coroutines.flow.Flow

class ProductsViewModel(
    private val productsRepository: ProductsRepository,
    connectivityRepository: ConnectivityRepository
) :
    ViewModel() {

    val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(
            Constants.PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = Constants.PAGE_SIZE
        ),
        pagingSourceFactory = { ProductPagingSource(productsRepository) }
    ).flow
        .cachedIn(viewModelScope)

    val isOnline = connectivityRepository.isConnected.asLiveData()

}