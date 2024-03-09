package com.eugeneprojects.productbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eugeneprojects.productbrowser.models.Product
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.repository.paging.ProductPagingSource
import com.eugeneprojects.productbrowser.util.Constants
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ProductsListViewModel(private val productsRepository: ProductsRepository) :
    ViewModel() {

        val products: StateFlow<PagingData<Product>> = Pager(
            config = PagingConfig(
                Constants.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = { ProductPagingSource(productsRepository) }
        ).flow
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

}