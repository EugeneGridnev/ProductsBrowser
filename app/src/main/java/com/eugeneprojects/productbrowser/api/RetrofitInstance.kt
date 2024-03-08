package com.eugeneprojects.productbrowser.api

import com.eugeneprojects.productbrowser.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api:ProductsAPI by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsAPI::class.java)
        }
}
