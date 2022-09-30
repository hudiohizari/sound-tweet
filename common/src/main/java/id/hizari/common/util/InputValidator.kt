package id.hizari.common.util

import androidx.lifecycle.MutableLiveData
import java.util.regex.Pattern

object InputValidator {

    const val INPUT_PASSWORD_MIN = 8
    const val INPUT_NAME_MIN = 4

    fun checkNotEmpty(
        errorText: String?,
        data: MutableLiveData<String?>,
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

    fun checkEmail(
        errorText: String?,
        email: MutableLiveData<String?>,
        errorData: MutableLiveData<String>
    ): Boolean {
        return if (!email.value.isNullOrEmpty()) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.value ?: "").matches()) {
                if (errorData.value != null) errorData.value = null
                true
            } else {
                if (errorData.value != errorText) errorData.postValue(errorText)
                false
            }
        } else {
            if (errorData.value != null) errorData.value = null
            false
        }
    }

    fun checkMinimumLengthError(
        errorText: String?,
        data: MutableLiveData<String?>,
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

}