package cz.zcu.students.lostandfound.common.datastore

import cz.zcu.students.lostandfound.features.settings.domain.model.LanguageOptions
import cz.zcu.students.lostandfound.features.settings.domain.model.ThemeOptions
import kotlinx.serialization.Serializable

/**
 * Class representing serialized app settings.
 *
 * @property theme app theme.
 * @property language app language.
 */
@Serializable
data class AppSettings(
    val theme: ThemeOptions = ThemeOptions.SYSTEM,
    val language: LanguageOptions = LanguageOptions.ENGLISH
)
