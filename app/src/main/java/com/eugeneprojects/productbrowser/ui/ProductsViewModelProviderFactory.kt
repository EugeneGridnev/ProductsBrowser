package com.eugeneprojects.productbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eugeneprojects.productbrowser.repository.ProductsRepository

class ProductsViewModelProviderFactory(
    val productsRepository: ProductsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsListViewModel(productsRepository) as T
    }
}