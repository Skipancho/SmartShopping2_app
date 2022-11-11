package com.jjsh.smartshopping.presentation.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecoration(
    private val top : Int = 0,
    private val bottom : Int = 0,
    private val width : Int = 0
) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = top
        outRect.bottom = bottom
        outRect.left = width
        outRect.right = width
    }
}