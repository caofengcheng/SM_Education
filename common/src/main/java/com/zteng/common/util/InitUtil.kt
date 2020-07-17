package com.zteng.common.util

import com.zteng.common.base.BaseApplication
import com.zteng.mvp.RestManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.zteng.common.database.Constants


object InitUtil {
    var context = BaseApplication.getContext()

    fun init() {
        makeAppDir()
        initCrashHandler()
        initFresco()
    }

    /**
     * 创建app需要用的文件夹
     */
    private fun makeAppDir() {
        /*创建目录*/
        val path = FileUtil.appSDMainPath
        FileUtil.makeDir(path)

        FileUtil.makeDir(path + Constants.DIR_CRASH_LOGS)
        FileUtil.makeDir(path + Constants.DIR_DOWNLOADS)
        FileUtil.makeDir(path + Constants.DIR_LOGS)
        FileUtil.makeDir(path + Constants.DIR_IMAGE_CACHE)
        FileUtil.makeDir(path + Constants.DIR_RECORDS)
        FileUtil.makeDir(path + Constants.DIR_ZIP)
    }


    private fun initCrashHandler() {
        CrashHandler.instance.init(context)
    }

    private fun initFresco() {
        Fresco.initialize(context)
        val imagePipeline = Fresco.getImagePipeline()
        imagePipeline.clearCaches()
    }

    private fun initRest() {
        RestManager.initRest(Constants.PURL)
    }
}
