package id.hizari.common.util

import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

object InputValidator {

    const val INPUT_PASSWORD_MIN = 8
    const val INPUT_NAME_MIN = 4

    fun checkNotEmpty(
        errorText: String?,
        data: MutableLiveData<String>,
        errorData: MutableLiveData<String>
    ): Boolean {
        return if (data.value != null) {
            if ((data.value?.length ?: 0) < 1) {
                if (errorData.value != errorText) errorData.postValue(errorText)
                false
            } else {
                if (errorData.value != null) errorData.postValue(null)
                true
            }
        } else {
            if (errorData.value != null) errorData.postValue(null)
            false
        }
    }

    fun checkMinimumLengthError(
        errorText: String?,
        data: MutableLiveData<String>,
        errorData: MutableLiveData<String>,
        minLength: Int
    ): Boolean {
        return if (!data.value.isNullOrEmpty()) {
            if ((data.value?.length ?: 0) < minLength) {
                if (errorData.value != errorText) errorData.postValue(errorText)
                false
            } else {
                if (errorData.value != null) errorData.postValue(null)
                true
            }
        } else {
            if (errorData.value != null) errorData.postValue(null)
            false
        }
    }

    fun checkRangeLengthError(
        errorText: String?,
        data: MutableLiveData<String>,
        errorData: MutableLiveData<String>,
        minLength: Int,
        maxLength: Int
    ): Boolean {
        return if (!data.value.isNullOrEmpty()) {
            if ((data.value?.length ?: 0) < minLength || (data.value?.length ?: 0) > maxLength) {
                if (errorData.value != errorText) errorData.postValue(errorText)
                false
            } else {
                if (errorData.value != null) errorData.postValue(null)
                true
            }
        } else {
            if (errorData.value != null) errorData.postValue(null)
            false
        }
    }

    fun checkPasswordCreation(
        errorTextLength: String?,
        errorTextFormat: String?,
        password: MutableLiveData<String>,
        passwordError: MutableLiveData<String>
    ): Boolean {
        return if (!password.value.isNullOrEmpty()) {
            if ((password.value?.length ?: 0) < INPUT_PASSWORD_MIN) {
                if (passwordError.value != errorTextLength) passwordError.postValue(errorTextLength)
                false
            } else {
                val pattern = Pattern.compile("^.*[a-zA-Z]+.*.*[0-9].*$")
                val matcher = pattern.matcher(password.value ?: "")
                if (matcher.matches()) {
                    if (passwordError.value != null) passwordError.postValue(null)
                    true
                } else {
                    if (passwordError.value != errorTextFormat) passwordError.postValue(
                        errorTextFormat
                    )
                    false
                }
            }
        } else {
            if (passwordError.value != null) passwordError.postValue(null)
            false
        }
    }

    fun checkConfirmPassword(
        errorText: String?,
        password: MutableLiveData<String>,
        confirmPassword: MutableLiveData<String>,
        confirmPasswordError: MutableLiveData<String>
    ): Boolean {
        return if (!confirmPassword.value.isNullOrEmpty()) {
            if (password.value != confirmPassword.value) {
                if (confirmPasswordError.value != errorText) confirmPasswordError.postValue(
                    errorText
                )
                false
            } else {
                if (confirmPasswordError.value != null) confirmPasswordError.postValue(null)
                true
            }
        } else {
            if (confirmPasswordError.value != null) confirmPasswordError.postValue(null)
            false
        }
    }

}