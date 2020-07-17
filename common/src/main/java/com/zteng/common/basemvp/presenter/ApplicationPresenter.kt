package com.zteng.common.basemvp.presenter

import android.content.Context

import com.zteng.common.basemvp.contract.ApplicationContract
import com.zteng.common.basemvp.model.ApplicationModel
import com.zteng.common.database.GlobalParam
import com.zteng.mvp.base.BasePresenter


class ApplicationPresenter :
    BasePresenter<ApplicationContract.Model, ApplicationContract.View>(),
    ApplicationContract.Presenter {


     override fun initModel() {
        mModel = ApplicationModel()
        mModel!!.setPresenter(this)
    }

    override fun onError(errorMessage: String) {
        mView.onError(errorMessage)
    }


    override lateinit var mView: ApplicationContract.View


}
