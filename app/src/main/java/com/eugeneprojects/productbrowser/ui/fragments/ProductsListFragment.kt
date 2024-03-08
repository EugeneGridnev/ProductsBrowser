package com.eugeneprojects.productbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugeneprojects.productbrowser.adapters.ProductsPagingAdapter
import com.eugeneprojects.productbrowser.databinding.FragmentProductsListBinding
import com.eugeneprojects.productbrowser.repository.ProductsRepository
import com.eugeneprojects.productbrowser.ui.ProductsListViewModel
import com.eugeneprojects.productbrowser.ui.ProductsViewModelProviderFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductsListFragment : Fragment() {

    private var binding: FragmentProductsListBinding? = null
    private lateinit var productsPagingAdapter: ProductsPagingAdapter
    private lateinit var viewModel: ProductsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productsRepository = ProductsRepository()
        val viewModelProviderFactory = ProductsViewModelProviderFactory(productsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ProductsListViewModel::class.java]
        productsPagingAdapter = ProductsPagingAdapter()
        setUpRecyclerView()

        observeProducts(productsPagingAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observeProducts(adapter: ProductsPagingAdapter) {
        lifecycleScope.launch {
            viewModel.getProducts().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

//    private fun hideProgressBar() {
//        binding.paginationProgressBar.visibility = View.INVISIBLE
//    }
//
//    private fun showProgressBar() {
//        binding.paginationProgressBar.visibility = View.VISIBLE
//    }

    private fun setUpRecyclerView() {
        productsPagingAdapter = ProductsPagingAdapter()
        binding?.rvProducts?.apply {
            adapter = productsPagingAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}