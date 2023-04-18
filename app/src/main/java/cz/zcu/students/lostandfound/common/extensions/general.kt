package cz.zcu.students.lostandfound.common.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun Any?.isNull() = (this == null)

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}