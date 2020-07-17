package com.zteng.common.base


import android.app.Application
import android.content.Context
import com.zteng.common.util.InitUtil
import com.zteng.common.util.MacAddress
import com.zteng.common.util.Tools
import com.zteng.mvp.GlobalParameter
import com.zteng.mvp.RestManager
import com.zteng.mvp.base.BasePresenterInterface
import com.zteng.mvp.base.BaseView


import io.reactivex.disposables.CompositeDisposable
import org.slf4j.LoggerFactory

abstract class BaseApplication< T : BasePresenterInterface<*>> : Application(), BaseView {
    var logger = LoggerFactory.getLogger(BaseApplication::class.java)

    var mDisposable = CompositeDisposable()
    abstract var mPresenter: T

    companion object {
        var  _context:Application? = null
        fun getContext():Context{
            return _context!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        _context = this
        printBootLog()
        InitUtil.init()
        initRest()
        MacAddress.getMacAddress(this)
    }

    /**
     * 需要子类手动调用init方法
     */
    open fun init() {
        mPresenter.subscribe()
    }


    private fun initRest() {
        RestManager.initRest(GlobalParameter.jcAddress)

    }

    private fun printBootLog() {
        logger.debug("application boot")
    }

    override fun onError(errorMessage: String) {
        logger.debug(errorMessage)
        Tools.displayToast(errorMessage)
    }


}
