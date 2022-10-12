package id.hizari.domain.usecase.filestack

import id.hizari.common.util.Resources
import id.hizari.domain.model.UploadedFile
import id.hizari.domain.repository.FileStackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.random.Random

/**
 * Sound Tweet - id.hizari.domain.usecase.filestack
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

class PostFileUseCase(
    private val fileStackRepository: FileStackRepository
) {

    operator fun invoke(file: File?): Flow<Resources<UploadedFile?>> = flow {
        emit(Resources.Loading())
        try {
            val response = fileStackRepository.postFile(file)
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error(e))
        }
    }
}