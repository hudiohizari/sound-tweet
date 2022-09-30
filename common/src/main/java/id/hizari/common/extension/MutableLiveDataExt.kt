package id.hizari.common.extension

import androidx.lifecycle.MutableLiveData
import id.hizari.common.util.Resources

/**
 * Sound Tweet - id.hizari.soundtweet.extention
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun <T> MutableLiveData<Resources<T>>.set(data: Resources<T>) {
    this.postValue(data)
}