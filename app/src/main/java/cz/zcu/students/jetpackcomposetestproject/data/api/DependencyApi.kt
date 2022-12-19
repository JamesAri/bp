package cz.zcu.students.jetpackcomposetestproject.data.api

import android.content.Context
import cz.zcu.students.jetpackcomposetestproject.R
import javax.inject.Inject

class DependencyApi
@Inject constructor(
    private val context: Context
) {
    fun getString(): String {
        return context.getString(R.string.dependency_string)
    }
}