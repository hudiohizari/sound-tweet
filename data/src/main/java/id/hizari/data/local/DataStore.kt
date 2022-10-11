package id.hizari.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
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
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_USERNAME = stringPreferencesKey("user_userName")
        private val USER_BIO = stringPreferencesKey("user_bio")
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
            preferences[USER_ID] = user?.id ?: -1
            preferences[USER_NAME] = user?.name ?: ""
            preferences[USER_USERNAME] = user?.userName ?: ""
            preferences[USER_BIO] = user?.bio ?: ""
        }
    }

    fun getLoggedInUser() : Flow<User?> {
        return context.dataStore.data.map { preferences ->
            User(
                preferences[USER_ID],
                preferences[USER_NAME],
                preferences[USER_USERNAME],
                preferences[USER_BIO],
                false
            )
        }.catch { e ->
            STLog.e("Error = ${e.message}")
        }
    }

}