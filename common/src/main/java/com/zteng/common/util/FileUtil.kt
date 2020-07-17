package com.zteng.common.util

import android.os.Environment
import android.text.TextUtils
import com.zteng.common.base.BaseApplication

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

object FileUtil {
    var context = BaseApplication.getContext()

    /**
     * SD卡路径
     */
    //判断sd卡是否存在
    val sdPath: String
        get() {
            val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            return if (sdCardExist) {
                Environment.getExternalStorageDirectory().path + "/"
            } else ""
        }

    val appSDMainPath: String
        get() {
            var dir = sdPath
            if (TextUtils.isEmpty(dir)) {
                dir =
                    context.getFilesDir().getPath() + "/"
            }
            return dir + "ElectronClass/"
        }

    /**
     * 创建目录
     * @param strDir 路径名称
     * @return 是否成功
     */
    fun makeDir(strDir: String): Boolean {
        val file = File(strDir)
        return file.exists() || file.mkdirs()
    }



    @Throws(IOException::class)
    fun copyFile(source: File, dest: File) {
        FileInputStream(source).channel.use { inputChannel ->
            FileOutputStream(dest).channel.use { outputChannel ->
                outputChannel.transferFrom(
                    inputChannel,
                    0,
                    inputChannel.size()
                )
            }
        }
    }

    /**
     * 判断文件是否存在.
     * @param filename 文件名
     * @return 是否存在.
     */
    fun isFileExists(filename: String): Boolean {
        val f = File(filename)
        return f.exists()
    }



    fun getExtensionName(fileName: String?): String? {
        if (fileName != null && fileName.length > 0) {
            val dot = fileName.lastIndexOf('.')
            if (dot > -1 && dot < fileName.length - 1) {
                return fileName.substring(dot + 1)
            }
        }
        return fileName
    }
}
