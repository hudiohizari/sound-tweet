package id.hizari.common.util

import android.util.Log
import id.hizari.common.BuildConfig

/**
 * superapp-android - id.hizari.common.util
 *
 * Created by Hudio Hizari on 27/09/22.
 * https://github.com/hudiohizari
 *
 */

object STLog {

    // Getting tag for
    private fun tag(): String {
        return this.javaClass.simpleName
    }

    // Check if build is debug
    private fun ifDebug(work: () -> Unit) {
        if (BuildConfig.DEBUG) work()
    }

    // Get where the log is called
    private fun getWhereLogIsCalled(): String {
        return try {
            // Call through ifDebug
            getStringLocation(Thread.currentThread().stackTrace[8])
        } catch (e: Exception) {
            try {
                // Call without ifDebug
                getStringLocation(Thread.currentThread().stackTrace[4])
            } catch (e: Exception) {
                "Unknown class and line"
            }
        }
    }

    private fun getStringLocation(it: StackTraceElement): String {
        return "${it.className.substringAfterLast(".")}.${it.methodName}(${it.fileName}:${it.lineNumber})"
    }

    fun d(msg: String) {
        ifDebug { Log.d(tag(), "${getWhereLogIsCalled()}: $msg") }
    }

    fun d(msg: String, tr: Throwable) {
        ifDebug { Log.d(tag(), "${getWhereLogIsCalled()}: $msg", tr) }
    }

    fun e(msg: String) {
        ifDebug { Log.e(tag(), "${getWhereLogIsCalled()}: $msg") }
    }

    fun e(msg: String, tr: Throwable) {
        ifDebug { Log.e(tag(), "${getWhereLogIsCalled()}: $msg", tr) }
    }

    fun i(msg: String) {
        ifDebug { Log.i(tag(), "${getWhereLogIsCalled()}: $msg") }
    }

    fun i(msg: String, tr: Throwable) {
        ifDebug { Log.i(tag(), "${getWhereLogIsCalled()}: $msg", tr) }
    }

    fun v(msg: String) {
        ifDebug { Log.v(tag(), "${getWhereLogIsCalled()}: $msg") }
    }

    fun v(msg: String, tr: Throwable) {
        ifDebug { Log.v(tag(), "${getWhereLogIsCalled()}: $msg", tr) }
    }

    fun w(msg: String) {
        ifDebug { Log.w(tag(), "${getWhereLogIsCalled()}: $msg") }
    }

    fun w(msg: String, tr: Throwable) {
        ifDebug { Log.w(tag(), "${getWhereLogIsCalled()}: $msg", tr) }
    }

    fun w(tr: Throwable) {
        ifDebug { Log.w(tag(), tr) }
    }

    fun wtf(msg: String) {
        ifDebug { Log.wtf(tag(), "${getWhereLogIsCalled()}: $msg") }
    }

    fun wtf(msg: String, tr: Throwable) {
        ifDebug { Log.wtf(tag(), "${getWhereLogIsCalled()}: $msg", tr) }
    }

    fun wtf(tr: Throwable) {
        ifDebug { Log.wtf(tag(), tr) }
    }

}