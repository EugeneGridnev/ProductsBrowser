package com.eugeneprojects.productbrowser.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eugeneprojects.productbrowser.models.Product
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.util.Constants
import retrofit2.HttpException

class ProductPagingSource (
    val productsRepository: ProductsRepository
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        val skip = 0
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize: Int = params.loadSize.coerceAtMost(Constants.PAGE_SIZE)
        val response = productsRepository.getProducts(pageSize, skip)

        if (response.isSuccessful) {
            val products = checkNotNull(response.body()).products.map { product->
                Product(product.thumbnail,
                    product.category,
                    product.description,
                    product.discountPercentage,
                    product.id,
                    product.images,
                    product.price,
                    product.rating,
                    product.stock,
                    product.thumbnail,
                    product.title)
            }

            val nextPageNumber = if (products.size <= (response.body()?.limit ?: pageSize)) null else pageNumber + 1
            val prevPageNumber = if (pageNumber == 1) null else pageNumber + 1

            return LoadResult.Page(products, prevPageNumber, nextPageNumber)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 1
    }
}