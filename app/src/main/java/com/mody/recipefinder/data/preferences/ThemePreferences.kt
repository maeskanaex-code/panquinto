package com.mody.recipefinder.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class AppTheme(val key: String, val displayName: String) {
    WARM("warm", "Warm"),
    COOL("cool", "Cool"),
    FOREST("forest", "Forest");

    companion object {
        fun fromKey(key: String?): AppTheme =
            entries.firstOrNull { it.key == key } ?: WARM
    }
}

private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

class ThemePreferences(private val context: Context) {

    private val key = stringPreferencesKey("selected_theme")

    val theme: Flow<AppTheme> = context.themeDataStore.data.map { prefs ->
        AppTheme.fromKey(prefs[key])
    }

    suspend fun setTheme(theme: AppTheme) {
        context.themeDataStore.edit { prefs ->
            prefs[key] = theme.key
        }
    }
}
