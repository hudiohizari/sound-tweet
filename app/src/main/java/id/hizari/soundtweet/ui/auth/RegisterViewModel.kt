package id.hizari.soundtweet.ui.auth

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.util.InputValidator
import id.hizari.common.util.InputValidator.INPUT_NAME_MIN
import id.hizari.common.util.Resources
import id.hizari.common.util.STLog
import id.hizari.domain.model.User
import id.hizari.domain.usecase.user.PostRegisterUseCase
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
class RegisterViewModel @Inject constructor(
    app: Application,
    private val postRegisterUseCase: PostRegisterUseCase
) : BaseContextViewModel(app) {

    val email = MutableLiveData<String?>()
    val emailError = MutableLiveData<String>()
    val name = MutableLiveData<String?>()
    val nameError = MutableLiveData<String>()
    val username = MutableLiveData<String?>()
    val usernameError = MutableLiveData<String>()
    val password = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String>()
    val isButtonEnabled = MutableLiveData(false)

    val userResource = MutableLiveData<Resources<User?>>()

    fun checkButton() {
        val emailOk = InputValidator.checkEmail(
            context.getString(R.string.email_error),
            email,
            emailError
        )
        val nameOk = InputValidator.checkMinimumLengthError(
            context.getString(R.string.name_error),
            name,
            nameError,
            INPUT_NAME_MIN
        )
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
        val enabled = emailOk && nameOk && usernameOk && passwordOk
        isButtonEnabled.postValue(enabled)
    }

    @Suppress("unused")
    fun View.onClickRegister() {
        postRegisterUseCase(
            email.value,
            name.value,
            username.value,
            password.value
        ).onEach {
            when (it) {
                is Resources.Success -> {
                    email.postValue(null)
                    name.postValue(null)
                    username.postValue(null)
                    password.postValue(null)
                }
                else -> STLog.e("Unhandled resource type")
            }
            userResource.postValue(it)
        }.launchIn(viewModelScope)
    }

}