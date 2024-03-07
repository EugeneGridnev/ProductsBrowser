package com.eugeneprojects.productbrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eugeneprojects.productbrowser.adapters.ProductsAdapter
import com.eugeneprojects.productbrowser.databinding.FragmentProductsListBinding
import com.eugeneprojects.productbrowser.ui.ProductsActivity
import com.eugeneprojects.productbrowser.ui.ProductsListViewModel

class ProductsListFragment : Fragment() {

    lateinit var binding: FragmentProductsListBinding
    lateinit var productsAdapter: ProductsAdapter
    private lateinit var viewModel: ProductsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = (activity as ProductsActivity).viewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}