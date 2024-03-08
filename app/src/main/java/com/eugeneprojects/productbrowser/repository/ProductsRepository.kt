package com.eugeneprojects.productbrowser.repository

import com.eugeneprojects.productbrowser.api.RetrofitInstance

class ProductsRepository {
    suspend fun getProducts(productsLimit: Int, skip: Int) = RetrofitInstance.api.getProducts(productsLimit, skip)
}