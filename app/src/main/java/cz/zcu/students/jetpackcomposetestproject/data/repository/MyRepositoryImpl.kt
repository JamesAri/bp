package cz.zcu.students.jetpackcomposetestproject.data.repository

import android.app.Application
import cz.zcu.students.jetpackcomposetestproject.data.remote.DependencyApi
import cz.zcu.students.jetpackcomposetestproject.domain.repository.MyRepository
import javax.inject.Inject

// With @Inject we don't need to declare @Provides in module

class MyRepositoryImpl @Inject constructor(
    private val dependencyApi: DependencyApi,
    private val context: Application,
) : MyRepository {

    override suspend fun doNetworkCall() {
        // api call or something
    }

}