package id.hizari.common.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import id.hizari.common.R

/**
 * Sound Tweet - id.hizari.common.util
 *
 * Created by Hudio Hizari on 10/10/2022.
 * https://github.com/hudiohizari
 *
 */

class Timer(listener: Listener) {

    interface Listener {
        fun onTimerTick(duration: String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    private var duration = 0L
    private var delay = 100L

    init {
        runnable = Runnable {
            duration += delay
            handler.postDelayed(runnable, delay)
            listener.onTimerTick(format())
        }
    }

    fun start() {
        handler.postDelayed(runnable, delay)
    }

    fun pause() {
        handler.removeCallbacks(runnable)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }

    private fun format(): String {
        val seconds = (duration / 1000) % 60
        val minutes = (duration / (1000 * 60)) % 60
        val hours = (duration / (1000 * 60))

        return if (hours > 0) {
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        } else "%02d:%02d".format(minutes, seconds)
    }

}