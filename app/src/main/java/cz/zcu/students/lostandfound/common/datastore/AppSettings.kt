package cz.zcu.students.lostandfound.common.datastore

import cz.zcu.students.lostandfound.features.settings.domain.language.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.themes.ThemeOptions
import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val theme: ThemeOptions = ThemeOptions.SYSTEM,
    val language: LanguageOptions = LanguageOptions.ENGLISH
)
