package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemChecklistBinding
import com.jjsh.smartshopping.domain.model.CheckItem

class CheckListAdapter(
    private val _updateCheckItem: (CheckItem) -> Unit = {},
    private val deleteCheckItem: (CheckItem) -> Unit = {}
) : ListAdapter<CheckItem, CheckListAdapter.CheckItemViewHolder>(
    ItemDiffCallBack<CheckItem>({ old, new -> old.id == new.id })
) {
    private val updateCheckItem: (CheckItem, Int) -> Unit
        get() = { item, position ->
            _updateCheckItem(item)
            notifyItemChanged(position, item)
        }

    class CheckItemViewHolder(
        private val binding: ItemChecklistBinding,
        private val updateCheckItem: (CheckItem, Int) -> Unit,
        private val deleteCheckItem: (CheckItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CheckItem) {
            binding.checkItem = item
            binding.btnMinus.setOnClickListener {
                if (item.amount > 1) {
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
            binding.ibDelete.setOnClickListener {
                deleteCheckItem(item)
            }
            binding.cbIsChecked.setOnCheckedChangeListener { compoundButton, b ->
                if (item.isChecked != b) item.isChecked = b
                updateCheckItem(item, adapterPosition)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                updateCheckItem: (CheckItem, Int) -> Unit,
                deleteCheckItem: (CheckItem) -> Unit
            ): CheckItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_checklist, parent, false)
                val binding = ItemChecklistBinding.bind(view)
                return CheckItemViewHolder(binding, updateCheckItem, deleteCheckItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckItemViewHolder {
        return CheckItemViewHolder.create(parent, updateCheckItem, deleteCheckItem)
    }

    override fun onBindViewHolder(holder: CheckItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}