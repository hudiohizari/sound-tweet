package id.hizari.soundtweet.ui.tweet

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.isNotNullOrEmpty
import id.hizari.common.extension.showSimpleDialogReturn
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.model.UploadedFile
import id.hizari.domain.usecase.filestack.PostFileUseCase
import id.hizari.domain.usecase.tweet.PostReplyTweetUseCase
import id.hizari.domain.usecase.tweet.PostTweetUseCase
import id.hizari.soundtweet.R
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
    private val postTweetUseCase: PostTweetUseCase,
    private val postReplyTweetUseCase: PostReplyTweetUseCase
) : BaseViewModel() {

    companion object {
        const val RECORDING_STATUS_FIRST_TIME = 0
        const val RECORDING_STATUS_RESUME = 1
        const val RECORDING_STATUS_PAUSE = 2
        const val RECORDING_STATUS_STOP = 3
    }

    val replyingTweetId = MutableLiveData<Long>()
    val caption = MutableLiveData<String>()
    val recordDuration = MutableLiveData<String>()
    val recordingStatus = MutableLiveData<Int>()

    val transcribing = MutableLiveData<Boolean>()
    val uploadedFileResource = MutableLiveData<Resources<UploadedFile?>>()
    val tweetResource = MutableLiveData<Resources<Tweet?>>()

    fun postFile(context: Context, text: String?) {
        recordingStatus.postValue(RECORDING_STATUS_STOP)
        postFileUseCase(listener?.getFile()).onEach {
            uploadedFileResource.postValue(it)
            if (it is Resources.Success) postTweet(context, it.data?.url, text)
        }.launchIn(viewModelScope)
    }

    private fun postTweet(context: Context, url: String?, text: String?) {
        if (replyingTweetId.value == null){
            postTweetUseCase(
                context,
                caption.value,
                url,
                text
            )
        } else {
            postReplyTweetUseCase(
                context,
                replyingTweetId.value,
                caption.value,
                url,
                text
            )
        }.onEach {
            tweetResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun View.onClickCancel() {
        if (caption.value.isNotNullOrEmpty() || recordingStatus.value != null || recordDuration.value != null) {
            context.apply {
                showSimpleDialogReturn(
                    getString(R.string.cancel_post_tweet_rationale_caption),
                    getString(R.string.cancel_post_tweet_rationale)
                ).apply {
                    setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                        navigateBack()
                    }
                    setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
                    show()
                }
            }
        } else navigateBack()
    }

    @Suppress("unused")
    fun View.onClickPost() {
        listener?.transcribe()
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
        fun transcribe()
    }

}