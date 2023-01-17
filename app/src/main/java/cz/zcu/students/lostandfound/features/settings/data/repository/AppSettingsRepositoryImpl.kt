package cz.zcu.students.lostandfound.features.settings.data.repository

import androidx.datastore.core.DataStore
import cz.zcu.students.lostandfound.common.datastore.AppSettings
import cz.zcu.students.lostandfound.features.settings.domain.language.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.repository.AppSettingsRepository
import cz.zcu.students.lostandfound.features.settings.domain.themes.ThemeOptions
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

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