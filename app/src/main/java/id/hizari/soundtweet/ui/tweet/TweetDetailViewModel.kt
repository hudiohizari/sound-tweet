package id.hizari.soundtweet.ui.tweet

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.isNotNullOrEmpty
import id.hizari.common.extension.toast
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.domain.usecase.tweet.GetTweetUseCase
import id.hizari.domain.usecase.tweet.PostLikeTweetUseCase
import id.hizari.domain.usecase.user.GetIsLoggedInUserUseCase
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.home
 *
 * Created by Hudio Hizari on 01/10/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class TweetDetailViewModel @Inject constructor(
    private val getTweetUseCase: GetTweetUseCase,
    private val postLikeTweetUseCase: PostLikeTweetUseCase,
    private val getLoggedInUserUserUseCase: GetIsLoggedInUserUseCase
) : BaseViewModel() {

    var lastId: Long? = -1

    val isRefreshing = MutableLiveData<Boolean>()
    val tweet = MutableLiveData<Tweet?>()
    val tweetResource = MutableLiveData<Resources<Tweet?>>()

    private fun getTweet(context: Context, id: Long?) {
        lastId = id
        getTweetUseCase(context, id).onEach {
            if (it is Resources.Success) tweet.postValue(it.data)
            tweetResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onNavigationBackClick() {
        navigateBack()
    }

    fun View.onClickReload() {
        getTweet(context, lastId)
    }

    @Suppress("unused")
    fun View.onClickUser() {
        getLoggedInUserUserUseCase().onEach { res ->
            if (res is Resources.Success) {
                tweetResource.value?.data?.user?.let {
                    if (it.username != res.data?.username) {
                        navigate(
                            TweetDetailFragmentDirections
                                .actionTweetDetailFragmentToUserProfileFragment()
                                .setUser(it)
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onClickMedia() {
        if (tweet.value?.postUrl.isNotNullOrEmpty()) {
            listener?.toggleMedia()
        }
    }

    fun View.onClickComment() {
        context.toast("onClickComment")
        navigateBack()
    }

    @Suppress("unused")
    fun View.onClickLike() {
        postLikeTweetUseCase(context, tweetResource.value?.data?.id).onEach {
            when (it) {
                is Resources.Success -> tweetResource.postValue(it)
                else -> STLog.e("Unhandled resource type = $it")
            }
        }.launchIn(viewModelScope)
    }

    fun onRefresh(context: Context) {
        getTweet(context, lastId)
        isRefreshing.postValue(false)
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun toggleMedia()
    }

}