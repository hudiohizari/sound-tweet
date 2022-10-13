package id.hizari.soundtweet.ui.navigation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.Resources
import id.hizari.domain.usecase.user.GetIsLoggedInLiveUseCase
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.navigation
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val getIsLoggedInLiveUseCase: GetIsLoggedInLiveUseCase
): BaseViewModel() {

    val isLoggedInResource = MutableLiveData<Resources<Boolean?>>()
    val navigateTo = MutableLiveData<Int>()
    val isShowFab = MutableLiveData(true)

    fun checkIsLoggedIn() {
        getIsLoggedInLiveUseCase().onEach {
            isLoggedInResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    @Suppress("unused")
    fun View.onClickPostTweet() {
        navigateTo.postValue(R.id.postTweetFragment)
    }

}