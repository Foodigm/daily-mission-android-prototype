package com.melmy.melmyprototype.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import com.melmy.melmyprototype.BuildConfig
import com.melmy.melmyprototype.R
import java.util.*

fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}


fun isToday(calendar: Calendar): Boolean {
    val today = Calendar.getInstance()
    return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
}

fun secondsToStringFormat(timeSeconds: Int): String {
    var hour = timeSeconds / 3600
    var minute = (timeSeconds % 3600) / 60
    var second = (timeSeconds % 60)
    var str1 = hour.toString()
    var str2 = minute.toString()
    var str3 = second.toString()
    if (str1.length < 2) str1 = "0$str1"
    if (str2.length < 2) str2 = "0$str2"
    if (str3.length < 2) str3 = "0$str3"
    return "$str1:$str2:$str3"
}

fun makeInquireReport(context: Context): String {
    val report: String = context.getString(R.string.report_body, getAndroidVersion(), getAppVersion())
    return "mailto:sgc109@gmail.com?cc=xema027@gmail.com&body=" + Uri.encode(report)
}

fun getAndroidVersion(): String {
    val release = Build.VERSION.RELEASE
    val sdkVersion = Build.VERSION.SDK_INT
    return sdkVersion.toString() + " (" + release + ")"
}

fun getAppVersion(): String {
    return BuildConfig.VERSION_NAME
}

/* 마켓 버전이랑 디바이스 버전 비교해서 업데이트 필요할경우 true 리턴 */
fun needUpdate(deviceVersion: String, marketVersion: String): Boolean {
    try {
        val val1 = deviceVersion.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val val2 = marketVersion.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var i = 0
        while (i < val1.size && i < val2.size && val1[i] == val2[i]) {
            i++
        }
        if (i < val1.size && i < val2.size) {
            val diff = Integer.valueOf(val1[i]).compareTo(Integer.valueOf(val2[i]))
            return Integer.signum(diff) == -1
        }
        return Integer.signum(val1.size - val2.size) == -1
    } catch (e: Exception) {
        return false
    }

}