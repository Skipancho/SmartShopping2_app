package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemDetailImageBinding

class ImageViewPagerAdapter: RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {
    class ImageViewHolder(
        private val binding: ItemDetailImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.ivItemImage.setImage(imageUrl)
        }
    }

    private val imageUrls = mutableListOf<String>()

    fun setImageUrls(list : List<String>) {
        imageUrls.clear()
        if (list.isNotEmpty()){
            imageUrls.addAll(list)
        }else {
            imageUrls.addAll(listOf("",""))
        }
        notifyItemRangeChanged(0,imageUrls.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_image,parent,false)
        val binding = ItemDetailImageBinding.bind(view)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size
}