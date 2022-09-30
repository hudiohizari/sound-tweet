package id.hizari.data.repository

import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.User
import id.hizari.domain.repository.UserRepository
import kotlin.random.Random

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class UserRepositoryImpl: UserRepository, SafeApiRequest() {

    override suspend fun searchUser(query: String?): MutableList<User> {
        return mutableListOf(
            User(
                0,
                "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cGVyc29ufGVufDB8fDB8fA%3D%3D&w=1000&q=80",
                "Martha Craig",
                "@craig_love",
                Random.nextBoolean()
            ),
            User(
                1,
                "https://divineyouwellness.com/blog/wp-content/uploads/2021/03/shutterstock_563564683-scaled.jpg",
                "Joshua Brown",
                "@jbrown",
                Random.nextBoolean()
            ),
            User(
                2,
                "https://vocasia.id/blog/wp-content/uploads/2021/09/pemimpin-otoriter-2.webp",
                "Komol Kuchkarov",
                "@kkuchkarov",
                Random.nextBoolean()
            ),
            User(
                3,
                "https://www.dmarge.com/wp-content/uploads/2021/01/dwayne-the-rock-.jpg",
                "Adam West",
                "@awest",
                Random.nextBoolean()
            )
        ).filter { it.name?.lowercase()?.contains(query?.lowercase() ?: "") == true }.toMutableList()
    }

}