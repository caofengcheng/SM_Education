package com.zteng.mvp.base

import org.slf4j.Logger
import org.slf4j.LoggerFactory


abstract class BasePresenter<T : BaseModelInterface, V : BaseView> : BasePresenterInterface<V> {
    protected var mLogger = LoggerFactory.getLogger(javaClass) as Logger
    protected var mModel: T? = null
    protected abstract var mView: V

    override fun setView(view: V) {
        mView = view
        initModel()
    }

    protected abstract fun initModel()

    override fun subscribe() {
        mModel!!.subscribe()
    }

    override fun unsubscribe() {
        mModel!!.unsubscribe()
    }

}
