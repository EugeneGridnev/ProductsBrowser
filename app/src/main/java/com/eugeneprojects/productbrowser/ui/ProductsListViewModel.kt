package com.eugeneprojects.productbrowser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.eugeneprojects.productbrowser.repository.ProductsRepository

class ProductsListViewModel(app: Application, val productsRepository: ProductsRepository) : AndroidViewModel(app) {

}