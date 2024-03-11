package com.eugeneprojects.productbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugeneprojects.productbrowser.R
import com.eugeneprojects.productbrowser.adapters.ProductsLoadStateAdapter
import com.eugeneprojects.productbrowser.adapters.ProductsPagingAdapter
import com.eugeneprojects.productbrowser.databinding.FragmentProductsListBinding
import com.eugeneprojects.productbrowser.network.ConnectivityRepository
import com.eugeneprojects.productbrowser.repository.ProductsRepositoryIMPL
import com.eugeneprojects.productbrowser.ui.ProductsViewModel
import com.eugeneprojects.productbrowser.ui.ProductsViewModelProviderFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductsListFragment : Fragment() {

    private var binding: FragmentProductsListBinding? = null
    private lateinit var viewModel: ProductsViewModel

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
        val connectivityRepository =ConnectivityRepository(requireContext())
        val viewModelProviderFactory = ProductsViewModelProviderFactory(productsRepository, connectivityRepository)

        viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ProductsViewModel::class.java]

        viewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            if (isOnline) {
                setUpProductsList()
            } else {
                Toast.makeText(activity, resources.getString(R.string.network_error_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpProductsList() {

        val productsPagingAdapter = ProductsPagingAdapter()

        binding?.recyclerViewProducts?.layoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewProducts?.adapter = productsPagingAdapter.withLoadStateFooter(ProductsLoadStateAdapter())

        productsPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val refreshState = combinedLoadStates.refresh
            binding?.recyclerViewProducts?.isVisible = refreshState != LoadState.Loading
            binding?.progressBar?.isVisible = refreshState == LoadState.Loading
            if (refreshState is LoadState.Error) {
                Toast.makeText(activity,resources.getString(R.string.toast_load_error_message), Toast.LENGTH_SHORT).show()
            }
        }

        observeProducts(productsPagingAdapter)

        setOnProductClick(productsPagingAdapter)
    }

    private fun observeProducts(adapter: ProductsPagingAdapter) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collectLatest(adapter::submitData)
            }
        }
    }

    private fun setOnProductClick(adapter: ProductsPagingAdapter) {

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("product", it)
            }
            findNavController().navigate(
                R.id.action_productsListFragment_to_productFragment,
                bundle
            )
        }
    }
}