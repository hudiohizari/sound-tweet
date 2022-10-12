package id.hizari.domain.repository

import id.hizari.domain.model.UploadedFile
import java.io.File

/**
 * Sound Tweet - id.hizari.domain.repository
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

interface FileStackRepository {

    suspend fun postFile(file: File?): UploadedFile?

}