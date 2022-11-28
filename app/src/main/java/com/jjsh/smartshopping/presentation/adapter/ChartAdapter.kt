package com.jjsh.smartshopping.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.ColorUtil
import com.jjsh.smartshopping.databinding.ItemChartBodyBinding
import com.jjsh.smartshopping.databinding.ItemChartHeadBinding
import com.jjsh.smartshopping.domain.model.PieChartData

class ChartAdapter : ListAdapter<PieChartData, RecyclerView.ViewHolder>(
    ItemDiffCallBack<PieChartData>({ old, new ->
        if (old is PieChartData.Body && new is PieChartData.Body) {
            old.category == new.category
        } else old is PieChartData.Head && new is PieChartData.Head
    })
) {
    class ChartHeadViewHolder(
        private val binding: ItemChartHeadBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun drawChart(list : List<PieChartData>) {
            val pieEntries = mutableListOf<PieEntry>()

            list.forEach {
                if (it is PieChartData.Body) {
                    pieEntries.add(PieEntry(it.percentage,it.category))
                }
            }

            val pieDataSet = PieDataSet(pieEntries,"")
            pieDataSet.colors = ColorUtil.basicColors
            pieDataSet.valueTextSize = 0f
            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)

            binding.pcPurchaseType.run {
                data = pieData
                description = null
                setEntryLabelColor(0xff828282.toInt())
                animateXY(500,500)
                invalidate()
            }
        }

        companion object {
            fun create(
                parent: ViewGroup
            ): ChartHeadViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chart_head, parent, false)
                val binding = ItemChartHeadBinding.bind(view)
                return ChartHeadViewHolder(binding)
            }
        }
    }

    class ChartBodyViewHolder(
        private val binding: ItemChartBodyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PieChartData) {
            binding.chartBody = item as PieChartData.Body
            binding.tvCategory.backgroundTintList = ColorStateList.valueOf(ColorUtil.basicColors[(adapterPosition-1)%8])
        }

        companion object {
            fun create(
                parent: ViewGroup
            ): ChartBodyViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chart_body, parent, false)
                val binding = ItemChartBodyBinding.bind(view)
                return ChartBodyViewHolder(binding)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PieChartData.Head -> HEAD
            is PieChartData.Body -> BODY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEAD -> ChartHeadViewHolder.create(parent)
            else -> ChartBodyViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ChartHeadViewHolder -> holder.drawChart(currentList)
            is ChartBodyViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<PieChartData>,
        currentList: MutableList<PieChartData>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyItemChanged(0)
    }

    companion object {
        private const val HEAD = 0
        private const val BODY = 1
    }
}