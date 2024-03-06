package com.eugeneprojects.productbrowser.repository

import com.eugeneprojects.productbrowser.api.RetrofitInstance

class ProductsRepository {
    suspend fun getProducts() = RetrofitInstance.api.getProducts()
}