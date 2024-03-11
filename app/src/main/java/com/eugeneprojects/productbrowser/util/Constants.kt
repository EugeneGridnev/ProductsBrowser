package com.eugeneprojects.productbrowser.util

import androidx.paging.PagingConfig

object Constants {

    const val BASE_URL = "https://dummyjson.com"
    const val PAGE_SIZE = 20
    //нужно чтобы пэйджинг3 грузил первично по 20 станиц
    const val PREFETCH_DISTANCE = PAGE_SIZE / 2
    val PAGING_CONFIG = PagingConfig(
        pageSize = PAGE_SIZE,
        prefetchDistance = PREFETCH_DISTANCE,
        enablePlaceholders = false
    )
}