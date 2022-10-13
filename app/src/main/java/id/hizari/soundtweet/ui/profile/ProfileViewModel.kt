package id.hizari.soundtweet.ui.profile

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.showSimpleDialogReturn
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.Tweet
import id.hizari.domain.model.User
import id.hizari.domain.usecase.tweet.GetOwnTweetsUseCase
import id.hizari.domain.usecase.tweet.PostLikeTweetUseCase
import id.hizari.domain.usecase.user.GetLoggedInUserLiveUseCase
import id.hizari.domain.usecase.user.PostLogoutUseCase
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.profile
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val postLogoutUseCase: PostLogoutUseCase,
    private val getLoggedInUserLiveUseCase: GetLoggedInUserLiveUseCase,
    private val getOwnTweetsUseCase: GetOwnTweetsUseCase,
    private val postLikeTweetUseCase: PostLikeTweetUseCase
): BaseViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    val userResource = MutableLiveData<Resources<User?>>()
    val tweetsResource = MutableLiveData<Resources<MutableList<Tweet>?>>()

    fun getLocalUser() {
        getLoggedInUserLiveUseCase().onEach {
            userResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun getTweets(context: Context, isShowLoading: Boolean = true) {
        getOwnTweetsUseCase(context).onEach { res ->
            if (!isShowLoading && res is Resources.Loading) return@onEach
            tweetsResource.postValue(res)
        }.launchIn(viewModelScope)
    }

    fun postLikeTweet(context: Context, id: Long?) {
        postLikeTweetUseCase(context, id).onEach {
            when (it) {
                is Resources.Success -> getTweets(context, false)
                else -> STLog.e("Unhandled resource type = $it")
            }
        }.launchIn(viewModelScope)
    }

    fun View.onClickLogout() {
        context.apply {
            showSimpleDialogReturn(
                getString(R.string.logout_rationale_caption),
                getString(R.string.logout_rationale)
            ).apply {
                setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                    postLogoutUseCase().onEach {
                        when (it) {
                            is Resources.Loading -> STLog.d("Loading")
                            is Resources.Success -> STLog.d("Success")
                            is Resources.Error -> STLog.e("Error = ${it.throwable?.message}")
                            else -> STLog.e("Unhandled resource $it")
                        }
                    }.launchIn(viewModelScope)
                }
                setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
                show()
            }
        }
    }

    @Suppress("unused")
    fun View.onClickEditProfile() {
        navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
    }

    fun onRefresh(context: Context) {
        getTweets(context)
        isRefreshing.postValue(false)
    }

}