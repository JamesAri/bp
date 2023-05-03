package cz.zcu.students.lostandfound.features.settings.data.repository

import androidx.datastore.core.DataStore
import cz.zcu.students.lostandfound.common.datastore.AppSettings
import cz.zcu.students.lostandfound.features.settings.domain.model.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import cz.zcu.students.lostandfound.features.settings.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of app settings repository [AppSettingsRepository].
 *
 * This repository takes advantage of
 * [Proto DataStore](https://developer.android.com/topic/libraries/architecture/datastore).
 *
 * @property datastore reference to Proto [DataStore].
 */
@Singleton
class AppSettingsRepositoryImpl @Inject constructor(
    private val datastore: DataStore<AppSettings>
) : AppSettingsRepository {

    override suspend fun changeTheme(themeOption: ThemeOptions) {
        datastore.updateData {
            it.copy(
                theme = themeOption,
            )
        }
    }

    override suspend fun changeLanguage(languageOption: LanguageOptions) {
        datastore.updateData {
            it.copy(
                language = languageOption,
            )
        }
    }

    override suspend fun getAppSettings(): Flow<AppSettings> {
        return datastore.data
    }
}