package com.zteng.mvp.base


import io.reactivex.SingleObserver
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseSingle<T> protected constructor(private val compositeDisposable: CompositeDisposable) :
    SingleObserver<T> {


    override fun onError(@NonNull e: Throwable) {
        //        Tools.displayToast(HttpExceptionUtil.exceptionHandler(e));
        onFailure(e, HttpExceptionUtil.exceptionHandler(e))
    }

    override fun onSubscribe(@NonNull d: Disposable) {
        compositeDisposable.add(d)
    }

    abstract fun onFailure(e: Throwable, errorMsg: String)
}
