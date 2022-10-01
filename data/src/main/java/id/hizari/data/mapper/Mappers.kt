package id.hizari.data.mapper

import id.hizari.data.network.model.dto.TweetDTO
import id.hizari.data.network.model.dto.UserDTO
import id.hizari.domain.model.Tweet
import id.hizari.domain.model.User

/**
 * Sound Tweet - id.hizari.data.mapper
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

fun UserDTO.toDomain(): User {
    return User(
        id,
        nickname,
        username,
        bio,
        userFollower?.map {
            it?.toDomain()
        }?.toMutableList()
    )
}

fun TweetDTO.toDomain(): Tweet {
    return Tweet(caption, createdAt, id, postUrl, updatedAt, userId)
}