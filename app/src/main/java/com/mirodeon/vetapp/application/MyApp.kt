package com.mirodeon.vetapp.application

import android.app.Application
import com.mirodeon.vetapp.room.db.DosageDatabase

class MyApp : Application() {

    val database: DosageDatabase by lazy { DosageDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}