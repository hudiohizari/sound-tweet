package id.hizari.data.repository

import id.hizari.data.network.service.FileStackService
import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.UploadedFile
import id.hizari.domain.repository.FileStackRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

class FileStackRepositoryImpl @Inject constructor(
    private val fileStackService: FileStackService
): FileStackRepository, SafeApiRequest() {

    override suspend fun postFile(file: File?): UploadedFile? {
        val body = file?.asRequestBody("audio/mpeg".toMediaTypeOrNull())
        val response = apiRequest { fileStackService.postFile(body) }
        return response?.toDomain()
    }

}