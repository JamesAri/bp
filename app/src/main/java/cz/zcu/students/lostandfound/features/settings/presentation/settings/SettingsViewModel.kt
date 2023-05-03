package cz.zcu.students.lostandfound.features.settings.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.features.settings.domain.model.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import cz.zcu.students.lostandfound.features.settings.domain.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Viewmodel for app settings.
 *
 * @property repo app settings repository [AppSettingsRepository].
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: AppSettingsRepository,
) : ViewModel() {

    /**
     * State of the current theme.
     */
    var themeState by mutableStateOf(ThemeOptions.SYSTEM)
        private set

    /**
     * State of the current language.
     */
    var languageState by mutableStateOf(LanguageOptions.ENGLISH)
        private set

    /**
     * Initializes app settings states.
     */
    init {
        viewModelScope.launch {
            repo.getAppSettings()
                .collect {
                    themeState = it.theme
                    languageState = it.language
                }
        }
    }

    /**
     * Changes current app theme.
     *
     * @param theme theme option to set.
     */
    fun setTheme(theme: ThemeOptions) {
        viewModelScope.launch {
            repo.changeTheme(theme)
        }
    }

    /**
     * Changes current app language.
     *
     * @param language language option to set.
     */
    fun setLanguage(language: LanguageOptions) {
        viewModelScope.launch {
            repo.changeLanguage(language)
        }
    }
}