package id.hizari.common.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Sound Tweet - id.hizari.soundtweet.extention
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun <T> MutableLiveData<T>.observeDebounce(
    owner: LifecycleOwner,
    observer: Observer<T>
) {
    var value: T
    observe(owner) { t ->
        value = t
        CoroutineScope(Dispatchers.Main).launch {
            delay(250)
            if (value != t) return@launch
            observer.onChanged(t)
        }
    }
}