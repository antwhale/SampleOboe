package com.boo.sample.base.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.Settings
import org.greenrobot.eventbus.EventBus
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

object CommonExt {

    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    fun getQueryParameter(uri: Uri, name: String, withDecode: Boolean = false): String {
        return uri.runCatching {
            getQueryParameter(name)?.let {
                if (withDecode) URLDecoder.decode(it, "UTF-8") else it
            }
        }.getOrDefault("") ?: ""
    }

    fun isToday(dateString: String): Boolean {
        val target = SimpleDateFormat("yyyyMMdd", Locale.KOREA).runCatching {
            parse(dateString)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                }
            }
        }.getOrNull()

        return target?.let {
            val targetYear = target.get(Calendar.YEAR)
            val targetDay = target.get(Calendar.DAY_OF_YEAR)

            val now = Calendar.getInstance()
            val nowYear = now.get(Calendar.YEAR)
            val nowDay = now.get(Calendar.DAY_OF_YEAR)

            nowYear == targetYear && nowDay == targetDay
        } ?: false
    }

    fun <T> T.postEvent() {
        EventBus.getDefault().post(this)
    }

    fun Boolean?.toInt(): Int =
            if (this == true) 1
            else 0
}