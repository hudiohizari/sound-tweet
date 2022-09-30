package id.hizari.soundtweet.ui.navigation

import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.toast
import id.hizari.common.util.STLog
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
class NavigationViewModel @Inject constructor(): BaseViewModel() {

    val login = MutableLiveData(false)

    @Suppress("unused")
    fun View.onClickPostTweet() {
        STLog.d("onClickPostTweet")
        context.toast("onClickPostTweet")
    }

}