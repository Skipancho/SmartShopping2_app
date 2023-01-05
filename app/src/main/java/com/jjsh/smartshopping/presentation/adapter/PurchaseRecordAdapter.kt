package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemPurchaseRecordBinding
import com.jjsh.smartshopping.domain.model.Purchase

class PurchaseRecordAdapter(
    private val onClick: (Purchase) -> Unit = {}
) : ListAdapter<Purchase, PurchaseRecordAdapter.PurchaseRecordViewHolder>(
    ItemDiffCallBack<Purchase>({ old, new -> old.id == new.id })
) {
    class PurchaseRecordViewHolder(
        private val binding: ItemPurchaseRecordBinding,
        private val onClick: (Purchase) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Purchase) {
            binding.purchase = item
            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        companion object {
            fun create(parent: ViewGroup, onClick: (Purchase) -> Unit): PurchaseRecordViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_purchase_record, parent, false)
                val binding = ItemPurchaseRecordBinding.bind(view)
                return PurchaseRecordViewHolder(binding, onClick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseRecordViewHolder {
        return PurchaseRecordViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: PurchaseRecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}