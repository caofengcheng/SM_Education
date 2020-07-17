package com.zteng.sm_education

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.zteng.home.HomeFragment
import com.zteng.mall.MallFragment
import com.zteng.my.MyFragment
import com.zteng.sm_education.adapter.FragmentTabAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_main)
        setFragment()
    }

    @SuppressLint("CommitTransaction")
    private fun setFragment(){
        //将fragment加入集合
        val fragmentList = listOf(HomeFragment(),MallFragment(),MyFragment())
        val beginTransaction = supportFragmentManager.beginTransaction()
        FragmentTabAdapter(this,fragmentList,R.id.frameLayout,item_rg,beginTransaction)
    }
}

