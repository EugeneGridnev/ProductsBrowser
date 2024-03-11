package com.eugeneprojects.productbrowser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eugeneprojects.productbrowser.R
import com.eugeneprojects.productbrowser.databinding.ItemProductImagesBinding

class ProductImagesAdapter(private val imageUrlList: List<String>) :
    RecyclerView.Adapter<ProductImagesAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ItemProductImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(imageUrl: String) {

            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(binding.imageViewProductImages)
        }

    }

    override fun getItemCount(): Int = imageUrlList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val binding = ItemProductImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(imageUrlList[position])
    }

}