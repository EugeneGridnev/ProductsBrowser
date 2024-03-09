package com.eugeneprojects.productbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugeneprojects.productbrowser.adapters.ProductsPagingAdapter
import com.eugeneprojects.productbrowser.databinding.FragmentProductsListBinding
import com.eugeneprojects.productbrowser.repository.ProductsRepositoryIMPL
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
        val productsRepository = ProductsRepositoryIMPL()
        val viewModelProviderFactory = ProductsViewModelProviderFactory(productsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[ProductsListViewModel::class.java]

        setUpRecyclerView()
        observeProducts(productsPagingAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun observeProducts(adapter: ProductsPagingAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collectLatest(adapter::submitData)
            }
        }
    }

    private fun setUpRecyclerView() {
        productsPagingAdapter = ProductsPagingAdapter()
        binding?.rvProducts?.apply {
            adapter = productsPagingAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}