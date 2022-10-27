package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemSearchHistoryBinding
import com.jjsh.smartshopping.domain.model.SearchHistory

class SearchHistoryAdapter(
    private val onClickItem: (String) -> Unit,
    private val onDeleteBtnClick: (SearchHistory) -> Unit
) : ListAdapter<SearchHistory, SearchHistoryAdapter.SearchHistoryViewHolder>(
    ItemDiffCallBack<SearchHistory>({ old, new ->
        old.text == new.text
    })
) {

    class SearchHistoryViewHolder(
        private val binding: ItemSearchHistoryBinding,
        private val onClickItem: (String) -> Unit,
        private val onDeleteBtnClick: (SearchHistory) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchHistory) {
            binding.tvKeyword.text = item.text
            binding.root.setOnClickListener { onClickItem(item.text) }
            binding.ibDelete.setOnClickListener { onDeleteBtnClick(item) }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onClickItem: (String) -> Unit,
                onDeleteBtnClick: (SearchHistory) -> Unit
            ): SearchHistoryViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_history, parent, false)
                val binding = ItemSearchHistoryBinding.bind(view)
                return SearchHistoryViewHolder(binding, onClickItem, onDeleteBtnClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder.create(parent, onClickItem, onDeleteBtnClick)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}