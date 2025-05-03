package com.example.primasaapp_mvil.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    companion object {
        val TOKEN_KEY = stringPreferencesKey("token")
        val USERNAME_KEY = stringPreferencesKey("username")
        val EMAIL_KEY = stringPreferencesKey("email")
        val SALES_CITY_KEY = stringPreferencesKey("Sales_city")
        val NAME_KEY = stringPreferencesKey("name")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun saveSalesCity(sales_city: String){
        context.dataStore.edit { preferences ->
            preferences[SALES_CITY_KEY] = sales_city
        }
    }

    suspend fun saveName(name: String){
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }


    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    val usernameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USERNAME_KEY] ?: "" }

    val emailFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[EMAIL_KEY] ?: "" }

    val salesCityFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[SALES_CITY_KEY] ?: "" }

    val nameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[NAME_KEY] ?: "" }

}
