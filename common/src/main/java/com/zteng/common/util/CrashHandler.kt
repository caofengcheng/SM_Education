package com.zteng.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.zteng.common.database.Constants

import com.zteng.common.database.GlobalPage

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import java.util.Locale

/**
 * 项目名称：PoliceTerminus
 * 类描述：app奔溃日志.
 * 创建人：linlingrong
 * 创建时间：2017-09-04 14:00
 */
class CrashHandler
/**
 * 保证只有一个CrashHandler实例
 */
private constructor() : Thread.UncaughtExceptionHandler {
    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null
    // 程序的Context对象
    private var mContext: Context? = null
    // 用来存储设备信息和异常信息
    private val mInfo = HashMap<String, String>()

    // 用于格式化日期,作为日志文件名的一部分
    private val formatter = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA)

    /**
     * 初始化
     * @param context context
     */
    fun init(context: Context) {
        mContext = context
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "error : ", e)
            }

            //            Intent intent = new Intent(mContext, LoginActivity.class);
            //            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //            mContext.startActivity(intent);
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param ex 异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        Log.e("ERROR", ex.message, ex)
        //        WonderMapApplication.getInstance().getSpUtil().setCrashLog(true);// 每次进入应用检查，是否有log，有则上传
        // 使用Toast来显示异常信息
        object : Thread() {
            @SuppressLint("WrongConstant")
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "很抱歉,程序出现异常,正在收集日志，即将退出", Toast.LENGTH_LONG)
                    .show()
                try {
                    val intent = Intent(mContext, Class.forName(GlobalPage.MainActvity))
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    mContext!!.startActivity(intent)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

                Looper.loop()
            }
        }.start()
        // 收集设备参数信息
        collectDeviceInfo(mContext!!)
        // 保存日志文件
        saveCrashInfo2File(ex)
        return true
    }

    /**
     * 收集设备参数信息
     * @param ctx context
     */
    private fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                mInfo["versionName"] = versionName
                mInfo["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occurred when collect package info", e)
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                mInfo[field.name] = field.get(null).toString()
                Log.d(TAG, field.name + " : " + field.get(null))
            } catch (e: Exception) {
                Log.e(TAG, "an error occurred when collect crash info", e)
            }

        }
    }

    /**
     * 保存错误信息到文件中
     * @param ex 异常
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {

        val sb = StringBuilder()
        for ((key, value) in mInfo) {
            sb.append(key).append("=").append(value).append("\n")
        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()

        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = formatter.format(Date())
            val fileName = "Crash-$time-$timestamp.log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path = FileUtil.appSDMainPath + Constants.DIR_CRASH
                val dir = File(path)
                Log.e(TAG, "saveCrashInfo2File: $path")
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                        Log.e(TAG, "an error occurred while make dir...")
                    }
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
            return fileName
        } catch (e: Exception) {
            Log.e(TAG, "an error occurred while writing file...", e)
        }

        return null
    }


    private object InstanceHolder {
        val mInstance = CrashHandler()
    }

    companion object {
        private val TAG = "CrashHandler"

        /**
         * 获取CrashHandler实例 ,单例模式
         */
        val instance: CrashHandler
            get() = InstanceHolder.mInstance
    }
}
