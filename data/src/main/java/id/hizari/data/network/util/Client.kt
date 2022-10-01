package id.hizari.data.network.util

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Client @Inject constructor(
    private val authInterceptor: AuthInterceptor,
    private val chuckerInterceptor: ChuckerInterceptor
) {

    fun provideClient(): OkHttpClient {
        val interceptorLogging = HttpLoggingInterceptor()
        interceptorLogging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptorLogging)
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}