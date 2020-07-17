package com.zteng.common.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout

import com.zteng.common.base.BaseViewHolder

abstract class CommonGridLayoutAdapter<T> protected constructor(
    private val gridLayout: GridLayout,
    private val itemResourceId: Int,
    private val column: Int
) {
    private var data: List<T>? = null

    fun setData(data: List<T>) {
        this.data = data
    }

    fun notifyDataChanged() {
        if (data == null) {
            return
        }
        gridLayout.removeAllViews()
        for (i in data!!.indices) {
            addView(i, true)
        }
        if (data!!.size % column == 0) {
            return
        }
        for (i in 0 until column - data!!.size % column) {
            addView(i, false)
        }
    }

    private fun addView(position: Int, visible: Boolean) {
        val view =
            LayoutInflater.from(gridLayout.context).inflate(itemResourceId, gridLayout, false)
        val rowSpec = GridLayout.spec(position / column, 1.0f)     //设置它的行和列
        val columnSpec = GridLayout.spec(position % column, 1.0f)
        val layoutParams = GridLayout.LayoutParams(rowSpec, columnSpec)
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL)
        setMargins(layoutParams, position)
        if (visible) {
            convert(BaseViewHolder(view), data!![position], position)
        } else {
            view.visibility = View.INVISIBLE
        }
        gridLayout.addView(view, layoutParams)
    }

    protected fun isFirstRow(position: Int): Boolean {
        return position < column
    }

    protected fun isLastRow(position: Int): Boolean {
        return position >= data!!.size / column * column
    }

    protected fun isFirstColumn(position: Int): Boolean {
        return position % column == 0
    }

    protected fun isLastColumn(position: Int): Boolean {
        return position % column == column - 1
    }

    abstract fun convert(baseViewHolder: BaseViewHolder, item: T, position: Int)

    abstract fun setMargins(layoutParams: GridLayout.LayoutParams, position: Int)
}
