package id.hizari.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Sound Tweet - id.hizari.domain.model
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

@Parcelize
data class Notification(
    val id: Long?,
    val caption: String?,
    val user: User?
): Parcelable