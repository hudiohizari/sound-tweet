package id.hizari.soundtweet.ui.profile

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.domain.model.User
import id.hizari.domain.usecase.tweet.GetUserTweetsUseCase
import id.hizari.domain.usecase.tweet.PostLikeTweetUseCase
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.profile
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserTweetsUseCase: GetUserTweetsUseCase,
    private val postLikeTweetUseCase: PostLikeTweetUseCase
): BaseViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    val userResource = MutableLiveData<Resources<User?>>()
    val tweetsResource = MutableLiveData<Resources<MutableList<Tweet>?>>()

    fun getTweets(context: Context, isShowLoading: Boolean = true) {
        getUserTweetsUseCase(
            context,
            userResource.value?.data?.username
        ).onEach { res ->
            if (!isShowLoading && res is Resources.Loading) return@onEach
            tweetsResource.postValue(res)
        }.launchIn(viewModelScope)
    }

    fun postLikeTweet(context: Context, id: Long?) {
        postLikeTweetUseCase(context, id).onEach {
            when (it) {
                is Resources.Success -> getTweets(context, false)
                is Resources.Error -> STLog.e("Error = ${it.throwable?.message}")
                else -> STLog.e("Unhandled resource type = $it")
            }
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onNavigationBackClick() {
        navigateBack()
    }

    fun onRefresh(context: Context) {
        getTweets(context)
        isRefreshing.postValue(false)
    }

}