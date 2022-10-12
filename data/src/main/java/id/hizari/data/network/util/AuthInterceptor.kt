package id.hizari.data.network.util

import id.hizari.common.util.STLog
import id.hizari.data.local.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: DataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().also { STLog.d("[1] $it") }

        val username = runBlocking {
            dataStore.getLoggedInUser().first()?.userName?.removePrefix("@")
        }.also { STLog.d("[2] $req $it") }

        return chain.proceedWithToken(req, username)
    }

    private fun Interceptor.Chain.proceedWithToken(req: Request, username: String?): Response =
        req.newBuilder()
            .apply { if (!username.isNullOrEmpty() && req.header("username") == null) {
                addHeader("username", username)
            } }
            .build()
            .let(::proceed)
}