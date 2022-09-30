package id.hizari.common.util

import android.content.Context
import android.util.Log
import id.hizari.common.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Sound Tweet - id.hizari.common.util
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

object DateUtil {

    private const val SERVER_DATE_TIME = "yyyy-MM-dd HH:mm:ss"

    private const val PRINT_DATE = "dd/MM/yyyy"


    private fun changeDateFormat(
        date: String?,
        oldFormat: String = SERVER_DATE_TIME,
        newFormat: String = PRINT_DATE
    ): String{
        val sdf = SimpleDateFormat(oldFormat, Locale("id", "ID"))
        return try {
            val d = sdf.parse(date ?: "") ?: ""
            sdf.timeZone = TimeZone.getTimeZone("GMT+7")
            sdf.applyPattern(newFormat)
            sdf.format(d)
        } catch (e: ParseException) {
            STLog.e("Error = ${e.message}")
            date ?: "Wrong format"
        }
    }

    fun getTimeAgo(
        context: Context,
        date: String?
    ): String {
        return try {
            val format = SimpleDateFormat(SERVER_DATE_TIME, Locale("id", "ID"))
            if (date != null) {
                val past = format.parse(date)
                if (past != null) {
                    val now = Date()
                    val seconds = TimeUnit.MILLISECONDS
                        .toSeconds(now.time - past.time).toInt()
                    val minutes = TimeUnit.MILLISECONDS
                        .toMinutes(now.time - past.time).toInt()
                    val hours = TimeUnit.MILLISECONDS
                        .toHours(now.time - past.time).toInt()
                    when {
                        seconds < 60 -> context.getString(R.string.second_ago, seconds)
                        minutes < 60 -> context.getString(R.string.minute_ago, minutes)
                        hours < 24 -> context.getString(R.string.hour_ago, hours)
                        else -> changeDateFormat(date)
                    }
                } else context.getString(R.string.wrong_format)
            } else context.getString(R.string.wrong_format)
        } catch (e: Exception) {
            STLog.e("Error = ${e.message}")
            context.getString(R.string.wrong_format)
        }
    }

}