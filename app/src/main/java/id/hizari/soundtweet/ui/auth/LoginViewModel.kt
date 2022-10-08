package id.hizari.soundtweet.ui.auth

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.InputValidator
import id.hizari.common.util.Resources
import id.hizari.domain.model.User
import id.hizari.domain.usecase.user.PostLoginUseCase
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseContextViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.auth
 *
 * Created by Hudio Hizari on 30/09/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    private val postLoginUseCase: PostLoginUseCase
) : BaseContextViewModel(app) {

    val username = MutableLiveData<String?>()
    val usernameError = MutableLiveData<String>()
    val password = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String>()
    val isButtonEnabled = MutableLiveData(false)

    val login = MutableLiveData<Resources<User?>>()

    fun checkButton() {
        val usernameOk = InputValidator.checkNotEmpty(
            context.getString(R.string.username_error),
            username,
            usernameError
        )
        val passwordOk = InputValidator.checkNotEmpty(
            context.getString(R.string.password_error),
            password,
            passwordError
        )
        val enabled = usernameOk && passwordOk
        isButtonEnabled.postValue(enabled)
    }

    @Suppress("unused")
    fun View.onClickLogin() {
        postLoginUseCase(username.value, password.value).onEach {
            login.postValue(it)
        }.launchIn(viewModelScope)
    }

}