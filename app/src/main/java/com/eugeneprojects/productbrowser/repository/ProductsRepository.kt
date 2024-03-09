package com.eugeneprojects.productbrowser.repository

import com.eugeneprojects.productbrowser.models.ProductsResponse
import retrofit2.Response

interface ProductsRepository {
    suspend fun getProducts(productsLimit: Int, skip: Int): Response<ProductsResponse>
}