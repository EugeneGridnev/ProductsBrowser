package com.eugeneprojects.productbrowser.repository

import com.eugeneprojects.productbrowser.api.RetrofitInstance

class ProductsRepositoryIMPL: ProductsRepository {

    override suspend fun getProducts(searchText: String, productsLimit: Int, skip: Int) =
        RetrofitInstance.api.getProducts(searchText, productsLimit, skip)
}