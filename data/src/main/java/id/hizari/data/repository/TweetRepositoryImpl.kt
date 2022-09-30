package id.hizari.data.repository

import id.hizari.data.network.util.SafeApiRequest
import id.hizari.domain.model.Tweet
import id.hizari.domain.repository.TweetRepository
import kotlin.random.Random

/**
 * Sound Tweet - id.hizari.data.repository
 *
 * Created by Hudio Hizari on 29/09/2022.
 * https://github.com/hudiohizari
 *
 */

class TweetRepositoryImpl: TweetRepository, SafeApiRequest() {

    override suspend fun getTweets(): MutableList<Tweet> {
        return mutableListOf(
            Tweet(
                0,
                "",
                "Martha Craig",
                "@craig_love",
                "2022-09-30 15:54:00",
                "You can only bring one item to a remote island to assist your research of native use of tools and usability. What do you bring?",
                "",
                Random.nextInt(10, 1000),
                Random.nextBoolean(),
                Random.nextLong(10, 99999),
                Random.nextLong(100, 9999),
                Random.nextLong(1000, 999999),
                mutableListOf("John Cena" , "Kieron Dotson")
            ),
            Tweet(
                1,
                "",
                "Joshua Brown",
                "@jbrown",
                "2022-09-30 12:41:00",
                "Y’all ready?",
                "",
                Random.nextInt(10, 1000),
                Random.nextBoolean(),
                Random.nextLong(10, 99999),
                Random.nextLong(100, 9999),
                Random.nextLong(1000, 999999),
                mutableListOf("Zack John")
            ),
            Tweet(
                2,
                "",
                "Komol Kuchkarov",
                "@kkuchkarov",
                "2022-09-30 10:12:00",
                "When we first launched Vector Mockups, I had wrote that in 2018 our free product downloads was 28K+ and we had set a goal to double this figure by the end of 2019.  Today our team and I are glad to announce tgat we aave easily hit our goals with 47k+ downloads in 2019.",
                "",
                Random.nextInt(10, 1000),
                Random.nextBoolean(),
                Random.nextLong(10, 99999),
                Random.nextLong(100, 9999),
                Random.nextLong(1000, 999999),
                null
            ),
            Tweet(
                3,
                "",
                "Adam West",
                "@awest",
                "2022-09-27 8:12:00",
                "Having issue with running a business",
                "",
                Random.nextInt(10, 1000),
                Random.nextBoolean(),
                Random.nextLong(10, 99999),
                Random.nextLong(100, 9999),
                Random.nextLong(1000, 999999),
                mutableListOf("John Cena", "Kieron Dotson", "asdkasd", "Asdasda", "dasdada")
            )
        )
    }

}