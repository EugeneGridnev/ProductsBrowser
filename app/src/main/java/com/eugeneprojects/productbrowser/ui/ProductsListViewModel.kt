package com.eugeneprojects.productbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eugeneprojects.productbrowser.models.Product
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.repository.paging.ProductPagingSource
import com.eugeneprojects.productbrowser.util.Constants
import kotlinx.coroutines.flow.Flow

class ProductsListViewModel(private val productsRepository: ProductsRepository) :
    ViewModel() {

//    private val _products = MutableLiveData<Result<ProductsResponse>>()
//    val products: LiveData<Result<ProductsResponse>> = _products

//    init {
//        getProducts()
//    }

    fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                Constants.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = { ProductPagingSource(productsRepository) }
        ).flow
            .cachedIn(viewModelScope)
    }

//    private fun handleProductsResponse(response: Response<ProductsResponse>) : Result<ProductsResponse> {
//        if(response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                return Result.Success(resultResponse)
//            }
//        }
//        return Result.Error(response.message())
//    }

}