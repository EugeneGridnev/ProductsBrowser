package com.eugeneprojects.productbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eugeneprojects.productbrowser.R
import com.eugeneprojects.productbrowser.databinding.ItemProductLayoutBinding

import com.eugeneprojects.productbrowser.models.Product

class ProductsPagingAdapter :
    PagingDataAdapter<Product, ProductsPagingAdapter.ProductViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position) ?: return
        holder.bind(product)
    }


    class ProductViewHolder(val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(product: Product) {
                this.itemView.apply {
                    Glide.with(this)
                        .load(product.thumbnail)
                        .placeholder(R.drawable.ic_image_placeholder)
                        .into(binding.ivProductThumbnail)
                    binding.tvProductTitle.text = product.title
                    binding.tvProductDescription.text = product.description
                }
            }
        }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}