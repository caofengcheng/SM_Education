package com.zteng.common.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class CommonPageAdapter : PagerAdapter() {
    private var data: List<View>? = null

    fun setData(data: List<View>) {
        this.data = data
    }

    override fun getCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(data!![position])
        return data!![position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
