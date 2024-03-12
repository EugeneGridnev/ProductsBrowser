package com.eugeneprojects.productbrowser.repository

import com.eugeneprojects.productbrowser.api.ProductsAPI
import javax.inject.Inject

class ProductsRepositoryIMPL @Inject constructor(private val api: ProductsAPI): ProductsRepository {

    override suspend fun getProducts(searchText: String, productsLimit: Int, skip: Int) =
        api.getProducts(searchText, productsLimit, skip)
}