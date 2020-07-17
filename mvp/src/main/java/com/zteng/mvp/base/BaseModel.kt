package com.zteng.mvp.base



import io.reactivex.annotations.NonNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import io.reactivex.disposables.CompositeDisposable

open class BaseModel : BaseModelInterface {
    var logger = LoggerFactory.getLogger(javaClass)


    private var schedulerProvider: BaseSchedulerProvider = SchedulerProvider
    protected var compositeDisposable: CompositeDisposable? = null

    fun setSchedulerProvider(@NonNull schedulerProvider: BaseSchedulerProvider) {
        this.schedulerProvider = schedulerProvider
    }


    override fun subscribe() {
        compositeDisposable = CompositeDisposable()
    }

    override fun unsubscribe() {
        compositeDisposable!!.dispose()
        compositeDisposable = null
    }


}
