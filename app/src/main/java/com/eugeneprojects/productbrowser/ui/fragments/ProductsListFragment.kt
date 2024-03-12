package com.eugeneprojects.productbrowser.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import com.eugeneprojects.productbrowser.ui.ProductsViewModel
import com.eugeneprojects.productbrowser.util.simpleScan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : Fragment() {

    private var binding: FragmentProductsListBinding? = null

    private lateinit var viewModel: ProductsViewModel
    private lateinit var productsPagingAdapter: ProductsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        setUpProductsList()
        observeProducts()
        setTextChangeListener()
        handleScrollingToTopWhenSearching(productsPagingAdapter)

        viewModel.isOnline.observe(viewLifecycleOwner) { isOnline ->
            if (isOnline) {
                productsPagingAdapter.retry()
            } else {
                Toast.makeText(activity, resources.getString(R.string.network_error_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun setUpProductsList() {

        productsPagingAdapter = ProductsPagingAdapter()

        binding?.recyclerViewProducts?.layoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewProducts?.adapter = productsPagingAdapter.withLoadStateFooter(ProductsLoadStateAdapter())

        setOnProductClick()

        initSwipeToRefresh(productsPagingAdapter)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                productsPagingAdapter.loadStateFlow.collect { loadStates ->
                    binding?.swipeRefresh?.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
                }
            }
        }

        productsPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val refreshState = combinedLoadStates.refresh
            binding?.recyclerViewProducts?.isVisible = refreshState != LoadState.Loading
            binding?.progressBar?.isVisible = refreshState == LoadState.Loading

            if (refreshState is LoadState.Error) {
                Toast.makeText(activity,resources.getString(R.string.toast_load_error_message), Toast.LENGTH_SHORT).show()
            }

            if (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.append.endOfPaginationReached && productsPagingAdapter.itemCount == 0) {
                binding?.recyclerViewProducts?.isVisible = false
                binding?.textViewStub?.isVisible = true
            } else {
                binding?.recyclerViewProducts?.isVisible = true
                binding?.textViewStub?.isVisible = false
            }
        }

    }

    private fun observeProducts() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.products.collectLatest(productsPagingAdapter::submitData)
            }
        }
    }

    private fun setOnProductClick() {

        productsPagingAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("product", it)
            }
            findNavController().navigate(
                R.id.action_productsListFragment_to_productFragment,
                bundle
            )
        }
    }

    private fun initSwipeToRefresh(adapter: ProductsPagingAdapter) {

        binding?.swipeRefresh?.setOnRefreshListener { adapter.refresh() }
    }

    private fun setTextChangeListener() {

        binding?.searchEditText?.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    private fun handleScrollingToTopWhenSearching(adapter: ProductsPagingAdapter) = lifecycleScope.launch {
        // list should be scrolled to the 1st item (index = 0) if data has been reloaded:
        // (prev state = Loading, current state = NotLoading)
        getRefreshLoadStateFlow(adapter)
            .simpleScan(2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding?.recyclerViewProducts?.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadStateFlow(adapter: ProductsPagingAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

}