package com.zteng.common.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast

import com.zteng.common.base.BaseApplication

import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object Tools {

    var baseApplication = BaseApplication

    /**
     * 气泡提示
     *
     * @param text 文本内容
     */
    fun displayToast(text: String) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        val toast = Toast.makeText(
            baseApplication._context,
            text,
            Toast.LENGTH_SHORT
        )
        val textView = toast.view.findViewById<TextView>(android.R.id.message)
        textView.setTextColor(Color.WHITE)
        textView.textSize = 20f
        toast.show()
    }

    /**
     * 获取机器序列号
     *
     * @param c Context
     * @return 机器序列号
     */
    @SuppressLint("HardwareIds")
    fun getMachineCode(c: Context): String {
        val tm = c.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                c,
                Manifest.permission.READ_PHONE_STATE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            return ""
        }
        val tmDevice: String
        val tmSerial: String
        val androidId: String
        if (tm == null) {
            return ""
        }
        tmDevice = "" + tm.deviceId
        tmSerial = "" + tm.simSerialNumber
        androidId = "" + android.provider.Settings.Secure.getString(
            c.contentResolver,
            android.provider.Settings.Secure.ANDROID_ID
        )
        val deviceUuid = UUID(
            androidId.hashCode().toLong(),
            tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong()
        )
        return deviceUuid.toString()
    }

    fun dp2px(dp: Int): Int {
        return (dp * baseApplication.getContext().getResources().getDisplayMetrics().density) as Int
    }

    fun px2dp(px: Int): Int {
        return (px / baseApplication.getContext().getResources().getDisplayMetrics().density) as Int
    }

    @JvmOverloads
    fun getStringWithDefault(text: String, defaultValue: String = "--"): String {
        return if (TextUtils.isEmpty(text)) defaultValue else text
    }

    @JvmOverloads
    fun formatDate(date: Date, pattern: String = "yyyy-MM-dd"): String {
        try {
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.CHINA)
            return simpleDateFormat.format(date)
        } catch (e: Exception) {
            return ""
        }

    }

    fun formatNumber(number: Long, pattern: String): String {
        val decimalFormat = DecimalFormat(pattern)
        return decimalFormat.format(number)
    }

    @JvmOverloads
    fun changeDateToTimeMillis(date: String, format: String = "yyyy-MM-dd"): Long {
        try {
            val simpleDateFormat = SimpleDateFormat(format, Locale.CHINA)
            val date1 = simpleDateFormat.parse(date)
            return date1.time
        } catch (e: ParseException) {
            return 0
        }

    }

    /**
     * 字符串转为时间
     *
     * @param date   时间
     * @param format 字符串
     * @return
     */
    fun string2Date(date: String?, format: String?): Date? {
        if (date == null || format == null) {
            return null
        }
        try {
            val simpleDateFormat = SimpleDateFormat(format, Locale.CHINA)
            return simpleDateFormat.parse(date)
        } catch (e: ParseException) {
            return null
        }

    }

    fun timeBetweenDates(date1: Date, date2: Date): Date {
        return Date(date1.time - date2.time)
    }


}
