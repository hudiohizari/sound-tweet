package id.hizari.soundtweet.ui.search

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.isNotNullOrEmpty
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.User
import id.hizari.domain.usecase.user.GetSearchUserUseCase
import id.hizari.domain.usecase.user.PostFollowUserUseCase
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
class SearchViewModel @Inject constructor(
    private val getSearchUserUseCase: GetSearchUserUseCase,
    private val postFollowUserUseCase: PostFollowUserUseCase
) : BaseViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    val isSearchFocused = MutableLiveData<Boolean>()
    val query = MutableLiveData<String>()
    val usersResource = MutableLiveData<Resources<MutableList<User>?>>()

    fun searchUser(isShowLoading: Boolean = true) {
        getSearchUserUseCase(query.value).onEach { res ->
            if (!isShowLoading && res is Resources.Loading) return@onEach
            usersResource.postValue(res)
        }.launchIn(viewModelScope)
    }

    fun followUser(userId: Long?) {
        postFollowUserUseCase(userId).onEach {when (it) {
            is Resources.Success -> searchUser(false)
            else -> STLog.e("Unhandled resource type = $it")
        }
        }.launchIn(viewModelScope)
    }

    fun onRefresh() {
        if (query.value.isNotNullOrEmpty()) searchUser()
        isRefreshing.postValue(false)
    }

    @Suppress("unused")
    fun View.onClickCancel() {
        isSearchFocused.postValue(false)
    }

}