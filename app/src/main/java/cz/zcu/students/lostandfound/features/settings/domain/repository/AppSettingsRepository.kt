package cz.zcu.students.lostandfound.features.settings.domain.repository

import cz.zcu.students.lostandfound.common.datastore.AppSettings
import cz.zcu.students.lostandfound.features.settings.domain.language.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.themes.ThemeOptions
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    suspend fun changeTheme(themeOption: ThemeOptions)
    suspend fun changeLanguage(languageOption: LanguageOptions)
    suspend fun getAppSettings(): Flow<AppSettings>
}