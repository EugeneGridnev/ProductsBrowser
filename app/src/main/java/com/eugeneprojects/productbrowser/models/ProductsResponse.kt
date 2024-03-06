package com.eugeneprojects.productbrowser.models

import com.eugeneprojects.productbrowser.models.Product

data class ProductsResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)