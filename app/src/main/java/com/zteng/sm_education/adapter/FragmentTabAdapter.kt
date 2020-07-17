package com.zteng.sm_education.adapter

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction


import android.widget.RadioGroup

import com.zteng.sm_education.MainActivity

class FragmentTabAdapter(
    private val fragmentActivity: MainActivity // Fragment所属的Activity
    ,
    private val fragments: List<Fragment> // 一个tab页面对应一个Fragment
    , private val fragmentContentId: Int // Activity中所要被替换的区域的id
    , private val rgs: RadioGroup // 用于切换tab
    ,private val transaction: FragmentTransaction
) : RadioGroup.OnCheckedChangeListener {

    var currentTab: Int = 0
        private set // 当前Tab页面索引

    var onRgsExtraCheckedChangedListener: OnRgsExtraCheckedChangedListener? =
        null // 用于让调用者在切换tab时候增加新的功能

    val currentFragment: Fragment
        get() = fragments[currentTab]


    init {
        // 默认显示第一页
        transaction.add(fragmentContentId, fragments[0])
        transaction.commit()
        rgs.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(radioGroup: RadioGroup, checkedId: Int) {
        for (i in 0 until rgs.childCount) {
            if (rgs.getChildAt(i).id == checkedId) {
                // 如果设置了切换tab额外功能功能接口
                if (null != onRgsExtraCheckedChangedListener) {
                    onRgsExtraCheckedChangedListener!!.OnRgsExtraCheckedChanged(
                        radioGroup, checkedId, i
                    )
                }

                val fragment = fragments[i]
                val ft = obtainFragmentTransaction(i)

                currentFragment.onPause() // 暂停当前tab
                // getCurrentFragment().onStop(); // 暂停当前tab

                if (fragment.isAdded) {
                    // fragment.onStart(); // 启动目标tab的onStart()
                    fragment.onResume() // 启动目标tab的onResume()
                } else {
                    ft.add(fragmentContentId, fragment)
                }
                showTab(i) // 显示目标tab
                ft.commit()

            }
        }

    }

    /**
     * 切换tab
     *
     * @param idx
     */
    fun showTab(idx: Int) {
        for (i in fragments.indices) {
            val fragment = fragments[i]
            val ft = obtainFragmentTransaction(idx)

            if (idx == i) {
                ft.show(fragment)
            } else {
                ft.hide(fragment)
            }
            ft.commit()
        }
        currentTab = idx // 更新目标tab为当前tab
    }

    /**
     * 获取一个带动画的FragmentTransaction
     *
     * @param index
     * @return
     */
    private fun obtainFragmentTransaction(index: Int): FragmentTransaction {
// 设置切换动画
        // if (index > currentTab) {
        // ft.setCustomAnimations(R.anim.slide_right_in,
        // R.anim.slide_left_out);
        // } else {
        // ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);
        //
        // }
        return fragmentActivity.supportFragmentManager
            .beginTransaction()
    }

    /**
     * 切换tab额外功能功能接口
     */
    class OnRgsExtraCheckedChangedListener {
        fun OnRgsExtraCheckedChanged(
            radioGroup: RadioGroup,
            checkedId: Int, index: Int
        ) {

        }
    }

}

