package com.zteng.mvp.base

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<T> {
    private var compositeDisposable: CompositeDisposable? = null

    protected constructor(compositeDisposable: CompositeDisposable) {
        this.compositeDisposable = compositeDisposable
    }

    override fun onNext(@NonNull result: T) {
        onSuccess(result)
    }

    override fun onError(@NonNull e: Throwable) {
        //        Tools.displayToast(HttpExceptionUtil.exceptionHandler(e));
        onFailure(e, HttpExceptionUtil.exceptionHandler(e))
    }

    override fun onComplete() {

    }

    override fun onSubscribe(@NonNull d: Disposable) {
        compositeDisposable?.add(d)
    }

    abstract fun onSuccess(result: T)

    abstract fun onFailure(e: Throwable, errorMsg: String)
}
