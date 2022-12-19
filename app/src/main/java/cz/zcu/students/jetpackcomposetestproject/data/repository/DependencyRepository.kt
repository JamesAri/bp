package cz.zcu.students.jetpackcomposetestproject.data.repository

import android.app.Application
import cz.zcu.students.jetpackcomposetestproject.R
import cz.zcu.students.jetpackcomposetestproject.data.remote.DependencyApi
import cz.zcu.students.jetpackcomposetestproject.domain.repository.MyRepository
import javax.inject.Inject

class DependencyRepository @Inject constructor(
    private val dependencyApi: DependencyApi,
    private val myRepository: MyRepository,
    private val stringOne: String,
    private val stringTwo: String,
    app: Application,
) {

    init {
        val appName = app.getString(R.string.app_name)
        println("Hello from the repository. The app name is $appName")
    }

    fun getString(): String = dependencyApi.getString()
    fun getStringOne(): String = stringOne
    fun getStringTwo(): String = stringTwo
}