package com.zteng.common.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtil {


    val nowDate: Date
        get() = Calendar.getInstance().time

    /**
     * 根据日期字符串判断当月第几周
     *
     * @return
     * @throws Exception
     */
    //第几周
    val week: Int
        @Throws(Exception::class)
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val date = sdf.parse(getNowDate(DatePattern.ALL_TIME))
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.WEEK_OF_MONTH)
        }

    /**
     * 返回当前月份
     *
     * @return
     */
    val nowMonth: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.MONTH) + 1
        }

    /**
     * 获取当前月号
     *
     * @return
     */
    val nowDay: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.DATE)
        }

    /**
     * 获取当前年份
     *
     * @return
     */
    val nowYear: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.YEAR)
        }

    /**
     * 获取本月份的天数
     *
     * @return
     */
    val nowDaysOfMonth: Int
        get() {
            val calendar = Calendar.getInstance()
            return daysOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.DATE) + 1)
        }

    /**
     * 获取周一到周日
     *
     * @return
     */
    val weekDay: Array<String?>
        get() {
            val dateFormat = SimpleDateFormat("dd")
            val calendar = Calendar.getInstance()
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_WEEK, -1)
            }
            val dates = arrayOfNulls<Date>(7)
            val dateS = arrayOfNulls<String>(7)
            for (i in 0..6) {
                dateS[i] = dateFormat.format(calendar.time)
                calendar.add(Calendar.DATE, 1)
            }
            return dateS
        }


    //获得系统时间加10分钟后的日期
    val afterTen: String
        get() {
            val now = Calendar.getInstance()
            now.add(Calendar.MINUTE, 10)
            val sdf = SimpleDateFormat("yyyy-MM-dd-HH-mm")
            return sdf.format(now.timeInMillis)
        }

    //获得系统时间加20分钟后的日期
    val afterTwenty: String
        get() {
            val now = Calendar.getInstance()
            now.add(Calendar.MINUTE, 20)
            val sdf = SimpleDateFormat("yyyy-MM-dd-HH-mm")
            return sdf.format(now.timeInMillis)
        }

    //获得系统时间加半小时后的小时
    val hour: String
        get() {
            val now = Calendar.getInstance()
            now.add(Calendar.MINUTE, 30)
            val sdf = SimpleDateFormat("HH")
            return sdf.format(now.timeInMillis)
        }

    //获得系统时间加半小时后的小时
    val minutes: String
        get() {
            val now = Calendar.getInstance()
            now.add(Calendar.MINUTE, 30)
            val sdf = SimpleDateFormat("mm")
            return sdf.format(now.timeInMillis)
        }

    private val msFormat = SimpleDateFormat("mm:ss")

    /**
     * 获取当前时间
     *
     * @return 返回当前时间，格式2017-05-04	10:54:21
     */
    fun getNowDate(pattern: DatePattern): String? {
        var dateString: String? = null
        val calendar = Calendar.getInstance()
        val dateNow = calendar.time
        val sdf = SimpleDateFormat(pattern.value, Locale.CHINA)
        dateString = sdf.format(dateNow)
        return dateString
    }

    /**
     * 将一个日期字符串转换成Data对象
     *
     * @param dateString 日期字符串
     * @param pattern    转换格式
     * @return 返回转换后的日期对象
     */
    fun stringToDate(dateString: String, pattern: DatePattern): Date? {
        var date: Date? = null
        val sdf = SimpleDateFormat(pattern.value, Locale.CHINA)
        try {
            date = sdf.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 将date转换成字符串
     *
     * @param date    日期
     * @param pattern 日期的目标格式
     * @return
     */
    fun dateToString(date: Date, pattern: String): String {
        var string = ""
        val sdf = SimpleDateFormat(pattern, Locale.CHINA)
        string = sdf.format(date)
        return string
    }

    /**
     * 获取指定日期周几
     *
     * @param date 指定日期
     * @return 返回值为： "周日", "周一", "周二", "周三", "周四", "周五", "周六"
     */
    fun getWeekOfDate(date: Date): String {
        val weekDays = arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        val calendar = Calendar.getInstance()
        calendar.time = date
        var week = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (week < 0)
            week = 0
        return weekDays[week]
    }

    /**
     * 获取指定日期对应周几的序列
     *
     * @param date 指定日期
     * @return 周一：1	周二：2	周三：3	周四：4	周五：5	周六：6	周日：7
     */
    fun getIndexWeekOfDate(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        var index = calendar.get(Calendar.DAY_OF_WEEK)
        return if (index == 1) {
            7
        } else {
            --index
        }
    }


    // 获取本周开始时间
    fun startWeek(date: Date): Int {
        // 使用Calendar类进行时间的计算
        val sdf = SimpleDateFormat("dd")
        val cal = Calendar.getInstance()
        cal.time = date
        // 获得当前日期是一个星期的第几天
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会计算到下一周去。
        // dayWeek值（1、2、3...）对应周日，周一，周二...
        var dayWeek = cal.get(Calendar.DAY_OF_WEEK)
        if (dayWeek == 1) {
            dayWeek = 7
        } else {
            dayWeek -= 1
        }
        println("前时间是本周的第几天:$dayWeek") // 输出要当前时间是本周的第几天
        return dayWeek
    }

    /**
     * 返回明天
     *
     * @return
     */
    fun tomorrow(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val now = Calendar.getInstance()
        now.time = Date()
        now.add(Calendar.DAY_OF_MONTH, 1)
        return sdf.format(now.time)
    }

    fun Year(): Array<String> {
        return arrayOf(
            "1999",
            "2000",
            "2001",
            "2002",
            "2003",
            "2004",
            "2005",
            "2006",
            "2007",
            "2008",
            "2009",
            "2010",
            "2011",
            "2012",
            "2013",
            "2014",
            "2015",
            "2016",
            "2017",
            "2018",
            "2019",
            "2020",
            "2021",
            "2022"
        )
    }


    fun Month(): Array<String> {
        return arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    }

    fun Day28(): Array<String?> {
        return arrayOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28"
        )
    }

    fun Day29(): Array<String?> {
        return arrayOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29"
        )
    }

    fun Day30(): Array<String?> {
        return arrayOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30"
        )
    }

    fun Day31(): Array<String?> {
        return arrayOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25",
            "26",
            "27",
            "28",
            "29",
            "30",
            "31"
        )
    }

    fun thisYear(): Int {
        val year = Year()
        val nowYear = nowYear
        for (i in year.indices) {
            if (year[i] == nowYear.toString()) {
                return i
            }
        }
        return 0
    }

    fun thisMonth(): Int {
        val month = Month()
        val nowMonth = nowMonth
        for (i in month.indices) {
            if (month[i] == nowMonth.toString()) {
                return i
            }
        }
        return 0
    }

    fun thisDay(): Int {
        var month = arrayOfNulls<String>(0)
        val day = nowDay.toString()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, nowYear)
        calendar.set(Calendar.MONTH, nowMonth - 1)

        val days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        when (days) {
            28 -> month = Day28()
            29 -> month = Day29()
            30 -> month = Day30()
            31 -> month = Day31()
        }
        for (i in month.indices) {
            if (month[i] == day) {
                return i
            }
        }
        return 0
    }

    fun thisDayEntries(): Array<String?> {
        var day = arrayOfNulls<String>(0)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, nowYear)
        calendar.set(Calendar.MONTH, nowMonth - 1)

        val days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        when (days) {
            28 -> day = Day28()
            29 -> day = Day29()
            30 -> day = Day30()
            31 -> day = Day31()
        }
        return day
    }

    /**
     * 获取指定月份的天数
     *
     * @param year  年份
     * @param month 月份
     * @return 对应天数
     */
    fun daysOfMonth(year: Int, month: Int): Int {
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> return 31
            4, 6, 9, 11 -> return 30
            2 -> return if (year % 4 == 0 && year % 100 == 0 || year % 400 != 0) {
                29
            } else {
                28
            }
            else -> return -1
        }
    }

    /**
     * 枚举日期格式
     */
    enum class DatePattern {

        /**
         * 格式："yyyy-MM-dd HH:mm:ss"
         */
        ALL_TIME {
            override val value: String
                get() = "yyyy-MM-dd HH:mm:ss"
        },
        /**
         * 格式："yyyy-MM"
         */
        ONLY_MONTH {
            override val value: String
                get() = "yyyy-MM"
        },
        /**
         * 格式："yyyy-MM-dd"
         */
        ONLY_DAY {
            override val value: String
                get() = "yyyy-MM-dd"
        },
        /**
         * 格式："yyyy-MM-dd HH"
         */
        ONLY_HOUR {
            override val value: String
                get() = "yyyy-MM-dd HH"
        },
        /**
         * 格式："yyyy-MM-dd HH:mm"
         */
        ONLY_MINUTE {
            override val value: String
                get() = "yyyy-MM-dd HH:mm"
        },
        /**
         * 格式："MM-dd"
         */
        ONLY_MONTH_DAY {
            override val value: String
                get() = "MM-dd"
        },
        /**
         * 格式："MM-dd HH:mm"
         */
        ONLY_MONTH_SEC {
            override val value: String
                get() = "MM-dd HH:mm"
        },
        /**
         * 格式："HH:mm:ss"
         */
        ONLY_TIME {
            override val value: String
                get() = "HH:mm:ss"
        },
        /**
         * 格式："dd"
         */
        ONLY_DD {
            override val value: String
                get() = "dd"

        },
        /**
         * 格式："HH:mm"
         */
        ONLY_HOUR_MINUTE {
            override val value: String
                get() = "HH:mm"
        };


        abstract val value: String
    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    fun timeParse(duration: Long): String {
        var time = ""
        if (duration > 1000) {
            time = timeParseMinute(duration)
        } else {
            val minute = duration / 60000
            val seconds = duration % 60000
            val second = Math.round(seconds.toFloat() / 1000).toLong()
            if (minute < 10) {
                time += "0"
            }
            time += "$minute:"
            if (second < 10) {
                time += "0"
            }
            time += second
        }
        return time
    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    fun timeParseMinute(duration: Long): String {
        try {
            return msFormat.format(duration)
        } catch (e: Exception) {
            e.printStackTrace()
            return "0:00"
        }

    }

    /**
     * 判断两个时间戳相差多少秒
     *
     * @param d
     * @return
     */
    fun dateDiffer(d: Long): Int {
        try {
            val l1 =
                java.lang.Long.parseLong(System.currentTimeMillis().toString().substring(0, 10))
            val interval = l1 - d
            return Math.abs(interval).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }

    }

    /**
     * 计算两个时间间隔
     *
     * @param sTime
     * @param eTime
     * @return
     */
    fun cdTime(sTime: Long, eTime: Long): String {
        val diff = eTime - sTime
        return if (diff > 1000) (diff / 1000).toString() + "秒" else diff.toString() + "毫秒"
    }

    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }

    /**
     * 根据年月获得 这个月总共有几天
     *
     * @param year
     * @param month
     * @return
     */
    fun getDay(year: Int, month: Int): Int {
        var day = 30
        var flag = false
        when (year % 4) {
            0 -> flag = true
            else -> flag = false
        }
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> day = 31
            2 -> day = if (flag) 29 else 28
            else -> day = 30
        }
        return day
    }
}

