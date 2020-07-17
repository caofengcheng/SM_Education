package com.zteng.mvp.base


interface BasePresenterInterface<T : BaseView> {
    fun onError(errorMessage: String)

    fun subscribe()

    fun unsubscribe()

    fun setView(view: T)
}
