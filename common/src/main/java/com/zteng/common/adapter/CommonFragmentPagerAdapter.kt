package com.zteng.common.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager

class CommonFragmentPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>?) :
    FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {
        return fragments!![i]
    }

    override fun getCount(): Int {
        return fragments?.size ?: 0
    }

    @JvmOverloads
    fun bindViewPager(
        viewPager: ViewPager,
        onPageChangeListener: ViewPager.OnPageChangeListener = object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                viewPager.currentItem = i
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        }
    ) {
        viewPager.adapter = this
        viewPager.offscreenPageLimit = count
        viewPager.addOnPageChangeListener(onPageChangeListener)
    }
}
