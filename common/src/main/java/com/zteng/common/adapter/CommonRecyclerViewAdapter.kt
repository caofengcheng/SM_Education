package com.zteng.common.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout

import com.zteng.common.R
import com.zteng.common.base.BaseViewHolder

abstract class CommonRecyclerViewAdapter<T> @JvmOverloads protected constructor(
    layout: Int,
    private val hasFooter: Boolean = true,
    private val hasEmpty: Boolean = true,
    layoutResourceMap: SparseIntArray? = null
) : RecyclerView.Adapter<BaseViewHolder>() {
    private var data: MutableList<T>? = null
    private var emptyViewId: Int = 0
    private val layoutResourceMap: SparseIntArray
    private var isShowError: Boolean = false

    private val isDataEmpty: Boolean
        get() = data == null || data!!.isEmpty()

    protected constructor(layoutResourceMap: SparseIntArray) : this(
        0,
        true,
        true,
        layoutResourceMap
    ) {
    }

    init {
        var layoutResourceMap = layoutResourceMap
        if (layoutResourceMap == null) {
            layoutResourceMap = SparseIntArray()
            layoutResourceMap.put(ITEM_TYPE_NORMAL, layout)
        }
        isShowError = false
        this.emptyViewId = R.layout.item_empty
        this.layoutResourceMap = layoutResourceMap
    }

    override fun getItemViewType(position: Int): Int {
        if (isShowError) {
            return ITEM_TYPE_ERROR
        }
        if (isDataEmpty) {
            return ITEM_TYPE_EMPTY_VIEW
        }
        return if (hasFooter && position == data!!.size) {
            ITEM_TYPE_FOOTER
        } else ITEM_TYPE_NORMAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            ITEM_TYPE_EMPTY_VIEW, ITEM_TYPE_ERROR -> return BaseViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.layout_empty, parent, false)
            )
            ITEM_TYPE_FOOTER -> return BaseViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_no_more_data,
                    parent,
                    false
                )
            )
            else -> return BaseViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    layoutResourceMap.get(viewType),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(baseViewHolder: BaseViewHolder, position: Int) {
        if (isShowError) {
            showWholeView(baseViewHolder, R.layout.item_error)
            isShowError = false
        }
        if (isDataEmpty) {
            if (hasEmpty) {
                //                showWholeView(baseViewHolder, emptyViewId);
            }
        } else if (position < data!!.size) {
            convert(baseViewHolder, data!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (isDataEmpty) 1 else data!!.size + if (hasFooter) 1 else 0
    }

    private fun showWholeView(baseViewHolder: BaseViewHolder, layout: Int) {
        val llEmptyView = baseViewHolder.getView(R.id.llEmptyView) as LinearLayout?
        llEmptyView!!.removeAllViews()
        LayoutInflater.from(llEmptyView.context).inflate(layout, llEmptyView, true)
    }

    protected fun isLastItem(position: Int): Boolean {
        return position == data!!.size - 1
    }

    abstract fun convert(baseViewHolder: BaseViewHolder, item: T)

    @JvmOverloads
    fun bindRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            recyclerView.context
        )
    ) {
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = this
    }

    fun setEmptyView(resId: Int) {
        emptyViewId = resId
    }

    fun showError() {
        isShowError = true
        notifyDataSetChanged()
    }


    fun addData(data: MutableList<T>?) {
        if (data == null) {
            return
        }
        if (this.data != null) {
            this.data!!.addAll(data)
        } else {
            setData(data)
        }
    }

    fun setData(data: List<T>?){
        this.data = data as MutableList<T>?
    }

    fun getData(): List<T>? {
        return data
    }



    companion object {
        private val ITEM_TYPE_EMPTY_VIEW = -1
        internal val ITEM_TYPE_NORMAL = -2
        private val ITEM_TYPE_FOOTER = -3
        private val ITEM_TYPE_ERROR = -4
    }
}
