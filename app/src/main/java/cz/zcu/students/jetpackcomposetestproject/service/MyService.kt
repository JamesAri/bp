package cz.zcu.students.jetpackcomposetestproject.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import cz.zcu.students.jetpackcomposetestproject.domain.repository.MyRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Won't work

//@AndroidEntryPoint
//class MyService @Inject constructor(
//    private val repository: MyRepository
//) : Service() {
//
//    override fun onBind(intent: Intent?): IBinder? {
//        TODO("Not yet implemented")
//    }
//}


// Using field injection

@AndroidEntryPoint
class MyService: Service() {

    @Inject
    lateinit var repository: MyRepository // must be public

    override fun onCreate() {
        super.onCreate()
//        repository.doNetworkCall()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}