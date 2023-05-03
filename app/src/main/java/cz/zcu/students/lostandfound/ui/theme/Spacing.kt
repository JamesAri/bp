package cz.zcu.students.lostandfound.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

//================================================================================================//
//                                    SPACING DEFINITIONS                                         //
//================================================================================================//


data class Spacing(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp,
)

// Spacing() is a factory to create a default value in cases where a
// CompositionLocal is used without a Provider.
val LocalSpacing: ProvidableCompositionLocal<Spacing> = compositionLocalOf { Spacing() }


/**
 * Provides global [MaterialTheme] with our [Spacing].
 */
@Suppress("unused")
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current