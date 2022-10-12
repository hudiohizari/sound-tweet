package id.hizari.soundtweet.ui.tweet

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.model.UploadedFile
import id.hizari.domain.usecase.filestack.PostFileUseCase
import id.hizari.domain.usecase.tweet.PostTweetUseCase
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
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
    private val postFileUseCase: PostFileUseCase,
    private val postTweetUseCase: PostTweetUseCase
) : BaseViewModel() {

    companion object {
        const val RECORDING_STATUS_FIRST_TIME = 0
        const val RECORDING_STATUS_RESUME = 1
        const val RECORDING_STATUS_PAUSE = 2
        const val RECORDING_STATUS_STOP = 3
    }

    val caption = MutableLiveData<String>()
    val recordDuration = MutableLiveData<String>()
    val recordingStatus = MutableLiveData<Int>()

    val uploadedFileResource = MutableLiveData<Resources<UploadedFile?>>()
    val tweetResource = MutableLiveData<Resources<Tweet?>>()

    fun postTweet(context: Context, url: String?) {
        postTweetUseCase(
            context,
            caption.value,
            url,
            ""
        ).onEach {
            tweetResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onClickCancel() {
        navigateBack()
    }

    @Suppress("unused")
    fun View.onClickPost() {
        recordingStatus.postValue(RECORDING_STATUS_STOP)
        postFileUseCase(listener?.getFile()).onEach {
            uploadedFileResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onClickRecord() {
        when (recordingStatus.value) {
            RECORDING_STATUS_FIRST_TIME, RECORDING_STATUS_RESUME -> {
                recordingStatus.postValue(RECORDING_STATUS_PAUSE)
            }
            else -> listener?.requestAudioPermission()
        }
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun requestAudioPermission()
        fun getFile(): File
    }

}