package id.hizari.soundtweet.ui.search

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.toast
import id.hizari.common.util.Resources
import id.hizari.domain.model.User
import id.hizari.domain.usecase.user.SearchUserUseCase
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
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    var lastSearch: String? = null

    val isRefreshing = MutableLiveData<Boolean>()
    val isSearchFocused = MutableLiveData<Boolean>()
    val users = MutableLiveData<Resources<MutableList<User>>>()
    val query = MutableLiveData<String>()

    fun searchUser() {
        lastSearch = query.value
        searchUserUseCase(query.value).onEach {
            users.postValue(it)
        }.launchIn(viewModelScope)
    }

    fun onRefresh() {
        query.postValue(lastSearch)
        searchUser()
        isRefreshing.postValue(false)
    }

    @Suppress("unused")
    fun View.onClickCancel() {
        isSearchFocused.postValue(false)
    }

}