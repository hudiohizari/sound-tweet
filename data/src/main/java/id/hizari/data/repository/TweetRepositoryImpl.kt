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
                "29/09/2022 15:54",
                "You can only bring one item to a remote island to assist your research of native use of tools and usability. What do you bring?",
                "",
                Random.nextInt(10, 1000),
                Random.nextLong(10, 999999),
                Random.nextLong(100, 999999),
                Random.nextLong(1000, 999999),
                mutableListOf("John Cena" , "Kieron Dotson"),
            ),
            Tweet(
                1,
                "",
                "Joshua Brown",
                "@jbrown",
                "29/09/2022 12:41",
                "Y’all ready?",
                "",
                Random.nextInt(10, 1000),
                Random.nextLong(10, 999999),
                Random.nextLong(100, 999999),
                Random.nextLong(1000, 999999),
                mutableListOf("Zack John"),
            ),
            Tweet(
                1,
                "",
                "Komol Kuchkarov",
                "@kkuchkarov",
                "29/09/2022 10:12",
                "When we first launched Vector Mockups, I had wrote that in 2018 our free product downloads was 28K+ and we had set a goal to double this figure by the end of 2019.  Today our team and I are glad to announce tgat we aave easily hit our goals with 47k+ downloads in 2019.",
                "",
                Random.nextInt(10, 1000),
                Random.nextLong(10, 999999),
                Random.nextLong(100, 999999),
                Random.nextLong(1000, 999999),
                mutableListOf("John Cena"),
            )
        )
    }

}