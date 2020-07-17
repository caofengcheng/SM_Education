package com.zteng.common.util

object StringUitl {

    fun replaceString(text: String?, arg: String): String {
        return text!!.replace(arg, "")
    }
}
