package com.eugeneprojects.productbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eugeneprojects.productbrowser.network.ConnectivityRepositoryIMPL
import com.eugeneprojects.productbrowser.repository.ProductsRepository

class ProductsViewModelProviderFactory(
    private val productsRepository: ProductsRepository,
    private val connectivityRepository: ConnectivityRepositoryIMPL
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(productsRepository, connectivityRepository) as T
    }
}