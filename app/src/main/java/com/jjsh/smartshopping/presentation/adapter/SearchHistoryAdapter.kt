package com.jjsh.smartshopping.presentation.adapter

import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemSearchHistoryBinding
import com.jjsh.smartshopping.domain.model.SearchHistory
import com.jjsh.smartshopping.presentation.base.BaseAdapter
import com.jjsh.smartshopping.presentation.base.BaseViewHolder

class SearchHistoryAdapter(
    private val onClickItem: (String) -> Unit,
    private val onDeleteBtnClick: (SearchHistory) -> Unit
) : BaseAdapter<ItemSearchHistoryBinding,SearchHistory>(
    checkItemsTheSame = {old, new -> old.text == new.text},
    R.layout.item_search_history
) {
    override val bind: (ItemSearchHistoryBinding, SearchHistory, Int) -> Unit
        get() = { binding, item, _ ->
            binding.tvKeyword.text = item.text
            binding.root.setOnClickListener { onClickItem(item.text) }
            binding.ibDelete.setOnClickListener { onDeleteBtnClick(item) }
        }
}