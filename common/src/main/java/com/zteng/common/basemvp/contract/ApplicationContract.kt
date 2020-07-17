package com.zteng.common.basemvp.contract

import com.zteng.mvp.base.BaseModelInterface
import com.zteng.mvp.base.BasePresenterInterface
import com.zteng.mvp.base.BaseView



interface ApplicationContract {
    interface Model : BaseModelInterface {
        fun setPresenter(presenter: Presenter)

    }

    interface View : BaseView {

    }

    interface Presenter : BasePresenterInterface<View> {
    }
}
