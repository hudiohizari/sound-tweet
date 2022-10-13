package id.hizari.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import id.hizari.common.util.STLog
import id.hizari.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStore @Inject constructor(
    private val context: Context
) {

    companion object {
        const val DATA_STORE_NAME = "SoundTweetDataStore"

        private val USER_ID = longPreferencesKey("user_id")
        private val USER_IMG_URL = stringPreferencesKey("img_url")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_USERNAME = stringPreferencesKey("user_userName")
        private val USER_BIO = stringPreferencesKey("user_bio")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PASSWORD = stringPreferencesKey("user_password")
        private val USER_JOINED = stringPreferencesKey("user_joined")
        private val USER_FOLLOWER = stringPreferencesKey("user_followed")
        private val USER_FOLLOWING = stringPreferencesKey("user_following")
        private val USER_FOLLOWING_USERNAME = stringPreferencesKey("user_following_username")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    fun getIsLoggedIn() : Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID] != null
        }.catch { e ->
            STLog.e("Error = ${e.message}")
        }
    }

    suspend fun setLoggedInUser(user: User?) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user?.id ?: -1L
            preferences[USER_IMG_URL] = user?.imgUrl ?: ""
            preferences[USER_NAME] = user?.name ?: ""
            preferences[USER_USERNAME] = user?.username ?: ""
            preferences[USER_BIO] = user?.bio ?: ""
            preferences[USER_EMAIL] = user?.email ?: ""
            preferences[USER_PASSWORD] = user?.password ?: ""
            preferences[USER_JOINED] = user?.joined ?: ""
            preferences[USER_FOLLOWER] = user?.follower ?: ""
            preferences[USER_FOLLOWING]  = user?.following ?: ""
            preferences[USER_FOLLOWING_USERNAME] = Gson().toJson(user?.userFollowingUsername)
        }
    }

    fun getLoggedInUser() : Flow<User?> {
        return context.dataStore.data.map { preferences ->
            @Suppress("UNCHECKED_CAST")
            User(
                preferences[USER_ID],
                preferences[USER_IMG_URL],
                preferences[USER_NAME],
                preferences[USER_USERNAME],
                preferences[USER_BIO],
                preferences[USER_EMAIL],
                preferences[USER_PASSWORD],
                preferences[USER_JOINED],
                false,
                preferences[USER_FOLLOWER],
                preferences[USER_FOLLOWING],
                preferences[USER_FOLLOWING_USERNAME]?.let {
                    Gson().fromJson<MutableList<String?>>(it, MutableList::class.java)
                } ?: mutableListOf()
            )
        }.catch { e ->
            STLog.e("Error = ${e.message}")
        }
    }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}