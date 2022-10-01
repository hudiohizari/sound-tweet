package id.hizari.data.network.model.dto


import com.google.gson.annotations.SerializedName

data class TweetDTO(
    val caption: String?,
    val createdAt: String?,
    val id: Int?,
    val postUrl: String?,
    val updatedAt: String?,
    val userId: Int?
)