package com.zteng.common.basemvp.model

import android.content.Context
import com.zteng.common.basemvp.contract.ApplicationContract
import com.zteng.common.database.GlobalParam
import com.zteng.common.util.DateUtil
import com.zteng.common.util.MacAddress
import com.zteng.mvp.RestManager
import com.zteng.mvp.base.BaseModel
import com.zteng.mvp.base.BaseSingle
import com.zteng.mvp.base.RxComposer


class ApplicationModel : BaseModel(), ApplicationContract.Model {
    private var mPresenter: ApplicationContract.Presenter? = null

    override fun setPresenter(presenter: ApplicationContract.Presenter) {
        mPresenter = presenter
    }

}
