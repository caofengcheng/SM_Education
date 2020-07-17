package com.zteng.common.base

import android.os.Bundle
import com.zteng.common.util.Tools

import com.zteng.mvp.base.BasePresenterInterface
import com.zteng.mvp.base.BaseView

import io.reactivex.disposables.CompositeDisposable
import android.support.v7.app.AppCompatActivity
import org.slf4j.LoggerFactory

abstract class BaseActivity<T : BasePresenterInterface<*>> : AppCompatActivity(), BaseView {
    var logger = LoggerFactory.getLogger(BaseActivity::class.java)
    var mDisposable = CompositeDisposable()
    protected var mPresenter: T? = null

    protected abstract val presenter: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }




    /**
     * 需要子类手动调用init方法
     */
    @Suppress("UNREACHABLE_CODE")
    protected fun init() {
        mPresenter = presenter
        initView()
        mPresenter!!.subscribe()
        initData()
        showData()
    }

    /**
     * 初始化view 包含findViewById等
     */
    protected abstract fun initView()

    /**
     * 获取数据 初始化presenter等
     */
    protected abstract fun initData()

    /**
     * 展示数据 显示已知数据到界面上 显示fragment 调用presenter接口等
     */
    protected abstract fun showData()

    /**
     * 释放数据 释放presenter等
     */
    protected abstract fun releaseData()

    override fun onError(errorMessage: String) {
        logger.debug(errorMessage)
        Tools.displayToast(errorMessage)
    }

    override fun onDestroy() {
        releaseData()
        if (mPresenter != null) {
            mPresenter!!.unsubscribe()
        }
        if (!mDisposable.isDisposed) {
            mDisposable.dispose()
        }
        super.onDestroy()
    }

}

