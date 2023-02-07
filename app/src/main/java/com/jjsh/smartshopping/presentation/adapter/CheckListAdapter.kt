package com.jjsh.smartshopping.presentation.adapter

import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemChecklistBinding
import com.jjsh.smartshopping.domain.model.CheckItem
import com.jjsh.smartshopping.presentation.base.BaseAdapter

class CheckListAdapter(
    private val _updateCheckItem: (CheckItem) -> Unit = {},
    private val deleteCheckItem: (CheckItem) -> Unit = {}
) : BaseAdapter<ItemChecklistBinding, CheckItem> (
    checkItemsTheSame = { old, new -> old.id == new.id },
    R.layout.item_checklist
) {
    private val updateCheckItem: (CheckItem, Int) -> Unit
        get() = { item, position ->
            _updateCheckItem(item)
            notifyItemChanged(position, item)
        }

    override val bind: (ItemChecklistBinding, CheckItem, Int) -> Unit
        get() = { binding, item, position ->
            binding.checkItem = item
            binding.btnMinus.setOnClickListener {
                if (item.amount > 1) {
                    item.amount--
                    updateCheckItem(item, position)
                }
            }
            binding.btnPlus.setOnClickListener {
                if (item.amount < 100) {
                    item.amount++
                    updateCheckItem(item, position)
                }
            }
            binding.ibDelete.setOnClickListener {
                deleteCheckItem(item)
            }
            binding.cbIsChecked.setOnClickListener {
                item.isChecked = !item.isChecked
                updateCheckItem(item, position)
            }
        }
}