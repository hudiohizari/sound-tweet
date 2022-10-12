package id.hizari.data.network.service

import id.hizari.common.util.Constant
import id.hizari.data.network.model.dto.UploadedFileDTO
import id.hizari.data.network.util.Client
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Sound Tweet - id.hizari.data.network.service
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

interface FileStackService {

    @POST("S3")
    suspend fun postFile(
        @Body body: RequestBody?,
        @Query("key") key: String = Constant.Key.FILE_STACK_KEY,
    ): Response<UploadedFileDTO>

    companion object {
        operator fun invoke(client: Client): FileStackService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.URL.FILE_STACK_BASE_URL)
                .client(client.provideClient())
                .build()
                .create(FileStackService::class.java)
        }
    }

}