package com.sangmeebee.searchmovieproject.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class DividerDecoration(
    private val headerPadding: Int = 0,
    private val separatorPadding: Int = 0,
    private val footerPadding: Int = 0,
    divideHeight: Int = 1,
    @ColorInt divideColor: Int,
) : RecyclerView.ItemDecoration() {

    private val mPaint: Paint = Paint().apply { color = divideColor }
    private val mHeightDp: Int = divideHeight.pxToDp()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {
            if (i < parent.childCount - 1) {
                val view: View = parent.getChildAt(i)
                c.drawRect(view.left.toFloat(),
                    separatorPadding.pxToDp() + view.bottom.toFloat(),
                    view.right.toFloat(),
                    (separatorPadding.pxToDp() + view.bottom + mHeightDp).toFloat(),
                    mPaint)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        outRect.apply {
            top = if (position == 0) {
                headerPadding.pxToDp()
            } else {
                separatorPadding.pxToDp()
            }
            bottom = if (position == itemCount - 1) {
                footerPadding.pxToDp()
            } else {
                separatorPadding.pxToDp()
            }
        }
    }
}
