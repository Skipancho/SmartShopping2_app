package com.jjsh.smartshopping.presentation.decoration

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class CustomDividerDecoration(
    private val height: Int,
    private val padding: Int,
    @ColorInt private val colorCode: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        color = colorCode
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = (parent.paddingStart + padding).toFloat()
        val right = (parent.width - parent.paddingEnd - padding).toFloat()

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height

            canvas.drawRect(left, top, right, bottom, paint)
        }
    }
}