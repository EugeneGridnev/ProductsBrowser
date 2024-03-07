package com.eugeneprojects.productbrowser.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eugeneprojects.productbrowser.repository.ProductsRepository

class ProductsViewModelProviderFactory(
    val app: Application,
    val productsRepository: ProductsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsListViewModel(app, productsRepository) as T
    }
}