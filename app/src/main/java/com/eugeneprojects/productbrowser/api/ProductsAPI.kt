package com.eugeneprojects.productbrowser.api

import com.eugeneprojects.productbrowser.models.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsAPI {

    @GET("/products")
    suspend fun getProducts(
        @Query("limit")
        productsLimit: Int,
        @Query("skip")
        skip: Int
    ): Response<ProductsResponse>
}