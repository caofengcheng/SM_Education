package com.zteng.mvp.base


import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

object  SchedulerProvider : BaseSchedulerProvider {

    @NonNull
    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    @NonNull
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    @NonNull
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}
