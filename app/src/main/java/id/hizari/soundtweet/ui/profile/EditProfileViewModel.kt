package id.hizari.soundtweet.ui.profile

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hizari.common.extension.showSimpleDialogReturn
import id.hizari.common.extension.toast
import id.hizari.common.util.InputValidator
import id.hizari.common.util.Resources
import id.hizari.domain.model.User
import id.hizari.domain.usecase.user.GetIsLoggedInUserUseCase
import id.hizari.domain.usecase.user.PutEditProfileUseCase
import id.hizari.soundtweet.R
import id.hizari.soundtweet.base.BaseContextViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.soundtweet.ui.home
 *
 * Created by Hudio Hizari on 13/10/2022.
 * https://github.com/hudiohizari
 *
 */

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    app: Application,
    private val getIsLoggedInUserUseCase: GetIsLoggedInUserUseCase,
    private val putEditProfileUseCase: PutEditProfileUseCase
) : BaseContextViewModel(app) {

    val userResource = MutableLiveData<Resources<User?>>()
    val bio = MutableLiveData<String?>()
    val bioError = MutableLiveData<String>()
    val email = MutableLiveData<String?>()
    val emailError = MutableLiveData<String>()
    val name = MutableLiveData<String?>()
    val nameError = MutableLiveData<String>()
    val username = MutableLiveData<String?>()
    val usernameError = MutableLiveData<String>()
    val password = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String>()
    val isButtonEnabled = MutableLiveData(false)

    init {
        getLoggedInUser()
    }

    fun checkButton() {
        val bioOk = InputValidator.checkNotEmpty(
            context.getString(R.string.bio_error),
            bio,
            bioError
        )
        val emailOk = InputValidator.checkEmail(
            context.getString(R.string.email_error),
            email,
            emailError
        )
        val nameOk = InputValidator.checkMinimumLengthError(
            context.getString(R.string.name_error),
            name,
            nameError,
            InputValidator.INPUT_NAME_MIN
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
        val enabled = bioOk && emailOk && nameOk && usernameOk && passwordOk
        isButtonEnabled.postValue(enabled)
    }

    private fun getLoggedInUser() {
        getIsLoggedInUserUseCase().onEach { res ->
            if (res is Resources.Success) res.data?.let {
                bio.postValue(it.bio)
                email.postValue(it.email)
                name.postValue(it.name)
                username.postValue(it.username?.removePrefix("@"))
                password.postValue(it.password)
            }
            userResource.postValue(res)
        }.launchIn(viewModelScope)
    }

    fun View.onNavigationBackClick() {
        val currentUser = userResource.value?.data
        val isNeedToShowDialog =
            bio.value != currentUser?.bio || email.value != currentUser?.email ||
                    name.value != currentUser?.name ||
                    username.value != currentUser?.username?.removePrefix("@") ||
                    password.value != currentUser?.password
        if (isNeedToShowDialog) {
            context.apply {
                showSimpleDialogReturn(
                    getString(R.string.cancel_edit_profile_rationale_caption),
                    getString(R.string.cancel_edit_profile_rationale)
                ).apply {
                    setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                        navigateBack()
                    }
                    setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
                    show()
                }
            }
        } else navigateBack()
    }

    fun View.onClickEdit() {
        context.apply {
            showSimpleDialogReturn(
                getString(R.string.edit_profile_assurance_caption),
                getString(R.string.edit_profile_assurance)
            ).apply {
                setPositiveButton(context.getString(R.string.save)) { _, _ ->
                    putEditProfileUseCase(
                        bio.value,
                        email.value,
                        name.value,
                        username.value,
                        password.value
                    ).onEach { res ->
                        userResource.postValue(res)
                        if (res is Resources.Success) {
                            context.apply {
                                toast(getString(R.string.profile_edited_successfully))
                            }
                            navigateBack()
                        }
                    }.launchIn(viewModelScope)
                }
                setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
                show()
            }
        }
    }

}