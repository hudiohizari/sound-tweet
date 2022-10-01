package id.hizari.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.hizari.common.util.STLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStore @Inject constructor(
    private val context: Context
) {

    companion object {
        const val DATA_STORE_NAME = "SoundTweetDataStore"

        private val IS_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
        private val LOGGED_IN_USERNAME = stringPreferencesKey("loggedInUsername")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATA_STORE_NAME
    )

    suspend fun setIsLoggedIn(boolean: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = boolean
        }
    }

    fun getIsLoggedIn() : Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }.catch { e ->
            STLog.e("Error = ${e.message}")
        }
    }

    suspend fun setLoggedInUsername(string: String?) {
        context.dataStore.edit { preferences ->
            preferences[LOGGED_IN_USERNAME] = string ?: ""
        }
    }

    fun getLoggedInUsername() : Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[LOGGED_IN_USERNAME]
        }.catch { e ->
            STLog.e("Error = ${e.message}")
        }
    }

}