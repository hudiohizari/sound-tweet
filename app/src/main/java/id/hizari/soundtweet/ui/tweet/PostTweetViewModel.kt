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

    val caption = MutableLiveData<String>()
    val recordDuration = MutableLiveData<String>()
    val isRecording = MutableLiveData(false)

    @Suppress("unused")
    fun View.onClickCancel() {
        navigateBack()
    }

    @Suppress("unused")
    fun View.onClickPost() {

    }

    @Suppress("unused")
    fun View.onClickRecord() {
        isRecording.postValue(!(isRecording.value ?: false))
    }

}