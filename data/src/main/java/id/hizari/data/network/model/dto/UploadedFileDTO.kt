package id.hizari.data.network.model.dto

import id.hizari.domain.model.UploadedFile

/**
 * Sound Tweet - id.hizari.data.network.model.dto
 *
 * Created by Hudio Hizari on 12/10/2022.
 * https://github.com/hudiohizari
 *
 */

data class UploadedFileDTO(
    val filename: String?,
    val key: String?,
    val size: Int?,
    val type: String?,
    val url: String?
) {

    fun toDomain(): UploadedFile {
        return UploadedFile(url)
    }

}