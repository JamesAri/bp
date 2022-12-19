package cz.zcu.students.jetpackcomposetestproject.data.repository

import cz.zcu.students.jetpackcomposetestproject.data.api.DependencyApi
import javax.inject.Inject

class DependencyRepository
@Inject constructor(
    private val dependencyApi: DependencyApi,
    private val stringOne: String,
    private val stringTwo: String,
) {
    fun getString(): String = dependencyApi.getString()
    fun getStringOne(): String = stringOne
    fun getStringTwo(): String = stringTwo
}