package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemChecklistBinding
import com.jjsh.smartshopping.domain.model.CheckItem

class CheckListAdapter(
    private val _updateCheckItem: (CheckItem) -> Unit = {}
) : ListAdapter<CheckItem, CheckListAdapter.CheckItemViewHolder>(
    ItemDiffCallBack<CheckItem>({ old, new -> old.id == new.id })
) {
    private val updateCheckItem: (CheckItem, Int) -> Unit
        get() = { item, position ->
            _updateCheckItem(item)
            notifyItemChanged(position, item.amount)
        }

    class CheckItemViewHolder(
        private val binding: ItemChecklistBinding,
        private val updateCheckItem: (CheckItem, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CheckItem) {
            binding.checkItem = item
            binding.btnMinus.setOnClickListener {
                if (item.amount > 0) {
                    item.amount--
                    updateCheckItem(item, adapterPosition)
                }
            }
            binding.btnPlus.setOnClickListener {
                if (item.amount < 100) {
                    item.amount++
                    updateCheckItem(item, adapterPosition)
                }
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                updateCheckItem: (CheckItem, Int) -> Unit
            ): CheckItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_checklist, parent, false)
                val binding = ItemChecklistBinding.bind(view)
                return CheckItemViewHolder(binding, updateCheckItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckItemViewHolder {
        return CheckItemViewHolder.create(parent, updateCheckItem)
    }

    override fun onBindViewHolder(holder: CheckItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}