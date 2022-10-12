package id.hizari.soundtweet.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.domain.usecase.tweet.GetHomeTweetsUseCase
import id.hizari.domain.usecase.tweet.PostLikeTweetUseCase
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.home
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeTweetsUseCase: GetHomeTweetsUseCase,
    private val postLikeTweetUseCase: PostLikeTweetUseCase
): BaseViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    val tweetsResource = MutableLiveData<Resources<MutableList<Tweet>?>>()

    fun getTweets(context: Context) {
        getHomeTweetsUseCase(context).onEach {
            tweetsResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun postLikeTweet(context: Context, id: Long?) {
        postLikeTweetUseCase(context, id).onEach {
            when (it) {
                is Resources.Success -> getTweets(context)
                else -> STLog.e("Unhandled resource type = $it")
            }
        }.launchIn(viewModelScope)
    }

    fun onRefresh(context: Context) {
        getTweets(context)
        isRefreshing.postValue(false)
    }

}