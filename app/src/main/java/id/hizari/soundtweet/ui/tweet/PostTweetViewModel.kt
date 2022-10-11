package id.hizari.soundtweet.ui.tweet

import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.toast
import id.hizari.soundtweet.base.BaseViewModel
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.home
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class PostTweetViewModel @Inject constructor(
) : BaseViewModel() {

    companion object {
        const val LISTENER_REQUEST_PERMISSION = 0
    }

    val listenerToFragment = MutableLiveData<Int>()
    val caption = MutableLiveData<String>()
    val recordDuration = MutableLiveData<String>()
    val isRecording = MutableLiveData(false)

    @Suppress("unused")
    fun View.onClickCancel() {
        navigateBack()
    }

    fun View.onClickPost() {
        context.toast("onClickPost")
    }

    @Suppress("unused")
    fun View.onClickRecord() {
        if (isRecording.value == true) {
            isRecording.postValue(false)
        } else listenerToFragment.postValue(LISTENER_REQUEST_PERMISSION)
    }

}