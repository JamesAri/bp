package cz.zcu.students.jetpackcomposetestproject.domain.repository

interface MyRepository {
    suspend fun doNetworkCall()
}