package cz.zcu.students.lostandfound

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Marking the Application class where the Dagger components should be generated. */
@HiltAndroidApp
class LostAndFoundApp : Application()