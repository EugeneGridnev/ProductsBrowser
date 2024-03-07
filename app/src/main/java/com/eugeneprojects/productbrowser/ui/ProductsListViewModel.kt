package com.eugeneprojects.productbrowser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eugeneprojects.productbrowser.models.ProductsResponse
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductsListViewModel(app: Application, val productsRepository: ProductsRepository) :
    AndroidViewModel(app) {

    private val _products = MutableLiveData<Resource<ProductsResponse>>()
    val products: LiveData<Resource<ProductsResponse>> = _products

    init {
        getProducts()
    }

    fun getProducts() = viewModelScope.launch {
        _products.postValue(Resource.Loading())
        val response = productsRepository.getProducts()
        _products.postValue(handleProductsResponse(response))
    }

    private fun handleProductsResponse(response: Response<ProductsResponse>) : Resource<ProductsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}