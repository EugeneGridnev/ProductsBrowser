package com.eugeneprojects.productbrowser.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.eugeneprojects.productbrowser.adapters.ProductImagesAdapter
import com.eugeneprojects.productbrowser.databinding.FragmentProductBinding
import com.eugeneprojects.productbrowser.models.Product
import com.eugeneprojects.productbrowser.network.ConnectivityRepositoryIMPL
import com.eugeneprojects.productbrowser.repository.ProductsRepositoryIMPL
import com.eugeneprojects.productbrowser.ui.ProductsViewModel
import com.eugeneprojects.productbrowser.ui.ProductsViewModelProviderFactory

class ProductFragment : Fragment() {

    private var binding: FragmentProductBinding? = null
    private val args: ProductFragmentArgs by navArgs()

    private lateinit var viewModel: ProductsViewModel
    private lateinit var productImagesAdapter: ProductImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsRepository = ProductsRepositoryIMPL()
        val connectivityRepository = ConnectivityRepositoryIMPL(requireContext())
        val viewModelProviderFactory = ProductsViewModelProviderFactory(productsRepository, connectivityRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ProductsViewModel::class.java]
        setUpProductUI()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpProductUI() {

        val product = args.product

        setUpViewPager(product)

        binding?.textViewProductTitle?.text = product.title
        binding?.textViewProductDescription?.text = product.description
        binding?.textViewProductBrand?.text = product.brand
        binding?.textViewProductPrice?.text = product.price.toString() + "$"
        binding?.textViewProductDiscountPercentage?.text = product.discountPercentage.toString() + "%"
        binding?.textViewProductCategory?.text = product.category
        binding?.textViewProductRating?.text = product.rating.toString()
        binding?.textViewProductStock?.text = product.stock.toString()
    }

    private fun setUpViewPager(product: Product) {

        productImagesAdapter = ProductImagesAdapter(product.images)

        binding?.viewPagerProductImages?.adapter = productImagesAdapter
        binding?.viewPagerProductImages?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}