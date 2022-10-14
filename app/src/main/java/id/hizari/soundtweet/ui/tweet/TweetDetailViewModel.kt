package id.hizari.soundtweet.ui.tweet

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.showPopupMenu
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.domain.usecase.tweet.GetTweetUseCase
import id.hizari.domain.usecase.tweet.PostLikeTweetUseCase
import id.hizari.domain.usecase.user.GetIsLoggedInUserUseCase
import id.hizari.soundtweet.R
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

    val isRefreshing = MutableLiveData<Boolean>()
    val tweet = MutableLiveData<Tweet?>()
    val tweetResource = MutableLiveData<Resources<Tweet?>>()

    private var lastId: Long? = null

    fun getTweet(context: Context, id: Long?) {
        lastId = id
        getTweetUseCase(context, id).onEach {
            if (it is Resources.Success) tweet.postValue(it.data)
            tweetResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun postLikeTweet(context: Context, id: Long?) {
        postLikeTweetUseCase(context, id).onEach {
            when (it) {
                is Resources.Success -> getTweet(context, lastId)
                is Resources.Error -> STLog.e("Error = ${it.throwable?.message}")
                else -> STLog.e("Unhandled resource type = $it")
            }
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onNavigationBackClick() {
        navigateBack()
    }

    fun View.onClickReload() {
        getTweet(context, lastId)
    }

    fun View.onClickTweetMenu() {
        showPopupMenu(R.menu.tweet_list_item_menu) {
            when (it) {
                R.id.menuEdit -> tweet.value?.let { tweet ->
                    listener?.editCaption(tweet)
                } ?: STLog.e("tweetResource.data is null")
                else -> STLog.e("Unhandled menu = $it")
            }
        }
    }

    @Suppress("unused")
    fun View.onClickUser() {
        getLoggedInUserUserUseCase().onEach { res ->
            if (res is Resources.Success) {
                tweet.value?.user?.let {
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
        tweet.value?.let {
            listener?.toggleMedia(it)
        } ?: STLog.e("tweetResource.data is null")
    }

    @Suppress("unused")
    fun View.onClickComment() {
        navigate(
            TweetDetailFragmentDirections
                .actionTweetDetailFragmentToPostTweetFragment()
                    .setTweetId(tweet.value?.id ?: -1)
        )
    }

    fun View.onClickLike() {
        postLikeTweet(context, lastId)
    }

    @Suppress("unused")
    fun View.onClickSeeAsText() {
        tweet.value?.let {
            listener?.openAsText(it)
        } ?: STLog.e("tweetResource.data is null")
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
        fun toggleMedia(tweet: Tweet)
        fun editCaption(tweet: Tweet)
        fun openAsText(tweet: Tweet)
    }

}