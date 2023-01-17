package cz.zcu.students.lostandfound.features.settings.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.zcu.students.lostandfound.features.settings.domain.language.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.repository.AppSettingsRepository
import cz.zcu.students.lostandfound.features.settings.domain.themes.ThemeOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: AppSettingsRepository,
) : ViewModel() {

    var themeState by mutableStateOf(ThemeOptions.SYSTEM)
        private set

    init {
        viewModelScope.launch {
            repo.getAppSettings()
                .collect {
                    themeState = it.theme
                }
        }
    }

    fun setTheme(theme: ThemeOptions) {
        viewModelScope.launch {
            repo.changeTheme(theme)
        }
    }

    fun setLanguage(language: LanguageOptions) {
        viewModelScope.launch {
            repo.changeLanguage(language)
        }
    }
}