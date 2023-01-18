package com.jjsh.smartshopping.presentation.adapter

import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemPurchaseRecordBinding
import com.jjsh.smartshopping.domain.model.Purchase
import com.jjsh.smartshopping.presentation.base.BaseAdapter

class PurchaseRecordAdapter(
    private val onClick: (Purchase) -> Unit = {}
) : BaseAdapter<ItemPurchaseRecordBinding, Purchase>(
    checkItemsTheSame = { old, new -> old.id == new.id },
    R.layout.item_purchase_record
) {
    override val bind: (ItemPurchaseRecordBinding, Purchase, Int) -> Unit
        get() = { binding, item, _ ->
            binding.purchase = item
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
}