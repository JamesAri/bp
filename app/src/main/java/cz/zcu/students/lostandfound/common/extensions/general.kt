package cz.zcu.students.lostandfound.common.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

/**
 * Checks whether called object is null.
 *
 * @return `true` if object is `null`, `false` otherwise.
 * @receiver [Any] object.
 */
fun Any?.isNull() = (this == null)

/**
 * Recursively searches for [ComponentActivity].
 *
 * @return Context's [ComponentActivity] activity if it has one, `null` otherwise.
 * @receiver [Context] in which we look for [ComponentActivity].
 */
fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}