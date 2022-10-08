package id.hizari.soundtweet.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.usecase.tweet.GetTweetsUseCase
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
    private val getTweetsUseCase: GetTweetsUseCase
): ViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    val tweets = MutableLiveData<Resources<MutableList<Tweet>?>>()

    fun getTweets(context: Context) {
        getTweetsUseCase(context).onEach {
            tweets.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun onRefresh(context: Context) {
        getTweets(context)
        isRefreshing.postValue(false)
    }

}