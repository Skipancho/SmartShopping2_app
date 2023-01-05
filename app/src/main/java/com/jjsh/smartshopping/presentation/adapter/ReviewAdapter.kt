package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemUserReviewBinding
import com.jjsh.smartshopping.domain.model.Review

class ReviewAdapter(
    private val onClick: (Review) -> Unit = {}
) : ListAdapter<Review, ReviewAdapter.ReviewItemViewHolder>(
    ItemDiffCallBack<Review>(checkItemsTheSame = { old, new -> old.id == new.id })
) {
    class ReviewItemViewHolder(
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
            fun create(parent: ViewGroup, onClick: (Review) -> Unit): ReviewItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_review, parent, false)
                val binding = ItemUserReviewBinding.bind(view)
                return ReviewItemViewHolder(binding, onClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
        return ReviewItemViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}