package id.hizari.soundtweet.ui.navigation

import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.domain.usecase.user.GetIsLoggedInLiveUseCase
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseViewModel
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
    val navigateTo = MutableLiveData<Int>()
    val isLoggedIn = MutableLiveData(false)
    val isShowFab = MutableLiveData(true)

    fun checkIsLoggedIn() = getIsLoggedInLiveUseCase()

    @Suppress("unused")
    fun View.onClickPostTweet() {
        navigateTo.postValue(R.id.tweetNavigation)
    }

}