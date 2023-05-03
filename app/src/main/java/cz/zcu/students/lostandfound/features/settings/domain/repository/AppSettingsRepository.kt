package cz.zcu.students.lostandfound.features.settings.domain.repository

import cz.zcu.students.lostandfound.common.datastore.AppSettings
import cz.zcu.students.lostandfound.features.settings.domain.model.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import kotlinx.coroutines.flow.Flow

/**
 * App settings repository.
 *
 * This repository provides multiple operations like
 * changing app's theme, language or getting the current app settings.
 *
 * @see AppSettings
 * */
interface AppSettingsRepository {
    /**
     * Changes current app's theme.
     *
     * @param themeOption theme option to set.
     */
    suspend fun changeTheme(themeOption: ThemeOptions)

    /**
     * Changes current app's language.
     *
     * @param languageOption language option to set.
     */
    suspend fun changeLanguage(languageOption: LanguageOptions)

    /**
     * Gets current app settings.
     *
     * @return app settings.
     */
    suspend fun getAppSettings(): Flow<AppSettings>
}