package com.eugeneprojects.productbrowser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.eugeneprojects.productbrowser.R
import com.eugeneprojects.productbrowser.repository.ProductsRepository

class ProductsActivity : AppCompatActivity() {

    lateinit var viewModel: ProductsListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productsRepository = ProductsRepository()
        val viewModelProviderFactory = ProductsViewModelProviderFactory(application, productsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ProductsListViewModel::class.java]
        setContentView(R.layout.activity_products)
    }
}