package id.hizari.soundtweet.ui.tweet

import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
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

        const val RECORDING_STATUS_FIRST_TIME = 0
        const val RECORDING_STATUS_RESUME = 1
        const val RECORDING_STATUS_PAUSE = 2
        const val RECORDING_STATUS_STOP = 3
    }

    val listenerToFragment = MutableLiveData<Int>()
    val caption = MutableLiveData<String>()
    val recordDuration = MutableLiveData<String>()
    val recordingStatus = MutableLiveData<Int>()

    @Suppress("unused")
    fun View.onClickCancel() {
        navigateBack()
    }

    @Suppress("unused")
    fun View.onClickPost() {
        recordingStatus.postValue(RECORDING_STATUS_STOP)
    }

    @Suppress("unused")
    fun View.onClickRecord() {
        when (recordingStatus.value) {
            RECORDING_STATUS_FIRST_TIME, RECORDING_STATUS_RESUME -> {
                recordingStatus.postValue(RECORDING_STATUS_PAUSE)
            }
            else -> listenerToFragment.postValue(LISTENER_REQUEST_PERMISSION)
        }
    }

}