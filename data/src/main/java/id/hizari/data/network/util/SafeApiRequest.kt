package id.hizari.data.network.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.hizari.data.R
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

abstract class SafeApiRequest {

    @Inject
    @ApplicationContext
    protected lateinit var context: Context

    suspend fun <T: Any> apiRequest(
        call: suspend () -> Response<T>
    ): T? {
        val response = call.invoke()
        return checkForError(response)
    }

    private fun <T> checkForError(response: Response<T>): T? {
        if (response.isSuccessful) {
            return response.body()
        } else {
            @Suppress("BlockingMethodInNonBlockingContext")
            val error = response.errorBody()?.string()
            val code = response.code()
            var apiCode = -1
            var message = ""

            if (code >= 500) {
                message = context.getString(R.string.server_down)
            } else {
                error?.let {
                    try {
                        apiCode = JSONObject(it)
                            .getInt("code")
                        message = JSONObject(it)
                            .getString("message")
                    } catch (e: JSONException) {
                        message = try {
                            it
                        } catch (e: Exception) {
                            "Error: ${response.message()}"
                        }
                    }
                }
            }

            throw ApiException(
                if (apiCode != -1) apiCode else code,
                message
            )
        }
    }

}