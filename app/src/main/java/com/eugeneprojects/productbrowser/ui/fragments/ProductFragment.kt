package com.eugeneprojects.productbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.eugeneprojects.productbrowser.R
import com.eugeneprojects.productbrowser.databinding.FragmentProductBinding
import com.eugeneprojects.productbrowser.repository.ProductsRepositoryIMPL
import com.eugeneprojects.productbrowser.ui.ProductsActivity
import com.eugeneprojects.productbrowser.ui.ProductsViewModel
import com.eugeneprojects.productbrowser.ui.ProductsViewModelProviderFactory

class ProductFragment : Fragment() {

    lateinit var binding: FragmentProductBinding
    lateinit var viewModel: ProductsViewModel
    val args: ProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsRepository = ProductsRepositoryIMPL()
        val viewModelProviderFactory = ProductsViewModelProviderFactory(productsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ProductsViewModel::class.java]
        setUpProductUI()
    }

    private fun setUpProductUI() {

        val product = args.product

        Glide.with(this)
            .load(product.thumbnail)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(binding.ivProductImage)
        binding.textViewProductTitle.text = product.title
        binding.textViewProductDescription.text = product.description
        binding.textViewProductBrand.text = product.brand
        binding.textViewProductPrice.text = product.price.toString()
        binding.textViewProductCategory.text = product.category
    }
}