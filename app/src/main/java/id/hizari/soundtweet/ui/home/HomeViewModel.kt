package id.hizari.soundtweet.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.set
import id.hizari.common.util.Resources
import id.hizari.domain.model.Tweet
import id.hizari.domain.usecase.tweet.GetTweetUseCase
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
    private val getTweetUseCase: GetTweetUseCase
): ViewModel() {

    val tweets = MutableLiveData<Resources<MutableList<Tweet>>>()

    init {
        getTweet()
    }

    private fun getTweet() {
        getTweetUseCase().onEach {
            tweets.set(it)
        }.launchIn(viewModelScope)
    }

}