package com.zteng.common.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

import java.util.ArrayList

/**
 * @author ：LiMing
 * @date ：2019-04-17
 * @desc ：
 */
class CommonPagerAdapter(fm: FragmentManager, title: List<String>, fragments: List<Fragment>) :
    FragmentPagerAdapter(fm) {

    private var title = ArrayList<String>()
    private var fragments = ArrayList<Fragment>()

    init {
        this.title = title as ArrayList<String>
        this.fragments = fragments as ArrayList<Fragment>
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}
