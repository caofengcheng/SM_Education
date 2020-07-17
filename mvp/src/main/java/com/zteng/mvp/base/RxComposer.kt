package com.zteng.mvp.base

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * 页面：
 * @author zhangguihao
 */
object RxComposer {

    val CHECK_NULL = 1
    val NOT_CHECK_NULL = 2

    @JvmStatic
    fun <T> composeSingle():SingleTransformer<T, T> {

        return SingleTransformer{
                upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}
