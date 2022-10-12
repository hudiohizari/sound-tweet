package id.hizari.soundtweet.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.Resources
import id.hizari.domain.model.Notification
import id.hizari.domain.usecase.notification.GetNotificationsUseCase
import id.hizari.soundtweet.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.notification
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
): BaseViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    val notificationResource = MutableLiveData<Resources<MutableList<Notification>?>>()

    fun getNotifications() {
        getNotificationsUseCase().onEach {
            notificationResource.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun onRefresh() {
        getNotifications()
        isRefreshing.postValue(false)
    }

}