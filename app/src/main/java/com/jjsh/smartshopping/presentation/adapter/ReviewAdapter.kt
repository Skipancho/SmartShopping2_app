package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemProductReviewBinding
import com.jjsh.smartshopping.databinding.ItemUserReviewBinding
import com.jjsh.smartshopping.domain.model.Review

class ReviewAdapter(
    private val onClick: (Review) -> Unit = {},
    private val isUserReview: Boolean = true
) : ListAdapter<Review, RecyclerView.ViewHolder>(
    ItemDiffCallBack<Review>(checkItemsTheSame = { old, new -> old.id == new.id })
) {
    class UserReviewItemViewHolder(
        private val binding: ItemUserReviewBinding,
        private val onClick: (Review) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) {
            binding.review = item
            binding.tvEdit.setOnClickListener {
                onClick(item)
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Review) -> Unit): UserReviewItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_review, parent, false)
                val binding = ItemUserReviewBinding.bind(view)
                return UserReviewItemViewHolder(binding, onClick)
            }
        }
    }

    class ProductReviewItemViewHolder(
        private val binding: ItemProductReviewBinding,
        private val onClick: (Review) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Review) {
            binding.review = item
            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Review) -> Unit): ProductReviewItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product_review, parent, false)
                val binding = ItemProductReviewBinding.bind(view)
                return ProductReviewItemViewHolder(binding, onClick)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (isUserReview) {
            true -> 0
            false -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> UserReviewItemViewHolder.create(parent, onClick)
            else -> ProductReviewItemViewHolder.create(parent, onClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserReviewItemViewHolder -> holder.bind(getItem(position))
            is ProductReviewItemViewHolder -> holder.bind(getItem(position))
        }
    }
}